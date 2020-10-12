/*
Navicat MariaDB Data Transfer

Source Server         : me
Source Server Version : 50532
Source Host           : localhost:3306
Source Database       : xjb

Target Server Type    : MariaDB
Target Server Version : 50532
File Encoding         : 65001

Date: 2014-08-23 05:21:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sysmenu
-- ----------------------------
DROP TABLE IF EXISTS `sysmenu`;
CREATE TABLE `sysmenu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `selfkey` varchar(255) NOT NULL COMMENT '自己的键',
  `parentkey` varchar(255) NOT NULL COMMENT '父级的键',
  `name` varchar(255) NOT NULL COMMENT '菜单名称',
  `priority` tinyint(4) NOT NULL DEFAULT '0' COMMENT '权限',
  `order` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
-- Records of sysmenu
-- ----------------------------
INSERT INTO `sysmenu` VALUES ('1', 'main', '0', '首页', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('2', 'global', '0', '全局', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('3', 'plane', '0', '界面', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('4', 'content', '0', '内容', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('5', 'users', '0', '用户', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('6', 'tools', '0', '工具', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('7', '', 'global', '站点信息', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('8', '', 'global', '注册', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('9', '', 'global', '上传设置', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('10', '', 'global', '水印设置', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('11', 'templateManagement', 'plane', '模板设置', '0', '0', 'Admin.yy?a=Template');
INSERT INTO `sysmenu` VALUES ('12', 'menuManagement', 'global', '菜单管理', '0', '0', 'Admin.yy?a=Menu');
INSERT INTO `sysmenu` VALUES ('13', '', '', '2', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('14', '', 'content', '内容审核', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('15', '', 'content', '词语过滤', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('16', 'userManagement', 'users', '用户管理', '0', '0', 'Admin.yy?a=Users');
INSERT INTO `sysmenu` VALUES ('17', '', 'users', '添加用户', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('18', '', 'users', '发送通知', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('19', '', 'users', '禁止用户', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('20', '', 'users', '禁止IP', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('21', 'feedbackManagement', 'content', '反馈管理', '0', '0', 'Admin.yy?a=Feedback');
INSERT INTO `sysmenu` VALUES ('22', 'version', 'global', 'APP版本', '0', '0', 'Admin.yy?a=Version');
