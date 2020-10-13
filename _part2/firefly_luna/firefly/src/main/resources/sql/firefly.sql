/*
Navicat MySQL Data Transfer

Source Server         : localhost --【本机】
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : firefly

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2016-03-30 09:51:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for business_web_picture
-- ----------------------------
DROP TABLE IF EXISTS `business_web_picture`;
CREATE TABLE `business_web_picture` (
  `ID` bigint(12) NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(255) DEFAULT NULL COMMENT '图片类型',
  `NAME` varchar(255) DEFAULT NULL COMMENT '图片名称',
  `SOURCE` varchar(555) DEFAULT NULL COMMENT '图片源',
  `VALUE` varchar(255) DEFAULT NULL COMMENT '本地存储地址',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of business_web_picture
-- ----------------------------
INSERT INTO `business_web_picture` VALUES ('7', 'jpg', 'test', 'http://desk.fd.zol-img.com.cn/t_s1920x1080c5/g5/M00/0F/05/ChMkJlbrrSiIMJpcADDX7qUB0ikAAN8IwGtSSgAMNgG402.jpg', 'D:/fileDown/test.jpg', '2016-03-24 18:09:29');
INSERT INTO `business_web_picture` VALUES ('8', 'jpg', 'test', 'http://desk.fd.zol-img.com.cn/t_s2560x1600c5/g5/M00/0F/05/ChMkJlbrrSiIMJpcADDX7qUB0ikAAN8IwGtSSgAMNgG402.jpg', 'D:/fileDown/test.jpg', '2016-03-24 18:10:07');
INSERT INTO `business_web_picture` VALUES ('9', 'exe', '文件', 'http://dlc2.pconline.com.cn/filedown3_51224_17008270/n8w6F7ZT/Thunder_5100000512247008270.exe', 'D:/fileDown/文件.exe', '2016-03-24 18:13:17');
INSERT INTO `business_web_picture` VALUES ('10', 'ts', '影视', 'http://hot.media.ysten.com/media/new/2013/02/27/hd_dy_yjsdjq_20130227.ts', 'D:/fileDown/影视.ts', '2016-03-24 18:14:34');
INSERT INTO `business_web_picture` VALUES ('11', 'jpg', '1212', 'http://avatar.csdn.net/6/9/E/1_funnyrabbit87.jpg', 'D:/fileDown/1212.jpg', '2016-03-29 17:23:56');

-- ----------------------------
-- Table structure for system_button
-- ----------------------------
DROP TABLE IF EXISTS `system_button`;
CREATE TABLE `system_button` (
  `BUTTON_ID` bigint(12) NOT NULL AUTO_INCREMENT,
  `BUTTON_CODE` varchar(100) NOT NULL DEFAULT '' COMMENT '按钮代码',
  `MENU_ID` bigint(20) DEFAULT NULL COMMENT '所属菜单ID',
  `BUTTON_NAME` varchar(50) DEFAULT NULL COMMENT '按钮名称',
  `BUTTON_DESC` varchar(255) DEFAULT NULL COMMENT '按钮描述',
  `CREATE_USER` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`BUTTON_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_button
-- ----------------------------
INSERT INTO `system_button` VALUES ('2', 'button_add', '9', '添加', '按钮管理，添加按钮', '系统管理员', '2014-05-26 17:41:39', null);
INSERT INTO `system_button` VALUES ('3', 'button_delete', '9', '删除', '按钮管理，删除按钮', '系统管理员', '2014-05-26 17:42:15', '2015-09-11 17:05:27');
INSERT INTO `system_button` VALUES ('4', 'button_update', '9', '修改', '按钮管理，修改按钮', '系统管理员', '2014-05-26 17:42:47', '2015-09-14 14:08:30');
INSERT INTO `system_button` VALUES ('5', 'menu_add', '6', '添加', '菜单管理，添加菜单', '系统管理员', '2014-05-26 17:16:03', null);
INSERT INTO `system_button` VALUES ('6', 'menu_delete', '6', '删除', '菜单管理，删除菜单', '系统管理员', '2014-05-26 17:16:36', null);
INSERT INTO `system_button` VALUES ('7', 'menu_down', '6', '下移', '排序下移', '系统管理员', '2014-06-06 14:28:17', null);
INSERT INTO `system_button` VALUES ('8', 'menu_up', '6', '上移', '排序上移', '系统管理员', '2014-06-06 14:27:23', null);
INSERT INTO `system_button` VALUES ('9', 'menu_update', '6', '修改', '菜单管理，修改菜单', '系统管理员', '2014-05-26 17:17:04', null);
INSERT INTO `system_button` VALUES ('10', 'roleManage_roleList', '3', '检索', '角色管理，检索角色', '系统管理员', '2014-05-27 16:15:00', null);
INSERT INTO `system_button` VALUES ('11', 'role_add', '3', '添加', '角色管理，添加角色', '系统管理员', '2014-05-26 14:19:10', null);
INSERT INTO `system_button` VALUES ('12', 'role_assign_menu', '3', '配置系统菜单', '角色管理，配置系统菜单', '系统管理员', '2014-05-26 14:21:35', null);
INSERT INTO `system_button` VALUES ('13', 'role_delete', '3', '删除', '角色管理，删除角色', '系统管理员', '2014-05-26 14:19:46', null);
INSERT INTO `system_button` VALUES ('14', 'role_update', '3', '编辑', '角色管理，编辑角色信息', '系统管理员', '2014-05-26 14:20:27', null);
INSERT INTO `system_button` VALUES ('15', 'sysButton_buttonList', '9', '检索', '按钮管理，检索按钮', '系统管理员', '2014-05-27 16:33:45', null);
INSERT INTO `system_button` VALUES ('16', 'sysMenu_menuList', '6', '检索', '菜单管理，检索菜单', '系统管理员', '2014-05-27 16:13:16', null);
INSERT INTO `system_button` VALUES ('17', 'systemConfig_refresh', '5', '刷新系统参数', '系统参数，刷新系统参数', '系统管理员', '2014-05-26 16:34:30', null);
INSERT INTO `system_button` VALUES ('18', 'systemConfig_systemConfigList', '5', '检索', '系统参数，检索系统参数', '系统管理员', '2014-05-27 16:16:49', null);
INSERT INTO `system_button` VALUES ('19', 'systemConfig_update', '5', '修改', '系统参数，修改系统参数', '系统管理员', '2014-05-26 16:33:05', null);
INSERT INTO `system_button` VALUES ('20', 'systemDic_add', '7', '添加', '系统字典表，添加系统字典', '系统管理员', '2014-05-26 17:32:37', null);
INSERT INTO `system_button` VALUES ('21', 'systemDic_delete', '7', '删除', '系统字典表，删除系统字典', '系统管理员', '2014-05-26 17:33:29', null);
INSERT INTO `system_button` VALUES ('22', 'systemDic_systemIdDicList', '7', '检索', '系统字典表，检索系统字典', '系统管理员', '2014-05-27 16:42:48', null);
INSERT INTO `system_button` VALUES ('23', 'systemDic_update', '7', '修改', '系统字典表，修改系统字典', '系统管理员', '2014-05-26 17:33:56', null);
INSERT INTO `system_button` VALUES ('24', 'user_add', '2', '添加', '用户管理添加用户', '系统管理员', '2014-05-26 13:43:42', '2014-05-26 13:44:43');
INSERT INTO `system_button` VALUES ('25', 'user_assign_role', '2', '配置用户角色', '用户管理，配置用户角色', '系统管理员', '2014-05-26 14:17:51', null);
INSERT INTO `system_button` VALUES ('26', 'user_delete', '2', '删除', '用户管理删除用户', '系统管理员', '2014-05-26 13:44:26', null);
INSERT INTO `system_button` VALUES ('27', 'user_update', '2', '修改', '用户管理修改用户', '系统管理员', '2014-05-26 13:45:35', null);
INSERT INTO `system_button` VALUES ('30', 'admin_user_userList', '2', '检索', '用户管理，检索用户', '系统管理员', '2015-07-17 15:49:33', '2015-12-09 17:15:51');

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `CONFIG_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CONFIG_NAME` varchar(64) DEFAULT NULL,
  `CONFIG_DESC` varchar(512) DEFAULT NULL,
  `CONFIG_VALUE` varchar(512) DEFAULT NULL,
  `CREATE_USER` varchar(200) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`CONFIG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_config
-- ----------------------------
INSERT INTO `system_config` VALUES ('2', 'baisc.redis.url', 'redis -IP地址', '192.168.1.84', '系统管理员', '2015-12-10 14:32:42', '2015-12-11 17:16:50');
INSERT INTO `system_config` VALUES ('3', 'baisc.redis.port', 'redis-端口', '6379', '系统管理员', '2015-12-10 14:33:03', null);
INSERT INTO `system_config` VALUES ('4', 'baisc.redis.maxidle', 'redis-maxidle', '500', '系统管理员', '2015-12-10 14:33:24', null);
INSERT INTO `system_config` VALUES ('5', 'baisc.redis.maxactive', 'redis-maxactive', '5000', '系统管理员', '2015-12-10 14:33:54', null);
INSERT INTO `system_config` VALUES ('6', 'baisc.redis.expiretime', 'redis-失效时间', '86400', '系统管理员', '2015-12-10 14:34:13', null);
INSERT INTO `system_config` VALUES ('7', 'qiniu.http.url', '七牛云域名地址', '', '系统管理员', '2016-02-02 16:20:09', null);
INSERT INTO `system_config` VALUES ('8', 'qiniu.storage.name', '七牛云存储空间名称', '', '系统管理员', '2016-02-02 16:21:11', null);
INSERT INTO `system_config` VALUES ('9', 'qiniu.accessKey', '七牛云-accessKey', '', '系统管理员', '2016-02-02 16:21:55', null);
INSERT INTO `system_config` VALUES ('10', 'qiniu.secretKey', '七牛云-secretKey', '', '系统管理员', '2016-02-02 16:22:24', null);
INSERT INTO `system_config` VALUES ('20', 'file.save.path', '文件下载路径', 'D:/fileDown', '系统管理员', '2016-03-24 17:48:16', null);

-- ----------------------------
-- Table structure for system_dic
-- ----------------------------
DROP TABLE IF EXISTS `system_dic`;
CREATE TABLE `system_dic` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(50) DEFAULT NULL,
  `VALUE` varchar(36) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `SORT_NUM` int(10) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_dic
-- ----------------------------

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
  `PROLOG_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OPR_TYPE` varchar(24) DEFAULT NULL,
  `OPR_TIME` datetime DEFAULT NULL,
  `OPR_TABLE_NAME` varchar(64) DEFAULT NULL,
  `OPR_DATA_ID` varchar(128) DEFAULT NULL,
  `OPR_DATA_OLD` text,
  `OPR_DATA_NEW` text,
  `OPR_COMMENT` longtext,
  `OPR_USER_ID` bigint(20) DEFAULT NULL,
  `OPR_USER_NAME` varchar(50) DEFAULT NULL,
  `OPR_USER_IP` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`PROLOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=236 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_log
-- ----------------------------
INSERT INTO `system_log` VALUES ('235', 'CREATE', '2016-03-21 18:07:27', 'business_picture', 'id=17', '', 'name=快递发送$picUrl=http://7xiy0w.com1.z0.glb.clouddn.com/20160321180722_336.jpg', '', '1', '系统管理员', null);

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `MENU_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MENU_NAME` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `MENU_ALIAS` varchar(30) DEFAULT NULL COMMENT '菜单英文别名',
  `MENU_DESC` text COMMENT '菜单描述',
  `MENU_LINK` varchar(255) DEFAULT NULL COMMENT '菜单地址',
  `MENU_SORTNUM` int(11) DEFAULT NULL COMMENT '菜单排序',
  `MENU_PARENTID` bigint(20) DEFAULT NULL COMMENT '父菜单ID',
  `CREATE_USER` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES ('1', '系统管理', 'SYSTEM_MENU', '系统管理', '', '10', '0', '系统管理员', null, null);
INSERT INTO `system_menu` VALUES ('2', '用户管理', 'USER_MENU', '用户管理', 'admin/user/userList', '1', '1', '系统管理员', null, null);
INSERT INTO `system_menu` VALUES ('3', '角色管理', 'ROLE_MENU', '角色管理', 'roleManage/roleList', '2', '1', '系统管理员', null, null);
INSERT INTO `system_menu` VALUES ('5', '系统参数', 'SYSTEM_CONFIG', '系统参数', 'systemConfig/systemConfigList', '4', '1', '系统管理员', null, null);
INSERT INTO `system_menu` VALUES ('6', '菜单管理', 'SYSTEM_MENU', '菜单管理', 'sysMenu/menuList', '3', '1', '系统管理员', null, null);
INSERT INTO `system_menu` VALUES ('7', '系统字典', 'SYSDIC_CONFIG', '字典管理', 'systemDic/systemIdDicList', '5', '1', '系统管理员', null, null);
INSERT INTO `system_menu` VALUES ('9', '按钮管理', 'SYSTEM_BUTTON', '按钮管理', 'sysButton/buttonList', '6', '1', '系统管理员', null, null);
INSERT INTO `system_menu` VALUES ('10', '业务管理', '', '业务管理', '', '1', '0', '系统管理员', null, null);
INSERT INTO `system_menu` VALUES ('11', '图片管理', 'PICTURE', '图片管理', 'pic/picList', '1', '10', '系统管理员', null, null);
INSERT INTO `system_menu` VALUES ('12', '短信管理', 'SMS', '短信管理', 'sms/list', '2', '10', '系统管理员', null, null);

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `ROLE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(30) DEFAULT NULL,
  `ROLE_DESC` varchar(100) DEFAULT NULL,
  `CREATE_USER` varchar(200) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('1', '系统管理员', '系统管理员', '系统管理员', '2015-07-16 19:55:24', '2015-07-16 19:55:31');
INSERT INTO `system_role` VALUES ('2', '测试管理员', '测试人员', '系统管理员', '2015-12-10 13:59:59', null);

-- ----------------------------
-- Table structure for system_role_button_rel
-- ----------------------------
DROP TABLE IF EXISTS `system_role_button_rel`;
CREATE TABLE `system_role_button_rel` (
  `REL_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BUTTON_CODE` varchar(100) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL DEFAULT '0',
  `CREATE_USER` varchar(200) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`REL_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=514 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role_button_rel
-- ----------------------------
INSERT INTO `system_role_button_rel` VALUES ('255', 'user_add', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('256', 'user_assign_role', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('257', 'user_delete', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('258', 'user_update', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('259', 'admin_user_userList', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('260', 'roleManage_roleList', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('261', 'role_add', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('262', 'role_assign_menu', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('263', 'role_delete', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('264', 'role_update', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('265', 'systemConfig_refresh', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('266', 'systemConfig_systemConfigList', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('267', 'systemConfig_update', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('268', 'menu_add', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('269', 'menu_delete', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('270', 'menu_down', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('271', 'menu_up', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('272', 'menu_update', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('273', 'sysMenu_menuList', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('274', 'systemDic_add', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('275', 'systemDic_delete', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('276', 'systemDic_systemIdDicList', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('277', 'systemDic_update', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('278', 'button_add', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('279', 'button_delete', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('280', 'button_update', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('281', 'sysButton_buttonList', '2', '系统管理员', '2015-07-21 10:03:00', null);
INSERT INTO `system_role_button_rel` VALUES ('285', 'button_add', '4', '系统管理员', '2015-07-21 10:03:03', null);
INSERT INTO `system_role_button_rel` VALUES ('286', 'button_delete', '4', '系统管理员', '2015-07-21 10:03:03', null);
INSERT INTO `system_role_button_rel` VALUES ('287', 'button_update', '4', '系统管理员', '2015-07-21 10:03:03', null);
INSERT INTO `system_role_button_rel` VALUES ('288', 'sysButton_buttonList', '4', '系统管理员', '2015-07-21 10:03:03', null);
INSERT INTO `system_role_button_rel` VALUES ('380', 'user_add', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('381', 'user_assign_role', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('382', 'user_delete', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('383', 'user_update', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('384', 'admin_user_userList', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('385', 'roleManage_roleList', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('386', 'role_add', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('387', 'role_assign_menu', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('388', 'role_delete', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('389', 'role_update', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('390', 'systemConfig_refresh', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('391', 'systemConfig_systemConfigList', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('392', 'systemConfig_update', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('393', 'menu_add', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('394', 'menu_delete', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('395', 'menu_down', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('396', 'menu_up', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('397', 'menu_update', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('398', 'sysMenu_menuList', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('399', 'systemDic_add', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('400', 'systemDic_delete', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('401', 'systemDic_systemIdDicList', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('402', 'systemDic_update', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('403', 'button_add', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('404', 'button_delete', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('405', 'button_update', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('406', 'sysButton_buttonList', '5', '系统管理员', '2015-07-22 11:20:34', null);
INSERT INTO `system_role_button_rel` VALUES ('487', 'user_add', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('488', 'user_assign_role', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('489', 'user_delete', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('490', 'user_update', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('491', 'admin_user_userList', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('492', 'roleManage_roleList', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('493', 'role_add', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('494', 'role_assign_menu', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('495', 'role_delete', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('496', 'role_update', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('497', 'menu_add', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('498', 'menu_delete', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('499', 'menu_down', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('500', 'menu_up', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('501', 'menu_update', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('502', 'sysMenu_menuList', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('503', 'systemConfig_refresh', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('504', 'systemConfig_systemConfigList', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('505', 'systemConfig_update', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('506', 'systemDic_add', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('507', 'systemDic_delete', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('508', 'systemDic_systemIdDicList', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('509', 'systemDic_update', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('510', 'button_add', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('511', 'button_delete', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('512', 'button_update', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_button_rel` VALUES ('513', 'sysButton_buttonList', '1', '系统管理员', '2016-02-03 11:59:24', null);

-- ----------------------------
-- Table structure for system_role_menu_rel
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu_rel`;
CREATE TABLE `system_role_menu_rel` (
  `REL_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MENU_ID` bigint(20) NOT NULL DEFAULT '0',
  `ROLE_ID` bigint(20) NOT NULL DEFAULT '0',
  `CREATE_USER` varchar(200) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`REL_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role_menu_rel
-- ----------------------------
INSERT INTO `system_role_menu_rel` VALUES ('140', '10', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_menu_rel` VALUES ('141', '11', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_menu_rel` VALUES ('142', '12', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_menu_rel` VALUES ('143', '1', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_menu_rel` VALUES ('144', '2', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_menu_rel` VALUES ('145', '3', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_menu_rel` VALUES ('146', '6', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_menu_rel` VALUES ('147', '5', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_menu_rel` VALUES ('148', '7', '1', '系统管理员', '2016-02-03 11:59:24', null);
INSERT INTO `system_role_menu_rel` VALUES ('149', '9', '1', '系统管理员', '2016-02-03 11:59:24', null);

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LOGIN_NAME` varchar(50) DEFAULT NULL,
  `USER_NAME` varchar(50) DEFAULT NULL,
  `USER_PWD` varchar(64) DEFAULT NULL,
  `USER_SALT` varchar(32) DEFAULT NULL,
  `USER_ROLES` varchar(512) DEFAULT NULL,
  `OPR_USERID` bigint(20) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `USER_EMAIL` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('1', 'admin', '系统管理员', '4b5f4739da10438b566d3c1a214785f30602f023', '62396b579caeac31', 'admin', '-1', '2014-01-02 14:59:27', '2014-01-02 14:59:30', 'liwei@leelong.cn');

-- ----------------------------
-- Table structure for system_user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role_rel`;
CREATE TABLE `system_user_role_rel` (
  `REL_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  `CREATE_USER` varchar(200) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`REL_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_user_role_rel
-- ----------------------------
INSERT INTO `system_user_role_rel` VALUES ('1', '1', '1', '系统管理员', '2015-07-16 20:43:17', null);
