/*
Navicat MySQL Data Transfer

Source Server         : xin
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : xin

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2018-06-21 14:03:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单编号',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `source` varchar(50) NOT NULL COMMENT '来源',
  `product_id` bigint(20) NOT NULL COMMENT '产品ID',
  `product_name` varchar(100) NOT NULL COMMENT '产品名字',
  `unit_price` int(10) unsigned NOT NULL COMMENT '单价',
  `number` int(10) unsigned NOT NULL COMMENT '数量',
  `selling_price` int(11) DEFAULT NULL COMMENT '卖价',
  `state` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '0等待支付，1支付成功，2支付失败，3撤销',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '交易变化时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
