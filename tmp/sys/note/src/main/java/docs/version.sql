/*
Navicat MariaDB Data Transfer

Source Server         : me
Source Server Version : 50532
Source Host           : localhost:3306
Source Database       : xjb

Target Server Type    : MariaDB
Target Server Version : 50532
File Encoding         : 65001

Date: 2014-08-22 22:39:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` char(5) NOT NULL DEFAULT '' COMMENT '版本12',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of version
-- ----------------------------
INSERT INTO `version` VALUES ('1', '1.01');
