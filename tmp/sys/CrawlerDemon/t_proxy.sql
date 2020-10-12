/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : dynamic

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2015-10-08 20:27:22
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_proxy
-- ----------------------------
DROP TABLE IF EXISTS `t_proxy`;
CREATE TABLE `t_proxy` (
  `num_proxy_rowid`   INT(11) NOT NULL AUTO_INCREMENT,
  `vc_proxy_ip`       VARCHAR(30)
                      CHARACTER SET gbk
                      COLLATE gbk_bin  DEFAULT NULL,
  `num_proxy_port`    INT(11)          DEFAULT NULL,
  `vc_proxy_type`     VARCHAR(20)
                      CHARACTER SET gbk
                      COLLATE gbk_bin  DEFAULT NULL,
  `vc_proxy_position` VARCHAR(255)
                      CHARACTER SET gbk
                      COLLATE gbk_bin  DEFAULT NULL,
  `vc_inter`          CHAR(255)
                      CHARACTER SET gbk
                      COLLATE gbk_bin  DEFAULT '0',
  `dt_last_checktime` DATETIME         DEFAULT NULL,
  `dt_collect_time`   DATETIME         DEFAULT NULL,
  PRIMARY KEY (`num_proxy_rowid`),
  UNIQUE KEY `idx_proxy_index_1` (`vc_proxy_ip`, `num_proxy_port`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;
