/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : center

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2017-02-21 15:10:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for param
-- ----------------------------
DROP TABLE IF EXISTS `param`;
CREATE TABLE `param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `param_key` varchar(30) NOT NULL COMMENT '参数key',
  `param_value` varchar(30) NOT NULL COMMENT '参数value',
  `description` varchar(30) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数表';

-- ----------------------------
-- Records of param
-- ----------------------------

-- ----------------------------
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `link_url` varchar(30) DEFAULT NULL COMMENT '链接URL',
  `parent_id` bigint(20) NOT NULL COMMENT '父id',
  `sequence` int(11) DEFAULT NULL COMMENT '排序号',
  `auth_code` varchar(30) NOT NULL COMMENT '权限码',
  `type` varchar(20) NOT NULL COMMENT '类型（module=模块，method=方法）',
  `description` varchar(30) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of resources
-- ----------------------------
INSERT INTO `resources` VALUES ('1', '系统管理', '', '0', null, 'system', 'module', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('2', '用户管理', 'user/toFind', '1', null, 'user', 'module', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('3', '用户添加', 'user/toAdd', '2', null, 'user_add', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('4', '用户删除', 'user/delete', '2', null, 'user_delete', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('5', '用户更新', 'user/toUpdate', '2', null, 'user_update', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('6', '用户查询', 'user/find', '2', null, 'user_find', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('7', '用户分配角色', 'role/toAssignRole', '2', null, 'user_assign_role', 'module', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('8', '角色管理', 'role/toFind', '1', null, 'role', 'module', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('9', '角色添加', 'role/toAdd', '8', null, 'role_add', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('10', '角色删除', 'role/delete', '8', null, 'role_delete', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('11', '角色更新', 'role/toUpdate', '8', null, 'role_update', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('12', '角色查询', 'role/find', '8', null, 'role_find', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('13', '分配访问资源', 'resources/toAssignResources', '8', null, 'role_assign_resources', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('14', '资源管理', 'resources/toFind', '1', null, 'resources', 'module', '', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('15', '资源添加', 'resources/toAdd', '14', null, 'resources_add', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('16', '资源更新', 'resources/toUpdate', '14', null, 'resources_update', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('17', '资源删除', 'resources/delete', '14', null, 'resources_delete', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('18', '资源查询', 'resources/find', '14', null, 'resources_find', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('19', '状态码管理', 'statusCode/toFind', '1', null, 'statusCode', 'module', '', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('20', '状态码添加', 'statusCode/toAdd', '19', null, 'statusCode_add', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('21', '状态码更新', 'statusCode/toUpdate', '19', null, 'statusCode_update', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('22', '状态码删除', 'statusCode/delete', '19', null, 'statusCode_delete', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('23', '状态码查询', 'statusCode/find', '19', null, 'statusCode_find', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('24', '参数管理', 'param/toFind', '1', null, 'param', 'module', '', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('25', '参数查询', 'param/find', '24', null, 'param_find', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('26', '参数删除', 'param/delete', '24', null, 'param_delete', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('27', '参数更新', 'param/toUpdate', '24', null, 'param_update', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');
INSERT INTO `resources` VALUES ('28', '参数添加', 'param/toAdd', '24', null, 'param_add', 'method', '1', '2016-12-21 15:17:30', null, null, null, 'N');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `description` varchar(30) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('6', '财务人员', '', '2017-01-03 09:35:17', null, null, null, 'N');
INSERT INTO `role` VALUES ('7', 'willenfoo', '财务人员44', '2017-01-03 09:04:05', null, '2017-01-03 09:04:05', null, 'Y');
INSERT INTO `role` VALUES ('8', '11', '2244', '2017-01-03 14:03:13', null, '2017-01-03 14:03:13', null, 'Y');

-- ----------------------------
-- Table structure for role_resources
-- ----------------------------
DROP TABLE IF EXISTS `role_resources`;
CREATE TABLE `role_resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `resources_id` bigint(20) DEFAULT NULL COMMENT '资源id',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COMMENT='角色资源关链表';

-- ----------------------------
-- Records of role_resources
-- ----------------------------
INSERT INTO `role_resources` VALUES ('62', '6', '1', 'N');
INSERT INTO `role_resources` VALUES ('63', '6', '2', 'N');
INSERT INTO `role_resources` VALUES ('64', '6', '3', 'N');
INSERT INTO `role_resources` VALUES ('65', '6', '4', 'N');
INSERT INTO `role_resources` VALUES ('66', '6', '5', 'N');
INSERT INTO `role_resources` VALUES ('67', '6', '6', 'N');
INSERT INTO `role_resources` VALUES ('68', '6', '7', 'N');
INSERT INTO `role_resources` VALUES ('69', '6', '8', 'N');
INSERT INTO `role_resources` VALUES ('70', '6', '9', 'N');
INSERT INTO `role_resources` VALUES ('71', '6', '10', 'N');
INSERT INTO `role_resources` VALUES ('72', '6', '11', 'N');
INSERT INTO `role_resources` VALUES ('73', '6', '14', 'N');
INSERT INTO `role_resources` VALUES ('74', '6', '15', 'N');
INSERT INTO `role_resources` VALUES ('75', '6', '16', 'N');
INSERT INTO `role_resources` VALUES ('76', '6', '17', 'N');
INSERT INTO `role_resources` VALUES ('77', '6', '18', 'N');
INSERT INTO `role_resources` VALUES ('78', '6', '19', 'N');
INSERT INTO `role_resources` VALUES ('79', '6', '20', 'N');
INSERT INTO `role_resources` VALUES ('80', '6', '21', 'N');
INSERT INTO `role_resources` VALUES ('81', '6', '23', 'N');
INSERT INTO `role_resources` VALUES ('82', '6', '24', 'N');
INSERT INTO `role_resources` VALUES ('83', '6', '25', 'N');
INSERT INTO `role_resources` VALUES ('84', '6', '26', 'N');
INSERT INTO `role_resources` VALUES ('85', '6', '27', 'N');
INSERT INTO `role_resources` VALUES ('86', '6', '28', 'N');

-- ----------------------------
-- Table structure for status_code
-- ----------------------------
DROP TABLE IF EXISTS `status_code`;
CREATE TABLE `status_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `group_num` varchar(30) NOT NULL COMMENT '组编号',
  `group_name` varchar(30) NOT NULL COMMENT '组名称',
  `node_num` varchar(30) NOT NULL COMMENT '节点编号',
  `node_key` varchar(30) NOT NULL COMMENT '节点key',
  `node_value` varchar(30) NOT NULL COMMENT '节点value',
  `sequence` int(11) DEFAULT NULL COMMENT '排序号',
  `is_use` varchar(2) NOT NULL DEFAULT 'Y' COMMENT '是否使用（Y=是，N=否）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='数据库状态码（10=待审核等等）';

-- ----------------------------
-- Records of status_code
-- ----------------------------
INSERT INTO `status_code` VALUES ('1', 'gender', '性别', 'male', 'male', '男', null, 'Y', '2016-11-25 11:27:47', null, null, null, 'N');
INSERT INTO `status_code` VALUES ('2', 'gender', '性别', 'madam', 'madam', '女', null, 'Y', '2016-11-25 11:27:50', null, null, null, 'N');
INSERT INTO `status_code` VALUES ('3', 'resources_type', '资源类型', 'module', 'module', '模块', null, 'Y', '2016-11-25 11:27:52', null, null, null, 'N');
INSERT INTO `status_code` VALUES ('4', 'resources_type', '资源类型', 'method', 'method', '方法', null, 'Y', '2016-11-25 11:27:55', null, null, null, 'N');
INSERT INTO `status_code` VALUES ('5', 'yes_no', '是否', 'Y', 'Y', '是', null, 'Y', '2016-11-25 11:27:57', null, null, null, 'N');
INSERT INTO `status_code` VALUES ('6', 'yes_no', '是否', 'N', 'N', '否', null, 'Y', '2016-11-25 11:27:59', null, null, null, 'N');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `expired_date` datetime DEFAULT NULL COMMENT '过期日期',
  `credentials_expired` varchar(30) DEFAULT NULL COMMENT '过期凭证',
  `full_name` varchar(30) DEFAULT NULL COMMENT '全名',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别(male=男，madam=女）',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `address` varchar(40) DEFAULT NULL COMMENT '地址',
  `mobile_phone` varchar(20) DEFAULT NULL COMMENT '移动电话',
  `email` varchar(40) DEFAULT NULL COMMENT '邮箱',
  `user_type` varchar(2) DEFAULT NULL COMMENT '用户类型',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  `state` varchar(2) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_userName_uq` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'c0037f72b24b21c5fe297eab8f7e1755', null, '1', '1', 'male', '18', '1', '1', '1', '1', '', '1', '2016-11-28 09:41:38', '101', '2016-08-16 15:42:59', '101', 'N');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `user_id` bigint(20) NOT NULL COMMENT '用户id，外健',
  `role_id` bigint(20) NOT NULL COMMENT '角色id，外健',
  `is_delete` varchar(2) NOT NULL DEFAULT 'N' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关链表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
