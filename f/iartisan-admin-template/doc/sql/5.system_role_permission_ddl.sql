DROP TABLE IF EXISTS `iartisan_admin_template`.`system_role_permission`;
CREATE TABLE `iartisan_admin_template`.`system_role_permission` (
  `ROLE_ID` varchar(50) NOT NULL COMMENT '角色ID',
  `PERMISSION_ID` varchar(50) NOT NULL COMMENT '资源id',
  `PERMISSION_TYPE` varchar(1) NOT NULL COMMENT 'm:菜单 r:resource',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '1', 'm', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '2', 'm', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '3', 'm', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '4', 'm', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '1', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '2', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '3', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '4', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '5', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '6', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '7', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '8', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '9', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '10', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '11', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '12', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '13', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '14', 'r', null);
INSERT INTO `iartisan_admin_template`.`system_role_permission` (ROLE_ID, PERMISSION_ID, PERMISSION_TYPE, CREATE_TIME) VALUES ('1', '15', 'r', null);