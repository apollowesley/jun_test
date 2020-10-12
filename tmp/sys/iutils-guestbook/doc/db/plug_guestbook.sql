/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50173
 Source Host           : localhost
 Source Database       : db_site_iutils

 Target Server Version : 50173
 File Encoding         : utf-8

 Date: 03/11/2017 12:33:05 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `plug_guestbook`
-- ----------------------------
DROP TABLE IF EXISTS `plug_guestbook`;
CREATE TABLE `plug_guestbook` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `channel` varchar(50) NOT NULL COMMENT '渠道',
  `type` varchar(10) DEFAULT NULL COMMENT '留言分类 咨询 建议 投诉 其他',
  `content` varchar(255) NOT NULL COMMENT '留言内容',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(100) DEFAULT NULL COMMENT '电话',
  `report` int(11) DEFAULT NULL COMMENT '举报',
  `ip` varchar(100) DEFAULT NULL COMMENT 'IP',
  `create_date` datetime DEFAULT NULL COMMENT '留言时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '回复人',
  `update_date` datetime DEFAULT NULL COMMENT '回复时间',
  `re_content` varchar(255) DEFAULT NULL COMMENT '回复内容',
  `status` char(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='留言板';

SET FOREIGN_KEY_CHECKS = 1;
