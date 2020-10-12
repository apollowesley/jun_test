/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50173
 Source Host           : localhost
 Source Database       : db_iutils

 Target Server Version : 50173
 File Encoding         : utf-8

 Date: 03/05/2017 15:46:35 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `sys_name` varchar(255) NOT NULL COMMENT '系统名称',
  `module_name` varchar(255) NOT NULL COMMENT '模块名称',
  `config_name` varchar(255) NOT NULL COMMENT '配置key',
  `config_value` varchar(255) DEFAULT NULL COMMENT '配置值',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='公共配置表';

-- ----------------------------
--  Table structure for `sys_organization`
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '组织机构名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父编号',
  `parent_ids` varchar(5000) DEFAULT NULL COMMENT '父编号列表',
  `available` tinyint(1) DEFAULT '0' COMMENT '是否可用',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `remarks` varchar(225) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `idx_sys_organization_parent_id` (`parent_id`),
  KEY `idx_sys_organization_parent_ids` (`parent_ids`(255))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='组织机构';

-- ----------------------------
--  Records of `sys_organization`
-- ----------------------------
BEGIN;
INSERT INTO `sys_organization` VALUES ('1', '组织机构', '0', '0/', '1', null, null, '1', '2016-09-25 20:56:43', null, '0'), ('2', '某某公司', '1', '0/1/', '1', '1', '2017-02-18 18:12:43', null, null, null, '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '资源名称',
  `type` varchar(50) DEFAULT NULL COMMENT '资源类型',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `url` varchar(200) DEFAULT NULL COMMENT '资源路径',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父编号',
  `parent_ids` varchar(5000) DEFAULT NULL COMMENT '父编号列表',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限字符串',
  `available` tinyint(1) DEFAULT '0' COMMENT '是否可用',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `remarks` varchar(225) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `idx_sys_resource_parent_id` (`parent_id`),
  KEY `idx_sys_resource_parent_ids` (`parent_ids`(255))
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='资源';

-- ----------------------------
--  Records of `sys_resource`
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` VALUES ('1', '资源', 'menu', '', '', '0', '0/', '', '1', '0', '1', null, null, null, null, '0'), ('2', '系统管理', 'menu', '', '', '1', '0/1/', 'sys:manage', '1', '10', '1', '2016-06-14 09:33:06', '1', '2016-11-18 01:21:14', null, '0'), ('11', '组织机构', 'menu', '', 'organization', '75', '0/1/75/', 'sys:organization', '1', '10', '1', null, '1', '2016-10-21 01:32:05', null, '0'), ('12', '查看', 'form', '', '', '11', '0/1/2/11/', 'sys:organization:view', '1', '1', '1', null, '1', '2017-01-10 14:43:53', null, '0'), ('13', '编辑', 'form', '', '', '11', '0/1/2/11/', 'sys:organization:edit', '1', '2', '1', null, '1', '2017-01-10 14:44:15', null, '0'), ('21', '用户管理', 'menu', '', 'user/list', '2', '0/1/2/', 'sys:user:view', '1', '10', '1', null, '1', '2017-02-18 18:11:51', null, '0'), ('22', '查看', 'form', '', '', '21', '0/1/2/21/', 'sys:user:view', '1', '4', '1', null, '1', '2017-01-10 10:38:16', null, '0'), ('23', '编辑', 'form', '', '', '21', '0/1/2/21/', 'sys:user:update', '1', '3', '1', null, '1', '2017-01-10 11:14:13', null, '0'), ('31', '资源管理', 'menu', '', 'resource', '75', '0/1/75/', 'sys:resource', '1', '20', '1', null, '1', '2016-10-21 01:32:27', null, '0'), ('32', '查看', 'form', '', '', '31', '0/1/2/31/', 'sys:resource:view', '1', '3', '1', null, '1', '2017-01-10 10:49:52', null, '0'), ('33', '编辑', 'form', '', '', '31', '0/1/2/31/', 'sys:resource:edit', '1', '1', '1', null, '1', '2017-01-10 10:49:08', null, '0'), ('41', '角色管理', 'menu', '', 'role', '75', '0/1/75/', 'sys:role:view', '1', '30', '1', null, '1', '2016-10-21 01:32:44', null, '0'), ('42', '查看', 'form', '', '', '41', '0/1/2/41/', 'sys:role:view', '1', '1', '1', null, '1', '2017-01-10 14:42:16', null, '0'), ('43', '编辑', 'form', '', '', '41', '0/1/2/41/', 'sys:role:edit', '1', '2', '1', null, '1', '2017-01-10 14:42:20', null, '0'), ('51', '会话管理', 'menu', '', 'sessions', '2', '0/1/2/', 'sys:sessions', '1', '60', '1', null, '1', '2016-10-21 01:30:52', null, '0'), ('73', '任务调度', 'menu', '', 'scheduleJob', '2', '0/1/2/', 'sys:scheduleJob:*', '1', '50', '1', '2016-07-15 22:50:56', '1', '2017-01-10 10:40:03', null, '0'), ('74', '系统日志', 'menu', '', 'slog', '2', '0/1/2/', 'sys:slog:view', '1', '70', '1', '2016-10-02 14:02:51', '1', '2016-10-21 01:31:14', null, '0'), ('75', '系统设置', 'menu', '', '', '1', '0/1/', 'sys:setting', '1', '20', '1', '2016-10-09 00:04:34', '1', '2016-11-18 01:18:34', null, '0'), ('76', '查看', 'form', '', '', '74', '0/1/2/74/', 'sys:slog:view', '1', '0', '1', '2016-10-21 00:27:13', null, null, null, '0'), ('77', '编辑', 'form', '', '', '74', '0/1/2/74/', 'sys:slog:edit', '1', '0', '1', '2016-10-21 00:27:31', null, null, null, '0'), ('97', '新增', 'form', '', '', '21', '0/1/2/21/', 'sys:user:create', '1', '1', '1', '2017-01-10 10:30:07', '1', '2017-01-10 10:37:20', null, '0'), ('98', '删除', 'form', '', '', '21', '0/1/2/21/', 'sys:user:delete', '1', '2', '1', '2017-01-10 10:37:07', '1', '2017-01-10 10:50:33', null, '0'), ('100', '删除', 'form', '', '', '11', '0/1/75/11/', 'sys:organization:delete', '1', '3', '1', '2017-01-10 11:00:30', '1', '2017-01-10 11:00:40', null, '0'), ('101', '公共配置', 'menu', '', 'sys/config', '2', '0/1/2/', 'sys:config:view', '1', '20', '1', '2017-01-14 21:31:11', null, null, null, '0'), ('102', '新增', 'form', '', '', '101', '0/1/2/101/', 'sys:config:create', '1', '1', '1', '2017-01-14 21:31:37', null, null, null, '0'), ('103', '删除', 'form', '', '', '101', '0/1/2/101/', 'sys:config:delete', '1', '2', '1', '2017-01-14 21:31:55', '1', '2017-01-14 21:32:20', null, '0'), ('104', '修改', 'form', '', '', '101', '0/1/2/101/', 'sys:config:update', '1', '3', '1', '2017-01-14 21:32:12', null, null, null, '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `role` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `organization_id` bigint(20) NOT NULL COMMENT '归属机构',
  `resource_ids` varchar(5000) DEFAULT NULL COMMENT '资源编号集合',
  `data_scope` varchar(50) NOT NULL COMMENT '数据范围',
  `available` tinyint(1) DEFAULT '0' COMMENT '是否可用',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `remarks` varchar(225) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='权限';

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', 'super', '超级管理员', '1', '2,11,12,13,21,22,23,31,32,33,41,42,43,51,73,74,75,76,77,97,98,100,101,102,103,104,', 'self', '1', '1', '2016-10-08 12:32:47', '1', '2017-01-14 21:32:30', '至高权限', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `sys_schedule_job`;
CREATE TABLE `sys_schedule_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) DEFAULT NULL COMMENT '任务分组',
  `cron` varchar(200) DEFAULT NULL COMMENT 'cron表达式',
  `bean_class` varchar(500) DEFAULT NULL COMMENT '任务执行时调用哪个类的方法 包名+类名',
  `is_concurrent` char(1) DEFAULT NULL COMMENT '是否有状态',
  `method_name` varchar(200) DEFAULT NULL COMMENT '任务调用的方法名',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `remarks` varchar(200) DEFAULT NULL COMMENT '描述',
  `status` char(1) DEFAULT NULL COMMENT '任务状态 0禁用 1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='任务调度';

-- ----------------------------
--  Records of `sys_schedule_job`
-- ----------------------------
BEGIN;
INSERT INTO `sys_schedule_job` VALUES ('1', '测试任务', '测试', '0/1 * * * * ?', 'cn.iutils.task.TaskTest', '1', 'run', '1', '2016-07-15 09:44:53', '1', '2016-09-25 13:23:19', '每一秒执行一次', '0'), ('2', '日志记录', '日志', '0/30 * * * * ?', 'cn.iutils.task.TaskLog', '1', 'run', '1', '2016-07-19 13:28:42', '1', '2016-10-08 11:15:24', '30秒清理记录日志', '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_slog`
-- ----------------------------
DROP TABLE IF EXISTS `sys_slog`;
CREATE TABLE `sys_slog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `menu` varchar(255) NOT NULL COMMENT '菜单',
  `remote_addr` varchar(50) NOT NULL COMMENT '操作IP',
  `request_uri` varchar(200) NOT NULL COMMENT '请求地址',
  `method` varchar(5) NOT NULL COMMENT '操作方式',
  `params` text COMMENT '提交的数据',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  `exception` text COMMENT '异常信息',
  `create_by` bigint(20) DEFAULT NULL COMMENT '记录人',
  `create_date` datetime NOT NULL COMMENT '记录时间',
  `time_consuming` varchar(50) NOT NULL COMMENT '耗时',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志';

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `no` varchar(100) DEFAULT NULL COMMENT '用户编号',
  `organization_id` bigint(20) NOT NULL COMMENT '机构编号',
  `username` varchar(100) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(100) DEFAULT NULL,
  `role_ids` varchar(100) DEFAULT NULL COMMENT '权限编号集合',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话号码',
  `mobile` varchar(45) DEFAULT NULL COMMENT '手机号码',
  `photo` varchar(1000) DEFAULT NULL COMMENT '头像',
  `login_ip` varchar(45) DEFAULT NULL COMMENT '登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '登录时间',
  `locked` tinyint(1) DEFAULT '0' COMMENT '是否锁定',
  `is_dept` tinyint(1) DEFAULT NULL COMMENT '是否部门管理员',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `remarks` varchar(225) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_user_username` (`username`),
  KEY `idx_sys_user_organization_id` (`organization_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', null, '1', 'super', 'ff7e419b2147a346fcf97e8a0d439143', '7b5f5d3a1d3ba80fed0ad6256eb0fc3c', '1,', '超级管理', '', '', '', '', null, null, '0', '0', '1', '2016-10-15 17:13:38', '1', '2017-02-18 18:12:10', '', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_qiniu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_qiniu`;
CREATE TABLE `sys_user_qiniu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `domain` varchar(200) NOT NULL COMMENT '域名',
  `access` varchar(200) NOT NULL COMMENT 'AK',
  `secret` varchar(200) NOT NULL COMMENT 'SK',
  `pub` varchar(200) NOT NULL COMMENT 'PUB',
  `pri` varchar(200) NOT NULL COMMENT 'PRI',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户七牛配置';

SET FOREIGN_KEY_CHECKS = 1;
