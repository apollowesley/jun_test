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

 Date: 01/06/2019 02:45:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for form_info
-- ----------------------------
DROP TABLE IF EXISTS `form_info`;
CREATE TABLE `form_info` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT 'form_id',
  `name` varchar(255) COLLATE utf8mb4_croatian_ci NOT NULL,
  `fields` varchar(255) COLLATE utf8mb4_croatian_ci NOT NULL,
  `create_time` varchar(30) COLLATE utf8mb4_croatian_ci DEFAULT '',
  `update_time` varchar(30) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1134518474701246467 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

SET FOREIGN_KEY_CHECKS = 1;
