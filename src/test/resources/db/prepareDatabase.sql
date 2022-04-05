DROP TABLE IF EXISTS words;
DROP TABLE IF EXISTS sections;
DROP TABLE IF EXISTS dictionaries;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id               SERIAL PRIMARY KEY,
    user_name        VARCHAR NOT NULL,
    password         VARCHAR NOT NULL,
    role             VARCHAR NOT NULL
);
CREATE UNIQUE INDEX users_unique_user_name_idx ON users (user_name);

CREATE TABLE dictionaries
(
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER NOT NULL,
    name        VARCHAR NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE sections
(
    id              SERIAL PRIMARY KEY,
    user_id         INTEGER NOT NULL,
    dictionary_id   INTEGER NOT NULL,
    name            VARCHAR NOT NULL,
    FOREIGN KEY (dictionary_id) REFERENCES dictionaries (id) ON DELETE CASCADE
);

CREATE TABLE words
(
    id              SERIAL PRIMARY KEY,
    user_id         INTEGER NOT NULL,
    section_id      INTEGER NOT NULL,
    dictionary_id   INTEGER NOT NULL,
    word            VARCHAR NOT NULL,
    translation     VARCHAR NOT NULL,
    FOREIGN KEY (section_id) REFERENCES sections (id) ON DELETE CASCADE
);

INSERT INTO users (user_name, password, role)
VALUES ('Admin', '0E012C5187EF01131BDC4B46DB6198FC', 'ADMIN'),
       ('DemoUser', '0E012C5187EF01131BDC4B46DB6198FC', 'USER'),
       ('Test002', '0E012C5187EF01131BDC4B46DB6198FC', 'USER');

INSERT INTO dictionaries (user_id, name)
VALUES (1, 'ADMIN_DICT_1'),
       (1, 'ADMIN_DICT_2'),
       (2, 'USER_1_DICT_1'),
       (2, 'USER_1_DICT_2'),
       (2, 'USER_1_DICT_3'),
       (3, 'USER_2_DICT_1'),
       (3, 'USER_2_DICT_2');

INSERT INTO sections (user_id, dictionary_id, name)
VALUES (1, 1, 'ADMIN_SECT_1'),
       (1, 1, 'ADMIN_SECT_2'),
       (1, 2, 'ADMIN_SECT_3'),
       (1, 2, 'ADMIN_SECT_4'),
       (2, 3, 'USER_1_SECT_1'),
       (2, 3, 'USER_1_SECT_2');

INSERT INTO words (user_id, section_id, dictionary_id, word, translation)
VALUES (1, 1, 1, 'Hello', 'Привет'),
       (1, 1, 1, 'Get', 'Получать'),
       (1, 1, 1, 'Give', 'Давать'),
       (1, 2, 1, 'Move', 'Двигаться'),
       (1, 2, 1, 'Run', 'Бежать'),
       (1, 3, 2, 'Ask', 'Задавать'),
       (1, 3, 2, 'Fill', 'Заполнять'),
       (1, 3, 2, 'Say', 'Сказать'),
       (1, 3, 2, 'Add', 'Добавить');