/*
Navicat MariaDB Data Transfer

Source Server         : me
Source Server Version : 100014
Source Host           : localhost:3306
Source Database       : xjb

Target Server Type    : MariaDB
Target Server Version : 100014
File Encoding         : 65001

Date: 2015-05-23 11:40:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bills
-- ----------------------------
DROP TABLE IF EXISTS `bills`;
CREATE TABLE `bills` (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `uid` int(11) NOT NULL,
  `bdate` datetime NOT NULL,
  `btype` int(11) NOT NULL DEFAULT '0',
  `bio` tinyint(4) NOT NULL DEFAULT '0',
  `bamount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `bcaption` mediumtext NOT NULL,
  `bbetravelleader` tinyint(1) NOT NULL DEFAULT '0',
  `bbetravelmember` tinyint(1) NOT NULL DEFAULT '0',
  `btid` int(11) NOT NULL DEFAULT '0',
  `bimageid` varchar(100) NOT NULL DEFAULT '',
  `bctype` tinyint(4) NOT NULL DEFAULT '0',
  `replys` int(11) NOT NULL DEFAULT '0',
  `forwards` int(11) NOT NULL DEFAULT '0',
  `rootbid` int(11) NOT NULL DEFAULT '0',
  `frombid` int(11) NOT NULL DEFAULT '0',
  `fromuid` int(11) NOT NULL DEFAULT '0',
  `fromuname` varchar(50) NOT NULL DEFAULT '',
  `fromw` tinyint(4) NOT NULL DEFAULT '0',
  `fromip` char(15) NOT NULL DEFAULT '',
  `like_count` int(255) DEFAULT '0' COMMENT '赞的数量',
  `isdeleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标记是否为删除',
  PRIMARY KEY (`bid`),
  KEY `bills_datetime_index` (`bdate`),
  KEY `bills_user_index` (`uid`),
  KEY `bills_parent_index` (`frombid`),
  KEY `bills_root_index` (`rootbid`),
  KEY `bills_sub_id_index` (`btid`)
) ENGINE=InnoDB AUTO_INCREMENT=2010 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bills
-- ----------------------------

-- ----------------------------
-- Table structure for bills_bctype
-- ----------------------------
DROP TABLE IF EXISTS `bills_bctype`;
CREATE TABLE `bills_bctype` (
  `bcid` tinyint(1) NOT NULL DEFAULT '0',
  `bcname` char(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`bcid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

-- ----------------------------
-- Records of bills_bctype
-- ----------------------------

-- ----------------------------
-- Table structure for bills_bio
-- ----------------------------
DROP TABLE IF EXISTS `bills_bio`;
CREATE TABLE `bills_bio` (
  `bio` tinyint(4) NOT NULL,
  `bioname` char(10) NOT NULL,
  `bsign` char(3) NOT NULL DEFAULT '',
  PRIMARY KEY (`bio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bills_bio
-- ----------------------------
INSERT INTO `bills_bio` VALUES ('0', '行程', '*');
INSERT INTO `bills_bio` VALUES ('1', '支出', '-');
INSERT INTO `bills_bio` VALUES ('2', '收入', '+');
INSERT INTO `bills_bio` VALUES ('3', '债务', '++');
INSERT INTO `bills_bio` VALUES ('4', '债权', '--');
INSERT INTO `bills_bio` VALUES ('5', '无金钱', '^-^');

-- ----------------------------
-- Table structure for bills_collection
-- ----------------------------
DROP TABLE IF EXISTS `bills_collection`;
CREATE TABLE `bills_collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id users表',
  `bid` int(11) NOT NULL COMMENT '记事id bills表',
  `collection_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_delete` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_collections_index` (`uid`),
  KEY `collections_datetime_index` (`collection_datetime`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bills_collection
-- ----------------------------

-- ----------------------------
-- Table structure for bills_fromw
-- ----------------------------
DROP TABLE IF EXISTS `bills_fromw`;
CREATE TABLE `bills_fromw` (
  `bfid` tinyint(1) NOT NULL DEFAULT '0',
  `bfname` char(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`bfid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

-- ----------------------------
-- Records of bills_fromw
-- ----------------------------

-- ----------------------------
-- Table structure for bills_images
-- ----------------------------
DROP TABLE IF EXISTS `bills_images`;
CREATE TABLE `bills_images` (
  `biid` int(11) NOT NULL AUTO_INCREMENT,
  `bibillid` int(11) NOT NULL DEFAULT '0',
  `bidir` varchar(100) NOT NULL,
  `bioriginalname` varchar(50) NOT NULL DEFAULT '',
  `uid` mediumint(9) NOT NULL,
  `username` varchar(32) NOT NULL,
  `bitime` datetime NOT NULL DEFAULT '2014-01-14 00:00:00',
  PRIMARY KEY (`biid`)
) ENGINE=InnoDB AUTO_INCREMENT=392 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bills_images
-- ----------------------------

-- ----------------------------
-- Table structure for bills_like
-- ----------------------------
DROP TABLE IF EXISTS `bills_like`;
CREATE TABLE `bills_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bid` int(11) NOT NULL DEFAULT '0',
  `uid` int(11) NOT NULL DEFAULT '0',
  `datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_bid_uid` (`bid`,`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of bills_like
-- ----------------------------

-- ----------------------------
-- Table structure for bills_topic
-- ----------------------------
DROP TABLE IF EXISTS `bills_topic`;
CREATE TABLE `bills_topic` (
  `btpid` int(11) NOT NULL AUTO_INCREMENT,
  `btpname` char(30) NOT NULL DEFAULT '',
  `uid` int(11) NOT NULL DEFAULT '0',
  `btptime` datetime NOT NULL DEFAULT '2014-01-14 00:00:00',
  `btplasttime` datetime NOT NULL DEFAULT '2014-01-14 00:00:00',
  `usercount` int(11) NOT NULL DEFAULT '0',
  `topiccount` int(11) NOT NULL DEFAULT '0',
  `tophot` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`btpid`),
  KEY `tophot_lasttime` (`btplasttime`) USING BTREE,
  KEY `tophot_topiccount` (`topiccount`) USING BTREE,
  KEY `tophot_tophot` (`tophot`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bills_topic
-- ----------------------------
INSERT INTO `bills_topic` VALUES ('1', '#吃货的天下#', '0', '2014-01-14 00:00:00', '2014-09-26 23:41:54', '0', '5', '0.0000002426454762754599');
INSERT INTO `bills_topic` VALUES ('2', '#梦#', '0', '2014-01-14 00:00:00', '2015-03-03 08:14:03', '0', '48', '0.000006846710426655613');
INSERT INTO `bills_topic` VALUES ('3', '#美#', '0', '2014-05-01 02:43:05', '2015-03-28 20:13:30', '0', '1', '0.00000020800836360028363');
INSERT INTO `bills_topic` VALUES ('4', '#人生#', '0', '2014-05-01 02:49:52', '2015-03-10 22:21:35', '0', '51', '0.00000802515934661985');
INSERT INTO `bills_topic` VALUES ('5', '#梦想#', '0', '2014-05-01 03:31:32', '2014-05-01 03:46:45', '0', '2', '0.00000005976374673749707');
INSERT INTO `bills_topic` VALUES ('6', '#分享图片#', '0', '2014-05-01 03:46:45', '2014-05-01 03:46:45', '0', '1', '0.00000002988187336874853');
INSERT INTO `bills_topic` VALUES ('7', '#音乐#', '0', '2014-05-10 18:34:30', '2014-05-10 18:34:30', '0', '1', '0.000000030642663369597804');
INSERT INTO `bills_topic` VALUES ('8', '#签到 心情#', '0', '2014-05-19 19:48:08', '2014-05-19 19:48:08', '0', '1', '0.00000003139498498393566');
INSERT INTO `bills_topic` VALUES ('10', '#每日一影#', '0', '2014-05-25 19:42:24', '2014-05-25 19:42:24', '0', '1', '0.00000003191404653674352');
INSERT INTO `bills_topic` VALUES ('11', '#生活#', '0', '2014-06-26 11:38:13', '2015-03-04 23:52:41', '0', '44', '0.000006406571511607252');
INSERT INTO `bills_topic` VALUES ('12', '#游戏#', '0', '2014-09-03 08:21:24', '2014-09-03 08:21:24', '0', '1', '0.000000044152790732329225');
INSERT INTO `bills_topic` VALUES ('13', '#新话题#', '0', '2014-09-18 12:07:23', '2014-09-18 12:07:23', '0', '1', '0.000000046862407192517235');
INSERT INTO `bills_topic` VALUES ('14', '#心情#', '0', '2014-09-21 23:34:31', '2015-02-26 15:46:40', '0', '39', '0.0000052592478817030635');
INSERT INTO `bills_topic` VALUES ('15', '#爱#', '0', '2014-11-09 21:22:15', '2014-12-28 18:16:21', '0', '5', '0.0000003971239331662659');
INSERT INTO `bills_topic` VALUES ('16', '#cc#', '0', '2014-11-16 16:05:03', '2014-11-16 16:05:03', '0', '1', '0.0000000616249032643081');
INSERT INTO `bills_topic` VALUES ('17', '#工作#', '0', '2014-12-01 16:48:06', '2015-03-02 23:12:14', '0', '17', '0.0000024136841694264306');
INSERT INTO `bills_topic` VALUES ('18', '#家#', '0', '2014-12-05 10:41:26', '2014-12-05 10:41:26', '0', '1', '0.00000006846959433066282');
INSERT INTO `bills_topic` VALUES ('19', '#背叛#', '0', '2014-12-28 22:01:42', '2014-12-28 22:01:42', '0', '1', '0.0000000795101728097949');
INSERT INTO `bills_topic` VALUES ('20', '#爱情#', '0', '2015-01-02 16:51:33', '2015-03-06 11:45:52', '0', '19', '0.000002819511076004076');
INSERT INTO `bills_topic` VALUES ('21', '#回家#', '0', '2015-01-02 20:53:50', '2015-02-18 06:12:44', '0', '3', '0.00000036849853429707984');
INSERT INTO `bills_topic` VALUES ('22', '#过去的你活在今天，却没有看见未来#', '0', '2015-01-08 20:59:48', '2015-01-08 20:59:48', '0', '1', '0.00000008598214958985224');
INSERT INTO `bills_topic` VALUES ('23', '#养#', '0', '2015-01-15 22:41:22', '2015-01-15 22:41:22', '0', '1', '0.00000009074882024264964');
INSERT INTO `bills_topic` VALUES ('24', '#人生大事#', '0', '2015-01-20 20:43:38', '2015-01-20 20:43:38', '0', '1', '0.00000009438867804031359');
INSERT INTO `bills_topic` VALUES ('25', '#wdcbb#', '0', '2015-01-26 13:51:12', '2015-01-26 13:51:12', '0', '1', '0.00000009900169659207449');
INSERT INTO `bills_topic` VALUES ('26', '#hdbchc#', '0', '2015-02-02 16:27:06', '2015-02-02 16:27:06', '0', '1', '0.00000010541092181534895');
INSERT INTO `bills_topic` VALUES ('27', '#养老城市#', '0', '2015-02-22 07:12:40', '2015-02-22 07:12:40', '0', '1', '0.00000012833759563878048');
INSERT INTO `bills_topic` VALUES ('28', '##', '0', '2015-02-26 13:36:56', '2015-02-26 13:36:56', '0', '1', '0.00000013471110462701662');
INSERT INTO `bills_topic` VALUES ('29', '#男人#', '0', '2015-02-27 07:39:38', '2015-02-28 20:16:33', '0', '2', '0.0000002767585375857467');
INSERT INTO `bills_topic` VALUES ('30', '#女人#', '0', '2015-02-27 07:39:38', '2015-02-27 07:39:38', '0', '1', '0.00000013590038284496852');
INSERT INTO `bills_topic` VALUES ('31', '#回忆#', '0', '2015-03-10 22:18:39', '2015-03-10 22:22:58', '0', '2', '0.0000003147162416082881');

-- ----------------------------
-- Table structure for bills_topic_tag
-- ----------------------------
DROP TABLE IF EXISTS `bills_topic_tag`;
CREATE TABLE `bills_topic_tag` (
  `btid` mediumint(9) NOT NULL AUTO_INCREMENT,
  `btname` varchar(50) NOT NULL,
  `uid` mediumint(9) NOT NULL,
  `bttime` datetime NOT NULL DEFAULT '2014-01-14 00:00:00',
  `btlastpost` datetime NOT NULL DEFAULT '2014-01-14 00:00:00',
  `btcount` mediumint(9) NOT NULL,
  PRIMARY KEY (`btid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bills_topic_tag
-- ----------------------------

-- ----------------------------
-- Table structure for bills_type
-- ----------------------------
DROP TABLE IF EXISTS `bills_type`;
CREATE TABLE `bills_type` (
  `btypeid` int(11) NOT NULL AUTO_INCREMENT,
  `btypename` char(10) NOT NULL,
  `btypeuserid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`btypeid`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bills_type
-- ----------------------------
INSERT INTO `bills_type` VALUES ('1', '工资', '0');
INSERT INTO `bills_type` VALUES ('2', '吃饭', '0');
INSERT INTO `bills_type` VALUES ('3', '早餐', '0');
INSERT INTO `bills_type` VALUES ('4', '午餐', '0');
INSERT INTO `bills_type` VALUES ('5', '晚餐', '0');
INSERT INTO `bills_type` VALUES ('6', '购物', '0');
INSERT INTO `bills_type` VALUES ('7', '信用卡还款', '0');
INSERT INTO `bills_type` VALUES ('8', '旅游', '0');
INSERT INTO `bills_type` VALUES ('9', '借债', '0');
INSERT INTO `bills_type` VALUES ('10', '还债', '0');
INSERT INTO `bills_type` VALUES ('11', '公交卡充值', '0');
INSERT INTO `bills_type` VALUES ('12', '水果', '0');
INSERT INTO `bills_type` VALUES ('13', '饮料', '0');
INSERT INTO `bills_type` VALUES ('14', '请客', '0');
INSERT INTO `bills_type` VALUES ('15', '玩乐', '0');
INSERT INTO `bills_type` VALUES ('16', '快递', '0');
INSERT INTO `bills_type` VALUES ('18', '房贷', '0');
INSERT INTO `bills_type` VALUES ('19', '房租', '0');
INSERT INTO `bills_type` VALUES ('20', '贷款', '0');
INSERT INTO `bills_type` VALUES ('21', '还贷款', '0');
INSERT INTO `bills_type` VALUES ('22', '车票', '0');
INSERT INTO `bills_type` VALUES ('23', '火车票', '0');
INSERT INTO `bills_type` VALUES ('24', '飞机票', '0');
INSERT INTO `bills_type` VALUES ('25', '游戏', '0');
INSERT INTO `bills_type` VALUES ('26', '团购', '0');
INSERT INTO `bills_type` VALUES ('27', '吃饭 购物等等', '0');
INSERT INTO `bills_type` VALUES ('28', '心情', '0');
INSERT INTO `bills_type` VALUES ('100', '一起', '1');
INSERT INTO `bills_type` VALUES ('105', '信用卡还款', '1');
INSERT INTO `bills_type` VALUES ('106', '写程序', '1');
INSERT INTO `bills_type` VALUES ('107', '味多美', '1');
INSERT INTO `bills_type` VALUES ('108', '回家', '1');
INSERT INTO `bills_type` VALUES ('109', '奇怪的心情', '1');
INSERT INTO `bills_type` VALUES ('110', '意外', '1');
INSERT INTO `bills_type` VALUES ('111', '搜狗', '1');
INSERT INTO `bills_type` VALUES ('113', '觉得没胃口', '1');
INSERT INTO `bills_type` VALUES ('114', '过年', '1');
INSERT INTO `bills_type` VALUES ('115', '面试', '1');
INSERT INTO `bills_type` VALUES ('116', '带饭', '1');
INSERT INTO `bills_type` VALUES ('117', '广西', '1');
INSERT INTO `bills_type` VALUES ('123', '洗浴', '1');
INSERT INTO `bills_type` VALUES ('124', '理发', '1');
INSERT INTO `bills_type` VALUES ('126', '天下互联', '1');
INSERT INTO `bills_type` VALUES ('127', '人生', '1');
INSERT INTO `bills_type` VALUES ('128', '学习', '1');
INSERT INTO `bills_type` VALUES ('129', '呼和浩特旅游', '1');
INSERT INTO `bills_type` VALUES ('130', '宾馆', '1');
INSERT INTO `bills_type` VALUES ('131', '梦', '1');
INSERT INTO `bills_type` VALUES ('132', '测试话题', '1');
INSERT INTO `bills_type` VALUES ('133', '天津海之旅', '1');
INSERT INTO `bills_type` VALUES ('134', '门票', '1');
INSERT INTO `bills_type` VALUES ('135', '医院', '1');
INSERT INTO `bills_type` VALUES ('136', '充值续费', '1');
INSERT INTO `bills_type` VALUES ('137', '补助', '1');

-- ----------------------------
-- Table structure for catch_exception
-- ----------------------------
DROP TABLE IF EXISTS `catch_exception`;
CREATE TABLE `catch_exception` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exception` varchar(10000) NOT NULL DEFAULT '',
  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=699 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of catch_exception
-- ----------------------------

-- ----------------------------
-- Table structure for chat_history
-- ----------------------------
DROP TABLE IF EXISTS `chat_history`;
CREATE TABLE `chat_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid_send` mediumint(9) NOT NULL,
  `uid_receive` mediumint(9) NOT NULL,
  `message_content` text NOT NULL,
  `message_datetime` datetime NOT NULL COMMENT '查询时不使用此列',
  `message_timestamp` bigint(20) DEFAULT NULL,
  `message_status` tinyint(4) NOT NULL DEFAULT '0',
  `message_type` mediumint(9) NOT NULL DEFAULT '0',
  `display_datetime` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `send_receive_user_index` (`uid_send`,`uid_receive`),
  KEY `message_date_index` (`message_timestamp`)
) ENGINE=InnoDB AUTO_INCREMENT=1127 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of chat_history
-- ----------------------------

-- ----------------------------
-- Table structure for configuration
-- ----------------------------
DROP TABLE IF EXISTS `configuration`;
CREATE TABLE `configuration` (
  `ckey` varchar(100) NOT NULL,
  `cvalue` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`ckey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of configuration
-- ----------------------------
INSERT INTO `configuration` VALUES ('mobileDefaultTemplate', '/mobile/template/jijianban');
INSERT INTO `configuration` VALUES ('webDefaultTemplate', '/template/default');
INSERT INTO `configuration` VALUES ('websiteName', '微我');
INSERT INTO `configuration` VALUES ('websiteTitle', '花钱，是一种享受');

-- ----------------------------
-- Table structure for feedbacks
-- ----------------------------
DROP TABLE IF EXISTS `feedbacks`;
CREATE TABLE `feedbacks` (
  `fid` mediumint(9) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `uid` mediumint(9) NOT NULL DEFAULT '0',
  `fdate` datetime NOT NULL,
  `contents` mediumtext NOT NULL,
  `replystatus` tinyint(1) NOT NULL DEFAULT '0',
  `rusername` varchar(50) NOT NULL DEFAULT '',
  `ruid` varchar(32) NOT NULL DEFAULT '',
  `frdate` datetime NOT NULL DEFAULT '2014-01-14 00:00:00',
  `rcontents` mediumtext NOT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of feedbacks
-- ----------------------------

-- ----------------------------
-- Table structure for join_as_a_partner
-- ----------------------------
DROP TABLE IF EXISTS `join_as_a_partner`;
CREATE TABLE `join_as_a_partner` (
  `jaapid` mediumint(9) NOT NULL AUTO_INCREMENT,
  `uid` mediumint(11) NOT NULL DEFAULT '0',
  `username` varchar(50) NOT NULL DEFAULT '',
  `jaapissponsor` tinyint(1) NOT NULL DEFAULT '0',
  `jaaptitle` char(50) NOT NULL DEFAULT '',
  `jaapcontent` mediumtext NOT NULL,
  `jaapcost` decimal(10,2) NOT NULL,
  `jaapadvice` text NOT NULL,
  `jaapthenumberofperson` int(11) NOT NULL DEFAULT '2',
  `jaappermission` tinyint(4) NOT NULL DEFAULT '0',
  `jaapjoinstate` tinyint(4) NOT NULL DEFAULT '0',
  `jaapapplyid` mediumint(9) NOT NULL DEFAULT '0',
  `jaappublicdate` datetime NOT NULL DEFAULT '2014-01-14 00:00:00',
  `jaapdeadline` datetime NOT NULL DEFAULT '2014-01-14 00:00:00',
  `jaapisfinish` tinyint(1) NOT NULL DEFAULT '0',
  `bid` mediumint(9) NOT NULL DEFAULT '0',
  `jaapevaluate` tinyint(3) NOT NULL DEFAULT '0',
  `jaapsummary` mediumtext NOT NULL,
  PRIMARY KEY (`jaapid`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of join_as_a_partner
-- ----------------------------

-- ----------------------------
-- Table structure for jpush_log
-- ----------------------------
DROP TABLE IF EXISTS `jpush_log`;
CREATE TABLE `jpush_log` (
  `id` int(11) NOT NULL COMMENT '这个使用jpush推送的消息id',
  `send_jpush` varchar(1000) NOT NULL,
  `response_code` char(4) NOT NULL DEFAULT '',
  `response_message` varchar(1000) NOT NULL DEFAULT '',
  `retry_times` tinyint(4) NOT NULL DEFAULT '0' COMMENT '重试次数，一般3次后不再重试',
  `retry_again` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否重试，1为重试，0为不重试，不重试的，定时删除',
  `retry_next_time` datetime DEFAULT NULL COMMENT '下一次重试时间，如果超过，则立即重试，并更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of jpush_log
-- ----------------------------

-- ----------------------------
-- Table structure for relationship_friends
-- ----------------------------
DROP TABLE IF EXISTS `relationship_friends`;
CREATE TABLE `relationship_friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id_1` int(11) NOT NULL,
  `user_id_2` int(11) NOT NULL,
  `datetime` datetime NOT NULL,
  `status` tinyint(4) NOT NULL COMMENT '关系状态：请求加好友状态，好友状态，',
  PRIMARY KEY (`id`),
  KEY `friends` (`user_id_1`,`user_id_2`,`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of relationship_friends
-- ----------------------------

-- ----------------------------
-- Table structure for relationship_shield
-- ----------------------------
DROP TABLE IF EXISTS `relationship_shield`;
CREATE TABLE `relationship_shield` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id_1` int(11) NOT NULL,
  `user_id_2` int(11) NOT NULL,
  `datetime` datetime NOT NULL,
  `type` tinyint(4) NOT NULL COMMENT '关系类型：',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of relationship_shield
-- ----------------------------

-- ----------------------------
-- Table structure for sysmenu
-- ----------------------------
DROP TABLE IF EXISTS `sysmenu`;
CREATE TABLE `sysmenu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `selfkey` varchar(255) NOT NULL COMMENT '自己的键',
  `parentkey` varchar(255) NOT NULL COMMENT '父级的键',
  `name` varchar(255) NOT NULL COMMENT '菜单名称',
  `priority` tinyint(4) NOT NULL DEFAULT '0' COMMENT '权限',
  `order` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sysmenu
-- ----------------------------
INSERT INTO `sysmenu` VALUES ('1', 'main', '0', '首页', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('2', 'global', '0', '全局', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('3', 'plane', '0', '界面', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('4', 'content', '0', '内容', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('5', 'users', '0', '用户', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('6', 'tools', '0', '工具', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('7', '', 'global', '站点信息', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('8', '', 'global', '注册', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('9', '', 'global', '上传设置', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('10', '', 'global', '水印设置', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('11', 'templateManagement', 'plane', '模板设置', '0', '0', 'Admin.yy?a=Template');
INSERT INTO `sysmenu` VALUES ('12', 'menuManagement', 'global', '菜单管理', '0', '0', 'Admin.yy?a=Menu');
INSERT INTO `sysmenu` VALUES ('13', '', '', '2', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('14', '', 'content', '内容审核', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('15', '', 'content', '词语过滤', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('16', 'userManagement', 'users', '用户管理', '0', '0', 'Admin.yy?a=Users');
INSERT INTO `sysmenu` VALUES ('17', '', 'users', '添加用户', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('18', '', 'users', '发送通知', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('19', '', 'users', '禁止用户', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('20', '', 'users', '禁止IP', '0', '0', '');
INSERT INTO `sysmenu` VALUES ('21', 'feedbackManagement', 'content', '反馈管理', '0', '0', 'Admin.yy?a=Feedback');
INSERT INTO `sysmenu` VALUES ('22', 'version', 'global', 'APP版本', '0', '0', 'Admin.yy?a=Version');
INSERT INTO `sysmenu` VALUES ('23', 'exception', 'global', 'APP异常', '0', '0', 'Admin.yy?a=Exception');

-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `tid` smallint(6) NOT NULL AUTO_INCREMENT,
  `tname` varchar(30) NOT NULL DEFAULT '',
  `tdirectory` varchar(100) NOT NULL,
  `tcopyright` varchar(100) NOT NULL DEFAULT '',
  `ttype` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of template
-- ----------------------------
INSERT INTO `template` VALUES ('2', 'default', '/template/default', '记账本官方', '0');
INSERT INTO `template` VALUES ('3', 'blue', '/template/blue', '测试', '0');
INSERT INTO `template` VALUES ('4', 'default', '/mobile/template/default', '记账本官方', '1');
INSERT INTO `template` VALUES ('5', 'jijianban', '/mobile/template/jijianban', '极简版', '1');

-- ----------------------------
-- Table structure for tuling_robot
-- ----------------------------
DROP TABLE IF EXISTS `tuling_robot`;
CREATE TABLE `tuling_robot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `send_message` text,
  `receive_message` text,
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tuling_robot
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `loginid` varchar(64) NOT NULL COMMENT '登录名',
  `username` varchar(64) NOT NULL COMMENT '用户名，默认等于登录名，可修改',
  `password` varchar(32) NOT NULL,
  `realname` varchar(32) NOT NULL DEFAULT '',
  `identity_card_number` varchar(18) NOT NULL DEFAULT '',
  `email` varchar(64) NOT NULL DEFAULT '',
  `avatarstatus` tinyint(1) NOT NULL DEFAULT '0',
  `adminid` tinyint(1) NOT NULL DEFAULT '0',
  `telephone` char(11) NOT NULL DEFAULT '',
  `identification` char(32) NOT NULL DEFAULT '' COMMENT '用户唯一标识，生成二维码使用',
  `jpushid` varchar(32) NOT NULL DEFAULT '' COMMENT 'jpush注册id，推送使用',
  `create_date` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间（触发器）',
  `last_use_date` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '最后一次使用时间（触发器）',
  `qq_open_id` varchar(50) DEFAULT NULL COMMENT 'QQ OpenId',
  `sina_weibo_access_token` varchar(50) DEFAULT NULL COMMENT '新浪微博通行证',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `loginid_index` (`loginid`),
  UNIQUE KEY `username_index` (`username`) USING BTREE,
  UNIQUE KEY `qqopenid_index` (`qq_open_id`) USING BTREE,
  UNIQUE KEY `sinaweiwo_index` (`sina_weibo_access_token`) USING BTREE,
  KEY `realname_index` (`realname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('2', 'admin', 'admin', 'A4458EBCAB8B9AAE027A5AE242E36229', '2', '2', '', '1', '1', '', '21232f297a57a5a743894a0e4a801fc3', '21232F297A57A5A743894A0E4A801FC3', '1970-01-01 00:00:00', '2015-03-07 22:45:46', null, null);
-- ----------------------------
-- Table structure for users_login_log
-- ----------------------------
DROP TABLE IF EXISTS `users_login_log`;
CREATE TABLE `users_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_uid` int(11) NOT NULL,
  `login_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `login_ip` varchar(128) NOT NULL DEFAULT '',
  `remark` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3081 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of users_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` char(16) NOT NULL DEFAULT '' COMMENT '版本号：xxxx.xxxx.xxxx(小于4位前面不要加0)',
  `content` varchar(255) DEFAULT '' COMMENT '更新内容',
  `downloadurl1` varchar(255) DEFAULT NULL,
  `downloadurl2` varchar(255) DEFAULT NULL,
  `downloadurl3` varchar(255) DEFAULT NULL,
  `downloadurl4` varchar(255) DEFAULT NULL,
  `downloadurl5` varchar(255) DEFAULT NULL,
  `downloadurl6` varchar(255) DEFAULT NULL,
  `downloadurl7` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of version
-- ----------------------------
INSERT INTO `version` VALUES ('1', '1.6.8.001', '修复：\\nQQ登录时不同步头像\\n新增：\\n聊天提示\\nQQ登录，已有账号绑定', 'http://bill.vpigirl.com/app/billing.apk', '', '', '', '', '', '');

-- ----------------------------
-- View structure for replys_counts
-- ----------------------------
DROP VIEW IF EXISTS `replys_counts`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER  VIEW `replys_counts` AS select `t`.`bid` AS `bid`,count(0) AS `counts` from (`bills` `t` join `bills` `x` on((`x`.`rootbid` = `t`.`bid`))) group by `t`.`bid` ;

-- ----------------------------
-- Procedure structure for updateHotTopic
-- ----------------------------
DROP PROCEDURE IF EXISTS `updateHotTopic`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateHotTopic`()
begin
set @now=UNIX_TIMESTAMP(NOW());
update bills_topic t1 set t1.tophot=0 where t1.tophot>0;
update bills_topic t1,(
select t.btpid,
topiccount/POW((@now-UNIX_TIMESTAMP(btplasttime)),1) as topic_order
from bills_topic t ORDER BY topic_order desc limit 100) t2 set t1.tophot=topic_order where t1.btpid=t2.btpid;
end
;;
DELIMITER ;

-- ----------------------------
-- Event structure for updateHotTopic
-- ----------------------------
DROP EVENT IF EXISTS `updateHotTopic`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `updateHotTopic` ON SCHEDULE EVERY 3 MINUTE STARTS '2015-04-22 23:44:29' ON COMPLETION PRESERVE ENABLE DO call updateHotTopic()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for update_replays_counts
-- ----------------------------
DROP EVENT IF EXISTS `update_replays_counts`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `update_replays_counts` ON SCHEDULE EVERY 10 MINUTE STARTS '2015-04-22 23:43:20' ON COMPLETION NOT PRESERVE ENABLE DO update bills t set t.replys=(select counts from  replys_counts x where x.bid=t.bid) where EXISTS (select 1 from replys_counts y where t.bid=y.bid)
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `userLikeOneBills`;
DELIMITER ;;
CREATE TRIGGER `userLikeOneBills` AFTER INSERT ON `bills_like` FOR EACH ROW begin
update bills t set t.like_count=(select count(*) from bills_like x where x.bid=new.bid) where t.bid=new.bid;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `userUnLikeOneBills`;
DELIMITER ;;
CREATE TRIGGER `userUnLikeOneBills` AFTER DELETE ON `bills_like` FOR EACH ROW begin
update bills t set t.like_count=(select count(*) from bills_like x where x.bid=old.bid) where t.bid=old.bid;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `display_datetime`;
DELIMITER ;;
CREATE TRIGGER `display_datetime` BEFORE INSERT ON `chat_history` FOR EACH ROW begin
set @timeDiff:=(UNIX_TIMESTAMP(new.message_datetime) - UNIX_TIMESTAMP( ( SELECT ch.message_datetime FROM chat_history ch, users u1, users u2 WHERE ( ( ch.uid_send = u1.uid AND ch.uid_receive = u2.uid ) OR ( ch.uid_send = u2.uid AND ch.uid_receive = u1.uid ) ) AND u1.uid = new.uid_send AND u2.uid = new.uid_receive ORDER BY ch.message_datetime DESC LIMIT 1 ) ));
if @timeDiff<120 then 
set new.display_datetime=0;
else 
set new.display_datetime=1;
end if;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `userCreateTime`;
DELIMITER ;;
CREATE TRIGGER `userCreateTime` BEFORE INSERT ON `users` FOR EACH ROW BEGIN
	set new.create_date = NOW();
	set new.last_use_date = NOW();
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `userLastUseTime`;
DELIMITER ;;
CREATE TRIGGER `userLastUseTime` BEFORE UPDATE ON `users` FOR EACH ROW BEGIN
	set new.last_use_date = NOW();
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `userLoginLogInsert`;
DELIMITER ;;
CREATE TRIGGER `userLoginLogInsert` AFTER INSERT ON `users_login_log` FOR EACH ROW BEGIN
	update xjb.users t set t.last_use_date = NOW() where t.uid=new.fk_uid;
END
;;
DELIMITER ;
