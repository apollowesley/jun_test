/*
Navicat MySQL Data Transfer

Source Server         : xin
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : xin

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2018-05-25 17:10:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `login_name` varchar(40) DEFAULT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '秘密',
  `nickname` varchar(50) NOT NULL COMMENT '昵称',
  `type` int(10) unsigned DEFAULT NULL COMMENT '类型',
  `state` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '状态：-1失败，0等待,1成功',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_uid` bigint(20) DEFAULT '0' COMMENT '修改人用户ID',
  `login_ip` varchar(50) DEFAULT NULL COMMENT '登录IP地址',
  `login_addr` varchar(100) DEFAULT NULL COMMENT '登录地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123123', 'ADMIN', '1', '1', '超级管理员', '2018-04-28 15:15:46', '2018-04-28 15:16:37', '1', null, null);
