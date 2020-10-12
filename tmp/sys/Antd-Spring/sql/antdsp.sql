/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50621
 Source Host           : localhost:3306
 Source Schema         : antdsp

 Target Server Type    : MySQL
 Target Server Version : 50621
 File Encoding         : 65001

 Date: 11/06/2019 17:48:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------
INSERT INTO `tb_role_menu` VALUES (21, 4, 14);
INSERT INTO `tb_role_menu` VALUES (22, 4, 16);
INSERT INTO `tb_role_menu` VALUES (23, 4, 17);
INSERT INTO `tb_role_menu` VALUES (24, 4, 18);
INSERT INTO `tb_role_menu` VALUES (25, 4, 8);
INSERT INTO `tb_role_menu` VALUES (26, 5, 8);
INSERT INTO `tb_role_menu` VALUES (27, 5, 14);
INSERT INTO `tb_role_menu` VALUES (28, 5, 15);
INSERT INTO `tb_role_menu` VALUES (29, 5, 16);
INSERT INTO `tb_role_menu` VALUES (30, 5, 17);
INSERT INTO `tb_role_menu` VALUES (31, 5, 18);
INSERT INTO `tb_role_menu` VALUES (32, 5, 19);
INSERT INTO `tb_role_menu` VALUES (33, 5, 20);
INSERT INTO `tb_role_menu` VALUES (34, 5, 21);
INSERT INTO `tb_role_menu` VALUES (35, 5, 22);

-- ----------------------------
-- Table structure for tb_role_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_user`;
CREATE TABLE `tb_role_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tb_system_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_system_menu`;
CREATE TABLE `tb_system_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime(0) NOT NULL,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `modified` datetime(0) NOT NULL,
  `modifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `icon` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `permission` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reamrk` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_type` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_system_menu
-- ----------------------------
INSERT INTO `tb_system_menu` VALUES (22, '2019-06-10 13:46:43', '', '2019-06-10 13:46:55', '', 'team', '角色管理', 8, NULL, NULL, '/system/role', 'MENU');
INSERT INTO `tb_system_menu` VALUES (21, '2019-06-10 13:44:18', '', '2019-06-10 13:44:18', '', NULL, '删除', 15, 'menu:del', NULL, NULL, 'BUTTON');
INSERT INTO `tb_system_menu` VALUES (20, '2019-06-10 13:43:58', '', '2019-06-10 13:43:58', '', NULL, '编辑', 15, 'menu:update', NULL, NULL, 'BUTTON');
INSERT INTO `tb_system_menu` VALUES (19, '2019-06-10 13:43:36', '', '2019-06-10 13:43:36', '', 'plus', '新增', 15, 'menu:add', NULL, NULL, 'BUTTON');
INSERT INTO `tb_system_menu` VALUES (18, '2019-06-10 13:43:07', '', '2019-06-10 13:43:07', '', NULL, '修改', 14, 'role:update', NULL, NULL, 'BUTTON');
INSERT INTO `tb_system_menu` VALUES (8, '2019-06-10 12:20:51', '', '2019-06-11 16:08:03', '', 'setting', '系统管理', 0, '', NULL, '/system', 'MENU');
INSERT INTO `tb_system_menu` VALUES (14, '2019-06-10 13:40:51', '', '2019-06-10 13:40:51', '', 'user', '用户管理', 8, NULL, NULL, '/system/user', 'MENU');
INSERT INTO `tb_system_menu` VALUES (15, '2019-06-10 13:41:26', '', '2019-06-10 13:41:26', '', 'menu', '菜单管理', 8, NULL, NULL, '/system/menu', 'MENU');
INSERT INTO `tb_system_menu` VALUES (16, '2019-06-10 13:42:10', '', '2019-06-10 13:42:10', '', 'plus', '新增', 14, 'role:add', NULL, NULL, 'BUTTON');
INSERT INTO `tb_system_menu` VALUES (17, '2019-06-10 13:42:40', '', '2019-06-10 13:42:40', '', NULL, '删除', 14, 'role:del', NULL, NULL, 'BUTTON');

-- ----------------------------
-- Table structure for tb_system_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_system_role`;
CREATE TABLE `tb_system_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime(0) NOT NULL,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `modified` datetime(0) NOT NULL,
  `modifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_system_role
-- ----------------------------
INSERT INTO `tb_system_role` VALUES (5, '2019-06-11 11:03:40', '', '2019-06-11 15:21:19', '', '网站管理员，拥有所有权限', 'admin');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime(0) NOT NULL,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `modified` datetime(0) NOT NULL,
  `modifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `login_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `qq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (2, '2019-06-03 16:09:52', '', '2019-06-03 16:09:52', '', '', 'a496401006@qq.com', 'jt-lee', '123456', '496401006', 'lijiantao', 'NORMAL');

SET FOREIGN_KEY_CHECKS = 1;
