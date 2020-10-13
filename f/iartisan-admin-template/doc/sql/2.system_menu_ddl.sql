DROP TABLE IF EXISTS `iartisan_admin_template`.`system_user`;
CREATE TABLE `iartisan_admin_template`.`system_menu` (
  `MENU_ID` varchar(50) NOT NULL COMMENT 'MENU_ID',
  `MENU_NAME` varchar(100) NOT NULL COMMENT '菜单名称',
  `MENU_PERMISSION` varchar(50) NOT NULL COMMENT '菜单权限编码 menu:一级菜单:二级菜单',
  `MENU_URL` varchar(100) DEFAULT NULL COMMENT '菜单跳转链接',
  `MENU_ICON` varchar(20) DEFAULT NULL COMMENT '菜单icon',
  `PARENT_MENU_ID` varchar(50) DEFAULT NULL COMMENT '上一级菜单id',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('1', '权限管理', 'auth:manage', '', 'layui-icon-set-fill', '', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('2', '菜单管理', 'auth:manage:menu:index', '/menuSupport/index', 'layui-icon-list', '1', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('201806285972', '流程部署', 'activiti:deployment', '/bpm/deployment/index', 'layui-icon-template-1', '201806287855', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('201806287855', '模拟流程', 'activiti', '', 'layui-icon-app', '', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('201907126892', '类人事管理', 'hr:management', '', 'layui-icon-user', '', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('201907176106', '部门管理', 'dept', '/hrDept/index', 'layui-icon-home', '201907126892', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('201907266729', '员工管理', 'hr:Staff', '/hrStaff/index', 'layui-icon-group', '201907126892', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('201908301154', '新建事项', 'new:todo', 'bpm/demo/newItem/index', 'layui-icon-list', '201806287855', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('201908301765', '待办任务', 'todo', 'bpm/demo/todoList', 'layui-icon-face-surprised', '201806287855', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('3', '用户管理', 'auth:manage:user:index', '/userSupport/index', 'layui-icon-friends', '1', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('4', '角色管理', 'auth:manage:role:index', '/roleSupport/index', 'layui-icon-face-smile', '1', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('5', '日志管理', 'auth:log', null, 'layui-icon-engine', null, null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('6', '操作日志', 'auth:log:operation:index', '/log/index', 'layui-icon-survey', '5', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('7', 'Druid监控', 'auth:log:druid', '/druid/sql.html', 'layui-icon-chart', '5', null, null);
INSERT INTO system_menu (MENU_ID, MENU_NAME, MENU_PERMISSION, MENU_URL, MENU_ICON, PARENT_MENU_ID, CREATE_TIME, UPDATE_TIME) VALUES ('8', '日志图表', 'auth:log:charts:index', '/logCharts/index', 'layui-icon-chart-screen', '5', null, null);