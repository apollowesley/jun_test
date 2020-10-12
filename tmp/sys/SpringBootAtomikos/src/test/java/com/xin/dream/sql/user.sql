/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.206
Source Server Version : 50631
Source Host           : 192.168.1.206:3306
Source Database       : testTwo

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2018-01-18 17:56:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) NOT NULL COMMENT '登录名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `level` bigint(20) DEFAULT NULL COMMENT '级别',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `note` varchar(255) DEFAULT NULL COMMENT '备注，说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
