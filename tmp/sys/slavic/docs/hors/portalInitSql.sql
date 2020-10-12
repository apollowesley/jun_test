SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hors_common_file_disk
-- ----------------------------
DROP TABLE IF EXISTS `hors_common_file_disk`;
CREATE TABLE `hors_common_file_disk`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `file_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件类型: PORTAL_AVATAR 后台用户头像',
  `file_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名',
  `file_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件内容 BASE64形式',
  `file_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '文件状态: 0正常 -1删除',
  `create_time` datetime NOT NULL COMMENT '文件上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for hors_portal_login_record
-- ----------------------------
DROP TABLE IF EXISTS `hors_portal_login_record`;
CREATE TABLE `hors_portal_login_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `login_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '未知IP' COMMENT '登陆IP',
  `ip_location` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '未知地址' COMMENT 'IP地址',
  `create_time` datetime NOT NULL COMMENT '登陆时间',
  `os_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '终端系统名称',
  `os_version` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '终端版本号',
  `browser_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器名称',
  `browser_version` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of hors_portal_login_record
-- ----------------------------
INSERT INTO `hors_portal_login_record` VALUES (1, 14, '192.168.1.98', '?????', '2020-04-04 13:25:40', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (2, 14, '192.168.1.98', '?????', '2020-04-04 20:31:41', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (3, 14, '192.168.1.98', '?????', '2020-04-04 21:49:57', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (4, 20, '192.168.1.98', '?????', '2020-04-04 23:25:44', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (5, 14, '192.168.1.98', '?????', '2020-04-04 23:26:32', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (6, 14, '192.168.1.98', '?????', '2020-04-04 23:27:42', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (7, 14, '192.168.1.98', '?????', '2020-04-04 23:28:08', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (8, 14, '192.168.1.98', '?????', '2020-04-05 10:35:20', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (9, 14, '192.168.1.98', '?????', '2020-04-05 10:36:04', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (10, 14, '192.168.1.98', '?????', '2020-04-05 10:36:39', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (11, 14, '192.168.1.98', '?????', '2020-04-05 10:37:00', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (12, 14, '192.168.1.98', '?????', '2020-04-05 10:37:08', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (13, 14, '192.168.1.98', '?????', '2020-04-05 15:35:53', NULL, NULL, NULL, NULL);
INSERT INTO `hors_portal_login_record` VALUES (14, 14, '192.168.1.98', '?????', '2020-04-06 13:53:04', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for hors_portal_resource
-- ----------------------------
DROP TABLE IF EXISTS `hors_portal_resource`;
CREATE TABLE `hors_portal_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `resource_type` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源类型:RESOURCE_MENU,RESOURCE_BUTTON,RESOURCE_INTERFACE',
  `resource_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源名称',
  `resource_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源描述',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父类ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of hors_portal_resource
-- ----------------------------
INSERT INTO `hors_portal_resource` VALUES (1, 'RESOURCE_MENU', 'PORTAL_MANAGER', '系统管理', NULL, '2019-10-31 20:26:30', '2019-10-31 20:26:33');
INSERT INTO `hors_portal_resource` VALUES (2, 'RESOURCE_MENU', 'PORTAL_MANAGER_USER_MENU', '用户管理菜单', 1, '2019-11-13 20:23:46', '2019-11-13 20:23:48');
INSERT INTO `hors_portal_resource` VALUES (3, 'RESOURCE_MENU', 'PORTAL_MANAGER_ROLE_MENU', '角色管理菜单', 1, '2019-11-13 20:24:45', '2019-11-13 20:24:47');
INSERT INTO `hors_portal_resource` VALUES (4, 'RESOURCE_MENU', 'PORTAL_MANAGER_RESOURCE_MENU', '资源管理菜单', 1, '2019-11-13 20:25:50', '2019-11-13 20:25:52');
INSERT INTO `hors_portal_resource` VALUES (5, 'RESOURCE_BUTTON', 'SYSTEM_MANAGER_BUTTON', '系统管理按钮', NULL, '2019-11-15 17:45:33', '2019-11-15 17:45:35');
INSERT INTO `hors_portal_resource` VALUES (6, 'RESOURCE_BUTTON', 'RESOURCE_MENU_BUTTON', '资源管理按钮', 5, '2019-11-15 17:46:53', '2019-11-15 17:46:54');
INSERT INTO `hors_portal_resource` VALUES (7, 'RESOURCE_BUTTON', 'USER_MENU_BUTTON', '用户管理按钮', 5, '2019-11-15 17:47:40', '2019-11-15 17:47:42');
INSERT INTO `hors_portal_resource` VALUES (8, 'RESOURCE_BUTTON', 'ROLE_MENU_BUTTON', '角色管理按钮', 5, '2019-11-15 17:48:08', '2019-11-15 17:48:10');
INSERT INTO `hors_portal_resource` VALUES (9, 'RESOURCE_BUTTON', 'ROLE_EDIT_BUTTON', '角色编辑按钮', 8, '2019-11-15 17:49:59', '2019-11-15 17:50:01');
INSERT INTO `hors_portal_resource` VALUES (10, 'RESOURCE_BUTTON', 'ROLE_RESOURCE_SETTING_BUTTON', '角色资源配置按钮', 8, '2019-11-15 17:50:34', '2019-11-15 17:50:35');
INSERT INTO `hors_portal_resource` VALUES (11, 'RESOURCE_BUTTON', 'ROLE_DELETE_BUTTON', '角色删除按钮', 8, '2019-11-15 17:51:07', '2019-11-15 17:51:05');
INSERT INTO `hors_portal_resource` VALUES (12, 'RESOURCE_BUTTON', 'ROLE_ADD_BUTTON', '添加角色按钮', 8, '2019-11-15 17:52:21', '2019-11-15 17:52:23');
INSERT INTO `hors_portal_resource` VALUES (13, 'RESOURCE_BUTTON', 'USER_ADD_BUTTON', '添加用户按钮', 7, '2019-11-15 17:52:51', '2019-11-15 17:52:53');
INSERT INTO `hors_portal_resource` VALUES (14, 'RESOURCE_BUTTON', 'USER_STATUS_CHANGE_BUTTON', '变更用户状态按钮', 7, '2019-11-15 17:54:18', '2019-11-15 17:54:19');
INSERT INTO `hors_portal_resource` VALUES (15, 'RESOURCE_BUTTON', 'USER_EDIT_BUTTON', '用户编辑按钮', 7, '2019-11-15 17:54:44', '2019-11-15 17:54:46');
INSERT INTO `hors_portal_resource` VALUES (16, 'RESOURCE_BUTTON', 'USER_ROLE_SETTING_BUTTON', '用户角色配置按钮', 7, '2019-11-15 17:55:13', '2019-11-15 17:55:14');
INSERT INTO `hors_portal_resource` VALUES (17, 'RESOURCE_BUTTON', 'USER_DELETE_BUTTON', '删除用户按钮', 7, '2019-11-15 17:55:39', '2019-11-15 17:55:41');

-- ----------------------------
-- Table structure for hors_portal_role
-- ----------------------------
DROP TABLE IF EXISTS `hors_portal_role`;
CREATE TABLE `hors_portal_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `hors_version` int(11) NOT NULL DEFAULT 0 COMMENT '数据版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of hors_portal_role
-- ----------------------------
INSERT INTO `hors_portal_role` VALUES (4, 'shixi', 'shixi11', '2019-11-14 13:27:36', '2019-11-14 13:27:36', 0);
INSERT INTO `hors_portal_role` VALUES (23, '1', '1', '2020-04-04 23:17:15', '2020-04-04 23:17:15', 0);

-- ----------------------------
-- Table structure for hors_portal_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `hors_portal_role_resource`;
CREATE TABLE `hors_portal_role_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `resource_id` bigint(20) NOT NULL COMMENT '资源ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 282 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of hors_portal_role_resource
-- ----------------------------
INSERT INTO `hors_portal_role_resource` VALUES (278, 4, 1, '2020-04-04 23:16:20', '2020-04-04 23:16:20');
INSERT INTO `hors_portal_role_resource` VALUES (279, 4, 2, '2020-04-04 23:16:20', '2020-04-04 23:16:20');
INSERT INTO `hors_portal_role_resource` VALUES (280, 4, 3, '2020-04-04 23:16:20', '2020-04-04 23:16:20');
INSERT INTO `hors_portal_role_resource` VALUES (281, 4, 4, '2020-04-04 23:16:20', '2020-04-04 23:16:20');

-- ----------------------------
-- Table structure for hors_portal_user
-- ----------------------------
DROP TABLE IF EXISTS `hors_portal_user`;
CREATE TABLE `hors_portal_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `real_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
  `nick_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `user_phone` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户手机号',
  `avatar_url` int(11) NULL DEFAULT 1 COMMENT '头像地址',
  `user_email` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户邮箱',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `user_status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '数据状态: -1停用 1正常',
  `hors_version` int(11) NOT NULL DEFAULT 0 COMMENT '数据版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of hors_portal_user
-- ----------------------------
INSERT INTO `hors_portal_user` VALUES (2, '123123', 'e10adc3949ba59abbe56e057f20f883e', '123123', '123123123', '123123', 1, '132123', '2019-11-11 19:01:47', '2020-04-04 23:09:59', 1, 6);
INSERT INTO `hors_portal_user` VALUES (14, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'xiaoguo', 'NickName', '18521360730', 22, '735376047@qq.com', '2019-11-24 16:18:05', '2020-04-05 11:43:31', 1, 36);
INSERT INTO `hors_portal_user` VALUES (19, 'admin1', 'e10adc3949ba59abbe56e057f20f883e', 'TEST', 'TEST', 'TEST', 1, 'TEST', '2019-11-24 16:18:05', '2019-11-24 16:18:05', 1, 0);
INSERT INTO `hors_portal_user` VALUES (39, '1', '1', '1', '1', '1', 1, '1', '2020-03-29 13:27:39', '2020-03-29 13:27:39', 1, 0);
INSERT INTO `hors_portal_user` VALUES (40, '11', '1', '1', '1', '1', 1, '1', '2020-03-29 13:29:03', '2020-03-29 13:29:03', 1, 0);
INSERT INTO `hors_portal_user` VALUES (41, '11111', '1', '1', '1', '1', 1, '1', '2020-03-29 15:20:33', '2020-03-29 15:20:33', 1, 0);
INSERT INTO `hors_portal_user` VALUES (42, '1234124124124', '12', '123', '123', '123', 1, '213', '2020-03-29 15:21:25', '2020-03-29 15:21:25', 1, 0);
INSERT INTO `hors_portal_user` VALUES (43, '123123213123', '123', '123', '123', '123', 1, '123', '2020-03-29 15:22:00', '2020-03-29 15:22:00', 1, 0);
INSERT INTO `hors_portal_user` VALUES (45, '123213', '4297f44b13955235245b2497399d7a93', '123213', '123123', '123123', 1, '123213', '2020-03-29 16:12:16', '2020-03-29 16:12:16', -1, 0);
INSERT INTO `hors_portal_user` VALUES (46, '999', 'b706835de79a2b4e80506f582af3676a', '999', '999', '999', 1, '999', '2020-03-29 16:12:30', '2020-03-29 16:12:30', 1, 0);
INSERT INTO `hors_portal_user` VALUES (47, '333', '182be0c5cdcd5072bb1864cdee4d3d6e', '33', '33', '33', 1, '33', '2020-04-04 23:10:34', '2020-04-04 23:10:34', 1, 0);
INSERT INTO `hors_portal_user` VALUES (48, '12', 'c4ca4238a0b923820dcc509a6f75849b', '1', '1', '1', 1, '1', '2020-04-04 23:15:57', '2020-04-04 23:15:57', 1, 0);

-- ----------------------------
-- Table structure for hors_portal_user_role
-- ----------------------------
DROP TABLE IF EXISTS `hors_portal_user_role`;
CREATE TABLE `hors_portal_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of hors_portal_user_role
-- ----------------------------
INSERT INTO `hors_portal_user_role` VALUES (32, 14, 4, '2019-11-24 16:41:36', '2019-11-24 16:41:36');

SET FOREIGN_KEY_CHECKS = 1;
