package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

@ActiveProfiles(value = "datajpa", resolver = ActiveDbProfileResolver.class)
public class DataJpaUserTest extends UserServiceTest {
}
