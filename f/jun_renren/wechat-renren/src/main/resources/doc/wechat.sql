/*
Navicat MySQL Data Transfer

Source Server         : 39.108.144.143
Source Server Version : 50722
Source Host           : 39.108.144.143:3306
Source Database       : wechat

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-11-16 10:45:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `QRTZ_BLOB_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_CALENDARS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_CRON_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('RenrenScheduler', 'TASK_2', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for `QRTZ_FIRED_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_JOB_DETAILS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', null, 'com.pflm.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597400B67B226A6F624964223A312C226265616E4E616D65223A22746573745461736B222C226D6574686F644E616D65223A2274657374222C22706172616D73223A2272656E72656E222C2263726F6E45787072657373696F6E223A223020302F3330202A202A202A203F222C22737461747573223A302C2272656D61726B223A22E69C89E58F82E695B0E6B58BE8AF95222C2263726561746554696D65223A2244656320312C20323031362031313A31363A343620504D227D7800);
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('RenrenScheduler', 'TASK_2', 'DEFAULT', null, 'com.pflm.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597400A47B226A6F624964223A322C226265616E4E616D65223A22746573745461736B222C226D6574686F644E616D65223A227465737432222C2263726F6E45787072657373696F6E223A223020302F3330202A202A202A203F222C22737461747573223A312C2272656D61726B223A22E697A0E58F82E695B0E6B58BE8AF95222C2263726561746554696D65223A2244656320332C203230313620323A35353A353620504D227D7800);

-- ----------------------------
-- Table structure for `QRTZ_LOCKS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
INSERT INTO `QRTZ_LOCKS` VALUES ('RenrenScheduler', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` VALUES ('RenrenScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for `QRTZ_PAUSED_TRIGGER_GRPS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_SCHEDULER_STATE`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('RenrenScheduler', 'admin-PC1542335804511', '1542335898813', '15000');

-- ----------------------------
-- Table structure for `QRTZ_SIMPLE_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_SIMPROP_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_TRIGGERS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', null, '1542335400000', '-1', '5', 'WAITING', 'CRON', '1542335233000', '0', null, '2', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597400B67B226A6F624964223A312C226265616E4E616D65223A22746573745461736B222C226D6574686F644E616D65223A2274657374222C22706172616D73223A2272656E72656E222C2263726F6E45787072657373696F6E223A223020302F3330202A202A202A203F222C22737461747573223A302C2272656D61726B223A22E69C89E58F82E695B0E6B58BE8AF95222C2263726561746554696D65223A2244656320312C20323031362031313A31363A343620504D227D7800);
INSERT INTO `QRTZ_TRIGGERS` VALUES ('RenrenScheduler', 'TASK_2', 'DEFAULT', 'TASK_2', 'DEFAULT', null, '1542335400000', '-1', '5', 'PAUSED', 'CRON', '1542335233000', '0', null, '2', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597400A47B226A6F624964223A322C226265616E4E616D65223A22746573745461736B222C226D6574686F644E616D65223A227465737432222C2263726F6E45787072657373696F6E223A223020302F3330202A202A202A203F222C22737461747573223A312C2272656D61726B223A22E697A0E58F82E695B0E6B58BE8AF95222C2263726561746554696D65223A2244656320332C203230313620323A35353A353620504D227D7800);

-- ----------------------------
-- Table structure for `schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('1', 'testTask', 'test', 'renren', '0 0/30 * * * ?', '0', '有参数测试', '2016-12-01 23:16:46');
INSERT INTO `schedule_job` VALUES ('2', 'testTask', 'test2', null, '0 0/30 * * * ?', '1', '无参数测试', '2016-12-03 14:55:56');

-- ----------------------------
-- Table structure for `schedule_job_log`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
INSERT INTO `schedule_job_log` VALUES ('1', '2', 'testTask', 'test2', null, '0', null, '3', '2018-11-16 10:27:47');

-- ----------------------------
-- Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) DEFAULT NULL COMMENT 'key',
  `value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"\",\"qiniuBucketName\":\"\",\"qiniuDomain\":\"\",\"qiniuPrefix\":\"\",\"qiniuSecretKey\":\"\",\"type\":1}', '0', '云存储配置信息');
INSERT INTO `sys_config` VALUES ('2', 'name', '12131', '1', null);

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', '中国区', '0', '0');
INSERT INTO `sys_dept` VALUES ('2', '1', '北京分公司', '1', '0');
INSERT INTO `sys_dept` VALUES ('3', '1', '上海分公司', '2', '0');
INSERT INTO `sys_dept` VALUES ('4', '3', '技术部', '0', '0');
INSERT INTO `sys_dept` VALUES ('5', '3', '销售部', '1', '0');

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":2,\"username\":\"qxw\",\"password\":\"78858982450c9801e5c11e7635720c4135fe11bc6ae5d7c3fd47d4c1d19c27ec\",\"salt\":\"48oN4xrpNfBkxQDSmOuj\",\"email\":\"870439570@qq.com\",\"mobile\":\"13650885415\",\"status\":1,\"roleIdList\":[],\"createUserId\":1,\"createTime\":\"Mar 6, 2018 11:55:28 AM\"}', '53', '0:0:0:0:0:0:0:1', '2018-03-06 11:55:28');
INSERT INTO `sys_log` VALUES ('2', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":2,\"username\":\"qxw\",\"salt\":\"48oN4xrpNfBkxQDSmOuj\",\"email\":\"870439570@qq.com\",\"mobile\":\"1365088541\",\"status\":1,\"roleIdList\":[],\"createUserId\":1,\"createTime\":\"Mar 6, 2018 11:55:28 AM\"}', '76', '0:0:0:0:0:0:0:1', '2018-03-06 14:44:28');
INSERT INTO `sys_log` VALUES ('3', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":1,\"roleName\":\"普通管理员\",\"remark\":\"普通管理员\",\"createUserId\":1,\"menuIdList\":[1,2,15,16,17,18,31,32,33,34,35,6,7,8,9,10,11,12,13,14,27,30,29],\"createTime\":\"Mar 6, 2018 2:48:04 PM\"}', '81', '0:0:0:0:0:0:0:1', '2018-03-06 14:48:04');
INSERT INTO `sys_log` VALUES ('4', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":2,\"roleName\":\"超级管理员\",\"remark\":\"超级管理员\",\"createUserId\":1,\"menuIdList\":[1,2,15,16,17,18,31,32,33,34,35,3,19,20,21,22,4,23,24,25,26,5,6,7,8,9,10,11,12,13,14,27,30,29],\"createTime\":\"Mar 6, 2018 2:57:26 PM\"}', '61', '0:0:0:0:0:0:0:1', '2018-03-06 14:57:26');
INSERT INTO `sys_log` VALUES ('5', 'admin', '暂停定时任务', 'io.renren.modules.job.controller.ScheduleJobController.pause()', '[1]', '68', '0:0:0:0:0:0:0:1', '2018-03-06 15:00:38');
INSERT INTO `sys_log` VALUES ('6', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":2,\"username\":\"qxw\",\"salt\":\"48oN4xrpNfBkxQDSmOuj\",\"email\":\"870439570@qq.com\",\"mobile\":\"1365088541\",\"status\":1,\"roleIdList\":[1],\"createUserId\":1,\"createTime\":\"Mar 6, 2018 11:55:28 AM\"}', '44', '0:0:0:0:0:0:0:1', '2018-03-06 15:43:42');
INSERT INTO `sys_log` VALUES ('7', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":1,\"username\":\"admin\",\"salt\":\"YzcmCZNvbXocrsz9dm8e\",\"email\":\"admin@jeefast.cn\",\"mobile\":\"13812345678\",\"status\":1,\"roleIdList\":[2],\"createUserId\":1,\"createTime\":\"Nov 1, 2017 11:11:11 AM\"}', '30', '0:0:0:0:0:0:0:1', '2018-03-06 15:43:55');
INSERT INTO `sys_log` VALUES ('8', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '{\"roleId\":1,\"roleName\":\"普通管理员\",\"remark\":\"普通管理员\",\"createUserId\":1,\"menuIdList\":[1,2,15,16,17,18,31,32,33,34,35,5,6,7,8,9,10,11,12,13,14,27,30,29],\"createTime\":\"Mar 6, 2018 2:48:04 PM\"}', '66', '0:0:0:0:0:0:0:1', '2018-03-06 16:00:40');
INSERT INTO `sys_log` VALUES ('9', 'qxw', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":3,\"username\":\"aaaa\",\"password\":\"3718f23075d69404f66e5c047e626fec78198ee42a66b060bd029db1e1e2065d\",\"salt\":\"if02zw4pl4Nou5elttAW\",\"email\":\"919085258@qq.com\",\"mobile\":\"13360013636\",\"status\":1,\"roleIdList\":[],\"createUserId\":2,\"createTime\":\"Mar 6, 2018 4:01:37 PM\"}', '53', '0:0:0:0:0:0:0:1', '2018-03-06 16:01:37');
INSERT INTO `sys_log` VALUES ('10', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '31', '2', '0:0:0:0:0:0:0:1', '2018-11-14 18:48:55');
INSERT INTO `sys_log` VALUES ('11', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '35', '69', '0:0:0:0:0:0:0:1', '2018-11-14 18:49:06');
INSERT INTO `sys_log` VALUES ('12', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '31', '2', '0:0:0:0:0:0:0:1', '2018-11-14 18:49:18');
INSERT INTO `sys_log` VALUES ('13', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '34', '34', '0:0:0:0:0:0:0:1', '2018-11-14 18:49:23');
INSERT INTO `sys_log` VALUES ('14', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '33', '51', '0:0:0:0:0:0:0:1', '2018-11-14 18:49:59');
INSERT INTO `sys_log` VALUES ('15', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '32', '27', '0:0:0:0:0:0:0:1', '2018-11-14 18:50:15');
INSERT INTO `sys_log` VALUES ('16', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '31', '85', '0:0:0:0:0:0:0:1', '2018-11-14 18:50:21');
INSERT INTO `sys_log` VALUES ('17', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"微信公众号\",\"type\":0,\"orderNum\":0}', '43', '0:0:0:0:0:0:0:1', '2018-11-14 18:51:07');
INSERT INTO `sys_log` VALUES ('18', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":36,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"微信公众号\",\"type\":0,\"icon\":\"fa fa-cog\",\"orderNum\":0}', '41', '0:0:0:0:0:0:0:1', '2018-11-14 18:51:37');
INSERT INTO `sys_log` VALUES ('19', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":36,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"微信公众号\",\"type\":0,\"icon\":\"fa  fa-weixin\",\"orderNum\":0}', '28', '0:0:0:0:0:0:0:1', '2018-11-14 18:52:32');
INSERT INTO `sys_log` VALUES ('20', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"parentId\":36,\"parentName\":\"微信公众号\",\"name\":\"大转盘礼品\",\"url\":\"modules/gift/user.html\",\"type\":1,\"orderNum\":0}', '32', '0:0:0:0:0:0:0:1', '2018-11-14 18:57:45');
INSERT INTO `sys_log` VALUES ('21', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":37,\"parentId\":36,\"parentName\":\"微信公众号\",\"name\":\"大转盘礼品\",\"url\":\"modules/gift/user.html\",\"perms\":\"sys:gift:list,sys:gift:info,sys:gift:save,sys:gift:update,sys:gift:delete\",\"type\":1,\"orderNum\":0}', '39', '0:0:0:0:0:0:0:1', '2018-11-14 19:02:16');
INSERT INTO `sys_log` VALUES ('22', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"服务监控\",\"type\":0,\"icon\":\"fas fa-first-aid\",\"orderNum\":0}', '49', '0:0:0:0:0:0:0:1', '2018-11-15 13:04:27');
INSERT INTO `sys_log` VALUES ('23', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":38,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"服务监控\",\"type\":0,\"icon\":\"fa fa-first-aid\",\"orderNum\":0}', '43', '0:0:0:0:0:0:0:1', '2018-11-15 13:04:46');
INSERT INTO `sys_log` VALUES ('24', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":38,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"服务监控\",\"type\":0,\"icon\":\"fas fa-first-aid\",\"orderNum\":0}', '40', '0:0:0:0:0:0:0:1', '2018-11-15 13:12:22');
INSERT INTO `sys_log` VALUES ('25', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":38,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"服务监控\",\"type\":0,\"icon\":\"fa fa-area-chart\",\"orderNum\":0}', '41', '0:0:0:0:0:0:0:1', '2018-11-15 13:26:59');
INSERT INTO `sys_log` VALUES ('26', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"服务注册中心\",\"url\":\"http://localhost:8761/\",\"type\":1,\"icon\":\"fa fa-life-ring\",\"orderNum\":0}', '41', '0:0:0:0:0:0:0:1', '2018-11-15 13:28:12');
INSERT INTO `sys_log` VALUES ('27', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":39,\"parentId\":38,\"parentName\":\"服务监控\",\"name\":\"服务注册中心\",\"url\":\"http://localhost:8761/\",\"type\":1,\"icon\":\"fa fa-life-ring\",\"orderNum\":0}', '62', '0:0:0:0:0:0:0:1', '2018-11-15 13:28:25');
INSERT INTO `sys_log` VALUES ('28', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '39', '72', '0:0:0:0:0:0:0:1', '2018-11-15 13:39:27');
INSERT INTO `sys_log` VALUES ('29', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '38', '30', '0:0:0:0:0:0:0:1', '2018-11-15 13:39:31');
INSERT INTO `sys_log` VALUES ('30', 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '{\"key\":\"name\",\"value\":\"12131\"}', '253', '0:0:0:0:0:0:0:1', '2018-11-15 13:54:16');
INSERT INTO `sys_log` VALUES ('31', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":37,\"parentId\":36,\"parentName\":\"微信公众号\",\"name\":\"大转盘礼品\",\"url\":\"modules/gift/gift.html\",\"perms\":\"sys:gift:list,sys:gift:info,sys:gift:save,sys:gift:update,sys:gift:delete\",\"type\":1,\"orderNum\":0}', '36', '0:0:0:0:0:0:0:1', '2018-11-15 14:43:22');
INSERT INTO `sys_log` VALUES ('32', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '{\"roleId\":1,\"roleName\":\"普通管理员\",\"remark\":\"普通管理员\",\"createUserId\":1,\"menuIdList\":[1,2,15,16,17,18,5,6,7,8,9,10,11,12,13,14,27,30,29,36,37],\"createTime\":\"Mar 6, 2018 2:48:04 PM\"}', '151', '0:0:0:0:0:0:0:1', '2018-11-15 15:20:41');
INSERT INTO `sys_log` VALUES ('33', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '{\"roleId\":2,\"roleName\":\"超级管理员\",\"remark\":\"超级管理员\",\"createUserId\":1,\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,4,23,24,25,26,5,6,7,8,9,10,11,12,13,14,27,30,29,36,37],\"createTime\":\"Mar 6, 2018 2:57:26 PM\"}', '42', '0:0:0:0:0:0:0:1', '2018-11-15 15:20:49');
INSERT INTO `sys_log` VALUES ('34', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":37,\"parentId\":36,\"parentName\":\"微信公众号\",\"name\":\"大转盘礼品\",\"url\":\"modules/gift/gift.html\",\"perms\":\"sys:gift:all\",\"type\":1,\"orderNum\":0}', '50', '0:0:0:0:0:0:0:1', '2018-11-15 15:27:22');
INSERT INTO `sys_log` VALUES ('35', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":37,\"parentId\":36,\"parentName\":\"微信公众号\",\"name\":\"微信基础token\",\"url\":\"modules/wechat/token.html\",\"perms\":\"sys:wechattoken:all\",\"type\":1,\"orderNum\":0}', '39', '0:0:0:0:0:0:0:1', '2018-11-15 18:53:55');
INSERT INTO `sys_log` VALUES ('36', 'admin', '立即执行任务', 'com.pflm.modules.job.controller.ScheduleJobController.run()', '[2]', '165', '0:0:0:0:0:0:0:1', '2018-11-16 10:27:30');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'fa fa-cog', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '用户管理', 'modules/sys/user.html', null, '1', 'fa fa-user', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', '角色管理', 'modules/sys/role.html', null, '1', 'fa fa-user-secret', '2');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'modules/sys/menu.html', null, '1', 'fa fa-th-list', '3');
INSERT INTO `sys_menu` VALUES ('5', '1', 'SQL监控', 'druid/sql.html', null, '1', 'fa fa-bug', '4');
INSERT INTO `sys_menu` VALUES ('6', '1', '定时任务', 'modules/job/schedule.html', null, '1', 'fa fa-tasks', '5');
INSERT INTO `sys_menu` VALUES ('7', '6', '查看', null, 'sys:schedule:list,sys:schedule:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('8', '6', '新增', null, 'sys:schedule:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('9', '6', '修改', null, 'sys:schedule:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('10', '6', '删除', null, 'sys:schedule:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('11', '6', '暂停', null, 'sys:schedule:pause', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('12', '6', '恢复', null, 'sys:schedule:resume', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('13', '6', '立即执行', null, 'sys:schedule:run', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('14', '6', '日志列表', null, 'sys:schedule:log', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('19', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('20', '3', '新增', null, 'sys:role:save,sys:menu:perms', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('21', '3', '修改', null, 'sys:role:update,sys:menu:perms', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('22', '3', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('23', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('24', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('25', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('26', '4', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('27', '1', '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'fa fa-sun-o', '6');
INSERT INTO `sys_menu` VALUES ('29', '1', '系统日志', 'modules/sys/log.html', 'sys:log:list', '1', 'fa fa-file-text-o', '7');
INSERT INTO `sys_menu` VALUES ('30', '1', '文件上传', 'modules/oss/oss.html', 'sys:oss:all', '1', 'fa fa-file-image-o', '6');
INSERT INTO `sys_menu` VALUES ('36', '0', '微信公众号', null, null, '0', 'fa  fa-weixin', '0');
INSERT INTO `sys_menu` VALUES ('37', '36', '微信基础token', 'modules/wechat/token.html', 'sys:wechattoken:all', '1', null, '0');

-- ----------------------------
-- Table structure for `sys_oss`
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '普通管理员', '普通管理员', null, '2018-03-06 14:48:04', '1');
INSERT INTO `sys_role` VALUES ('2', '超级管理员', '超级管理员', null, '2018-03-06 14:57:26', '1');

-- ----------------------------
-- Table structure for `sys_role_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('82', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('83', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('84', '1', '15');
INSERT INTO `sys_role_menu` VALUES ('85', '1', '16');
INSERT INTO `sys_role_menu` VALUES ('86', '1', '17');
INSERT INTO `sys_role_menu` VALUES ('87', '1', '18');
INSERT INTO `sys_role_menu` VALUES ('88', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('89', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('90', '1', '7');
INSERT INTO `sys_role_menu` VALUES ('91', '1', '8');
INSERT INTO `sys_role_menu` VALUES ('92', '1', '9');
INSERT INTO `sys_role_menu` VALUES ('93', '1', '10');
INSERT INTO `sys_role_menu` VALUES ('94', '1', '11');
INSERT INTO `sys_role_menu` VALUES ('95', '1', '12');
INSERT INTO `sys_role_menu` VALUES ('96', '1', '13');
INSERT INTO `sys_role_menu` VALUES ('97', '1', '14');
INSERT INTO `sys_role_menu` VALUES ('98', '1', '27');
INSERT INTO `sys_role_menu` VALUES ('99', '1', '30');
INSERT INTO `sys_role_menu` VALUES ('100', '1', '29');
INSERT INTO `sys_role_menu` VALUES ('101', '1', '36');
INSERT INTO `sys_role_menu` VALUES ('102', '1', '37');
INSERT INTO `sys_role_menu` VALUES ('103', '2', '1');
INSERT INTO `sys_role_menu` VALUES ('104', '2', '2');
INSERT INTO `sys_role_menu` VALUES ('105', '2', '15');
INSERT INTO `sys_role_menu` VALUES ('106', '2', '16');
INSERT INTO `sys_role_menu` VALUES ('107', '2', '17');
INSERT INTO `sys_role_menu` VALUES ('108', '2', '18');
INSERT INTO `sys_role_menu` VALUES ('109', '2', '3');
INSERT INTO `sys_role_menu` VALUES ('110', '2', '19');
INSERT INTO `sys_role_menu` VALUES ('111', '2', '20');
INSERT INTO `sys_role_menu` VALUES ('112', '2', '21');
INSERT INTO `sys_role_menu` VALUES ('113', '2', '22');
INSERT INTO `sys_role_menu` VALUES ('114', '2', '4');
INSERT INTO `sys_role_menu` VALUES ('115', '2', '23');
INSERT INTO `sys_role_menu` VALUES ('116', '2', '24');
INSERT INTO `sys_role_menu` VALUES ('117', '2', '25');
INSERT INTO `sys_role_menu` VALUES ('118', '2', '26');
INSERT INTO `sys_role_menu` VALUES ('119', '2', '5');
INSERT INTO `sys_role_menu` VALUES ('120', '2', '6');
INSERT INTO `sys_role_menu` VALUES ('121', '2', '7');
INSERT INTO `sys_role_menu` VALUES ('122', '2', '8');
INSERT INTO `sys_role_menu` VALUES ('123', '2', '9');
INSERT INTO `sys_role_menu` VALUES ('124', '2', '10');
INSERT INTO `sys_role_menu` VALUES ('125', '2', '11');
INSERT INTO `sys_role_menu` VALUES ('126', '2', '12');
INSERT INTO `sys_role_menu` VALUES ('127', '2', '13');
INSERT INTO `sys_role_menu` VALUES ('128', '2', '14');
INSERT INTO `sys_role_menu` VALUES ('129', '2', '27');
INSERT INTO `sys_role_menu` VALUES ('130', '2', '30');
INSERT INTO `sys_role_menu` VALUES ('131', '2', '29');
INSERT INTO `sys_role_menu` VALUES ('132', '2', '36');
INSERT INTO `sys_role_menu` VALUES ('133', '2', '37');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'admin@jeefast.cn', '13812345678', '1', null, '2017-11-01 11:11:11', null);
INSERT INTO `sys_user` VALUES ('2', 'qxw', '78858982450c9801e5c11e7635720c4135fe11bc6ae5d7c3fd47d4c1d19c27ec', '48oN4xrpNfBkxQDSmOuj', '870439570@qq.com', '1365088541', '1', null, '2018-03-06 11:55:28', '1');
INSERT INTO `sys_user` VALUES ('3', 'aaaa', '3718f23075d69404f66e5c047e626fec78198ee42a66b060bd029db1e1e2065d', 'if02zw4pl4Nou5elttAW', '919085258@qq.com', '13360013636', '1', null, '2018-03-06 16:01:37', '2');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '2', '1');
INSERT INTO `sys_user_role` VALUES ('2', '1', '2');

-- ----------------------------
-- Table structure for `sys_user_token`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES ('1', 'e92d5014299be99dfdf737465894c54c', '2018-11-16 22:37:23', '2018-11-16 10:37:23');
INSERT INTO `sys_user_token` VALUES ('2', '69a631fff8abda321d4e17d8ece105bf', '2018-03-07 04:01:04', '2018-03-06 16:01:04');

-- ----------------------------
-- Table structure for `tb_token`
-- ----------------------------
DROP TABLE IF EXISTS `tb_token`;
CREATE TABLE `tb_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户Token';

-- ----------------------------
-- Records of tb_token
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'apiadmin', '13812345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');

-- ----------------------------
-- Table structure for `w_token`
-- ----------------------------
DROP TABLE IF EXISTS `w_token`;
CREATE TABLE `w_token` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `access_token` varchar(500) NOT NULL COMMENT '基础token',
  `expires_in` int(11) NOT NULL COMMENT '有效期  单位秒',
  `create_time` varchar(20) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '1',
  `expiry_time` varchar(20) NOT NULL,
  `update_time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='基础token表';

-- ----------------------------
-- Records of w_token
-- ----------------------------
INSERT INTO `w_token` VALUES ('1', '15_CW-j158x54pw3-9SIENswh6Vj-5u1n8MmIo5-oKa_RNHnA12UU5jr7eKEkeMb4V8C-TXCawgxgaHXLG84K4SpjqVB2T8UvpvxgWEq6SP1VgggotEXhdVm0RXFrXZfTbfimajUzPj0ODpEOm6YPKiABARID', '7200', '2018-11-15 17:56:30', '1', '2018-11-15 19:56:30', null);
