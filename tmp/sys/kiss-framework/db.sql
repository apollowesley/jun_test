# MySQL-Front 5.1  (Build 4.2)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;


# Host: localhost    Database: test
# ------------------------------------------------------
# Server version 5.1.39-community

CREATE DATABASE `test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `test`;

#
# Source for table attachment
#

CREATE TABLE `attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accessType` int(11) DEFAULT NULL,
  `attachedAt` datetime DEFAULT NULL,
  `attachmentContentId` bigint(20) NOT NULL,
  `contentType` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `attachment_size` int(11) DEFAULT NULL,
  `attachedBy_id` varchar(255) DEFAULT NULL,
  `TaskData_Attachments_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1C935438EF5F064` (`attachedBy_id`),
  KEY `FK1C93543F21826D9` (`TaskData_Attachments_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table attachment
#


#
# Source for table booleanexpression
#

CREATE TABLE `booleanexpression` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expression` longtext,
  `type` varchar(255) DEFAULT NULL,
  `Escalation_Constraints_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKE3D208C0AFB75F7D` (`Escalation_Constraints_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table booleanexpression
#


#
# Source for table content
#

CREATE TABLE `content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table content
#


#
# Source for table deadline
#

CREATE TABLE `deadline` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deadline_date` datetime DEFAULT NULL,
  `escalated` smallint(6) DEFAULT NULL,
  `Deadlines_StartDeadLine_Id` bigint(20) DEFAULT NULL,
  `Deadlines_EndDeadLine_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK21DF3E78684BACA3` (`Deadlines_StartDeadLine_Id`),
  KEY `FK21DF3E7827ABEB8A` (`Deadlines_EndDeadLine_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table deadline
#


#
# Source for table delegation_delegates
#

CREATE TABLE `delegation_delegates` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK47485D572C122ED2` (`entity_id`),
  KEY `FK47485D5736B2F154` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table delegation_delegates
#


#
# Source for table email_header
#

CREATE TABLE `email_header` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `body` longtext,
  `fromAddress` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `replyToAddress` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table email_header
#


#
# Source for table escalation
#

CREATE TABLE `escalation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `Deadline_Escalation_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK67B2C6B5C7A04C70` (`Deadline_Escalation_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table escalation
#


#
# Source for table eventtypes
#

CREATE TABLE `eventtypes` (
  `InstanceId` bigint(20) NOT NULL,
  `element` varchar(255) DEFAULT NULL,
  KEY `FKB0E5621F7665489A` (`InstanceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table eventtypes
#


#
# Source for table i18ntext
#

CREATE TABLE `i18ntext` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `shortText` varchar(255) DEFAULT NULL,
  `text` longtext,
  `Task_Subjects_Id` bigint(20) DEFAULT NULL,
  `Task_Names_Id` bigint(20) DEFAULT NULL,
  `Task_Descriptions_Id` bigint(20) DEFAULT NULL,
  `Reassignment_Documentation_Id` bigint(20) DEFAULT NULL,
  `Notification_Subjects_Id` bigint(20) DEFAULT NULL,
  `Notification_Names_Id` bigint(20) DEFAULT NULL,
  `Notification_Documentation_Id` bigint(20) DEFAULT NULL,
  `Notification_Descriptions_Id` bigint(20) DEFAULT NULL,
  `Deadline_Documentation_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2349686B2162DFB4` (`Notification_Descriptions_Id`),
  KEY `FK2349686BD488CEEB` (`Notification_Names_Id`),
  KEY `FK2349686B5EEBB6D9` (`Reassignment_Documentation_Id`),
  KEY `FK2349686B3330F6D9` (`Deadline_Documentation_Id`),
  KEY `FK2349686B8046A239` (`Notification_Documentation_Id`),
  KEY `FK2349686B69B21EE8` (`Task_Descriptions_Id`),
  KEY `FK2349686BB2FA6B18` (`Task_Subjects_Id`),
  KEY `FK2349686B98B62B` (`Task_Names_Id`),
  KEY `FK2349686BF952CEE4` (`Notification_Subjects_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table i18ntext
#


#
# Source for table j_jbpm_info
#

CREATE TABLE `j_jbpm_info` (
  `jbpm_info_id` varchar(32) NOT NULL COMMENT '流水号',
  `jbpm_file_name` varchar(100) NOT NULL COMMENT '对应工作流文件名称',
  `jbpm_file_id` varchar(100) NOT NULL COMMENT '对应工作流文件id',
  `jbpm_business_id` varchar(100) DEFAULT NULL COMMENT '业务ID',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`jbpm_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作流信息表';

#
# Dumping data for table j_jbpm_info
#


#
# Source for table m_bbs
#

CREATE TABLE `m_bbs` (
  `bbs_id` varchar(32) NOT NULL COMMENT ' 主键id',
  `title` varchar(300) NOT NULL COMMENT '文章标题',
  `content` varchar(600) NOT NULL COMMENT '文章内容',
  `read_number` int(11) NOT NULL COMMENT '文章阅读次数',
  `status` int(11) NOT NULL COMMENT '文章状态0:普通1:火热',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`bbs_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛';

#
# Dumping data for table m_bbs
#


#
# Source for table m_bbs_reply
#

CREATE TABLE `m_bbs_reply` (
  `reply_id` varchar(32) NOT NULL,
  `reply_content` varchar(600) DEFAULT NULL COMMENT '回复内容',
  `bbs_id` varchar(32) DEFAULT NULL COMMENT '回复BBSID',
  `status` int(1) DEFAULT '0' COMMENT '回复状态 0：未审批 1：审批通过 2：审批不通过',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`reply_id`),
  KEY `FK_Reference_12` (`bbs_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛回复';

#
# Dumping data for table m_bbs_reply
#


#
# Source for table m_information
#

CREATE TABLE `m_information` (
  `information_id` varchar(32) NOT NULL,
  `menu_id` varchar(32) DEFAULT NULL COMMENT '对应菜单id',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `is_home` int(1) DEFAULT '0' COMMENT '是否首页显示 0：不显示 1：显示',
  `content` longtext COMMENT '内容',
  `enable` int(1) DEFAULT '0' COMMENT '是否有效 0：有效 -1：无效',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  `release_time` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`information_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table m_information
#


#
# Source for table m_menu
#

CREATE TABLE `m_menu` (
  `menu_id` varchar(32) NOT NULL COMMENT '流水号',
  `menu_name` varchar(32) NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(255) DEFAULT NULL COMMENT '菜单URL',
  `menu_level` int(11) NOT NULL COMMENT '菜单等级',
  `parent_menu` varchar(32) DEFAULT NULL COMMENT '父菜单',
  `enable` int(1) DEFAULT '0' COMMENT '0:可用 -1不可用',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  `menu_sort` int(11) DEFAULT NULL,
  `modifiable` int(1) DEFAULT '0' COMMENT '0不可修改，1可修改',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站菜单';

#
# Dumping data for table m_menu
#


#
# Source for table m_menu_information
#

CREATE TABLE `m_menu_information` (
  `menu_id` varchar(32) NOT NULL,
  `information_id` varchar(32) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`menu_id`,`information_id`),
  KEY `FK_Reference_9` (`information_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单 内容中间表';

#
# Dumping data for table m_menu_information
#


#
# Source for table m_message
#

CREATE TABLE `m_message` (
  `message_id` varchar(32) NOT NULL COMMENT '主键id',
  `message_content` varchar(255) NOT NULL COMMENT '留言内容',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '留言状态0:未审批:1审批通过:2审批不通过',
  `message_user` varchar(32) DEFAULT NULL COMMENT '留言人',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table m_message
#


#
# Source for table m_message_reply
#

CREATE TABLE `m_message_reply` (
  `reply_id` varchar(32) NOT NULL,
  `reply_content` varchar(600) NOT NULL COMMENT '回复内容',
  `message_id` varchar(32) DEFAULT NULL COMMENT '回复留言id',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`reply_id`),
  KEY `FK_Reference_11` (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='留言回复表';

#
# Dumping data for table m_message_reply
#


#
# Source for table m_netuser
#

CREATE TABLE `m_netuser` (
  `user_id` varchar(32) NOT NULL COMMENT '主键',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `nickname` varchar(100) DEFAULT NULL COMMENT '呢称',
  `enable` int(1) DEFAULT '0' COMMENT '0:可用 -1不可用',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table m_netuser
#


#
# Source for table m_picture
#

CREATE TABLE `m_picture` (
  `picture_id` varchar(32) NOT NULL COMMENT '主键id',
  `picture_type_id` varchar(32) DEFAULT NULL COMMENT '图片模块关联id',
  `picture_name` varchar(255) NOT NULL COMMENT '图片名称',
  `picture_url` varchar(255) NOT NULL COMMENT '图片保存路径',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`picture_id`),
  KEY `FK_Reference_13` (`picture_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table m_picture
#


#
# Source for table m_picture_type
#

CREATE TABLE `m_picture_type` (
  `picture_type_id` varchar(32) NOT NULL COMMENT '主键id',
  `type_name` varchar(200) NOT NULL COMMENT '模块名称',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`picture_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片类型';

#
# Dumping data for table m_picture_type
#


#
# Source for table nodeinstancelog
#

CREATE TABLE `nodeinstancelog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `log_date` datetime DEFAULT NULL,
  `nodeId` varchar(255) DEFAULT NULL,
  `nodeInstanceId` varchar(255) DEFAULT NULL,
  `nodeName` varchar(255) DEFAULT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table nodeinstancelog
#


#
# Source for table notification
#

CREATE TABLE `notification` (
  `DTYPE` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `priority` int(11) NOT NULL,
  `Escalation_Notifications_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2D45DD0B3E0890B` (`Escalation_Notifications_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table notification
#


#
# Source for table notification_bas
#

CREATE TABLE `notification_bas` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK2DD68EE02C122ED2` (`entity_id`),
  KEY `FK2DD68EE09C76EABA` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table notification_bas
#


#
# Source for table notification_email_header
#

CREATE TABLE `notification_email_header` (
  `Notification_id` bigint(20) NOT NULL,
  `emailHeaders_id` bigint(20) NOT NULL,
  `mapkey` varchar(255) NOT NULL,
  PRIMARY KEY (`Notification_id`,`mapkey`),
  UNIQUE KEY `emailHeaders_id` (`emailHeaders_id`),
  KEY `FKF30FE3441F7B912A` (`emailHeaders_id`),
  KEY `FKF30FE34430BE501C` (`Notification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table notification_email_header
#


#
# Source for table notification_recipients
#

CREATE TABLE `notification_recipients` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK98FD214E2C122ED2` (`entity_id`),
  KEY `FK98FD214E9C76EABA` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table notification_recipients
#


#
# Source for table organizationalentity
#

CREATE TABLE `organizationalentity` (
  `DTYPE` varchar(31) NOT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table organizationalentity
#

INSERT INTO `organizationalentity` VALUES ('User','21232f297a57a5a743894a0e4a801fc1');
INSERT INTO `organizationalentity` VALUES ('Group','297e5e0d3c6f60cd013c6f83ab1f0002');
INSERT INTO `organizationalentity` VALUES ('Group','297e5e0d3c7203f2013c724bf5b80004');
INSERT INTO `organizationalentity` VALUES ('Group','297e5e0d3c90bdeb013c90c0ffd80002');
INSERT INTO `organizationalentity` VALUES ('Group','297e5e0d3c90bdeb013c90de69060004');
INSERT INTO `organizationalentity` VALUES ('User','297e5e0d3d006e26013d0077faa80007');
INSERT INTO `organizationalentity` VALUES ('Group','297e5e0d3d87db10013d882035c9000c');
INSERT INTO `organizationalentity` VALUES ('Group','297e5e0d3d87db10013d8822631e000f');
INSERT INTO `organizationalentity` VALUES ('User','297e5e0d3da46695013da49dbfa90003');
INSERT INTO `organizationalentity` VALUES ('User','Administrator');

#
# Source for table peopleassignments_bas
#

CREATE TABLE `peopleassignments_bas` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK9D8CF4EC2C122ED2` (`entity_id`),
  KEY `FK9D8CF4EC36B2F154` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table peopleassignments_bas
#


#
# Source for table peopleassignments_exclowners
#

CREATE TABLE `peopleassignments_exclowners` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FKC77B97E42C122ED2` (`entity_id`),
  KEY `FKC77B97E436B2F154` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table peopleassignments_exclowners
#


#
# Source for table peopleassignments_potowners
#

CREATE TABLE `peopleassignments_potowners` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK1EE418D2C122ED2` (`entity_id`),
  KEY `FK1EE418D36B2F154` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table peopleassignments_potowners
#


#
# Source for table peopleassignments_recipients
#

CREATE TABLE `peopleassignments_recipients` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FKC6F615C22C122ED2` (`entity_id`),
  KEY `FKC6F615C236B2F154` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table peopleassignments_recipients
#


#
# Source for table peopleassignments_stakeholders
#

CREATE TABLE `peopleassignments_stakeholders` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK482F79D52C122ED2` (`entity_id`),
  KEY `FK482F79D536B2F154` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table peopleassignments_stakeholders
#


#
# Source for table processinstanceeventinfo
#

CREATE TABLE `processinstanceeventinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `eventType` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table processinstanceeventinfo
#


#
# Source for table processinstanceinfo
#

CREATE TABLE `processinstanceinfo` (
  `InstanceId` bigint(20) NOT NULL AUTO_INCREMENT,
  `OPTLOCK` int(11) DEFAULT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `lastReadDate` datetime DEFAULT NULL,
  `lastModificationDate` datetime DEFAULT NULL,
  `state` int(11) NOT NULL,
  `processInstanceByteArray` longblob,
  PRIMARY KEY (`InstanceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table processinstanceinfo
#


#
# Source for table processinstancelog
#

CREATE TABLE `processinstancelog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `end_date` datetime DEFAULT NULL,
  `outcome` varchar(255) DEFAULT NULL,
  `parentProcessInstanceId` bigint(20) DEFAULT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table processinstancelog
#


#
# Source for table reassignment
#

CREATE TABLE `reassignment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Escalation_Reassignments_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK724D0560A5C17EE0` (`Escalation_Reassignments_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table reassignment
#


#
# Source for table reassignment_potentialowners
#

CREATE TABLE `reassignment_potentialowners` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK90B59CFF2C122ED2` (`entity_id`),
  KEY `FK90B59CFFE17E130F` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table reassignment_potentialowners
#


#
# Source for table sessioninfo
#

CREATE TABLE `sessioninfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lastModificationDate` datetime DEFAULT NULL,
  `rulesByteArray` longblob,
  `startDate` datetime DEFAULT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table sessioninfo
#


#
# Source for table subtasksstrategy
#

CREATE TABLE `subtasksstrategy` (
  `DTYPE` varchar(100) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `Task_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKDE5DF2E136B2F154` (`Task_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table subtasksstrategy
#


#
# Source for table t_dept
#

CREATE TABLE `t_dept` (
  `dept_id` varchar(32) NOT NULL COMMENT '流水号',
  `dept_name` varchar(32) NOT NULL COMMENT '部门名称',
  `dept_code` varchar(32) NOT NULL COMMENT '部门编号',
  `dept_level` int(11) NOT NULL COMMENT '部门等级',
  `parent_dept` varchar(32) DEFAULT NULL COMMENT '父部门',
  `enable` int(1) DEFAULT '0' COMMENT '0:可用 -1不可用',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  `dept_desc` varchar(255) DEFAULT NULL COMMENT '部门描述',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信息部门';

#
# Dumping data for table t_dept
#

INSERT INTO `t_dept` VALUES ('297e5e0d3d008014013d008cb7a00006','测试部门','CSBM',1,NULL,0,'2013-02-22 14:17:34','2013-05-13 16:42:17','21232f297a57a5a743894a0e4a801fc1','297e5e0d3d006e26013d0077faa80007',5,'3211');
INSERT INTO `t_dept` VALUES ('297e5e0d3e8332d8013e83380e930005','部门测试','BMCE',1,NULL,0,'2013-05-08 16:18:09',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,'');
INSERT INTO `t_dept` VALUES ('402845813c6bfe00013c6c08c8620005','信息部','IT',1,NULL,0,'2013-01-10 10:21:14','2013-01-22 11:35:19',NULL,'1',3,'负责日常信息化事物');

#
# Source for table t_dept_role
#

CREATE TABLE `t_dept_role` (
  `dept_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`dept_id`,`role_id`),
  UNIQUE KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table t_dept_role
#

INSERT INTO `t_dept_role` VALUES ('402845813c6bfe00013c6c08c8620005','297e5e0d3c6f60cd013c6f83ab1f0002','2013-01-25 21:16:22',NULL,NULL,NULL,1);
INSERT INTO `t_dept_role` VALUES ('402845813c6bfe00013c6c08c8620005','297e5e0d3c90bdeb013c90c0ffd80002','2013-01-31 21:17:13',NULL,NULL,NULL,1);

#
# Source for table t_menu
#

CREATE TABLE `t_menu` (
  `menu_id` varchar(32) NOT NULL COMMENT '流水号',
  `menu_name` varchar(32) NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(255) DEFAULT NULL COMMENT '菜单URL',
  `menu_level` int(11) NOT NULL COMMENT '菜单等级',
  `parent_menu` varchar(32) DEFAULT NULL COMMENT '父菜单',
  `enable` int(1) DEFAULT '0' COMMENT '0:可用 -1不可用',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  `menu_sort` int(11) DEFAULT NULL,
  `modifiable` int(1) DEFAULT '0' COMMENT '0不可修改，1可修改',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单管理';

#
# Dumping data for table t_menu
#

INSERT INTO `t_menu` VALUES ('297e5e0d3c533e24013c537c20730005','部门管理','',2,'297e5e0d3c533e24013c537c2073000l',0,'2013-01-19 23:45:09','2013-05-07 17:13:26','1','21232f297a57a5a743894a0e4a801fc1',4,2,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3c533e24013c537c2073000a','菜单管理','/user/getMenus.htm',2,'297e5e0d3c533e24013c537c2073000l',0,'2013-01-10 11:14:01','2013-05-07 17:13:20',NULL,'21232f297a57a5a743894a0e4a801fc1',3,1,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3c533e24013c537c2073000l','系统管理',NULL,1,NULL,0,'2013-01-10 10:19:10','2013-05-13 16:38:18',NULL,'21232f297a57a5a743894a0e4a801fc1',85,1,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3c55c1f7013c55ced6a40001','数据清理','',2,'297e5e0d3c533e24013c537c2073000l',0,'2013-01-20 10:34:44','2013-05-07 17:13:36','1','21232f297a57a5a743894a0e4a801fc1',3,6,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3c55c1f7013c55cf29ed0002','菜单清理','/system/toShowMenuClear.htm',3,'297e5e0d3c55c1f7013c55ced6a40001',0,'2013-01-20 10:35:05',NULL,'1',NULL,1,1,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3c55c1f7013c55cf64540003','部门清理','/system/toShowDeptClear.htm',3,'297e5e0d3c55c1f7013c55ced6a40001',0,'2013-01-20 10:35:20',NULL,'1',NULL,1,2,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3c55c1f7013c55d1243b0004','部门信息管理','/rela/toShowDepts.htm',3,'297e5e0d3c533e24013c537c20730005',0,'2013-01-20 10:37:15',NULL,'1',NULL,1,1,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3c55c1f7013c55d1dfea0005','部门角色管理','/rela/toShowRoles.htm',3,'297e5e0d3c533e24013c537c20730005',0,'2013-01-20 10:38:03',NULL,'1',NULL,1,2,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3c6b097b013c6b28755b0001','系统配置','/system/toSystemConfig.htm',2,'297e5e0d3c533e24013c537c2073000l',0,'2013-01-24 14:04:39','2013-01-25 23:09:42','1','21232f297a57a5a743894a0e4a801fc1',2,4,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3c7203f2013c7241a5a40002','用户管理','/user/toShowUsers.htm',2,'297e5e0d3c533e24013c537c2073000l',0,'2013-01-25 23:09:30',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,3,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3c754242013c75432de70002','日志管理','',2,'297e5e0d3c533e24013c537c2073000l',0,'2013-01-26 13:10:02','2013-05-07 17:13:49','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,7,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3c754242013c75435e1a0003','系统日志','/system/toShowSystemLogs.htm',3,'297e5e0d3c754242013c75432de70002',-1,'2013-01-26 13:10:14',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,1,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3c754242013c7543a4e20004','登录日志','/system/toShowUserLogs.htm',3,'297e5e0d3c754242013c75432de70002',0,'2013-01-26 13:10:32',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,2,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3d61d4c6013d61efa9620003','角色清理','/system/toShowRoleClear.htm',3,'297e5e0d3c55c1f7013c55ced6a40001',0,'2013-03-13 12:08:49',NULL,'297e5e0d3d006e26013d0077faa80007',NULL,1,3,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3d61d4c6013d61f036250004','用户清理','/system/toShowUserClear.htm',3,'297e5e0d3c55c1f7013c55ced6a40001',0,'2013-03-13 12:09:25',NULL,'297e5e0d3d006e26013d0077faa80007',NULL,1,4,0);
INSERT INTO `t_menu` VALUES ('297e5e0d3d8b8168013d8b8448bb0003','业务管理',NULL,1,NULL,0,'2013-03-21 13:55:35','2013-03-21 13:55:44','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,2,1);
INSERT INTO `t_menu` VALUES ('297e5e0d3d8bc744013d8bef64900004','测试','',2,'297e5e0d3d8b8168013d8b8448bb0003',0,'2013-03-21 15:52:34',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,1,1);
INSERT INTO `t_menu` VALUES ('297e5e0d3daad48d013daad558c60002','示例',NULL,1,NULL,0,'2013-03-27 15:52:21',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,3,1);
INSERT INTO `t_menu` VALUES ('297e5e0d3daad48d013daad5bf930003','文件上传','/system/toShowFileUploadExample.htm',2,'297e5e0d3daad48d013daad558c60002',0,'2013-03-27 15:52:47','2013-04-15 10:45:57','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,1,1);
INSERT INTO `t_menu` VALUES ('297e5e0d3dafec6d013dafed3ef40001','导入导出','/system/toShowExportExg.htm',2,'297e5e0d3daad48d013daad558c60002',0,'2013-03-28 15:36:33','2013-04-15 10:45:50','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',3,2,1);
INSERT INTO `t_menu` VALUES ('297e5e0d3dc4d999013dc4dd3bcc0002','自定义表单',NULL,1,NULL,0,'2013-04-01 17:11:05','2013-04-12 16:14:04','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,5,1);
INSERT INTO `t_menu` VALUES ('297e5e0d3dc4d999013dc4de13ab0003','新增自定义表单','/form/toShowCkeditor.htm',2,'297e5e0d3dc4d999013dc4dd3bcc0002',0,'2013-04-01 17:12:01',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,1,1);
INSERT INTO `t_menu` VALUES ('297e5e0d3e7e3df4013e7e4408430005','操作管理','/operate/toShowOperates.htm',2,'297e5e0d3c533e24013c537c2073000l',0,'2013-05-07 17:13:07','2013-05-07 17:13:56','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,5,0);
INSERT INTO `t_menu` VALUES ('402845813dfd4a9f013dfd4ee62d0005','工作流',NULL,1,NULL,0,'2013-04-12 16:13:59',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,4,1);
INSERT INTO `t_menu` VALUES ('402845813dfd4a9f013dfd4f990e0006','创建流程','',2,'402845813dfd4a9f013dfd4ee62d0005',0,'2013-04-12 16:14:45',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,1,1);

#
# Source for table t_operate
#

CREATE TABLE `t_operate` (
  `operate_id` varchar(32) NOT NULL COMMENT '主键',
  `operate_name` varchar(30) NOT NULL COMMENT '操作名称',
  `operate_url` varchar(100) NOT NULL COMMENT '操作链接',
  `operate_desc` varchar(600) DEFAULT NULL COMMENT '描述',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  `enable` int(1) DEFAULT '0' COMMENT '是否有效 0：有效 -1：无效',
  `menu_id` varchar(32) NOT NULL DEFAULT '',
  `operate_code` varchar(100) NOT NULL DEFAULT '' COMMENT '操作编号',
  PRIMARY KEY (`operate_id`),
  UNIQUE KEY `operate_url` (`operate_url`),
  KEY `FK_Reference_99` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作管理';

#
# Dumping data for table t_operate
#

INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e88639a020009','菜单-保存','/user/save1LevelMenu.htm','菜单管理-主菜单信息-保存','2013-05-09 16:23:48','2013-05-10 10:14:34','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',5,0,'297e5e0d3c533e24013c537c2073000a','CDGL-BC');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e886621b6000a','新增-页面','/user/showAddMenu.htm','菜单管理-主菜单信息-\n进入主菜单信息新增页面','2013-05-09 16:26:34','2013-05-10 10:19:56','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',5,0,'297e5e0d3c533e24013c537c2073000a','CDGL-XZ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e88885097000b','新增子菜单-页面','/user/toAddSubMenu.htm','菜单管理-进入新增子菜单页面','2013-05-09 17:03:55','2013-05-10 10:20:10','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',4,0,'297e5e0d3c533e24013c537c2073000a','CDGL-XZZCD');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e888981d9000c','编辑子菜单-页面','/user/toEditSubMenu.htm','菜单管理-进入编辑子菜单页面','2013-05-09 17:05:13','2013-05-10 10:20:18','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',4,0,'297e5e0d3c533e24013c537c2073000a','CDGL-BJZCD');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e888be8d9000d','子菜单-保存','/user/saveSubMenu.htm','菜单管理-保存子菜单','2013-05-09 17:07:50','2013-05-10 10:14:45','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,0,'297e5e0d3c533e24013c537c2073000a','CDGL-BCZCD');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e888d6614000e','新增部门-页面','/rela/toAddDept.htm','部门管理-部门信息管理-进入新增部门页面','2013-05-09 17:09:28','2013-05-10 10:20:32','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',3,0,'297e5e0d3c55c1f7013c55d1243b0004','BMXXGL-XZ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e888eb0c5000f','新增子部门-页面','/rela/toAddSubDept.htm','部门管理-部门信息管理-进入新增子部门页面','2013-05-09 17:10:52','2013-05-10 10:20:42','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',3,0,'297e5e0d3c55c1f7013c55d1243b0004','BMXXGL-XZZBM');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e888f64700010','编辑-页面','/rela/toEditDept.htm','部门管理-部门信息管理-进入编辑页面','2013-05-09 17:11:38','2013-05-10 10:20:50','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,0,'297e5e0d3c55c1f7013c55d1243b0004','BMXXGL-BJ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e8890798e0011','部门-保存','/rela/saveDept.htm','部门管理-部门信息管理-保存','2013-05-09 17:12:49','2013-05-10 10:14:21','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,0,'297e5e0d3c55c1f7013c55d1243b0004','BMXXGL-BC');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e8896309a0013','新增角色-页面','/rela/toShowAddRole.htm','部门管理-部门角色管理-进入新增角色页面','2013-05-09 17:19:04','2013-05-10 10:21:08','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',3,0,'297e5e0d3c55c1f7013c55d1dfea0005','BMJSGL-XZJS');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e88976f480014','编辑-页面','/rela/toShowEditRole.htm','部门管理-部门角色管理-进入编辑页面','2013-05-09 17:20:25','2013-05-10 10:21:17','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',3,0,'297e5e0d3c55c1f7013c55d1dfea0005','BMJSGL-BJ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e8898c1050015','访问菜单配置-页面','/rela/toShowMenus.htm','部门管理-部门角色管理-进入访问菜单配置页面','2013-05-09 17:21:52','2013-05-10 10:21:26','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,0,'297e5e0d3c55c1f7013c55d1dfea0005','BGJSGL-FWCDPZ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e889a57890016','新增子角色-页面','/rela/toShowAddSubRole.htm','部门管理-部门角色管理-进入新增子角色页面','2013-05-09 17:23:36','2013-05-10 10:21:34','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,0,'297e5e0d3c55c1f7013c55d1dfea0005','BMJSGL-XZZJS');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e889c65010017','操作权限配置-页面','/rela/toShowOperates.html','部门管理-部门角色管理-进入操作权限配置页面','2013-05-09 17:25:50','2013-05-10 10:21:47','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,0,'297e5e0d3c55c1f7013c55d1dfea0005','BMJSGL-CZQXPZ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e889f44370018','角色-保存','/rela/saveRole.htm','部门管理-部门角色管-保存角色','2013-05-09 17:28:59','2013-05-10 10:13:57','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,0,'297e5e0d3c55c1f7013c55d1dfea0005','BMJSGL-BC');
INSERT INTO `t_operate` VALUES ('297e5e0d3e87f5b5013e88a1864d0019','角色菜单-保存','/rela/saveRoleMenu.htm','部门管理-部门角色管理-保存角色菜单','2013-05-09 17:31:27','2013-05-10 10:14:06','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,0,'297e5e0d3c55c1f7013c55d1dfea0005','BMJSGL-BCJSCD');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c3a753d0006','新增用户-页面','/user/toShowAddUser.htm','用户管理-进入新增用户页面','2013-05-10 10:17:21','2013-05-10 10:19:22','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,0,'297e5e0d3c7203f2013c7241a5a40002','YHGL-XZYH');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c3b2fc60007','编辑用户-页面','/user/toShowEditUser.htm','用户管理-进入编辑用户页面','2013-05-10 10:18:09','2013-05-10 10:19:36','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',2,0,'297e5e0d3c7203f2013c7241a5a40002','YHGL-BJYH');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c3c1c0f0008','用户-保存','/user/saveUser.htm','用户管理-保存用户','2013-05-10 10:19:09',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3c7203f2013c7241a5a40002','YHGL-BCYH');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c3fafe40009','角色配置-页面','/user/toShowConfigUser.htm','用户管理-进入用户角色配置页面','2013-05-10 10:23:04',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3c7203f2013c7241a5a40002','YHGL-JSPZ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c404b3a000a','重置密码','/user/resetPassword.htm','用户管理-重置密码','2013-05-10 10:23:43',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3c7203f2013c7241a5a40002','YHGL-CZMM');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c412f3f000b','角色配置-保存','/user/addUserRole.htm','用户管理-保存用户角色配置','2013-05-10 10:24:42',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3c7203f2013c7241a5a40002','YHGL-BCJSPZ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c431409000c','角色配置-删除','/user/delUserRole.htm','用户管理-已关联角色-删除','2013-05-10 10:26:46',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3c7203f2013c7241a5a40002','YHGL-SCJSPZ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c441a25000d','新增-页面','/system/toAddSystemConfig.htm','系统配置-进入新增页面','2013-05-10 10:27:53','2013-05-10 10:28:40','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',3,0,'297e5e0d3c6b097b013c6b28755b0001','XTPZ-XZ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c458ab2000e','编辑-页面','/system/toEditSystemConfig.htm','系统配置-进入编辑页面','2013-05-10 10:29:27',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3c6b097b013c6b28755b0001','XTPZ-BJ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c4602db000f','系统配置-保存','/system/saveMySystem.htm','系统配置-保存','2013-05-10 10:29:58',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3c6b097b013c6b28755b0001','XTPZ-BC');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c50e2c50010','新增-页面','/operate/toAddOperate.htm','操作管理-进入新增页面','2013-05-10 10:41:51',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3e7e3df4013e7e4408430005','CZGL-XZ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c5169870011','编辑-页面','/operate/toEditOperate.htm','操作管理-进入编辑页面','2013-05-10 10:42:25',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3e7e3df4013e7e4408430005','CZGL-BJ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c520aa70012','操作-保存','/operate/saveOperate.htm','操作管理-保存','2013-05-10 10:43:07',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3e7e3df4013e7e4408430005','CZGL-BC');
INSERT INTO `t_operate` VALUES ('297e5e0d3e8c2aa2013e8c5345340013','设置系统日志','/system/setSystemLogsPath.htm','系统日志-设置','2013-05-10 10:44:27',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3c754242013c75435e1a0003','XTRZ-SZXTRZ');
INSERT INTO `t_operate` VALUES ('297e5e0d3e9d1099013e9d21a5710007','删除菜单','/system/deleteMenus.htm','菜单清理-删除菜单','2013-05-13 17:03:48',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3c55c1f7013c55cf29ed0002','CDQL-SCCD');
INSERT INTO `t_operate` VALUES ('297e5e0d3e9d1099013e9d227cea0008','删除部门','/system/deleteDepts.htm','部门清理-删除部门','2013-05-13 17:04:43',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3c55c1f7013c55cf64540003','BMQL-SCBM');
INSERT INTO `t_operate` VALUES ('297e5e0d3e9d1099013e9d22ed960009','删除角色','/system/deleteRoles.htm','角色清理-删除角色','2013-05-13 17:05:12',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3d61d4c6013d61efa9620003','JSQL-SCJS');
INSERT INTO `t_operate` VALUES ('297e5e0d3e9d1099013e9d23bfb5000a','删除用户','/system/deleteUsers.htm','用户清理-删除用户','2013-05-13 17:06:05',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'297e5e0d3d61d4c6013d61f036250004','YHQL-SCYH');

#
# Source for table t_role
#

CREATE TABLE `t_role` (
  `role_id` varchar(32) NOT NULL COMMENT '流水号',
  `role_code` varchar(32) NOT NULL COMMENT '角色编号',
  `role_name` varchar(32) NOT NULL COMMENT '角色名称',
  `role_level` int(11) NOT NULL COMMENT '角色等级',
  `parent_role` varchar(32) DEFAULT NULL COMMENT '父角色',
  `enable` int(1) DEFAULT '0' COMMENT '0:可用 -1不可用',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色管理';

#
# Dumping data for table t_role
#

INSERT INTO `t_role` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','admin','系统管理员',1,NULL,0,'2013-01-10 10:19:49','2013-03-02 16:56:24',NULL,'21232f297a57a5a743894a0e4a801fc1',5);
INSERT INTO `t_role` VALUES ('297e5e0d3c90bdeb013c90c0ffd80002','CSYH','测试用户',1,NULL,0,'2013-01-31 21:17:13','2013-03-12 10:38:51','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',4);

#
# Source for table t_role_menu
#

CREATE TABLE `t_role_menu` (
  `role_id` varchar(32) NOT NULL,
  `menu_id` varchar(32) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `FK_Reference_6` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单中间表';

#
# Dumping data for table t_role_menu
#

INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c533e24013c537c20730005','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c533e24013c537c2073000a','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c533e24013c537c2073000l','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c55c1f7013c55ced6a40001','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c55c1f7013c55cf29ed0002','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c55c1f7013c55cf64540003','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c55c1f7013c55d1243b0004','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c55c1f7013c55d1dfea0005','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c6b097b013c6b28755b0001','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c7203f2013c7241a5a40002','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c754242013c75432de70002','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c754242013c75435e1a0003','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3c754242013c7543a4e20004','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3d61d4c6013d61efa9620003','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3d61d4c6013d61f036250004','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3daad48d013daad558c60002','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3daad48d013daad5bf930003','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3dafec6d013dafed3ef40001','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3dc4d999013dc4dd3bcc0002','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3dc4d999013dc4de13ab0003','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e7e3df4013e7e4408430005','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','402845813dfd4a9f013dfd4ee62d0005','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','402845813dfd4a9f013dfd4f990e0006','2013-05-07 17:14:11',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c90bdeb013c90c0ffd80002','297e5e0d3d8b8168013d8b8448bb0003','2013-05-13 17:08:01',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_menu` VALUES ('297e5e0d3c90bdeb013c90c0ffd80002','297e5e0d3d8bc744013d8bef64900004','2013-05-13 17:08:01',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);

#
# Source for table t_role_operate
#

CREATE TABLE `t_role_operate` (
  `role_id` varchar(32) NOT NULL,
  `operate_id` varchar(32) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`role_id`,`operate_id`),
  KEY `FK_Reference_16` (`operate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和操作权限中间表';

#
# Dumping data for table t_role_operate
#

INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e88639a020009','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e886621b6000a','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e88885097000b','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e888981d9000c','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e888be8d9000d','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e888d6614000e','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e888eb0c5000f','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e888f64700010','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e8890798e0011','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e8896309a0013','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e88976f480014','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e8898c1050015','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e889a57890016','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e889c65010017','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e889f44370018','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e87f5b5013e88a1864d0019','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c3a753d0006','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c3b2fc60007','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c3c1c0f0008','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c3fafe40009','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c404b3a000a','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c412f3f000b','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c431409000c','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c441a25000d','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c458ab2000e','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c4602db000f','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c50e2c50010','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c5169870011','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c520aa70012','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e8c2aa2013e8c5345340013','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e9d1099013e9d21a5710007','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e9d1099013e9d227cea0008','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e9d1099013e9d22ed960009','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_role_operate` VALUES ('297e5e0d3c6f60cd013c6f83ab1f0002','297e5e0d3e9d1099013e9d23bfb5000a','2013-05-13 17:30:41',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);

#
# Source for table t_system
#

CREATE TABLE `t_system` (
  `system_id` varchar(32) NOT NULL COMMENT '流水号',
  `system_name` varchar(60) DEFAULT NULL COMMENT '名称',
  `system_code` varchar(32) DEFAULT NULL COMMENT '编号',
  `value` varchar(200) DEFAULT NULL COMMENT '值',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  `enable` int(11) DEFAULT '0' COMMENT '0:可用 -1不可用',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置';

#
# Dumping data for table t_system
#

INSERT INTO `t_system` VALUES ('297e5e0d3d85f169013d867b54f10004','是否支持子部门','SUB_DEPT_SUPPORT','是','2013-03-20 14:27:42',NULL,'21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',9,0,'部门是否支持新增子部门');
INSERT INTO `t_system` VALUES ('297e5e0d3d85f169013d867bc0510005','是否支持子角色','SUB_ROLE_SUPPORT','是','2013-03-20 14:28:09',NULL,'21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',10,-1,'角色是否可以新增子角色');
INSERT INTO `t_system` VALUES ('297e5e0d3d85f169013d867d36230006','多角色支持','MORE_DEPT_SUPPORT','是','2013-03-20 14:29:45',NULL,'21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',7,0,'单一用户是否可以配置多个角色');
INSERT INTO `t_system` VALUES ('297e5e0d3e7dd01d013e7e270f210006','操作权限支持','OPERATE_ROLE_SUPPORT','是','2013-05-07 16:41:29',NULL,'21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',5,0,'支持按钮级别的权限控制');
INSERT INTO `t_system` VALUES ('402845813df856c9013df867bcc40005','流程语言支持','TASK_LAN_SUPPORT','zh-CN','2013-04-11 17:23:00',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1,0,'英文：en-UK\n中文：zh-CN');

#
# Source for table t_user
#

CREATE TABLE `t_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `username` varchar(100) NOT NULL DEFAULT '' COMMENT '登录名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `enable` int(1) DEFAULT '0' COMMENT '0:可用 -1不可用',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

#
# Dumping data for table t_user
#

INSERT INTO `t_user` VALUES ('21232f297a57a5a743894a0e4a801fc1','admin','c4ca4238a0b923820dcc509a6f75849b',0,'2013-01-09 17:54:56','2013-02-23 11:29:38',NULL,'21232f297a57a5a743894a0e4a801fc1',4);
INSERT INTO `t_user` VALUES ('297e5e0d3d006e26013d0077faa80007','lidaowei','e10adc3949ba59abbe56e057f20f883e',0,'2013-02-22 13:54:55','2013-02-22 22:36:11','21232f297a57a5a743894a0e4a801fc1','21232f297a57a5a743894a0e4a801fc1',4);

#
# Source for table t_user_detail
#

CREATE TABLE `t_user_detail` (
  `user_detail_id` varchar(32) NOT NULL COMMENT '流水号',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `realname` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `nickname` varchar(100) DEFAULT NULL COMMENT '呢称',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ',
  `birthday` varchar(30) DEFAULT NULL COMMENT '生日',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`user_detail_id`),
  KEY `FK_Reference_1` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详细信息';

#
# Dumping data for table t_user_detail
#

INSERT INTO `t_user_detail` VALUES ('21232f297a57a5a743894a0e4a801fc2','21232f297a57a5a743894a0e4a801fc1','superman','小小超人','lidaowei@vip.qq.com','274754617','1986-09-19','2013-01-10 10:39:53','2013-02-23 11:29:38',NULL,'21232f297a57a5a743894a0e4a801fc1',4);
INSERT INTO `t_user_detail` VALUES ('297e5e0d3d006e26013d0077faa90008','297e5e0d3d006e26013d0077faa80007','LJ','小SB','','274754617','1986-09-19','2013-02-22 13:54:55','2013-02-22 22:36:11',NULL,'21232f297a57a5a743894a0e4a801fc1',4);

#
# Source for table t_user_log
#

CREATE TABLE `t_user_log` (
  `user_log_id` varchar(32) NOT NULL COMMENT '流水号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `login_ip` varchar(32) DEFAULT NULL COMMENT '登录ip',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`user_log_id`),
  KEY `FK_Reference_2` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录记录';

#
# Source for table t_user_role
#

CREATE TABLE `t_user_role` (
  `user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_Reference_4` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色中间表';

#
# Dumping data for table t_user_role
#

INSERT INTO `t_user_role` VALUES ('21232f297a57a5a743894a0e4a801fc1','297e5e0d3c6f60cd013c6f83ab1f0002','2013-05-10 11:56:03',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);
INSERT INTO `t_user_role` VALUES ('297e5e0d3d006e26013d0077faa80007','297e5e0d3c90bdeb013c90c0ffd80002','2013-03-20 16:47:22',NULL,'21232f297a57a5a743894a0e4a801fc1',NULL,1);

#
# Source for table task
#

CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `archived` smallint(6) DEFAULT NULL,
  `allowedToDelegate` varchar(255) DEFAULT NULL,
  `priority` int(11) NOT NULL,
  `activationTime` datetime DEFAULT NULL,
  `completedOn` datetime DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `documentAccessType` int(11) DEFAULT NULL,
  `documentContentId` bigint(20) NOT NULL,
  `documentType` varchar(255) DEFAULT NULL,
  `expirationTime` datetime DEFAULT NULL,
  `faultAccessType` int(11) DEFAULT NULL,
  `faultContentId` bigint(20) NOT NULL,
  `faultName` varchar(255) DEFAULT NULL,
  `faultType` varchar(255) DEFAULT NULL,
  `outputAccessType` int(11) DEFAULT NULL,
  `outputContentId` bigint(20) NOT NULL,
  `outputType` varchar(255) DEFAULT NULL,
  `parentId` bigint(20) NOT NULL,
  `previousStatus` int(11) DEFAULT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `processSessionId` int(11) NOT NULL,
  `skipable` bit(1) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `workItemId` bigint(20) NOT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  `taskInitiator_id` varchar(255) DEFAULT NULL,
  `actualOwner_id` varchar(255) DEFAULT NULL,
  `createdBy_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK27A9A59E619A0` (`createdBy_id`),
  KEY `FK27A9A56CE1EF3A` (`actualOwner_id`),
  KEY `FK27A9A5F213F8B5` (`taskInitiator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table task
#


#
# Source for table task_comment
#

CREATE TABLE `task_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addedAt` datetime DEFAULT NULL,
  `text` longtext,
  `addedBy_id` varchar(255) DEFAULT NULL,
  `TaskData_Comments_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK61F475A5B35E68F5` (`TaskData_Comments_Id`),
  KEY `FK61F475A52FF04688` (`addedBy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table task_comment
#


#
# Source for table variableinstancelog
#

CREATE TABLE `variableinstancelog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `log_date` datetime DEFAULT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `variableId` varchar(255) DEFAULT NULL,
  `variableInstanceId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table variableinstancelog
#


#
# Source for table workiteminfo
#

CREATE TABLE `workiteminfo` (
  `workItemId` bigint(20) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `state` bigint(20) NOT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  `workItemByteArray` longblob,
  PRIMARY KEY (`workItemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table workiteminfo
#


#
#  Foreign keys for table attachment
#

ALTER TABLE `attachment`
ADD CONSTRAINT `FK1C935438EF5F064` FOREIGN KEY (`attachedBy_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FK1C93543F21826D9` FOREIGN KEY (`TaskData_Attachments_Id`) REFERENCES `task` (`id`);

#
#  Foreign keys for table booleanexpression
#

ALTER TABLE `booleanexpression`
ADD CONSTRAINT `FKE3D208C0AFB75F7D` FOREIGN KEY (`Escalation_Constraints_Id`) REFERENCES `escalation` (`id`);

#
#  Foreign keys for table deadline
#

ALTER TABLE `deadline`
ADD CONSTRAINT `FK21DF3E7827ABEB8A` FOREIGN KEY (`Deadlines_EndDeadLine_Id`) REFERENCES `task` (`id`),
ADD CONSTRAINT `FK21DF3E78684BACA3` FOREIGN KEY (`Deadlines_StartDeadLine_Id`) REFERENCES `task` (`id`);

#
#  Foreign keys for table delegation_delegates
#

ALTER TABLE `delegation_delegates`
ADD CONSTRAINT `FK47485D572C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FK47485D5736B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`);

#
#  Foreign keys for table escalation
#

ALTER TABLE `escalation`
ADD CONSTRAINT `FK67B2C6B5C7A04C70` FOREIGN KEY (`Deadline_Escalation_Id`) REFERENCES `deadline` (`id`);

#
#  Foreign keys for table eventtypes
#

ALTER TABLE `eventtypes`
ADD CONSTRAINT `FKB0E5621F7665489A` FOREIGN KEY (`InstanceId`) REFERENCES `processinstanceinfo` (`InstanceId`);

#
#  Foreign keys for table i18ntext
#

ALTER TABLE `i18ntext`
ADD CONSTRAINT `FK2349686B2162DFB4` FOREIGN KEY (`Notification_Descriptions_Id`) REFERENCES `notification` (`id`),
ADD CONSTRAINT `FK2349686B3330F6D9` FOREIGN KEY (`Deadline_Documentation_Id`) REFERENCES `deadline` (`id`),
ADD CONSTRAINT `FK2349686B5EEBB6D9` FOREIGN KEY (`Reassignment_Documentation_Id`) REFERENCES `reassignment` (`id`),
ADD CONSTRAINT `FK2349686B69B21EE8` FOREIGN KEY (`Task_Descriptions_Id`) REFERENCES `task` (`id`),
ADD CONSTRAINT `FK2349686B8046A239` FOREIGN KEY (`Notification_Documentation_Id`) REFERENCES `notification` (`id`),
ADD CONSTRAINT `FK2349686B98B62B` FOREIGN KEY (`Task_Names_Id`) REFERENCES `task` (`id`),
ADD CONSTRAINT `FK2349686BB2FA6B18` FOREIGN KEY (`Task_Subjects_Id`) REFERENCES `task` (`id`),
ADD CONSTRAINT `FK2349686BD488CEEB` FOREIGN KEY (`Notification_Names_Id`) REFERENCES `notification` (`id`),
ADD CONSTRAINT `FK2349686BF952CEE4` FOREIGN KEY (`Notification_Subjects_Id`) REFERENCES `notification` (`id`);

#
#  Foreign keys for table m_bbs_reply
#

ALTER TABLE `m_bbs_reply`
ADD CONSTRAINT `FK_Reference_12` FOREIGN KEY (`bbs_id`) REFERENCES `m_bbs` (`bbs_id`);

#
#  Foreign keys for table m_menu_information
#

ALTER TABLE `m_menu_information`
ADD CONSTRAINT `FK_Reference_10` FOREIGN KEY (`menu_id`) REFERENCES `m_menu` (`menu_id`),
ADD CONSTRAINT `FK_Reference_9` FOREIGN KEY (`information_id`) REFERENCES `m_information` (`information_id`);

#
#  Foreign keys for table m_message_reply
#

ALTER TABLE `m_message_reply`
ADD CONSTRAINT `FK_Reference_11` FOREIGN KEY (`message_id`) REFERENCES `m_message` (`message_id`);

#
#  Foreign keys for table m_picture
#

ALTER TABLE `m_picture`
ADD CONSTRAINT `FK_Reference_13` FOREIGN KEY (`picture_type_id`) REFERENCES `m_picture_type` (`picture_type_id`);

#
#  Foreign keys for table notification
#

ALTER TABLE `notification`
ADD CONSTRAINT `FK2D45DD0B3E0890B` FOREIGN KEY (`Escalation_Notifications_Id`) REFERENCES `escalation` (`id`);

#
#  Foreign keys for table notification_bas
#

ALTER TABLE `notification_bas`
ADD CONSTRAINT `FK2DD68EE02C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FK2DD68EE09C76EABA` FOREIGN KEY (`task_id`) REFERENCES `notification` (`id`);

#
#  Foreign keys for table notification_email_header
#

ALTER TABLE `notification_email_header`
ADD CONSTRAINT `FKF30FE3441F7B912A` FOREIGN KEY (`emailHeaders_id`) REFERENCES `email_header` (`id`),
ADD CONSTRAINT `FKF30FE34430BE501C` FOREIGN KEY (`Notification_id`) REFERENCES `notification` (`id`);

#
#  Foreign keys for table notification_recipients
#

ALTER TABLE `notification_recipients`
ADD CONSTRAINT `FK98FD214E2C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FK98FD214E9C76EABA` FOREIGN KEY (`task_id`) REFERENCES `notification` (`id`);

#
#  Foreign keys for table peopleassignments_bas
#

ALTER TABLE `peopleassignments_bas`
ADD CONSTRAINT `FK9D8CF4EC2C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FK9D8CF4EC36B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`);

#
#  Foreign keys for table peopleassignments_exclowners
#

ALTER TABLE `peopleassignments_exclowners`
ADD CONSTRAINT `FKC77B97E42C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FKC77B97E436B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`);

#
#  Foreign keys for table peopleassignments_potowners
#

ALTER TABLE `peopleassignments_potowners`
ADD CONSTRAINT `FK1EE418D2C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FK1EE418D36B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`);

#
#  Foreign keys for table peopleassignments_recipients
#

ALTER TABLE `peopleassignments_recipients`
ADD CONSTRAINT `FKC6F615C22C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FKC6F615C236B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`);

#
#  Foreign keys for table peopleassignments_stakeholders
#

ALTER TABLE `peopleassignments_stakeholders`
ADD CONSTRAINT `FK482F79D52C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FK482F79D536B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`);

#
#  Foreign keys for table reassignment
#

ALTER TABLE `reassignment`
ADD CONSTRAINT `FK724D0560A5C17EE0` FOREIGN KEY (`Escalation_Reassignments_Id`) REFERENCES `escalation` (`id`);

#
#  Foreign keys for table reassignment_potentialowners
#

ALTER TABLE `reassignment_potentialowners`
ADD CONSTRAINT `FK90B59CFF2C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FK90B59CFFE17E130F` FOREIGN KEY (`task_id`) REFERENCES `reassignment` (`id`);

#
#  Foreign keys for table subtasksstrategy
#

ALTER TABLE `subtasksstrategy`
ADD CONSTRAINT `FKDE5DF2E136B2F154` FOREIGN KEY (`Task_Id`) REFERENCES `task` (`id`);

#
#  Foreign keys for table t_dept_role
#

ALTER TABLE `t_dept_role`
ADD CONSTRAINT `FK_Reference_7` FOREIGN KEY (`dept_id`) REFERENCES `t_dept` (`dept_id`),
ADD CONSTRAINT `FK_Reference_8` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`);

#
#  Foreign keys for table t_operate
#

ALTER TABLE `t_operate`
ADD CONSTRAINT `FK_Reference_99` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`menu_id`);

#
#  Foreign keys for table t_role_menu
#

ALTER TABLE `t_role_menu`
ADD CONSTRAINT `FK_Reference_5` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`),
ADD CONSTRAINT `FK_Reference_6` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`menu_id`);

#
#  Foreign keys for table t_role_operate
#

ALTER TABLE `t_role_operate`
ADD CONSTRAINT `FK_Reference_15` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`),
ADD CONSTRAINT `FK_Reference_16` FOREIGN KEY (`operate_id`) REFERENCES `t_operate` (`operate_id`);

#
#  Foreign keys for table t_user_detail
#

ALTER TABLE `t_user_detail`
ADD CONSTRAINT `FK_Reference_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`);

#
#  Foreign keys for table t_user_log
#

ALTER TABLE `t_user_log`
ADD CONSTRAINT `FK_Reference_2` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`);

#
#  Foreign keys for table t_user_role
#

ALTER TABLE `t_user_role`
ADD CONSTRAINT `FK_Reference_3` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`),
ADD CONSTRAINT `FK_Reference_4` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`);

#
#  Foreign keys for table task
#

ALTER TABLE `task`
ADD CONSTRAINT `FK27A9A56CE1EF3A` FOREIGN KEY (`actualOwner_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FK27A9A59E619A0` FOREIGN KEY (`createdBy_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FK27A9A5F213F8B5` FOREIGN KEY (`taskInitiator_id`) REFERENCES `organizationalentity` (`id`);

#
#  Foreign keys for table task_comment
#

ALTER TABLE `task_comment`
ADD CONSTRAINT `FK61F475A52FF04688` FOREIGN KEY (`addedBy_id`) REFERENCES `organizationalentity` (`id`),
ADD CONSTRAINT `FK61F475A5B35E68F5` FOREIGN KEY (`TaskData_Comments_Id`) REFERENCES `task` (`id`);


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
