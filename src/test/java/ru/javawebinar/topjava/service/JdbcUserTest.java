package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

@ActiveProfiles(value = "jdbc", resolver = ActiveDbProfileResolver.class)
public class JdbcUserTest extends UserServiceTest {
}
