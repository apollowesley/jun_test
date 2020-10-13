DROP TABLE IF EXISTS `iartisan_admin_template`.`system_role`;
CREATE TABLE `iartisan_admin_template`.`system_role` (
  `ROLE_ID` varchar(48) NOT NULL COMMENT '主键id',
  `ROLE_NAME` varchar(50) NOT NULL COMMENT '角色名称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

INSERT INTO `iartisan_admin_template`.`system_role` (ROLE_ID, ROLE_NAME, CREATE_TIME) VALUES ('1', 'admin', now());