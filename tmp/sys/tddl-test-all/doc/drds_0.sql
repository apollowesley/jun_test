/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50622
Source Host           : localhost:3306
Source Database       : drds_0

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2016-09-24 14:41:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for broadcast
-- ----------------------------
DROP TABLE IF EXISTS `broadcast`;
CREATE TABLE `broadcast` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bname` varchar(32) NOT NULL,
  `school` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='测试广播表';

-- ----------------------------
-- Records of broadcast
-- ----------------------------
INSERT INTO `broadcast` VALUES ('2', '李四', '某二学校');

-- ----------------------------
-- Table structure for course00
-- ----------------------------
DROP TABLE IF EXISTS `course00`;
CREATE TABLE `course00` (
  `cId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cName` varchar(32) NOT NULL,
  `teacher` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`cId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成绩表';

-- ----------------------------
-- Records of course00
-- ----------------------------

-- ----------------------------
-- Table structure for course01
-- ----------------------------
DROP TABLE IF EXISTS `course01`;
CREATE TABLE `course01` (
  `cId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cName` varchar(32) NOT NULL,
  `teacher` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`cId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成绩表';

-- ----------------------------
-- Records of course01
-- ----------------------------

-- ----------------------------
-- Table structure for course02
-- ----------------------------
DROP TABLE IF EXISTS `course02`;
CREATE TABLE `course02` (
  `cId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cName` varchar(32) NOT NULL,
  `teacher` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`cId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成绩表';

-- ----------------------------
-- Records of course02
-- ----------------------------

-- ----------------------------
-- Table structure for course03
-- ----------------------------
DROP TABLE IF EXISTS `course03`;
CREATE TABLE `course03` (
  `cId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cName` varchar(32) NOT NULL,
  `teacher` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`cId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成绩表';

-- ----------------------------
-- Records of course03
-- ----------------------------

-- ----------------------------
-- Table structure for sclass00
-- ----------------------------
DROP TABLE IF EXISTS `sclass00`;
CREATE TABLE `sclass00` (
  `classId` bigint(20) NOT NULL AUTO_INCREMENT,
  `className` varchar(32) NOT NULL,
  `header` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Records of sclass00
-- ----------------------------

-- ----------------------------
-- Table structure for sclass01
-- ----------------------------
DROP TABLE IF EXISTS `sclass01`;
CREATE TABLE `sclass01` (
  `classId` bigint(20) NOT NULL AUTO_INCREMENT,
  `className` varchar(32) NOT NULL,
  `header` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Records of sclass01
-- ----------------------------

-- ----------------------------
-- Table structure for sclass02
-- ----------------------------
DROP TABLE IF EXISTS `sclass02`;
CREATE TABLE `sclass02` (
  `classId` bigint(20) NOT NULL AUTO_INCREMENT,
  `className` varchar(32) NOT NULL,
  `header` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Records of sclass02
-- ----------------------------

-- ----------------------------
-- Table structure for sclass03
-- ----------------------------
DROP TABLE IF EXISTS `sclass03`;
CREATE TABLE `sclass03` (
  `classId` bigint(20) NOT NULL AUTO_INCREMENT,
  `className` varchar(32) NOT NULL,
  `header` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Records of sclass03
-- ----------------------------

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `value` bigint(20) NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='sequence表';

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('1', 'studentId', '13042', '2016-01-24 05:12:17');
INSERT INTO `sequence` VALUES ('2', 'courseId', '1', '2015-12-04 05:17:25');
INSERT INTO `sequence` VALUES ('3', 'ni', '1500', '2016-01-24 05:12:14');
INSERT INTO `sequence` VALUES ('4', 'AUTO_SEQ_COURSE', '3000', '2016-02-23 13:15:57');
INSERT INTO `sequence` VALUES ('5', 'AUTO_SEQ_BROADCAST', '1000', '2016-02-22 20:59:53');
INSERT INTO `sequence` VALUES ('6', 'AUTO_SEQ_SCLASS', '2000', '2016-02-22 23:12:14');
INSERT INTO `sequence` VALUES ('7', 'AUTO_SEQ_STUDENT', '2000', '2016-09-23 17:32:24');

-- ----------------------------
-- Table structure for student00
-- ----------------------------
DROP TABLE IF EXISTS `student00`;
CREATE TABLE `student00` (
  `sId` bigint(20) NOT NULL AUTO_INCREMENT,
  `sName` varchar(32) NOT NULL,
  `gender` int(2) DEFAULT NULL,
  `classId` bigint(20) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `phoneNum` varchar(32) DEFAULT NULL,
  `bId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生表';

-- ----------------------------
-- Records of student00
-- ----------------------------

-- ----------------------------
-- Table structure for student01
-- ----------------------------
DROP TABLE IF EXISTS `student01`;
CREATE TABLE `student01` (
  `sId` bigint(20) NOT NULL AUTO_INCREMENT,
  `sName` varchar(32) NOT NULL,
  `gender` int(2) DEFAULT NULL,
  `classId` bigint(20) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `phoneNum` varchar(32) DEFAULT NULL,
  `bId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sId`)
) ENGINE=InnoDB AUTO_INCREMENT=2002 DEFAULT CHARSET=utf8 COMMENT='学生表';

-- ----------------------------
-- Records of student01
-- ----------------------------
INSERT INTO `student01` VALUES ('1', '张三', '1', '7', 'zhangsan@126.com', '18612482592', '2');
INSERT INTO `student01` VALUES ('1001', '王五', '2', '3', null, null, null);
INSERT INTO `student01` VALUES ('2001', '李四', '1', '7', null, null, null);

-- ----------------------------
-- Table structure for student02
-- ----------------------------
DROP TABLE IF EXISTS `student02`;
CREATE TABLE `student02` (
  `sId` bigint(20) NOT NULL AUTO_INCREMENT,
  `sName` varchar(32) NOT NULL,
  `gender` int(2) DEFAULT NULL,
  `classId` bigint(20) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `phoneNum` varchar(32) DEFAULT NULL,
  `bId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生表';

-- ----------------------------
-- Records of student02
-- ----------------------------

-- ----------------------------
-- Table structure for student03
-- ----------------------------
DROP TABLE IF EXISTS `student03`;
CREATE TABLE `student03` (
  `sId` bigint(20) NOT NULL AUTO_INCREMENT,
  `sName` varchar(32) NOT NULL,
  `gender` int(2) DEFAULT NULL,
  `classId` bigint(20) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `phoneNum` varchar(32) DEFAULT NULL,
  `bId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生表';

-- ----------------------------
-- Records of student03
-- ----------------------------

-- ----------------------------
-- Table structure for studentcourse00
-- ----------------------------
DROP TABLE IF EXISTS `studentcourse00`;
CREATE TABLE `studentcourse00` (
  `scId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cId` bigint(20) NOT NULL,
  `sId` bigint(20) NOT NULL,
  `score` double NOT NULL,
  `examDate` datetime NOT NULL,
  PRIMARY KEY (`scId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选课表';

-- ----------------------------
-- Records of studentcourse00
-- ----------------------------

-- ----------------------------
-- Table structure for studentcourse01
-- ----------------------------
DROP TABLE IF EXISTS `studentcourse01`;
CREATE TABLE `studentcourse01` (
  `scId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cId` bigint(20) NOT NULL,
  `sId` bigint(20) NOT NULL,
  `score` double NOT NULL,
  `examDate` datetime NOT NULL,
  PRIMARY KEY (`scId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选课表';

-- ----------------------------
-- Records of studentcourse01
-- ----------------------------

-- ----------------------------
-- Table structure for studentcourse02
-- ----------------------------
DROP TABLE IF EXISTS `studentcourse02`;
CREATE TABLE `studentcourse02` (
  `scId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cId` bigint(20) NOT NULL,
  `sId` bigint(20) NOT NULL,
  `score` double NOT NULL,
  `examDate` datetime NOT NULL,
  PRIMARY KEY (`scId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选课表';

-- ----------------------------
-- Records of studentcourse02
-- ----------------------------

-- ----------------------------
-- Table structure for studentcourse03
-- ----------------------------
DROP TABLE IF EXISTS `studentcourse03`;
CREATE TABLE `studentcourse03` (
  `scId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cId` bigint(20) NOT NULL,
  `sId` bigint(20) NOT NULL,
  `score` double NOT NULL,
  `examDate` datetime NOT NULL,
  PRIMARY KEY (`scId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选课表';

-- ----------------------------
-- Records of studentcourse03
-- ----------------------------
