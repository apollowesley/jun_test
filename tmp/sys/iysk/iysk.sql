/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50505
 Source Host           : localhost
 Source Database       : iysk

 Target Server Type    : MySQL
 Target Server Version : 50505
 File Encoding         : utf-8

 Date: 08/23/2016 11:22:31 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `i_active`
-- ----------------------------
DROP TABLE IF EXISTS `i_active`;
CREATE TABLE `i_active` (
  `id` varchar(255) NOT NULL,
  `activeTime` int(11) DEFAULT NULL,
  `mac` varchar(127) DEFAULT NULL,
  `iy` smallint(6) DEFAULT NULL,
  `channel` varchar(127) DEFAULT NULL,
  `callback` varchar(255) DEFAULT NULL,
  `device` varchar(127) DEFAULT NULL,
  `os` varchar(127) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
