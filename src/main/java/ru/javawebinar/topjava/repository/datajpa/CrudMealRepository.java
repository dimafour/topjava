package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=?1 AND m.user.id=?2")
    int delete(int id, int userId);

    Optional<Meal> findByIdAndUserId(Integer id, Integer userId);

    List<Meal> getMealsByUserIdOrderByDateTimeDesc(Integer userId);

    Meal getFirstByUserId(Integer userId);

    @Transactional
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >= :start " +
            "AND m.dateTime < :end ORDER BY m.dateTime DESC")
    List<Meal> getBetweenHalfOpen(@Param("start") LocalDateTime startDateTime,
                                  @Param("end") LocalDateTime endDateTime,
                                  @Param("userId") int userId);

}
