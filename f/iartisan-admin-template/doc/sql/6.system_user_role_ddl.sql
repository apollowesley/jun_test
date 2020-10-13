DROP TABLE IF EXISTS `iartisan_admin_template`.`system_user_role`;
CREATE TABLE `iartisan_admin_template`.`system_user_role` (
  `USER_ID` varchar(50) NOT NULL COMMENT '用户ID',
  `ROLE_ID` varchar(50) NOT NULL COMMENT '角色ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

INSERT INTO `iartisan_admin_template`.`system_user_role` (USER_ID, ROLE_ID) VALUES ('1', '1');