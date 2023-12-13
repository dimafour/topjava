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

INSERT INTO meals (user_id, date, description, calories)
VALUES  (100000, '13.12.2023 11:00', 'User 10000 meal', 100),
        (100001, '13.12.2023 12:00',  'User 10001 meal', 200),
        (100001, '13.12.2023 20:00',  'User 10001 meal', 300),
        (100000, '13.12.2023 20:00',  'User 10000 meal', 400);
