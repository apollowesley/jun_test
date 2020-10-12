/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : assessment

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-11-07 09:20:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `uid` varchar(32) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for indicator
-- ----------------------------
DROP TABLE IF EXISTS `indicator`;
CREATE TABLE `indicator` (
  `uid` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `type_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0为指标，1为观测点',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of indicator
-- ----------------------------
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca7273e00000', '生源情况', '2018-10-31 22:06:30', '2018-10-31 22:06:30', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca72d5960001', '培养模式', '2018-10-31 22:06:55', '2018-10-31 22:06:55', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca7327000002', '招生录取情况', '2018-10-31 22:07:16', '2018-10-31 22:07:16', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca7368fe0003', '培养方案', '2018-10-31 22:07:33', '2018-10-31 22:07:33', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca74d9430004', '近四年国家统一高考录取学生入学平均分', '2018-11-04 14:28:16', '2018-11-04 14:28:16', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca757b3a0005', '近四年国家统一高考录取的学生第一志愿录取率', '2018-11-04 14:27:26', '2018-11-04 14:27:26', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca75c8670006', '培养目标', '2018-10-31 22:10:08', '2018-10-31 22:10:08', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca75f6140007', '课程体系', '2018-10-31 22:10:20', '2018-10-31 22:10:20', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca76eb0f0008', '培养目标和规格要求与专业人才培养定位、专业设置的符合程度', '2018-11-04 14:29:33', '2018-11-04 14:29:33', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca77bc400009', '毕业生知识、能力和素质需求的支持程度', '2018-10-31 22:12:16', '2018-10-31 22:12:16', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca7e011c000a', '教学计划中专业主干课程和主要专业课程对知识和能力需求的支持程度', '2018-10-31 22:19:07', '2018-10-31 22:19:07', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca7e5aec000b', '培养模式改革创新', '2018-10-31 22:19:30', '2018-10-31 22:19:30', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca7e93f9000c', '教学资源', '2018-10-31 22:19:45', '2018-10-31 22:19:45', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca7f8a41000d', '专业人才培养模式改革创新和具体措施和实施效果', '2018-10-31 22:20:48', '2018-10-31 22:20:48', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca7fd0b4000e', '专业生师比', '2018-10-31 22:21:19', '2018-10-31 22:21:19', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca80604e000f', '博士学位教师比例', '2018-10-31 22:21:42', '2018-10-31 22:21:42', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca80db780010', '专业师资基本情况', '2018-10-31 22:22:14', '2018-10-31 22:22:14', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca811d5e0011', '高层次教师情况', '2018-10-31 22:22:31', '2018-10-31 22:22:31', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca819ae60012', '近四年本专业本科生授课情况', '2018-10-31 22:23:03', '2018-10-31 22:23:03', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca824d440013', '近四年本专业教授为本专业本科生授课的授课率', '2018-10-31 22:23:49', '2018-10-31 22:23:49', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8308150014', '近四年由本专业高级职称承担的专业课比例', '2018-10-31 22:24:36', '2018-10-31 22:24:36', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca83b0730015', '具有行业经历专任教师比例', '2018-10-31 22:25:19', '2018-10-31 22:25:19', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca84366e0016', '专业教师科研情况', '2018-10-31 22:25:54', '2018-10-31 22:25:54', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca84d9500017', '中青年教师参加实践教学能力培训比例', '2018-10-31 22:26:35', '2018-10-31 22:26:35', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca854a220018', '近四年教师发表学术论文情况', '2018-10-31 22:27:04', '2018-10-31 22:27:04', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca85d2de0019', '近四年教师获得省部级以上科研奖励情况', '2018-10-31 22:27:39', '2018-10-31 22:27:39', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8664d6001a', '近四年教师发表教研论文数量', '2018-10-31 22:28:17', '2018-10-31 22:28:17', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca872255001b', '教师教研情况', '2018-10-31 22:29:05', '2018-10-31 22:29:05', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca877268001c', '实验实践教学条件', '2018-10-31 22:29:26', '2018-10-31 22:29:26', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca879fec001d', '图书资料', '2018-10-31 22:29:37', '2018-10-31 22:29:37', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca88189d001e', '本科教学工程与教学成果奖', '2018-10-31 22:31:27', '2018-10-31 22:31:27', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8881df001f', '教学质量保障', '2018-10-31 22:30:35', '2018-10-31 22:30:35', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca89cc740020', '培养效果', '2018-10-31 22:32:00', '2018-10-31 22:32:00', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8a0d4a0021', '专业特色', '2018-10-31 22:32:16', '2018-10-31 22:32:16', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8a841b0022', '就业情况与培养质量', '2018-10-31 22:32:47', '2018-10-31 22:32:47', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8adb010023', '在校学生的综合素质', '2018-10-31 22:33:09', '2018-10-31 22:33:09', '0', '0');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8b36950024', '专业特色、实施过程和效果', '2018-10-31 22:33:33', '2018-10-31 22:33:33', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8bd29c0025', '质量评价', '2018-10-31 22:34:13', '2018-10-31 22:34:13', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8c0c1b0026', '反馈及效果', '2018-10-31 22:34:27', '2018-10-31 22:34:27', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8c57d40027', '近四年就业率', '2018-10-31 22:34:47', '2018-10-31 22:34:47', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8cb2d40028', '十名优秀校友简介', '2018-10-31 22:35:10', '2018-10-31 22:35:10', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8d9a6f0029', '近四年参加创新创业活动和参与科研项目学生人数与在校总数比值', '2018-10-31 22:36:09', '2018-10-31 22:36:09', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8e400f002a', '近四年学生获奖省级以上各类竞赛奖励情况', '2018-10-31 22:36:52', '2018-10-31 22:36:52', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8ef4be002b', '近四年学生发表的学术论文及专利授权情况', '2018-10-31 22:37:38', '2018-10-31 22:37:38', '0', '1');
INSERT INTO `indicator` VALUES ('297efed566ca6ec00166ca8f5e0a002c', '五名优秀在校生的简介', '2018-10-31 22:38:05', '2018-10-31 22:38:05', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dc8a1f6a001c', '近四年教师获得省部级以上科研奖励情况', '2018-11-04 10:25:31', '2018-11-04 10:25:31', '1', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dc8ca6fe001e', '近四年教师主持科研课题情况', '2018-11-04 10:28:17', '2018-11-04 10:28:17', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dc95e76f0021', '近十年教师主持编写本专业教材情况', '2018-11-04 10:38:23', '2018-11-04 10:38:23', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dc96b3440022', '近十年教师主持省部级以上教研项目情况', '2018-11-04 10:39:15', '2018-11-04 10:39:15', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dc9861230025', '现有教学仪器设备（含软件）均值', '2018-11-04 10:41:05', '2018-11-04 10:41:05', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dc993b4d0026', '近四年新增教学实验仪器设备（含软件）均值', '2018-11-04 10:42:01', '2018-11-04 10:42:01', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dc9c32170029', '近四年校外实习实践基地数量及各基地实习学生人次数与专业在校学生总数比值', '2018-11-04 10:45:15', '2018-11-04 10:45:15', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dc9d8200002b', '现有生均专业纸质图书资料册数', '2018-11-04 10:46:41', '2018-11-04 10:46:41', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dc9ee8e1002c', '现有专业电子图书资料的个数', '2018-11-04 10:48:13', '2018-11-04 10:48:13', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dc9f8d5a002d', '历年省级以上本科教学工程项目', '2018-11-04 10:48:55', '2018-11-04 10:48:55', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dc9ffbe5002e', '历年省级以上教学成果奖', '2018-11-04 10:49:24', '2018-11-04 10:49:24', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dca08616002f', '质量监控', '2018-11-04 10:49:59', '2018-11-04 10:49:59', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dca751e0003c', '近四年学生获省级以上各竞赛奖励情况', '2018-11-04 10:57:24', '2018-11-04 10:57:24', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dca84b30003d', '近四年学生发表学术论文及专利授权情况', '2018-11-04 10:58:28', '2018-11-04 10:58:28', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dd6be235006c', '课程设置与培养目标的吻合程度', '2018-11-04 14:32:06', '2018-11-04 14:32:06', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dc68330166dd6c614c006d', '课程设置对知识、能力和素质需求的支持程度', '2018-11-04 14:32:48', '2018-11-04 14:32:48', '0', '1');
INSERT INTO `indicator` VALUES ('2c92e55966dd72bd0166dd7e56f20002', '专业国际化人才培养的改革措施与实施效果', '2018-11-04 14:52:16', '2018-11-04 14:52:16', '0', '1');
INSERT INTO `indicator` VALUES ('4028fe8166e8e33f0166e8f2da820006', '教师科研情况', '2018-11-06 20:15:21', '2018-11-06 20:15:21', '0', '0');
INSERT INTO `indicator` VALUES ('4028fe8166e8e33f0166e91cc7cf001d', '教学成果奖', '2018-11-06 21:01:09', '2018-11-06 21:01:09', '0', '0');
INSERT INTO `indicator` VALUES ('4028fe8166e91f1d0166e929d9ea0005', '质量保障体系', '2018-11-06 21:15:26', '2018-11-06 21:15:26', '0', '0');
INSERT INTO `indicator` VALUES ('4028fe8166e91f1d0166e93397a40007', '本科教学工程项目', '2018-11-06 21:26:04', '2018-11-06 21:26:04', '0', '0');

-- ----------------------------
-- Table structure for indicator_grade
-- ----------------------------
DROP TABLE IF EXISTS `indicator_grade`;
CREATE TABLE `indicator_grade` (
  `uid` varchar(32) NOT NULL,
  `reference_uid` varchar(32) NOT NULL,
  `grade` double NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `indicator_uid` varchar(32) NOT NULL,
  `profession_uid` varchar(32) DEFAULT NULL COMMENT '专业UID',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of indicator_grade
-- ----------------------------
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcab925a0041', '2c92e55966dc68330166dc7d07a20007', '80', '2018-11-04 11:02:03', '2018-11-04 11:02:03', '0', '297efed566ca6ec00166ca757b3a0005', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcac6fa60042', '2c92e55966dc68330166dc79344e0005', '100', '2018-11-04 11:03:00', '2018-11-04 11:03:00', '0', '297efed566ca6ec00166ca74d9430004', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcad10530043', '2c92e55966dc68330166dc815f4e000e', '60', '2018-11-04 11:03:41', '2018-11-04 11:03:41', '0', '297efed566ca6ec00166ca76eb0f0008', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcaf489e0046', '2c92e55966dc68330166dc81f85f000f', '80', '2018-11-04 11:06:06', '2018-11-04 11:06:06', '0', '297efed566ca6ec00166ca77bc400009', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcafde3e0047', '2c92e55966dc68330166dc82ec010010', '80', '2018-11-04 11:06:45', '2018-11-04 11:06:45', '0', '297efed566ca6ec00166ca7e011c000a', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcb0b1080048', '2c92e55966dc68330166dc83e3880012', '75', '2018-11-04 11:07:39', '2018-11-04 11:07:39', '0', '297efed566ca6ec00166ca7f8a41000d', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcb1849d0049', '2c92e55966dc68330166dc84669f0013', '85', '2018-11-04 11:08:33', '2018-11-04 11:08:33', '0', '297efed566ca6ec00166ca7fd0b4000e', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcb1c7bc004a', '2c92e55966dc68330166dc851e940014', '65', '2018-11-04 11:08:50', '2018-11-04 11:08:50', '0', '297efed566ca6ec00166ca80604e000f', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcb23622004b', '2c92e55966dc68330166dc858b5a0015', '80', '2018-11-04 11:09:18', '2018-11-04 11:09:18', '0', '297efed566ca6ec00166ca811d5e0011', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcb2ab92004c', '2c92e55966dc68330166dc85eff50016', '75', '2018-11-04 11:09:48', '2018-11-04 11:09:48', '0', '297efed566ca6ec00166ca824d440013', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcb2f2ff004d', '2c92e55966dc68330166dc8673d00017', '80', '2018-11-04 11:10:07', '2018-11-04 11:10:07', '0', '297efed566ca6ec00166ca8308150014', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcb3c70d004e', '2c92e55966dc68330166dc86fef30018', '80', '2018-11-04 11:11:01', '2018-11-04 11:11:01', '0', '297efed566ca6ec00166ca83b0730015', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcb418dd004f', '2c92e55966dc68330166dc876bd60019', '80', '2018-11-04 11:11:22', '2018-11-04 11:11:22', '0', '297efed566ca6ec00166ca84d9500017', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcb796af0051', '2c92e55966dc68330166dcb720670050', '65', '2018-11-04 11:15:11', '2018-11-04 11:15:11', '0', '297efed566ca6ec00166ca854a220018', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcb841f70052', '2c92e55966dc68330166dc8b3d73001d', '80', '2018-11-04 11:15:55', '2018-11-04 11:15:55', '0', '297efed566ca6ec00166ca85d2de0019', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcb94dec0053', '2c92e55966dc68330166dc93ca60001f', '80', '2018-11-04 11:17:03', '2018-11-04 11:17:03', '0', '2c92e55966dc68330166dc8ca6fe001e', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcb9c7890054', '2c92e55966dc68330166dc9437690020', '80', '2018-11-04 11:18:20', '2018-11-04 11:18:20', '0', '297efed566ca6ec00166ca8664d6001a', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcbb75430055', '2c92e55966dc68330166dc97119a0023', '90', '2018-11-04 11:19:24', '2018-11-04 11:19:24', '0', '2c92e55966dc68330166dc95e76f0021', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcbc0dbb0056', '2c92e55966dc68330166dca1512b0030', '60', '2018-11-04 11:20:03', '2018-11-04 11:20:03', '0', '2c92e55966dc68330166dc9861230025', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcbc8e4e0057', '2c92e55966dc68330166dc9a4c800028', '90', '2018-11-04 11:20:36', '2018-11-04 11:20:36', '0', '2c92e55966dc68330166dc993b4d0026', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcbd32ba0058', '2c92e55966dc68330166dc9c81c5002a', '80', '2018-11-04 11:21:18', '2018-11-04 11:21:18', '0', '2c92e55966dc68330166dc9c32170029', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcbd94370059', '2c92e55966dc68330166dca2435f0032', '75', '2018-11-04 11:21:43', '2018-11-04 11:21:43', '0', '2c92e55966dc68330166dc9d8200002b', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcbefddb005a', '2c92e55966dc68330166dca2435f0032', '75', '2018-11-04 11:23:16', '2018-11-04 11:23:16', '0', '2c92e55966dc68330166dc9ee8e1002c', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcbf7520005b', '2c92e55966dc68330166dca2f3ae0033', '60', '2018-11-04 11:23:46', '2018-11-04 11:23:46', '0', '2c92e55966dc68330166dc9f8d5a002d', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcbfc374005c', '2c92e55966dc68330166dca3c2580035', '80', '2018-11-04 11:24:06', '2018-11-04 11:24:06', '0', '2c92e55966dc68330166dca08616002f', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcbfffea005d', '2c92e55966dc68330166dca44e6d0036', '60', '2018-11-04 11:24:22', '2018-11-04 11:24:22', '0', '297efed566ca6ec00166ca8bd29c0025', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcc05010005e', '2c92e55966dc68330166dca4a7cc0037', '74', '2018-11-04 11:24:42', '2018-11-04 11:24:42', '0', '297efed566ca6ec00166ca8c0c1b0026', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcc0a79c005f', '2c92e55966dc68330166dca502110038', '80', '2018-11-04 11:25:05', '2018-11-04 11:25:05', '0', '297efed566ca6ec00166ca8c57d40027', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcc111610060', '2c92e55966dc68330166dca58c2c0039', '80', '2018-11-04 11:25:32', '2018-11-04 11:25:32', '0', '297efed566ca6ec00166ca8cb2d40028', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcc185920061', '2c92e55966dc68330166dca5e4c8003a', '70', '2018-11-04 11:26:02', '2018-11-04 11:26:02', '0', '297efed566ca6ec00166ca8d9a6f0029', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcc276f80062', '2c92e55966dc68330166dca673bb003b', '75', '2018-11-04 11:27:03', '2018-11-04 11:27:03', '0', '2c92e55966dc68330166dca751e0003c', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcc2ccf70063', '2c92e55966dc68330166dca978f7003e', '80', '2018-11-04 11:27:25', '2018-11-04 11:27:25', '0', '2c92e55966dc68330166dca84b30003d', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcc327740064', '2c92e55966dc68330166dcaa14df003f', '80', '2018-11-04 11:27:49', '2018-11-04 11:27:49', '0', '297efed566ca6ec00166ca8f5e0a002c', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('2c92e55966dc68330166dcc36d650065', '2c92e55966dc68330166dcaa6ee60040', '80', '2018-11-04 11:28:07', '2018-11-04 11:28:07', '0', '297efed566ca6ec00166ca8b36950024', '297efed566ca6ec00166ca905a15002d');
INSERT INTO `indicator_grade` VALUES ('4028fe8166e999ef0166ebb857e50001', '2c92e55966dc68330166dca34d360034', '70', '2018-11-07 09:10:18', '2018-11-07 09:10:18', '0', '2c92e55966dc68330166dc9ffbe5002e', '297efed566ca6ec00166ca905a15002d');

-- ----------------------------
-- Table structure for indicator_grade_reference
-- ----------------------------
DROP TABLE IF EXISTS `indicator_grade_reference`;
CREATE TABLE `indicator_grade_reference` (
  `uid` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `indicator_uid` varchar(32) NOT NULL,
  `grade` double NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of indicator_grade_reference
-- ----------------------------
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc781ce10003', '高考录取的学生入学平均分500-520', '高考录取的学生入学平均分', '297efed566ca6ec00166ca74d9430004', '60', '2018-11-04 10:05:51', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc787bba0004', '高考录取的学生入学平均分520-550', '高考录取的学生入学平均分', '297efed566ca6ec00166ca74d9430004', '70', '2018-11-04 10:06:15', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc79344e0005', '高考录取的学生入学平均分550-580', '高考录取的学生入学平均分', '297efed566ca6ec00166ca74d9430004', '100', '2018-11-04 10:07:02', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc7cb7a40006', '统一高考录取的第一志愿率60%以上', '统一高考录取的第一志愿率', '297efed566ca6ec00166ca757b3a0005', '60', '2018-11-04 10:10:52', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc7d07a20007', '统一高考录取的第一志愿率70%以上', '统一高考录取的第一志愿率', '297efed566ca6ec00166ca757b3a0005', '80', '2018-11-04 10:11:13', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc7d71f10008', '统一高考录取的第一志愿率80%以上', '统一高考录取的第一志愿率', '297efed566ca6ec00166ca757b3a0005', '100', '2018-11-04 10:11:40', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc7f2664000a', '培养目标和规格要求与专业人才培养定位、专业设置的符合程度80分', '培养目标和专业人才符合程度', '297efed566ca6ec00166ca76eb0f0008', '80', '2018-11-04 14:42:29', null, '1');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc7f5633000b', '培养目标和规格要求与专业人才培养定位、专业设置的符合程度100', '培养目标和专业人才符合程度', '297efed566ca6ec00166ca76eb0f0008', '100', '2018-11-04 14:41:50', null, '1');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc8028af000c', '毕业生知识、能力和素质需求的支持程度60', '毕业生知识、能力和素质需求的支持程度', '297efed566ca6ec00166ca77bc400009', '60', '2018-11-04 10:14:38', null, '1');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc805c01000d', '毕业生知识、能力和素质需求的支持程度80', '毕业生知识、能力和素质需求的支持程度', '297efed566ca6ec00166ca77bc400009', '80', '2018-11-04 14:47:07', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc815f4e000e', '培养目标和规格要求与专业人才培养定位、专业设置的符合程度60', '培养目标和专业人才符合程度', '297efed566ca6ec00166ca76eb0f0008', '60', '2018-11-04 10:15:58', null, '1');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc81f85f000f', '毕业生知识、能力和素质需求的支持程度80', '毕业生知识、能力和素质需求的支持程度', '297efed566ca6ec00166ca77bc400009', '80', '2018-11-04 10:16:37', null, '1');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc82ec010010', '教学计划中专业主干课程和主要专业课程对知识和能力需求的支持程度80', '教学计划中专业主干课程和主要专业课程对知识和能力需求的支持程度', '297efed566ca6ec00166ca7e011c000a', '80', '2018-11-04 14:51:01', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc8350620011', '专业人才培养模式改革创新和具体措施和实施效果90', '专业人才培养模式改革创新和具体措施和实施效果', '297efed566ca6ec00166ca7f8a41000d', '90', '2018-11-04 10:18:05', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc83e3880012', '专业人才培养模式改革创新和具体措施和实施效果75', '专业人才培养模式改革创新和具体措施和实施效果', '297efed566ca6ec00166ca7f8a41000d', '75', '2018-11-04 10:18:42', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc84669f0013', '专业生师比85', '专业生师比', '297efed566ca6ec00166ca7fd0b4000e', '85', '2018-11-04 10:19:16', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc851e940014', '博士学位教师比例65', '博士学位教师比例', '297efed566ca6ec00166ca80604e000f', '65', '2018-11-04 10:20:03', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc858b5a0015', '高层次教师情况80', '高层次教师情况', '297efed566ca6ec00166ca811d5e0011', '80', '2018-11-04 10:20:31', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc85eff50016', '近四年本专业教授为本专业本科生授课的授课率75', '近四年本专业教授为本专业本科生授课的授课率', '297efed566ca6ec00166ca824d440013', '75', '2018-11-04 10:20:57', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc8673d00017', '近四年由本专业高级职称承担的专业课比例80', '近四年由本专业高级职称承担的专业课比例80', '297efed566ca6ec00166ca8308150014', '80', '2018-11-04 10:21:30', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc86fef30018', '具有行业经历专任教师比例80', '具有行业经历专任教师比例', '297efed566ca6ec00166ca83b0730015', '80', '2018-11-04 10:22:06', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc876bd60019', '中青年教师参加实践教学能力培训比例80', '中青年教师参加实践教学能力培训比例', '297efed566ca6ec00166ca84d9500017', '80', '2018-11-04 10:22:34', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc87e239001a', '近四年教师发表教研论文数量80', '近四年教师发表教研论文数量', '297efed566ca6ec00166ca8664d6001a', '80', '2018-11-04 10:23:04', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc8865c9001b', '近四年教师获得省部级以上科研奖励情况80', '近四年教师获得省部级以上科研奖励情况', '297efed566ca6ec00166ca85d2de0019', '80', '2018-11-04 10:23:38', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc8b3d73001d', '近四年教师获得省部级以上科研奖励情况80', '近四年教师获得省部级以上科研奖励情况', '297efed566ca6ec00166ca85d2de0019', '80', '2018-11-04 10:26:44', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc93ca60001f', '近四年教师主持科研课题情况80', '近四年教师主持科研课题情况', '2c92e55966dc68330166dc8ca6fe001e', '80', '2018-11-04 10:36:05', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc9437690020', '近四年教师发表教研论文数量80', '近四年教师发表教研论文数量', '297efed566ca6ec00166ca8664d6001a', '80', '2018-11-04 10:36:33', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc97119a0023', '近十年教师主持编写本专业教材情况90', '近十年教师主持编写本专业教材情况', '2c92e55966dc68330166dc95e76f0021', '90', '2018-11-04 10:39:39', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc9774de0024', '近十年教师主持省部级以上教研项目情况80', '近十年教师主持省部级以上教研项目情况', '2c92e55966dc68330166dc96b3440022', '80', '2018-11-04 10:40:05', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc99cc880027', '现有教学仪器设备（含软件）均值80', '现有教学仪器设备（含软件）均值', '2c92e55966dc68330166dc9861230025', '80', '2018-11-04 10:42:38', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc9a4c800028', '近四年新增教学实验仪器设备（含软件）均值90', '近四年新增教学实验仪器设备（含软件）均值', '2c92e55966dc68330166dc993b4d0026', '90', '2018-11-04 10:43:11', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dc9c81c5002a', '近四年校外实习实践基地数量及各基地实习学生人次数与专业在校学生总数比值80', '近四年校外实习实践基地数量及各基地实习学生人次数与专业在校学生总数比值', '2c92e55966dc68330166dc9c32170029', '80', '2018-11-04 10:45:36', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca1512b0030', '现有教学仪器设备（含软件）均值60', '现有教学仪器设备（含软件）均值', '2c92e55966dc68330166dc9861230025', '60', '2018-11-04 10:50:51', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca1be8c0031', '近四年校外实习实践基地数量及各基地实习学生人次数与专业在校学生总数比值', '近四年校外实习实践基地数量及各基地实习学生人次数与专业在校学生总数比值', '2c92e55966dc68330166dc9c32170029', '70', '2018-11-04 10:51:19', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca2435f0032', '现有生均专业纸质图书资料册数75', '现有生均专业纸质图书资料册数', '2c92e55966dc68330166dc9d8200002b', '75', '2018-11-04 10:51:53', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca2f3ae0033', '历年省级以上本科教学工程项目60', '历年省级以上本科教学工程项目', '2c92e55966dc68330166dc9f8d5a002d', '60', '2018-11-04 10:52:38', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca34d360034', '历年省级以上教学成果奖70', '历年省级以上教学成果奖', '2c92e55966dc68330166dc9ffbe5002e', '70', '2018-11-04 10:53:01', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca3c2580035', '质量监控80', '质量监控', '2c92e55966dc68330166dca08616002f', '80', '2018-11-04 10:53:31', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca44e6d0036', '质量评价60', '质量评价', '297efed566ca6ec00166ca8bd29c0025', '60', '2018-11-04 10:54:07', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca4a7cc0037', '反馈及效果74', '反馈及效果', '297efed566ca6ec00166ca8c0c1b0026', '74', '2018-11-04 10:54:30', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca502110038', '近四年就业率80', '近四年就业率', '297efed566ca6ec00166ca8c57d40027', '80', '2018-11-04 10:54:53', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca58c2c0039', '十名优秀校友简介80', '十名优秀校友简介', '297efed566ca6ec00166ca8cb2d40028', '80', '2018-11-04 10:55:28', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca5e4c8003a', '近四年参加创新创业活动和参与科研项目学生人数与在校总数比值70', '近四年参加创新创业活动和参与科研项目学生人数与在校总数比值', '297efed566ca6ec00166ca8d9a6f0029', '70', '2018-11-04 10:55:51', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca673bb003b', '近四年学生获奖省级以上各类竞赛奖励情况75', '近四年学生获奖省级以上各类竞赛奖励情况', '297efed566ca6ec00166ca8e400f002a', '75', '2018-11-04 10:56:28', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dca978f7003e', '近四年学生发表学术论文及专利授权情况80', '近四年学生发表学术论文及专利授权情况', '2c92e55966dc68330166dca84b30003d', '80', '2018-11-04 10:59:46', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dcaa14df003f', '五名优秀在校生的简介80', '五名优秀在校生的简介', '297efed566ca6ec00166ca8f5e0a002c', '80', '2018-11-04 11:00:25', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dcaa6ee60040', '专业特色、实施过程和效果80', '专业特色、实施过程和效果', '297efed566ca6ec00166ca8b36950024', '80', '2018-11-04 11:00:48', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dc68330166dcb720670050', '近四年教师发表学术论文情况65', '近四年教师发表学术论文情况', '297efed566ca6ec00166ca854a220018', '75', '2018-11-04 14:39:56', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dd72bd0166dd774c270000', '培养目标和规格要求与专业人才培养定位、专业设置的符合程度80', '培养目标和规格要求与专业人才培养定位、专业设置的符合程度', '297efed566ca6ec00166ca76eb0f0008', '80', '2018-11-04 14:44:35', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dd72bd0166dd7b46360001', '课程设置对知识、能力和素质需求的支持程度75', '课程设置对知识、能力和素质需求的支持程度', '2c92e55966dc68330166dd6c614c006d', '75', '2018-11-04 14:48:55', null, '0');
INSERT INTO `indicator_grade_reference` VALUES ('2c92e55966dd72bd0166dd7eba230003', '专业国际化人才培养的改革措施与实施效果60', '专业国际化人才培养的改革措施与实施效果', '2c92e55966dd72bd0166dd7e56f20002', '60', '2018-11-04 14:52:41', null, '0');

-- ----------------------------
-- Table structure for profession
-- ----------------------------
DROP TABLE IF EXISTS `profession`;
CREATE TABLE `profession` (
  `uid` varchar(32) NOT NULL COMMENT '唯一id',
  `name` varchar(255) NOT NULL COMMENT '专业名称',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of profession
-- ----------------------------
INSERT INTO `profession` VALUES ('297efed566ca6ec00166ca905a15002d', '计算机科学与技术', '2018-10-31 22:39:09', '2018-10-31 22:39:09', '0');

-- ----------------------------
-- Table structure for profession_grade
-- ----------------------------
DROP TABLE IF EXISTS `profession_grade`;
CREATE TABLE `profession_grade` (
  `profession_uid` varchar(32) NOT NULL,
  `grade` double NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `uid` varchar(32) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of profession_grade
-- ----------------------------
INSERT INTO `profession_grade` VALUES ('297efed566ca6ec00166ca905a15002d', '69.87', '2018-11-06 22:51:41', '2018-11-06 22:51:41', '0', '4028fe8166e972370166e981f9c60000');

-- ----------------------------
-- Table structure for profession_indicator_relation
-- ----------------------------
DROP TABLE IF EXISTS `profession_indicator_relation`;
CREATE TABLE `profession_indicator_relation` (
  `uid` varchar(32) NOT NULL,
  `indicator_uid` varchar(32) NOT NULL,
  `profession_uid` varchar(32) NOT NULL,
  `parent_uid` varchar(32) NOT NULL,
  `indicator_ratio` double NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `grade` double DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of profession_indicator_relation
-- ----------------------------
INSERT INTO `profession_indicator_relation` VALUES ('2c92e55966dc68330166dd59a5eb0066', '297efed566ca6ec00166ca74d9430004', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca7327000002', '0.6', '2018-11-04 14:12:11', '2018-11-04 14:12:11', '0', '100');
INSERT INTO `profession_indicator_relation` VALUES ('2c92e55966dc68330166dd59a5ec0067', '297efed566ca6ec00166ca757b3a0005', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca7327000002', '0.4', '2018-11-04 14:12:11', '2018-11-04 14:12:11', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('2c92e55966dc68330166dd5c59920068', '297efed566ca6ec00166ca7368fe0003', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca72d5960001', '0.6', '2018-11-04 14:15:08', '2018-11-04 14:15:08', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('2c92e55966dc68330166dd5c59920069', '297efed566ca6ec00166ca7e5aec000b', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca72d5960001', '0.4', '2018-11-04 14:15:08', '2018-11-04 14:15:08', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('2c92e55966dc68330166dd62225b006a', '297efed566ca6ec00166ca75c8670006', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca7368fe0003', '0.2', '2018-11-04 14:21:28', '2018-11-04 14:21:28', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('2c92e55966dc68330166dd62225b006b', '297efed566ca6ec00166ca75f6140007', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca7368fe0003', '0.8', '2018-11-04 14:21:28', '2018-11-04 14:21:28', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('2c92e55966dda9d60166ddfb3ca90001', '297efed566ca6ec00166ca7273e00000', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca905a15002d', '0.15', '2018-11-04 17:08:41', '2018-11-04 17:08:41', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('2c92e55966dda9d60166ddfb3ca90002', '297efed566ca6ec00166ca72d5960001', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca905a15002d', '0.15', '2018-11-04 17:08:41', '2018-11-04 17:08:41', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('2c92e55966dda9d60166ddfb3ca90003', '297efed566ca6ec00166ca7e93f9000c', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca905a15002d', '0.25', '2018-11-04 17:08:41', '2018-11-04 17:08:41', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('2c92e55966dda9d60166ddfb3caa0004', '297efed566ca6ec00166ca88189d001e', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca905a15002d', '0.15', '2018-11-04 17:08:41', '2018-11-04 17:08:41', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('2c92e55966dda9d60166ddfb3caa0005', '297efed566ca6ec00166ca8881df001f', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca905a15002d', '0.1', '2018-11-04 17:08:41', '2018-11-04 17:08:41', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('2c92e55966dda9d60166ddfb3caa0006', '297efed566ca6ec00166ca89cc740020', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca905a15002d', '0.2', '2018-11-04 17:08:41', '2018-11-04 17:08:41', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8e5e5ce0000', '297efed566ca6ec00166ca7327000002', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca7273e00000', '1', '2018-11-06 20:01:12', '2018-11-06 20:01:12', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8ed09fa0001', '2c92e55966dc68330166dd6be235006c', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca75f6140007', '0.3', '2018-11-06 20:09:00', '2018-11-06 20:09:00', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8ed09fa0002', '2c92e55966dc68330166dd6c614c006d', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca75f6140007', '0.4', '2018-11-06 20:09:00', '2018-11-06 20:09:00', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8ed09fa0003', '297efed566ca6ec00166ca7e011c000a', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca75f6140007', '0.3', '2018-11-06 20:09:00', '2018-11-06 20:09:00', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8efa76e0004', '297efed566ca6ec00166ca7f8a41000d', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca7e5aec000b', '0.6', '2018-11-06 20:11:52', '2018-11-06 20:11:52', '0', '75');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8efa76e0005', '2c92e55966dd72bd0166dd7e56f20002', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca7e5aec000b', '0.4', '2018-11-06 20:11:52', '2018-11-06 20:11:52', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8fbcf020007', '297efed566ca6ec00166ca80db780010', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca7e93f9000c', '0.35', '2018-11-06 20:25:08', '2018-11-06 20:25:08', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8fbcf020008', '297efed566ca6ec00166ca84366e0016', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca7e93f9000c', '0.25', '2018-11-06 20:25:08', '2018-11-06 20:25:08', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8fbcf020009', '297efed566ca6ec00166ca872255001b', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca7e93f9000c', '0.25', '2018-11-06 20:25:08', '2018-11-06 20:25:08', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8fbcf03000a', '297efed566ca6ec00166ca877268001c', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca7e93f9000c', '0.1', '2018-11-06 20:25:08', '2018-11-06 20:25:08', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8fbcf03000b', '297efed566ca6ec00166ca879fec001d', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca7e93f9000c', '0.05', '2018-11-06 20:25:08', '2018-11-06 20:25:08', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8fec9ae000c', '297efed566ca6ec00166ca7fd0b4000e', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca80db780010', '0.25', '2018-11-06 20:28:23', '2018-11-06 20:28:23', '0', '85');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8fec9af000d', '297efed566ca6ec00166ca80604e000f', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca80db780010', '0.15', '2018-11-06 20:28:23', '2018-11-06 20:28:23', '0', '65');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8fec9af000e', '297efed566ca6ec00166ca811d5e0011', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca80db780010', '0.15', '2018-11-06 20:28:23', '2018-11-06 20:28:23', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8fec9af000f', '297efed566ca6ec00166ca824d440013', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca80db780010', '0.15', '2018-11-06 20:28:23', '2018-11-06 20:28:23', '0', '75');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8fec9af0010', '297efed566ca6ec00166ca83b0730015', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca80db780010', '0.15', '2018-11-06 20:28:23', '2018-11-06 20:28:23', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e8fec9af0011', '297efed566ca6ec00166ca84d9500017', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca80db780010', '0.15', '2018-11-06 20:28:23', '2018-11-06 20:28:23', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e90137fa0012', '297efed566ca6ec00166ca854a220018', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca84366e0016', '0.4', '2018-11-06 20:31:03', '2018-11-06 20:31:03', '0', '65');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e90137fa0013', '297efed566ca6ec00166ca85d2de0019', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca84366e0016', '0.3', '2018-11-06 20:31:03', '2018-11-06 20:31:03', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e90137fa0014', '2c92e55966dc68330166dc8ca6fe001e', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca84366e0016', '0.3', '2018-11-06 20:31:03', '2018-11-06 20:31:03', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e902c85a0015', '297efed566ca6ec00166ca8664d6001a', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca872255001b', '0.3', '2018-11-06 20:43:20', '2018-11-06 20:43:20', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e902c85a0016', '2c92e55966dc68330166dc95e76f0021', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca872255001b', '0.3', '2018-11-06 20:43:20', '2018-11-06 20:43:20', '0', '90');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e902c85b0017', '2c92e55966dc68330166dc96b3440022', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca872255001b', '0.4', '2018-11-06 20:43:20', '2018-11-06 20:43:20', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e907c70a0018', '2c92e55966dc68330166dc9861230025', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca877268001c', '0.3', '2018-11-06 20:38:13', '2018-11-06 20:38:13', '0', '60');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e907c70a0019', '2c92e55966dc68330166dc993b4d0026', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca877268001c', '0.3', '2018-11-06 20:38:13', '2018-11-06 20:38:13', '0', '90');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e907c70a001a', '2c92e55966dc68330166dc9c32170029', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca877268001c', '0.4', '2018-11-06 20:38:13', '2018-11-06 20:38:13', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e91a8a7a001b', '2c92e55966dc68330166dc9d8200002b', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca879fec001d', '0.6', '2018-11-06 21:05:53', '2018-11-06 21:05:53', '0', '75');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e8e33f0166e91a8a7a001c', '2c92e55966dc68330166dc9ee8e1002c', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca879fec001d', '0.4', '2018-11-06 20:58:42', '2018-11-06 20:58:42', '0', '75');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e927327c0001', '4028fe8166e91f1d0166e93397a40007', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca88189d001e', '0.5', '2018-11-06 21:26:29', '2018-11-06 21:26:29', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e927327c0002', '4028fe8166e8e33f0166e91cc7cf001d', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca88189d001e', '0.5', '2018-11-06 21:26:29', '2018-11-06 21:26:29', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e934a0070008', '2c92e55966dc68330166dc9f8d5a002d', '297efed566ca6ec00166ca905a15002d', '4028fe8166e91f1d0166e93397a40007', '1', '2018-11-06 21:27:12', '2018-11-06 21:27:12', '0', '60');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e9352a340009', '2c92e55966dc68330166dc9ffbe5002e', '297efed566ca6ec00166ca905a15002d', '4028fe8166e8e33f0166e91cc7cf001d', '1', '2018-11-06 21:27:47', '2018-11-06 21:27:47', '0', '70');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e9361d4f000a', '4028fe8166e91f1d0166e929d9ea0005', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca8881df001f', '1', '2018-11-06 21:28:49', '2018-11-06 21:28:49', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e9376df4000b', '2c92e55966dc68330166dca08616002f', '297efed566ca6ec00166ca905a15002d', '4028fe8166e91f1d0166e929d9ea0005', '0.3', '2018-11-06 21:30:15', '2018-11-06 21:30:15', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e9376df5000c', '297efed566ca6ec00166ca8bd29c0025', '297efed566ca6ec00166ca905a15002d', '4028fe8166e91f1d0166e929d9ea0005', '0.4', '2018-11-06 21:30:15', '2018-11-06 21:30:15', '0', '60');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e9376df5000d', '297efed566ca6ec00166ca8c0c1b0026', '297efed566ca6ec00166ca905a15002d', '4028fe8166e91f1d0166e929d9ea0005', '0.3', '2018-11-06 21:30:15', '2018-11-06 21:30:15', '0', '74');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e9387955000e', '297efed566ca6ec00166ca8a841b0022', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca89cc740020', '0.5', '2018-11-06 21:31:24', '2018-11-06 21:31:24', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e9387955000f', '297efed566ca6ec00166ca8adb010023', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca89cc740020', '0.5', '2018-11-06 21:31:24', '2018-11-06 21:31:24', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e93973f10010', '297efed566ca6ec00166ca8c57d40027', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca8a841b0022', '0.5', '2018-11-06 21:32:28', '2018-11-06 21:32:28', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e93973f10011', '297efed566ca6ec00166ca8cb2d40028', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca8a841b0022', '0.5', '2018-11-06 21:32:28', '2018-11-06 21:32:28', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e93b79890012', '297efed566ca6ec00166ca8d9a6f0029', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca8adb010023', '0.3', '2018-11-06 21:34:41', '2018-11-06 21:34:41', '0', '70');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e93b79890013', '2c92e55966dc68330166dca751e0003c', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca8adb010023', '0.25', '2018-11-06 21:34:41', '2018-11-06 21:34:41', '0', '75');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e93b79890014', '297efed566ca6ec00166ca8ef4be002b', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca8adb010023', '0.2', '2018-11-06 21:34:41', '2018-11-06 21:34:41', '0', '0');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e93b798a0015', '297efed566ca6ec00166ca8f5e0a002c', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca8adb010023', '0.25', '2018-11-06 21:34:41', '2018-11-06 21:34:41', '0', '80');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e946b9530016', '297efed566ca6ec00166ca76eb0f0008', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca75c8670006', '0.3', '2018-11-06 21:46:58', '2018-11-06 21:46:58', '0', '60');
INSERT INTO `profession_indicator_relation` VALUES ('4028fe8166e91f1d0166e946b9530017', '297efed566ca6ec00166ca77bc400009', '297efed566ca6ec00166ca905a15002d', '297efed566ca6ec00166ca75c8670006', '0.7', '2018-11-06 21:46:58', '2018-11-06 21:46:58', '0', '80');
