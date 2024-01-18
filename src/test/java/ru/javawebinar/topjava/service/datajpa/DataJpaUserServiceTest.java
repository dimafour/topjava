package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(value = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Test
    public void getUserWithMeals(){
        User user = service.getUserWithMeals(ADMIN_ID);
        USER_MEAL_MATCHER.assertMatch(user, admin);
    }

    @Test
    public void getUserWithEmptyMeals(){
        User user = service.getUserWithMeals(GUEST_ID);
        USER_MATCHER.assertMatch(user, guest);
    }
}
