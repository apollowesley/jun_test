/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50622
Source Host           : localhost:3306
Source Database       : drds_1

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2016-09-24 14:41:23
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
-- Table structure for course04
-- ----------------------------
DROP TABLE IF EXISTS `course04`;
CREATE TABLE `course04` (
  `cId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cName` varchar(32) NOT NULL,
  `teacher` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`cId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成绩表';

-- ----------------------------
-- Records of course04
-- ----------------------------

-- ----------------------------
-- Table structure for course05
-- ----------------------------
DROP TABLE IF EXISTS `course05`;
CREATE TABLE `course05` (
  `cId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cName` varchar(32) NOT NULL,
  `teacher` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`cId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成绩表';

-- ----------------------------
-- Records of course05
-- ----------------------------

-- ----------------------------
-- Table structure for course06
-- ----------------------------
DROP TABLE IF EXISTS `course06`;
CREATE TABLE `course06` (
  `cId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cName` varchar(32) NOT NULL,
  `teacher` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`cId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成绩表';

-- ----------------------------
-- Records of course06
-- ----------------------------

-- ----------------------------
-- Table structure for course07
-- ----------------------------
DROP TABLE IF EXISTS `course07`;
CREATE TABLE `course07` (
  `cId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cName` varchar(32) NOT NULL,
  `teacher` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`cId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='成绩表';

-- ----------------------------
-- Records of course07
-- ----------------------------
INSERT INTO `course07` VALUES ('7', '语文', '张老师');

-- ----------------------------
-- Table structure for sclass04
-- ----------------------------
DROP TABLE IF EXISTS `sclass04`;
CREATE TABLE `sclass04` (
  `classId` bigint(20) NOT NULL AUTO_INCREMENT,
  `className` varchar(32) NOT NULL,
  `header` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Records of sclass04
-- ----------------------------

-- ----------------------------
-- Table structure for sclass05
-- ----------------------------
DROP TABLE IF EXISTS `sclass05`;
CREATE TABLE `sclass05` (
  `classId` bigint(20) NOT NULL AUTO_INCREMENT,
  `className` varchar(32) NOT NULL,
  `header` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Records of sclass05
-- ----------------------------

-- ----------------------------
-- Table structure for sclass06
-- ----------------------------
DROP TABLE IF EXISTS `sclass06`;
CREATE TABLE `sclass06` (
  `classId` bigint(20) NOT NULL AUTO_INCREMENT,
  `className` varchar(32) NOT NULL,
  `header` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Records of sclass06
-- ----------------------------

-- ----------------------------
-- Table structure for sclass07
-- ----------------------------
DROP TABLE IF EXISTS `sclass07`;
CREATE TABLE `sclass07` (
  `classId` bigint(20) NOT NULL AUTO_INCREMENT,
  `className` varchar(32) NOT NULL,
  `header` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Records of sclass07
-- ----------------------------

-- ----------------------------
-- Table structure for student04
-- ----------------------------
DROP TABLE IF EXISTS `student04`;
CREATE TABLE `student04` (
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
-- Records of student04
-- ----------------------------

-- ----------------------------
-- Table structure for student05
-- ----------------------------
DROP TABLE IF EXISTS `student05`;
CREATE TABLE `student05` (
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
-- Records of student05
-- ----------------------------

-- ----------------------------
-- Table structure for student06
-- ----------------------------
DROP TABLE IF EXISTS `student06`;
CREATE TABLE `student06` (
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
-- Records of student06
-- ----------------------------

-- ----------------------------
-- Table structure for student07
-- ----------------------------
DROP TABLE IF EXISTS `student07`;
CREATE TABLE `student07` (
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
-- Records of student07
-- ----------------------------

-- ----------------------------
-- Table structure for studentcourse04
-- ----------------------------
DROP TABLE IF EXISTS `studentcourse04`;
CREATE TABLE `studentcourse04` (
  `scId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cId` bigint(20) NOT NULL,
  `sId` bigint(20) NOT NULL,
  `score` double NOT NULL,
  `examDate` datetime NOT NULL,
  PRIMARY KEY (`scId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选课表';

-- ----------------------------
-- Records of studentcourse04
-- ----------------------------

-- ----------------------------
-- Table structure for studentcourse05
-- ----------------------------
DROP TABLE IF EXISTS `studentcourse05`;
CREATE TABLE `studentcourse05` (
  `scId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cId` bigint(20) NOT NULL,
  `sId` bigint(20) NOT NULL,
  `score` double NOT NULL,
  `examDate` datetime NOT NULL,
  PRIMARY KEY (`scId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选课表';

-- ----------------------------
-- Records of studentcourse05
-- ----------------------------

-- ----------------------------
-- Table structure for studentcourse06
-- ----------------------------
DROP TABLE IF EXISTS `studentcourse06`;
CREATE TABLE `studentcourse06` (
  `scId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cId` bigint(20) NOT NULL,
  `sId` bigint(20) NOT NULL,
  `score` double NOT NULL,
  `examDate` datetime NOT NULL,
  PRIMARY KEY (`scId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选课表';

-- ----------------------------
-- Records of studentcourse06
-- ----------------------------

-- ----------------------------
-- Table structure for studentcourse07
-- ----------------------------
DROP TABLE IF EXISTS `studentcourse07`;
CREATE TABLE `studentcourse07` (
  `scId` bigint(20) NOT NULL AUTO_INCREMENT,
  `cId` bigint(20) NOT NULL,
  `sId` bigint(20) NOT NULL,
  `score` double NOT NULL,
  `examDate` datetime NOT NULL,
  PRIMARY KEY (`scId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='选课表';

-- ----------------------------
-- Records of studentcourse07
-- ----------------------------
INSERT INTO `studentcourse07` VALUES ('7', '7', '1', '56.85', '2016-02-23 22:06:46');
