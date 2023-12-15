DELETE FROM user_role;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories)
VALUES (100000, '13.12.2023 11:00', 'User meal #1', 800),
       (100000, '13.12.2023 12:00', 'User meal #2', 200),
       (100000, '14.12.2023 20:00', 'User meal #3', 300),
       (100001, '14.12.2023 20:00', 'Admin meal', 1000),
       (100000, '13.12.2023 2:00', 'User meal #4', 1400),
       (100000, '11.12.2023 15:00', 'User meal #5', 1500);
