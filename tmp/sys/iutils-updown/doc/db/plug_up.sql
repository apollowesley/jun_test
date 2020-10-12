/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50173
 Source Host           : localhost
 Source Database       : db_site_iutils

 Target Server Version : 50173
 File Encoding         : utf-8

 Date: 03/11/2017 15:21:40 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `plug_up`
-- ----------------------------
DROP TABLE IF EXISTS `plug_up`;
CREATE TABLE `plug_up` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `channel` varchar(50) NOT NULL COMMENT '渠道',
  `content_id` bigint(20) NOT NULL COMMENT '内容编号',
  `hits` int(11) NOT NULL COMMENT '点击数',
  `operator` text COMMENT '点击人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `status` char(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='支持表';

SET FOREIGN_KEY_CHECKS = 1;
