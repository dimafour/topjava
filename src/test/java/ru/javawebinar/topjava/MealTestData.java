package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int USER_MEAL1_ID = START_SEQ + 3;
    public static final int USER_MEAL2_ID = START_SEQ + 4;
    public static final int USER_MEAL3_ID = START_SEQ + 5;
    public static final int ADMIN_MEAL_ID = START_SEQ + 6;
    public static final LocalDate DATE_FROM = LocalDate.of(2023, 12, 10);
    public static final LocalDate DATE_TO = LocalDate.of(2023, 12, 13);

    public static final Meal USER_MEAL1 = new Meal(USER_MEAL1_ID, LocalDateTime.of(
            2023, 12, 13, 11, 0), "User meal #1", 100);
    public static final Meal USER_MEAL2 = new Meal(USER_MEAL2_ID, LocalDateTime.of(
            2023, 12, 13, 12, 0), "User meal #2", 200);
    public static final Meal USER_MEAL3 = new Meal(USER_MEAL3_ID, LocalDateTime.of(
            2023, 12, 14, 20, 0), "User meal #3", 300);
    public static final Meal ADMIN_MEAL = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(
            2023, 12, 14, 20, 0), "Admin meal #1", 1000);

    public static List<Meal> userMealList = Arrays.asList(USER_MEAL3, USER_MEAL1, USER_MEAL2);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(
                2023, 12, 14, 22, 0), "New Meal", 666);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(USER_MEAL1);
        updated.setDateTime(LocalDateTime.of(2023, 11, 15, 2, 0));
        updated.setDescription("Updated Meal");
        updated.setCalories(1234);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
