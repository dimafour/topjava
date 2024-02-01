package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password,
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id;
                   DELETE FROM user_role WHERE user_id=:id;
                """, parameterSource) == 0) {
            return null;
        }
        List<Role> roles = new ArrayList<>(user.getRoles());
        jdbcTemplate.batchUpdate("""
                        INSERT INTO user_role (user_id, role) VALUES (?, ?);
                        """,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, user.id());
                        ps.setString(2, roles.get(i).toString());
                    }

                    @Override
                    public int getBatchSize() {
                        return roles.size();
                    }
                });
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        return getOneUserWithRoles(jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id));
    }

    @Override
    public User getByEmail(String email) {
        ValidationUtil.validate(email);
        return getOneUserWithRoles(jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email));
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_role ur on users.id = ur.user_id ORDER BY name, email",
                rs -> {
                    Map<User, EnumSet<Role>> usersRoles = new LinkedHashMap<>();
                    int prevId = -1;
                    User user = null;
                    while (rs.next()) {
                        int curId = rs.getInt("id");
                        user = curId == prevId ? user : ROW_MAPPER.mapRow(rs, rs.getRow());
                        prevId = curId;
                        usersRoles.putIfAbsent(user, EnumSet.noneOf(Role.class));
                        String role = rs.getString("role");
                        if (role != null) {
                            usersRoles.get(user).add(Role.valueOf(role));
                        }
                    }
                    usersRoles.forEach((u, r) -> u.setRoles(usersRoles.get(u).isEmpty() ? EnumSet.noneOf(Role.class) : r));
                    return new ArrayList<>(usersRoles.keySet());
                });
    }

    private User getOneUserWithRoles(List<User> users) {
        User user = DataAccessUtils.singleResult(users);
        if (user == null) {
            return null;
        }
        user.setRoles(jdbcTemplate.query("SELECT * FROM user_role WHERE user_id=?", rs -> {
            Set<Role> roles = new HashSet<>();
            while (rs.next()) {
                roles.add(Role.valueOf(rs.getString("role")));
            }
            return roles;
        }, user.id()));
        return user;
    }
}
