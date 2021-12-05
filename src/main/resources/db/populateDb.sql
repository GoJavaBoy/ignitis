DELETE FROM user_roles;
DELETE FROM posts;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (email, password)
VALUES ('user@yandex.ru', '{noop}password'),
       ('admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

INSERT INTO posts (user_id, title, text)
VALUES (100000, "User 1 Post_1", "Text for post 1"),
       (100000, "User 1 Post_2", "Text for post 2"),
       (100000, "User 1 Post_3", "Text for post 3"),
       (100001, "User 2 Post_1", "Text for post 1"),
       (100001, "User 2 Post_2", "Text for post 2");