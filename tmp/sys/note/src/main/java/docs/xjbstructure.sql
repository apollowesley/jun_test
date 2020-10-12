/*
Navicat MySQL Data Transfer

Source Server         : memysql
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : xjb

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2015-05-24 00:09:54
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
-- Table structure for bills_bctype
-- ----------------------------
DROP TABLE IF EXISTS `bills_bctype`;
CREATE TABLE `bills_bctype` (
  `bcid` tinyint(1) NOT NULL DEFAULT '0',
  `bcname` char(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`bcid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

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
-- Table structure for bills_fromw
-- ----------------------------
DROP TABLE IF EXISTS `bills_fromw`;
CREATE TABLE `bills_fromw` (
  `bfid` tinyint(1) NOT NULL DEFAULT '0',
  `bfname` char(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`bfid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

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
  `filename` varchar(255) NOT NULL DEFAULT '' COMMENT '图片原来的名字',
  `filesize` varchar(255) NOT NULL DEFAULT '' COMMENT '文件大小',
  PRIMARY KEY (`biid`)
) ENGINE=InnoDB AUTO_INCREMENT=419 DEFAULT CHARSET=utf8;

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
-- Table structure for configuration
-- ----------------------------
DROP TABLE IF EXISTS `configuration`;
CREATE TABLE `configuration` (
  `ckey` varchar(100) NOT NULL,
  `cvalue` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`ckey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
