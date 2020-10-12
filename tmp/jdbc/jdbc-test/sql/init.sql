DROP TABLE IF EXISTS `t_lesson`;
CREATE TABLE `t_lesson`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       varchar(255) DEFAULT NULL COMMENT '课程标题',
    `teacher`     varchar(255) DEFAULT NULL COMMENT '课程老师',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `test`.`t_lesson`(`id`, `title`, `teacher`, `create_time`, `update_time`)
VALUES (1, '《廖师兄带你装逼带你飞》', '廖师兄', '2020-01-01 15:51:31', '2020-01-06 15:51:57');