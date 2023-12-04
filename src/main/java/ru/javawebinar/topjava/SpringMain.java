package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);

            adminUserController.create(new User(null, "admin", "admin@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "user", "user@mail.ru", "user", Role.USER));
            System.out.println("UserList: ");
            adminUserController.getAll().forEach(System.out::println);

            System.out.println("Get All meals: ");
            mealRestController.getAll().forEach(System.out::println);

            mealRestController.create(new Meal(LocalDateTime.of(2023, 12, 3, 4, 0), "Porridge", 666, 2));
            mealRestController.update(new Meal(LocalDateTime.of(2023, 12, 3, 10, 0), "Dinner", 1555, 2), 6);
            System.out.println("Get All meals after update: ");
            mealRestController.getAll().forEach(System.out::println);

            System.out.println("Get All today meals from [4 to 10) am: ");
            mealRestController.getAll(LocalDate.now(), LocalDate.now(), LocalTime.of(4, 0), LocalTime.of(10, 0)).forEach(System.out::println);

        }
    }
}
