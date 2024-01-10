package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

@ActiveProfiles(value = "jpa", resolver = ActiveDbProfileResolver.class)
public class JpaUserTest extends UserServiceTest {
}
