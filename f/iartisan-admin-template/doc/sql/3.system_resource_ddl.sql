DROP TABLE IF EXISTS `iartisan_admin_template`.`system_resource`;
CREATE TABLE `iartisan_admin_template`.`system_resource` (
  `RESOURCE_ID` varchar(48) NOT NULL COMMENT '主键',
  `RESOURCE_NAME` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `RESOURCE_PERMISSION` varchar(50) NOT NULL COMMENT '资源权限编码 resource:菜单id:资源id',
  `MENU_ID` varchar(50) NOT NULL COMMENT '对应的MENU_ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`RESOURCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源权限表';

INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('1', '添加页面', 'auth:manage:role:addDataPage', '4', null, null);
INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('2', '添加', 'auth:manage:role:addData', '4', null, null);
INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('3', '详情页面', 'auth:manage:role:queryDetailPage', '4', null, null);
INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('4', '删除', 'auth:manage:role:deleteData', '4', null, null);

INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('5', '添加页面', 'auth:manage:menu:addDataPage', '2', null, null);
INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('6', '添加', 'auth:manage:menu:addData', '2', null, null);
INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('7', '修改页面', 'auth:manage:menu:modifyDataPage', '2', null, null);
INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('8', '修改', 'auth:manage:menu:modifyData', '2', null, null);
INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('9', '删除', 'auth:manage:menu:deleteData', '2', null, null);

INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('10', '添加页面', 'auth:manage:user:addDataPage', '3', null, null);
INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('11', '添加', 'auth:manage:user:addData', '3', null, null);
INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('12', '修改页面', 'auth:manage:user:modifyDataPage', '3', null, null);
INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('13', '修改', 'auth:manage:user:modifyData', '3', null, null);
INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('14', '删除', 'auth:manage:user:deleteData', '3', null, null);
INSERT INTO `iartisan_admin_template`.`system_resource` (RESOURCE_ID, RESOURCE_NAME, RESOURCE_PERMISSION, MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('15', '状态切换', 'auth:manage:user:changeStatus', '3', null, null);
