--
-- 由SQLiteStudio v3.1.1 产生的文件 周五 十二月 22 18:14:26 2017
--
-- 文本编码：UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- 表：t_user
DROP TABLE IF EXISTS t_user;

CREATE TABLE t_user (
    id         INTEGER         PRIMARY KEY AUTOINCREMENT,
    username   VARCHAR (50),
    state      INTEGER,
    isdel      BOOLEAN,
    remark     TEXT (255),
    add_time   DATETIME,
    money      DECIMAL (10, 2),
    left_money DOUBLE
);

INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (1, '张三', 1, 0, '', 1513933928276, 100, 20);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (2, '张三', 1, 0, '', '2017-12-22 15:26:24', 100.5, 20.3);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (3, '王五', 0, 0, '批量修改备注', 1513927584000, 100.7, 20.299999237060547);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (4, '王五', 1, 0, '', '2017-12-22 15:26:24', 100.5, 20.3);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (5, '王五', 1, 0, '', '2017-12-22 15:26:24', 100.5, 20.3);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (6, '王五', 1, 0, '', '2017-12-22 15:26:24', 100.5, 20.3);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (7, '王五', 1, 0, '', '2017-12-22 15:26:24', 100.5, 20.3);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (8, '王五', 1, 0, '', '2017-12-22 15:26:24', 100.5, 20.3);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (9, '王五', 1, 0, '', '2017-12-22 15:26:24', 100.5, 20.3);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (10, '王五', 1, 0, '', '2017-12-22 15:26:24', 100.5, 20.3);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (11, '张三', 1, 0, '备注', 1513933858051, 100.5, 22.100000381469727);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (12, '张三notnull', 1, 1, NULL, 1513933928276, 100.5, NULL);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (13, 'username0', 1, 0, 'remark0', 1513934034530, 0, 200);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (15, 'username2', 1, 0, 'remark2', 1513934034530, 2, 200);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (16, 'username0', 2, 0, 'remark0', 1513934083961, 0, 200);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (17, 'username1', 2, 0, 'remark1', 1513934083961, 1, 200);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (18, 'username2', 2, 0, 'remark2', 1513934083961, 2, 200);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (20, 'usernameWithColumns1', NULL, NULL, NULL, 1513934119204, 1, NULL);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (21, 'usernameWithColumns2', NULL, NULL, NULL, 1513934119204, 2, NULL);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (22, 'jim', NULL, NULL, NULL, '2017-12-22 17:33:07', 100, NULL);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (23, 'jim', NULL, NULL, NULL, '2017-12-22 17:33:07', 100, NULL);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (24, 'jim', NULL, NULL, NULL, '2017-12-22 17:33:07', 100, NULL);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (25, 'jim', NULL, NULL, NULL, '2017-12-22 17:33:07', 100, NULL);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (26, 'usernameWithColumns0', 3, NULL, NULL, 1513935900026, 0, NULL);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (27, 'usernameWithColumns1', 3, NULL, NULL, 1513935900026, 1, NULL);
INSERT INTO t_user (id, username, state, isdel, remark, add_time, money, left_money) VALUES (28, 'usernameWithColumns2', 3, NULL, NULL, 1513935900026, 2, NULL);

-- 表：user_info
DROP TABLE IF EXISTS user_info;

CREATE TABLE user_info (
    id          INTEGER       PRIMARY KEY AUTOINCREMENT,
    user_id     INTEGER,
    city        VARCHAR (50),
    address     VARCHAR (100),
    status      VARCHAR (10),
    create_time DATETIME
);

INSERT INTO user_info (id, user_id, city, address, status, create_time) VALUES (1, 1, '杭州', '西湖', '1', '2017-12-22 17:06:39');
INSERT INTO user_info (id, user_id, city, address, status, create_time) VALUES (2, 2, '杭州', '西湖', '1', '2017-12-22 17:06:39');
INSERT INTO user_info (id, user_id, city, address, status, create_time) VALUES (3, 3, '杭州', '西湖', '1', '2017-12-22 17:06:39');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
