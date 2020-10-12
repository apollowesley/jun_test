/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50173
 Source Host           : localhost
 Source Database       : db_site_iutils

 Target Server Version : 50173
 File Encoding         : utf-8

 Date: 03/11/2017 15:21:31 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `plug_comment`
-- ----------------------------
DROP TABLE IF EXISTS `plug_comment`;
CREATE TABLE `plug_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL COMMENT '父类编号',
  `parent_ids` varchar(100) NOT NULL COMMENT '父类编号集合',
  `channel` varchar(50) NOT NULL COMMENT '渠道',
  `content_id` bigint(20) NOT NULL COMMENT '内容编号',
  `content` varchar(255) NOT NULL COMMENT '评论内容',
  `report` int(11) DEFAULT NULL COMMENT '举报',
  `ip` varchar(100) DEFAULT NULL COMMENT 'IP',
  `create_by` bigint(20) DEFAULT NULL COMMENT '评论人',
  `create_date` datetime DEFAULT NULL COMMENT '评论时间',
  `status` char(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='评论表';

SET FOREIGN_KEY_CHECKS = 1;
