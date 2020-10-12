/*
 Navicat Premium Data Transfer

 Source Server         : locahost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : star

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 01/06/2019 02:45:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for form_field_value
-- ----------------------------
DROP TABLE IF EXISTS `form_field_value`;
CREATE TABLE `form_field_value` (
  `form_id` bigint(30) NOT NULL,
  `field_value_id` bigint(30) NOT NULL,
  `field_value` text COLLATE utf8mb4_croatian_ci,
  `create_time` varchar(30) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `update_time` varchar(30) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`form_id`,`field_value_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

SET FOREIGN_KEY_CHECKS = 1;
