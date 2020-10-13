DROP TABLE IF EXISTS `iartisan_admin_template`.`system_user`;
CREATE TABLE `iartisan_admin_template`.`system_user` (
  `USER_ID` varchar(48) NOT NULL COMMENT '主键',
  `USER_NAME` varchar(50) NOT NULL COMMENT '用户名',
  `USER_PWD` varchar(50) NOT NULL COMMENT '密码',
  `STATUS` varchar(1) NOT NULL DEFAULT '0' COMMENT '员工状态',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

INSERT INTO `iartisan_admin_template`.`system_user` (USER_ID, USER_NAME, USER_PWD, STATUS, CREATE_TIME) VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'E', now());