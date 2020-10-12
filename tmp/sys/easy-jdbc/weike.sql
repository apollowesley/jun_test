/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-05-16 18:19:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for weike
-- ----------------------------
DROP TABLE IF EXISTS `weike`;
CREATE TABLE `weike` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `ctime` datetime DEFAULT NULL,
  `wk_img` varchar(255) DEFAULT NULL,
  `wk_href` varchar(255) DEFAULT NULL,
  `type` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of weike
-- ----------------------------
INSERT INTO `weike` VALUES ('1', '律师执业心之旅', '王慧', '2015-03-29 00:00:00', '1.jpg', 'http://mp.weixin.qq.com/s/lDV91JzNx2Vf0EfDhbvqJA', '1');
INSERT INTO `weike` VALUES ('2', '孤往的精神——读《一个悲观主义者的积极思考》有感', '王慧', '2015-03-30 00:00:00', '2.jpg', 'http://mp.weixin.qq.com/s/s1VNPVpt-8LH-qYKlBXj2A', '1');
INSERT INTO `weike` VALUES ('3', '孤往的精神（一）', '王慧', '2015-03-31 00:00:00', '2.jpg', 'http://mp.weixin.qq.com/s/3-66JhG6jDMKjBTDsrSMKw', '1');
INSERT INTO `weike` VALUES ('4', '孤往的精神（二）', '王慧', '2015-04-01 00:00:00', '2.jpg', 'http://mp.weixin.qq.com/s/3QDpUfpzR7mn_NUpXnHWYg', '1');
INSERT INTO `weike` VALUES ('5', '孤往的精神（三）', '王慧', '2015-04-02 00:00:00', '2.jpg', 'http://mp.weixin.qq.com/s/gqdU4GdOXz1lwWGsFKfJqg', '1');
INSERT INTO `weike` VALUES ('6', '孤往的精神（四）', '王慧', '2015-04-03 00:00:00', '2.jpg', 'https://mp.weixin.qq.com/s/pWRpDDx9A85sVRc2YBnPBA', '1');
INSERT INTO `weike` VALUES ('7', '孤往的精神（五）', '王慧', '2015-04-07 00:00:00', '2.jpg', 'http://mp.weixin.qq.com/s/3Oo3DVPxn5jhp6-PnbQX0w', '1');