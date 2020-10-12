/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : admin

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 29/06/2017 23:06:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别名称',
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类别封面',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类别描述',
  `parent_id` int(11) DEFAULT NULL COMMENT '父类别ID',
  `parent_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ancestors` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '别路径',
  `ancestor_names` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父类别名称路径',
  `level` tinyint(1) DEFAULT NULL COMMENT '层级',
  `site_id` int(11) DEFAULT NULL COMMENT '站点Id',
  `priority` tinyint(5) DEFAULT 0,
  `deleted` tinyint(1) DEFAULT 0 COMMENT '删除标识',
  `created` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文章类别' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_category
-- ----------------------------
INSERT INTO `t_category` VALUES (1, '开源项目', '', '', NULL, '', '1', '开源项目', 0, NULL, NULL, 0, '2016-01-06 21:24:59', '2016-03-07 15:54:18');
INSERT INTO `t_category` VALUES (2, '技术问答', '', '', 3, '问答', 'null,2', 'null,技术问答', 1, NULL, NULL, 1, '2016-01-08 00:15:14', '2016-02-28 01:12:14');
INSERT INTO `t_category` VALUES (3, '问答', '', '向广大热心程序员寻求答案', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2016-01-08 00:20:19', '2016-01-14 00:42:08');
INSERT INTO `t_category` VALUES (4, '技术分享', '', '', 3, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2016-01-08 00:25:30', '2016-01-14 00:42:08');
INSERT INTO `t_category` VALUES (5, 'Java', '', '', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2016-01-08 00:26:18', '2016-01-14 00:42:08');
INSERT INTO `t_category` VALUES (6, 'C#', '', '', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2016-01-08 00:26:34', '2016-01-14 00:42:08');
INSERT INTO `t_category` VALUES (7, 'PHP', '', '', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2016-01-08 00:26:41', '2016-01-14 00:42:08');
INSERT INTO `t_category` VALUES (8, 'C/C++', '', '', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2016-01-08 00:26:50', '2016-01-14 00:42:08');
INSERT INTO `t_category` VALUES (9, 'Ruby', '', '', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2016-01-08 00:26:59', '2016-01-14 00:42:08');
INSERT INTO `t_category` VALUES (10, 'Python', '', '', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2016-01-08 00:27:10', '2016-01-14 00:42:08');
INSERT INTO `t_category` VALUES (11, 'Go', '', '', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2016-01-08 00:27:15', '2016-01-14 00:42:08');
INSERT INTO `t_category` VALUES (12, 'Java Script', '', '', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2016-01-08 00:27:30', '2016-01-14 00:42:08');
INSERT INTO `t_category` VALUES (13, '职业生涯', '', '', 3, '问答', 'null,13', 'null,职业生涯', 1, NULL, NULL, 0, '2016-01-08 00:28:57', '2016-01-14 00:42:08');
INSERT INTO `t_category` VALUES (14, '你真逗', '', '', 2, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2016-01-09 22:58:23', '2016-01-14 00:42:08');

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门名称',
  `leader` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '负责人',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系电话',
  `parent` int(11) DEFAULT 0 COMMENT '上级部门ID',
  `ancestors` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '祖先ID路径',
  `level` tinyint(1) DEFAULT 1 COMMENT '层级',
  `priority` tinyint(5) DEFAULT 0 COMMENT '优先级',
  `created` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `updated` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES (1, '益策（中国）', '1', '', NULL, 0, '1', 1, 0, '2016-06-27 17:29:34', '2017-06-15 00:38:32');
INSERT INTO `t_department` VALUES (10, '广州', '32', '益策总部', '020-84120887', 1, '1,10', 2, 0, '2016-06-27 17:29:34', '2017-06-15 00:38:27');
INSERT INTO `t_department` VALUES (11, '深圳', '', '', '0755-82077814', 1, '1,11', 2, 3, '2016-09-19 13:47:15', '2017-06-15 00:38:27');
INSERT INTO `t_department` VALUES (14, '成都', '', '', '028-61114433', 1, '1,14', 2, 1, '2016-09-19 13:47:39', '2017-06-15 00:38:27');
INSERT INTO `t_department` VALUES (17, '武汉', '', '', '027-87110487', 1, '1,17', 2, 2, '2016-09-19 13:48:00', '2017-06-15 00:38:27');
INSERT INTO `t_department` VALUES (18, '重庆', '', '', '023-68077434', 1, '1,18', 2, 4, '2016-09-19 13:48:07', '2017-06-15 00:38:27');
INSERT INTO `t_department` VALUES (19, '嘿嘿嘿', '21', NULL, NULL, 0, '19', 1, 1, '2017-05-19 14:55:56', '2017-06-15 00:38:32');

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '键',
  `value` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '值',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `priority` tinyint(5) DEFAULT NULL COMMENT '优先级',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态: 1-正常 0-禁用',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_dict_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 871 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_dict
-- ----------------------------
INSERT INTO `t_dict` VALUES (19, 'user_type', '用户类型', '1', '用户', NULL, 1, 0, '2016-01-15 20:42:24');
INSERT INTO `t_dict` VALUES (20, 'user_type', '用户类型', '-1', '管理员', NULL, 2, 0, '2016-01-15 20:42:24');
INSERT INTO `t_dict` VALUES (179, 'app_course_tag', '课程标签', 'recommend', '推荐', NULL, 2, 1, '2016-05-23 18:38:30');
INSERT INTO `t_dict` VALUES (180, 'app_course_tag', '课程标签', 'hot', '热卖', NULL, 1, 1, '2016-05-23 18:38:30');
INSERT INTO `t_dict` VALUES (181, 'app_course_tag', '课程标签', 'free', '免费', NULL, 4, 1, '2016-05-23 18:38:30');
INSERT INTO `t_dict` VALUES (182, 'app_course_tag', '课程标签', 'newest', '最新', NULL, 3, 1, '2016-05-23 18:38:30');
INSERT INTO `t_dict` VALUES (183, 'app_course_tag', '课程标签', 'trinity', '三位一体', NULL, 5, 1, '2016-05-23 18:38:30');
INSERT INTO `t_dict` VALUES (204, 'status', '禁用状态', '0', '禁用', NULL, 1, 1, '2016-05-27 10:59:19');
INSERT INTO `t_dict` VALUES (205, 'status', '正常状态', '1', '正常', NULL, 2, 1, '2016-05-27 10:59:19');
INSERT INTO `t_dict` VALUES (228, 'room_topic_type', '直播主题类别', '3', '跨界观', '', 1, 1, '2016-07-22 11:29:47');
INSERT INTO `t_dict` VALUES (229, 'room_topic_type', '直播主题类别', '2', '讲书院', '', 2, 1, '2016-07-22 11:29:47');
INSERT INTO `t_dict` VALUES (230, 'room_topic_type', '直播主题类别', '1', '校长谈', '', 3, 1, '2016-07-22 11:29:47');
INSERT INTO `t_dict` VALUES (235, 'app_msg', '错误码定义', 'invalid.encode', '-6', '', 6, 1, '2016-07-22 11:30:44');
INSERT INTO `t_dict` VALUES (236, 'app_msg', '错误码定义', 'invalid.version', '-4', '', 4, 1, '2016-07-22 11:30:44');
INSERT INTO `t_dict` VALUES (237, 'app_msg', '错误码定义', 'network.error', '-1', '', 2, 1, '2016-07-22 11:30:44');
INSERT INTO `t_dict` VALUES (238, 'app_msg', '错误码定义', 'invalid.sign', '-5', '', 5, 1, '2016-07-22 11:30:44');
INSERT INTO `t_dict` VALUES (239, 'app_msg', '错误码定义', 'params.required', '-3', '', 3, 1, '2016-07-22 11:30:44');
INSERT INTO `t_dict` VALUES (240, 'app_msg', '错误码定义', 'phone.validateCode.sent', '-8', '', 8, 1, '2016-07-22 11:30:44');
INSERT INTO `t_dict` VALUES (241, 'app_msg', '错误码定义', 'invalid.appid', '-2', '', 0, 1, '2016-07-22 11:30:44');
INSERT INTO `t_dict` VALUES (242, 'app_msg', '错误码定义', 'login.expired', '-7', '', 7, 1, '2016-07-22 11:30:44');
INSERT INTO `t_dict` VALUES (386, 'reward_mark', '打赏的金额对应的描述', '20', '润喉片', '<#FFFF00> {receiver} <#FF0000>讲了这么久，嗓子都哑了，<#FFFF00> {payer} <#FF0000>递给一盒润喉片', 40, 1, '2016-10-18 14:53:20');
INSERT INTO `t_dict` VALUES (387, 'reward_mark', '打赏的金额对应的描述', '10', '敬茶', '<#FFFF00> {payer} <#FF0000>请<#FFFF00> {receiver} <#FF0000>喝杯茶解解渴', 50, 1, '2016-10-18 14:53:25');
INSERT INTO `t_dict` VALUES (388, 'reward_mark', '打赏的金额对应的描述', '0', '棒棒糖', '<#FFFF00> {payer} <#FF0000>听得很开心，送给<#FFFF00> {receiver} <#FF0000>一根棒棒糖', 2, 1, '2016-10-18 14:53:29');
INSERT INTO `t_dict` VALUES (389, 'reward_mark', '打赏的金额对应的描述', '100', '金话筒', '<#FFFF00> {payer} <#FF0000>送出一个金话筒，感谢<#FFFF00> {receiver} <#FF0000>精心的付出', 20, 1, '2016-10-18 14:53:33');
INSERT INTO `t_dict` VALUES (390, 'reward_mark', '打赏的金额对应的描述', '-1', '其他', '7', 3, 1, '2016-09-10 12:01:23');
INSERT INTO `t_dict` VALUES (391, 'reward_mark', '打赏的金额对应的描述', '5', '干货', '<#FFFF00> {receiver} <#FF0000>的内容满满都是干货，<#FFFF00> {payer} <#FF0000>听得津津有味', 60, 1, '2016-10-18 14:53:37');
INSERT INTO `t_dict` VALUES (392, 'reward_mark', '打赏的金额对应的描述', '50', '32个赞', '<#FFFF00> {payer} <#FF0000>听得眉飞色舞，给<#FFFF00> {receiver} <#FF0000>32个赞', 30, 1, '2016-10-18 14:53:38');
INSERT INTO `t_dict` VALUES (393, 'room_guest_type', '直播嘉宾类别', '0', '学员', '学员', 1, 1, '2016-10-25 14:30:02');
INSERT INTO `t_dict` VALUES (394, 'room_guest_type', '直播嘉宾类别', '139', '分享嘉宾', '', 5, 1, '2016-10-25 14:30:02');
INSERT INTO `t_dict` VALUES (395, 'room_guest_type', '直播嘉宾类别', '140', '对话嘉宾', '', 4, 1, '2016-10-25 14:30:02');
INSERT INTO `t_dict` VALUES (396, 'room_guest_type', '直播嘉宾类别', '142', '助教', '', 2, 1, '2016-10-25 14:30:02');
INSERT INTO `t_dict` VALUES (397, 'room_guest_type', '直播嘉宾类别', '141', '主持人', '', 3, 1, '2016-10-25 14:30:02');
INSERT INTO `t_dict` VALUES (435, 'app_index_guest_tag', '嘉宾标签', '3', '人力资源', '', 3, 0, '2016-12-15 18:10:30');
INSERT INTO `t_dict` VALUES (436, 'app_index_guest_tag', '嘉宾标签', '2', '战略布局', '', 2, 0, '2016-12-15 18:10:30');
INSERT INTO `t_dict` VALUES (437, 'app_index_guest_tag', '嘉宾标签', '1', '互联网思维', '', 0, 0, '2016-12-15 18:10:30');
INSERT INTO `t_dict` VALUES (438, 'app_index_guest_tag', '嘉宾标签', '4', '财税领域', '', 4, 0, '2016-12-15 18:10:30');
INSERT INTO `t_dict` VALUES (673, 'app_index_hot_keyword', '首页热门关键字', '3', '张学友', '', 3, 1, '2016-12-19 11:42:58');
INSERT INTO `t_dict` VALUES (674, 'app_index_hot_keyword', '首页热门关键字', '2', '学道', '', 2, 1, '2016-12-19 11:42:58');
INSERT INTO `t_dict` VALUES (675, 'app_index_hot_keyword', '首页热门关键字', '1', '财税', '', 1, 1, '2016-12-19 11:42:58');
INSERT INTO `t_dict` VALUES (676, 'app_index_hot_keyword', '首页热门关键字', '10', '周二', '', 10, 1, '2016-12-19 11:42:58');
INSERT INTO `t_dict` VALUES (677, 'app_index_hot_keyword', '首页热门关键字', '7', '周五', '', 7, 1, '2016-12-19 11:42:58');
INSERT INTO `t_dict` VALUES (678, 'app_index_hot_keyword', '首页热门关键字', '6', '周六', '', 6, 1, '2016-12-19 11:42:58');
INSERT INTO `t_dict` VALUES (679, 'app_index_hot_keyword', '首页热门关键字', '5', '周日', '', 5, 1, '2016-12-19 11:42:58');
INSERT INTO `t_dict` VALUES (680, 'app_index_hot_keyword', '首页热门关键字', '4', '测试新加字段的--文章链接', '', 4, 1, '2016-12-19 11:42:58');
INSERT INTO `t_dict` VALUES (681, 'app_index_hot_keyword', '首页热门关键字', '9', '周三', '', 9, 1, '2016-12-19 11:42:58');
INSERT INTO `t_dict` VALUES (682, 'app_index_hot_keyword', '首页热门关键字', '8', '周四', '', 8, 1, '2016-12-19 11:42:58');
INSERT INTO `t_dict` VALUES (683, 'app_index_hot_keyword', '首页热门关键字', '11', '周一', '', 11, 1, '2016-12-19 11:42:58');
INSERT INTO `t_dict` VALUES (693, 'app_index_focus_lable', '标签', '15', '测试三', '', 15, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (694, 'app_index_focus_lable', '标签', '16', '测试四', '', 16, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (695, 'app_index_focus_lable', '标签', '13', '测试一', '', 13, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (696, 'app_index_focus_lable', '标签', '14', '测试二', '', 14, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (697, 'app_index_focus_lable', '标签', '11', '这个标签无解', 'none', 11, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (698, 'app_index_focus_lable', '标签', '12', '这有五个字', '', 12, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (699, 'app_index_focus_lable', '标签', '3', '通用管理大神', 'new', 3, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (700, 'app_index_focus_lable', '标签', '2', '职场提升大神', 'new', 2, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (701, 'app_index_focus_lable', '标签', '1', '财税金融大神', 'hot', 1, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (702, 'app_index_focus_lable', '标签', '10', '大厨食神', 'meals', 10, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (703, 'app_index_focus_lable', '标签', '7', '测试标签', 'hot', 7, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (704, 'app_index_focus_lable', '标签', '6', '情商管理', 'new', 6, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (705, 'app_index_focus_lable', '标签', '5', '办公技能', 'new', 5, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (706, 'app_index_focus_lable', '标签', '4', '职业素养', 'new', 4, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (707, 'app_index_focus_lable', '标签', '9', '宠物家园', 'animals', 9, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (708, 'app_index_focus_lable', '标签', '8', '益策王牌', '', 8, 1, '2016-12-22 17:10:10');
INSERT INTO `t_dict` VALUES (836, 'app_index_module', 'APP首页课程分类', '3', '直播预告', '', 3, 1, '2017-05-15 15:33:13');
INSERT INTO `t_dict` VALUES (837, 'app_index_module', 'APP首页课程分类', '2', '推荐分类', '', 2, 1, '2017-05-15 15:33:13');
INSERT INTO `t_dict` VALUES (838, 'app_index_module', 'APP首页课程分类', '1', '轮播图', '', 1, 1, '2017-05-15 15:33:13');
INSERT INTO `t_dict` VALUES (839, 'app_index_module', 'APP首页课程分类', '10', '首页开关', '1 ', 10, 1, '2017-05-15 15:33:13');
INSERT INTO `t_dict` VALUES (840, 'app_index_module', 'APP首页课程分类', '7', '首页嘉宾', '', 7, 1, '2017-05-15 15:33:13');
INSERT INTO `t_dict` VALUES (841, 'app_index_module', 'APP首页课程分类', '6', '课程分类', '', 6, 1, '2017-05-15 15:33:13');
INSERT INTO `t_dict` VALUES (842, 'app_index_module', 'APP首页课程分类', '5', '财道家塾', '', 5, 1, '2017-05-15 15:33:13');
INSERT INTO `t_dict` VALUES (843, 'app_index_module', 'APP首页课程分类', '4', '学道家塾', '', 4, 1, '2017-05-15 15:33:13');
INSERT INTO `t_dict` VALUES (844, 'app_index_module', 'APP首页课程分类', '9', '课程优惠分类', '', 9, 1, '2017-05-15 15:33:13');
INSERT INTO `t_dict` VALUES (845, 'app_index_module', 'APP首页课程分类', '8', '广告页', '', 8, 1, '2017-05-15 15:33:13');
INSERT INTO `t_dict` VALUES (850, 'email_login_url', '邮箱域名地址映射', 'qq.com', 'https://mail.qq.com', '', 1, 1, '2017-06-08 23:47:37');
INSERT INTO `t_dict` VALUES (851, 'email_login_url', '邮箱域名地址映射', 'hotmail.com', 'https://login.live.com/login.srf?cbcxt=mai', '', 2, 1, '2017-06-08 23:47:37');
INSERT INTO `t_dict` VALUES (852, 'email_login_url', '邮箱域名地址映射', 'sina.cn', 'https://mail.sina.com.cn', '', 9, 1, '2017-06-08 23:47:37');
INSERT INTO `t_dict` VALUES (853, 'email_login_url', '邮箱域名地址映射', 'live.cn', 'https://login.live.com/login.srf?cbcxt=mai', '', 4, 1, '2017-06-08 23:47:37');
INSERT INTO `t_dict` VALUES (854, 'email_login_url', '邮箱域名地址映射', 'sohu.com', 'http://mail.sohu.com', '', 3, 1, '2017-06-08 23:47:37');
INSERT INTO `t_dict` VALUES (855, 'email_login_url', '邮箱域名地址映射', 'sina.com', 'https://mail.sina.com.cn', '', 6, 1, '2017-06-08 23:47:37');
INSERT INTO `t_dict` VALUES (856, 'email_login_url', '邮箱域名地址映射', 'gmail.com', 'https://mail.google.com', '', 5, 1, '2017-06-08 23:47:37');
INSERT INTO `t_dict` VALUES (857, 'email_login_url', '邮箱域名地址映射', 'yahoo.com', 'https://login.yahoo.com', '', 7, 1, '2017-06-08 23:47:37');
INSERT INTO `t_dict` VALUES (858, 'email_login_url', '邮箱域名地址映射', '163.com', 'http://mail.163.com', '', 8, 1, '2017-06-08 23:47:37');
INSERT INTO `t_dict` VALUES (867, 'default_permission_group', '创建权限时可快速添加的一组默认的子权限', 'add', '添加', 'fa fa-plus-square', 3, 1, '2017-06-10 23:50:22');
INSERT INTO `t_dict` VALUES (868, 'default_permission_group', '创建权限时可快速添加的一组默认的子权限', 'view', '查看', 'fa fa-eye', 4, 1, '2017-06-10 23:50:22');
INSERT INTO `t_dict` VALUES (869, 'default_permission_group', '创建权限时可快速添加的一组默认的子权限', 'edit', '编辑', 'fa fa-pencil', 2, 1, '2017-06-10 23:50:22');
INSERT INTO `t_dict` VALUES (870, 'default_permission_group', '创建权限时可快速添加的一组默认的子权限', 'del', '删除', 'fa fa-trash', 1, 1, '2017-06-10 23:50:22');

-- ----------------------------
-- Table structure for t_file_mapping
-- ----------------------------
DROP TABLE IF EXISTS `t_file_mapping`;
CREATE TABLE `t_file_mapping`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件key',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件地址',
  `ext` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '扩展名',
  `size` int(11) NOT NULL DEFAULT 0 COMMENT '文件大小',
  `hash` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件HASH',
  `mime` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'MIME类型',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名',
  `folder` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否目录',
  `parent` int(11) NOT NULL DEFAULT 0 COMMENT '父文件(所属目录)',
  `duration` int(11) DEFAULT 0 COMMENT '时长',
  `ancestors` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '祖先ID路径',
  `downloads` int(5) NOT NULL DEFAULT 0 COMMENT '下载量',
  `uploaded` timestamp(0) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上传时间',
  `creator` int(11) DEFAULT NULL COMMENT '创建者',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` int(11) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key`(`key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 994 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件映射' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_file_mapping
-- ----------------------------
INSERT INTO `t_file_mapping` VALUES (11, 'admin/image/2Q8ZUulU.jpg', 'http://static.xuehu365.com/admin/image/2Q8ZUulU.jpg', 'jpg', 75458, 'FmdHqTTI0EZU_y0VCFgiZLUlml8a', 'image/jpeg', 'buddy.jpg', 0, 0, 0, '0', 2, '2016-08-25 15:44:20', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (38, 'admin/image/2Q8ZEuQL.jpg', 'http://static.xuehu365.com/admin/image/2Q8ZEuQL.jpg', 'jpg', 40324, 'Fhl3FiUz9OXqUlaHjvIVFlq8zuiK', 'image/jpeg', '217160091193710182.jpg', 0, 0, 0, '0', 0, '2016-08-25 16:47:54', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (40, 'admin/image/2Q8ZEnba.png', 'http://static.xuehu365.com/admin/image/2Q8ZEnba.png', 'png', 59283, 'FmnbVpij5jzUEXQhwDaUqS6Q2fRA', 'image/png', '1697.tm.png', 0, 0, 0, '0', 0, '2016-08-25 16:48:21', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (43, 'admin/file/2Q8TgPTl.sql', 'http://static.xuehu365.com/admin/file/2Q8TgPTl.sql', 'sql', 4398, 'FvySIDa1g3XNiMxBstiPTYYFyawv', 'application/octet-stream', 't_article.sql', 0, 0, 0, '0', 0, '2016-08-26 15:36:18', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (45, 'admin/image/2Q8T6RFD.jpg', 'http://static.xuehu365.com/admin/image/2Q8T6RFD.jpg', 'jpg', 59110, 'FvioLxH5wLw6PcapsH6ifc67AoXp', 'image/jpeg', 'angela.jpg', 0, 0, 0, '0', 0, '2016-08-26 17:59:11', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (49, 'admin/image/2Q7vqfg8.jpg', 'http://static.xuehu365.com/admin/image/2Q7vqfg8.jpg', 'jpg', 79390, 'FuR3lrTw497d9zK-wO3jLtscKaRi', 'image/jpeg', '2sY5o9vd261fxOHhkRnU_big.jpg', 0, 0, 0, '0', 0, '2016-09-01 10:28:48', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (50, 'admin/image/2Q7vq9aU.jpeg', 'http://static.xuehu365.com/admin/image/2Q7vq9aU.jpeg', 'jpeg', 8143, 'FrKbS3sYDxtzubJF_U63DXohfP_H', 'image/jpeg', 'QQ图片20160422145845.jpeg', 0, 0, 0, '0', 0, '2016-09-01 10:30:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (51, 'admin/image/2Q7vq3c4.jpg', 'http://static.xuehu365.com/admin/image/2Q7vq3c4.jpg', 'jpg', 11803, 'FlSXZEf4QubOfZvLSX5U3H5PAoBD', 'image/jpeg', '2QBCAzXD_188250.jpg', 0, 0, 0, '0', 0, '2016-09-01 10:31:14', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (52, 'admin/image/2Q7vpuhr.jpg', 'http://static.xuehu365.com/admin/image/2Q7vpuhr.jpg', 'jpg', 13761, 'FrOsZxBBvoPgGqhotMLEDBJi0_hz', 'image/jpeg', '2QBCDMb5_188250.jpg', 0, 0, 0, '0', 0, '2016-09-01 10:31:49', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (53, 'admin/image/2Q7vY9mo.jpg', 'http://static.xuehu365.com/admin/image/2Q7vY9mo.jpg', 'jpg', 44461, 'FnJ6Z7UkR1rOi9_MbExQMX_g32nP', 'image/jpeg', 'QQ图片20160901114153.jpg', 0, 0, 0, '0', 0, '2016-09-01 11:42:21', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (54, 'admin/image/2Q7p2KNa.jpg', 'http://static.xuehu365.com/admin/image/2Q7p2KNa.jpg', 'jpg', 17019, 'FuE_4dCpasfAF22NUpbjPUowW1TJ', 'image/jpeg', '16090115459052270.jpg', 0, 0, 0, '0', 0, '2016-09-02 14:26:24', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (55, 'admin/image/2Q7oMKga.jpg', 'http://static.xuehu365.com/admin/image/2Q7oMKga.jpg', 'jpg', 22381, 'Flwf4b1d6mxMzJHPy0lresy3_IXh', 'image/jpeg', '16090105056088017.jpg', 0, 0, 0, '0', 0, '2016-09-02 17:13:13', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (59, 'admin/audio/2Q7KaGYp.mp3', 'http://static.xuehu365.com/admin/audio/2Q7KaGYp.mp3', 'mp3', 47554, 'FoNJttGw_HBxGA9Q64ltiW0S1-TS', 'audio/mp3', '微信网页版_webwxgetvoice(1).mp3', 0, 0, 0, '0', 0, '2016-09-07 19:26:02', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (60, 'admin/audio/2Q7KZoZH.mp3', 'http://static.xuehu365.com/admin/audio/2Q7KZoZH.mp3', 'mp3', 59866, 'Fnm66iH2MxPRRIQjrn2BFPHsde1j', 'audio/mp3', '微信网页版_webwxgetvoice(11).mp3', 0, 0, 0, '0', 0, '2016-09-07 19:27:50', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (66, 'admin/audio/2Q7EwsHq.mp3', 'http://static.xuehu365.com/admin/audio/2Q7EwsHq.mp3', 'mp3', 47554, 'FoNJttGw_HBxGA9Q64ltiW0S1-TS', 'audio/mp3', '微信网页版_webwxgetvoice(1).mp3', 0, 0, 0, '0', 0, '2016-09-08 18:33:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (69, 'admin/audio/2Q7Ew4Zg.mp3', 'http://static.xuehu365.com/admin/audio/2Q7Ew4Zg.mp3', 'mp3', 57058, 'Fufl8UubRLuaeO8jE8aI0RoARsYj', 'audio/mp3', '微信网页版_webwxgetvoice(4).mp3', 0, 0, 0, '0', 0, '2016-09-08 18:37:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (70, 'admin/audio/2Q7Ew1bI.mp3', 'http://static.xuehu365.com/admin/audio/2Q7Ew1bI.mp3', 'mp3', 56194, 'FslD6PRlmOvnBs3lueVMLmHhvnXK', 'audio/mp3', '微信网页版_webwxgetvoice(8).mp3', 0, 0, 0, '0', 0, '2016-09-08 18:37:15', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (71, 'admin/audio/2Q7EvxOc.mp3', 'http://static.xuehu365.com/admin/audio/2Q7EvxOc.mp3', 'mp3', 44890, 'FloaJZYJJHfrWUZSmUVRR_xeR2FA', 'audio/mp3', '微信网页版_webwxgetvoice(6).mp3', 0, 0, 0, '0', 0, '2016-09-08 18:37:31', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (72, 'admin/audio/2Q7Evtmg.mp3', 'http://static.xuehu365.com/admin/audio/2Q7Evtmg.mp3', 'mp3', 59074, 'FuvxoknVRyNz-Fq8FY1Tds2RZ-IJ', 'audio/mp3', '微信网页版_webwxgetvoice(7).mp3', 0, 0, 0, '0', 0, '2016-09-08 18:37:45', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (74, 'admin/audio/2Q7EvZC0.mp3', 'http://static.xuehu365.com/admin/audio/2Q7EvZC0.mp3', 'mp3', 57994, 'FrhNQJHjcbsJ5DvmJfu0ET5_uxeF', 'audio/mp3', '微信网页版_webwxgetvoice(16).mp3', 0, 0, 0, '0', 0, '2016-09-08 18:39:04', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (83, 'admin/image/2Q5YppKz.png', 'http://static.xuehu365.com/admin/image/2Q5YppKz.png', 'png', 101710, 'FiI7art4WmY0_ecEVJ0riraAiz_D', 'image/png', '共同体管理.png', 0, 0, 0, '0', 0, '2016-09-26 13:54:11', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (87, 'admin/image/2Q5SxyEu.png', 'http://static.xuehu365.com/admin/image/2Q5SxyEu.png', 'png', 49287, 'Fs3spqBuxxVUoLkJPLrnAb47v2bo', 'image/png', '乐视.png', 0, 0, 0, '0', 0, '2016-09-27 13:59:28', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (90, 'admin/image/2Q5NwTgE.jpg', 'http://static.xuehu365.com/admin/image/2Q5NwTgE.jpg', 'jpg', 52814, 'FpL3XH84RVu1iaZITh1QuDqMRpJY', 'image/jpeg', '103569885.jpg', 0, 0, 0, '0', 0, '2016-09-28 10:36:45', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (91, 'admin/image/2Q5Nvoas.jpg', 'http://static.xuehu365.com/admin/image/2Q5Nvoas.jpg', 'jpg', 75799, 'FjKcMrfj0GIQ_mGVf-5Rqu9rg810', 'image/jpeg', '635597559352343750.jpg', 0, 0, 0, '0', 0, '2016-09-28 10:39:23', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (92, 'admin/image/2Q5NBzIx.jpg', 'http://static.xuehu365.com/admin/image/2Q5NBzIx.jpg', 'jpg', 96567, 'Fjo3k7jw4XbpmtBan5Kfv5DUtCUd', 'image/jpeg', '6741626376552006973.jpg', 0, 0, 0, '0', 0, '2016-09-28 13:41:25', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (94, 'admin/image/2Q5MAxtE.jpg', 'http://static.xuehu365.com/admin/image/2Q5MAxtE.jpg', 'jpg', 82475, 'FiQHNpvVryhljDr9jXEG2y0hL9Sq', 'image/jpeg', '14_5.jpg', 0, 0, 0, '0', 0, '2016-09-28 17:51:45', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (95, 'admin/image/2Q5I99pc.jpg', 'http://static.xuehu365.com/admin/image/2Q5I99pc.jpg', 'jpg', 23399, 'Fp0CdP-i-vR7cT65A2DBkWAlQqrv', 'image/jpeg', '安又琪4.jpg', 0, 0, 0, '0', 0, '2016-09-29 10:24:01', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (96, 'admin/image/2Q5GTqaO.jpg', 'http://static.xuehu365.com/admin/image/2Q5GTqaO.jpg', 'jpg', 22795, 'FnYE_uzoOpnM5uRfigCTYmwynRm_', 'image/jpeg', 'u=3348368455,1867438270&fm=11&gp=0.jpg', 0, 0, 0, '0', 0, '2016-09-29 17:14:23', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (98, 'admin/image/2Q5AyzZ0.jpg', 'http://static.xuehu365.com/admin/image/2Q5AyzZ0.jpg', 'jpg', 29358, 'FqQBcFCoc72lJG0CwWDApXzvbyqY', 'image/jpeg', '安又琪1.jpg', 0, 0, 0, '0', 0, '2016-09-30 15:48:19', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (99, 'admin/image/2Q5Ayjqc.jpg', 'http://static.xuehu365.com/admin/image/2Q5Ayjqc.jpg', 'jpg', 36731, 'FvApWapm_oui0OVzpaf6bCRJk4cF', 'image/jpeg', '安又琪3.jpg', 0, 0, 0, '0', 0, '2016-09-30 15:49:19', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (100, 'admin/image/2Q5AxoSm.jpg', 'http://static.xuehu365.com/admin/image/2Q5AxoSm.jpg', 'jpg', 26986, 'FqTnoB32pNkQYdGpNq4cb8V57dH-', 'image/jpeg', '小特.jpg', 0, 0, 0, '0', 0, '2016-09-30 15:53:00', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (101, 'admin/image/2Q5AxZQh.jpg', 'http://static.xuehu365.com/admin/image/2Q5AxZQh.jpg', 'jpg', 40634, 'FlO36Su0b2Qo3tx_EspfWy7QSRur', 'image/jpeg', '1436426400236055108.jpg', 0, 0, 0, '0', 0, '2016-09-30 15:53:57', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (103, 'admin/image/2Q3PIuXA.jpg', 'http://static.xuehu365.com/admin/image/2Q3PIuXA.jpg', 'jpg', 27458, 'FidsteXbVXUvjrn5tWbcBmGVk1sU', 'image/jpeg', '周星驰.jpg', 0, 0, 0, '0', 0, '2016-10-19 09:59:08', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (107, 'admin/image/2Q1KWhXe.jpg', 'http://static.xuehu365.com/admin/image/2Q1KWhXe.jpg', 'jpg', 11574, 'FjYy7kuQNxxkV0-88AuHHHedTizY', 'image/jpeg', '火箭.jpg', 0, 0, 0, '0', 0, '2016-11-10 10:33:29', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (108, 'admin/image/2Q1JAsuP.jpg', 'http://static.xuehu365.com/admin/image/2Q1JAsuP.jpg', 'jpg', 24239, 'FlcjYpJj7VJWNQ6qh8Ze_gB5dfce', 'image/jpeg', '充值成功提示问题.jpg', 0, 0, 0, '0', 0, '2016-11-10 16:06:25', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (109, 'admin/image/2Q1EelZG.jpg', 'http://static.xuehu365.com/admin/image/2Q1EelZG.jpg', 'jpg', 16242, 'FviQUNH-G-N0nbW81c6Cb3lhWuJl', 'image/jpeg', 'u=1077517299,914628865&fm=11&gp=0.jpg', 0, 0, 0, '0', 0, '2016-11-11 10:39:05', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (110, 'admin/image/2Q1EV189.jpg', 'http://static.xuehu365.com/admin/image/2Q1EV189.jpg', 'jpg', 91040, 'FjHkwara94L1_JR8pXJQXw6GPcm1', 'image/jpeg', '5254.jpg', 0, 0, 0, '0', 0, '2016-11-11 11:17:48', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (114, 'admin/audio/2Q0ZuaNl.mp3', 'http://static.xuehu365.com/admin/audio/2Q0ZuaNl.mp3', 'mp3', 59722, 'FtjwYFWspE62EP64g1YpmmZ9trMT', 'audio/mp3', '微信网页版_webwxgetvoice(61).mp3', 0, 0, 0, '0', 0, '2016-11-18 09:53:24', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (116, 'admin/image/2Q00VJgt.jpg', 'http://static.xuehu365.com/admin/image/2Q00VJgt.jpg', 'jpg', 11158, 'Fsh90KJjkCjQRhvdkxWsN3NuLL95', 'image/jpeg', 'testin.jpg', 0, 0, 0, '0', 0, '2016-11-24 11:13:19', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (122, 'admin/image/2PybBRrf.jpg', 'http://static.xuehu365.com/admin/image/2PybBRrf.jpg', 'jpg', 5983, 'FkRGUIHa-KnVwkO0P6xds4LrYhWg', 'image/jpeg', 'ae705c3eb5220af16979cd1e694a7e87.jpg', 0, 0, 0, '0', 0, '2016-12-09 09:37:55', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (124, 'admin/image/2PyIUO4k.jpg', 'http://static.xuehu365.com/admin/image/2PyIUO4k.jpg', 'jpg', 79162, 'FuF45kq65MAocGu-XPYyYJ75iyCd', 'image/jpeg', '033.jpg', 0, 0, 0, '0', 0, '2016-12-12 14:21:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (133, 'admin/image/2PyIUNXS.jpeg', 'http://static.xuehu365.com/admin/image/2PyIUNXS.jpeg', 'jpeg', 46067, 'FvxysVA9dJta-Z3BlvMyQzVmkVOQ', 'image/jpeg', '20150515015506_5cxFy.thumb.700_0.jpeg', 0, 0, 0, '0', 0, '2016-12-12 14:21:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (134, 'admin/image/2PyIUNWS.jpg', 'http://static.xuehu365.com/admin/image/2PyIUNWS.jpg', 'jpg', 73041, 'FvAlkzxUBGFx5iiIqU0PffxL78sY', 'image/jpeg', '11449941865510539317.jpg', 0, 0, 0, '0', 0, '2016-12-12 14:21:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (136, 'admin/image/2PyIUNU7.jpg', 'http://static.xuehu365.com/admin/image/2PyIUNU7.jpg', 'jpg', 40507, 'FiAD1YZqzlNEqcLfFlO4V4xJoo6G', 'image/jpeg', 'view.jpg', 0, 0, 0, '0', 0, '2016-12-12 14:21:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (138, 'admin/image/2PxLpw6N.gif', 'http://static.xuehu365.com/admin/image/2PxLpw6N.gif', 'gif', 2418, 'FhzJi79pYPlhhR-6ArGxcmvv2igr', 'image/gif', 'loading.gif', 0, 0, 0, '0', 0, '2016-12-22 15:06:20', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (139, 'admin/image/2PxB7twF.png', 'http://static.xuehu365.com/admin/image/2PxB7twF.png', 'png', 2968, 'FgMfITUAo2Z1PMXhnmP15ULfd-hZ', 'image/png', 'file_icon_sum.png', 0, 0, 0, '0', 0, '2016-12-24 11:03:58', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (140, 'admin/audio/2PwDhNyt.amr', 'http://static.xuehu365.com/admin/audio/2PwDhNyt.amr', 'amr', 44278, 'Fg1a1eJ0bnkpdHxMAloH4UPNyDed', 'application/octet-stream', '2ch12000hz128bit20170103T142454.amr', 0, 0, 0, '0', 0, '2017-01-03 14:59:21', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (141, 'admin/audio/2PvxLxDj.mp3', 'http://static.xuehu365.com/admin/audio/2PvxLxDj.mp3', 'mp3', 47554, 'FoNJttGw_HBxGA9Q64ltiW0S1-TS', 'audio/mp3', '微信网页版_webwxgetvoice(1).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:04:50', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (144, 'admin/audio/2PvxLxBv.mp3', 'http://static.xuehu365.com/admin/audio/2PvxLxBv.mp3', 'mp3', 57058, 'Fufl8UubRLuaeO8jE8aI0RoARsYj', 'audio/mp3', '微信网页版_webwxgetvoice(4).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:04:50', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (145, 'admin/audio/2PvxLxBO.mp3', 'http://static.xuehu365.com/admin/audio/2PvxLxBO.mp3', 'mp3', 56986, 'FsVgwfc4JuObiBcbHYftAV2AVxg0', 'audio/mp3', '微信网页版_webwxgetvoice(5).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:04:50', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (146, 'admin/audio/2PvxLxAs.mp3', 'http://static.xuehu365.com/admin/audio/2PvxLxAs.mp3', 'mp3', 44890, 'FloaJZYJJHfrWUZSmUVRR_xeR2FA', 'audio/mp3', '微信网页版_webwxgetvoice(6).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:04:50', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (147, 'admin/audio/2PvxLxAK.mp3', 'http://static.xuehu365.com/admin/audio/2PvxLxAK.mp3', 'mp3', 59074, 'FuvxoknVRyNz-Fq8FY1Tds2RZ-IJ', 'audio/mp3', '微信网页版_webwxgetvoice(7).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:04:50', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (148, 'admin/audio/2PvxLx9m.mp3', 'http://static.xuehu365.com/admin/audio/2PvxLx9m.mp3', 'mp3', 56194, 'FslD6PRlmOvnBs3lueVMLmHhvnXK', 'audio/mp3', '微信网页版_webwxgetvoice(8).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:04:50', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (150, 'admin/audio/2PvxLx8q.mp3', 'http://static.xuehu365.com/admin/audio/2PvxLx8q.mp3', 'mp3', 55258, 'FmgTiTlqwuKPW_u22URIBPCOORG_', 'audio/mp3', '微信网页版_webwxgetvoice(10).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:04:50', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (151, 'admin/audio/2PvxLlCu.mp3', 'http://static.xuehu365.com/admin/audio/2PvxLlCu.mp3', 'mp3', 59866, 'Fnm66iH2MxPRRIQjrn2BFPHsde1j', 'audio/mp3', '微信网页版_webwxgetvoice(11).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:05:36', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (152, 'admin/audio/2PvxLlC2.mp3', 'http://static.xuehu365.com/admin/audio/2PvxLlC2.mp3', 'mp3', 26890, 'Fpr--PaoVHx1nvE8v_Bv-DWW-GxI', 'audio/mp3', '微信网页版_webwxgetvoice(12).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:05:36', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (153, 'admin/audio/2PvxLlBY.mp3', 'http://static.xuehu365.com/admin/audio/2PvxLlBY.mp3', 'mp3', 59866, 'Fmce16CEgJXmoPfp2cMeVzOQFC7k', 'audio/mp3', '微信网页版_webwxgetvoice(13).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:05:36', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (154, 'admin/audio/2PvxLlAz.mp3', 'http://static.xuehu365.com/admin/audio/2PvxLlAz.mp3', 'mp3', 57562, 'Fg1_V9Xdq1fKb--_ydRejh_GAkFG', 'audio/mp3', '微信网页版_webwxgetvoice(14).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:05:36', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (155, 'admin/audio/2PvxLlAU.mp3', 'http://static.xuehu365.com/admin/audio/2PvxLlAU.mp3', 'mp3', 55618, 'FiynykcDA1ZUNahhAsEhh3cVajGa', 'audio/mp3', '微信网页版_webwxgetvoice(15).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:05:36', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (171, 'admin/file/2PvxLIMH.xls', 'http://static.xuehu365.com/admin/file/2PvxLIMH.xls', 'xls', 47616, 'FvvDt4mT7i143yba_CIfifDTJFkO', 'application/vnd.ms-excel', '平台迁移回归测试列表.xls', 0, 0, 0, '0', 0, '2017-01-06 10:07:27', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (182, 'admin/audio/2PvxKxiw.mp3', 'http://static.xuehu365.com/admin/audio/2PvxKxiw.mp3', 'mp3', 59434, 'Fhkb_d7ui4fnQ0aiI_281rpoWGc3', 'audio/mp3', '微信网页版_webwxgetvoice(31).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:08:47', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (183, 'admin/audio/2PvxKtSL.mp3', 'http://static.xuehu365.com/admin/audio/2PvxKtSL.mp3', 'mp3', 59866, 'Fm6LD9BSjFqBmeUOUu5CX44MZFFS', 'audio/mp3', '微信网页版_webwxgetvoice(32).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:09:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (184, 'admin/audio/2PvxKtRe.mp3', 'http://static.xuehu365.com/admin/audio/2PvxKtRe.mp3', 'mp3', 56266, 'Fkp3VFq3ALLL5ZbHD1we0rX9Q2Dc', 'audio/mp3', '微信网页版_webwxgetvoice(33).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:09:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (185, 'admin/audio/2PvxKtR0.mp3', 'http://static.xuehu365.com/admin/audio/2PvxKtR0.mp3', 'mp3', 53170, 'FtgmKBtlQ5K1ko-MhQUjNU2cc1Zb', 'audio/mp3', '微信网页版_webwxgetvoice(34).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:09:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (186, 'admin/audio/2PvxKtQU.mp3', 'http://static.xuehu365.com/admin/audio/2PvxKtQU.mp3', 'mp3', 54394, 'FjRAI140s5Njoz_9kCFYJwWrR3uv', 'audio/mp3', '微信网页版_webwxgetvoice(35).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:09:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (187, 'admin/audio/2PvxKtPj.mp3', 'http://static.xuehu365.com/admin/audio/2PvxKtPj.mp3', 'mp3', 35530, 'FhZaesuzvyWbqv39SI4NpsRxspHB', 'audio/mp3', '微信网页版_webwxgetvoice(36).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:09:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (188, 'admin/audio/2PvxKtPD.mp3', 'http://static.xuehu365.com/admin/audio/2PvxKtPD.mp3', 'mp3', 28114, 'Fl7vEWLJsSo-x18UGYW2JT6LiPQS', 'audio/mp3', '微信网页版_webwxgetvoice(37).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:09:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (189, 'admin/audio/2PvxKtOi.mp3', 'http://static.xuehu365.com/admin/audio/2PvxKtOi.mp3', 'mp3', 54538, 'FkxpYlg8ODPkpPvkl8X8zZh-G-i2', 'audio/mp3', '微信网页版_webwxgetvoice(38).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:09:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (190, 'admin/audio/2PvxKtO4.mp3', 'http://static.xuehu365.com/admin/audio/2PvxKtO4.mp3', 'mp3', 47626, 'FskJuGhYgTxC4CiZPajCFTZur5a2', 'audio/mp3', '微信网页版_webwxgetvoice(39).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:09:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (191, 'admin/audio/2PvxKtNN.mp3', 'http://static.xuehu365.com/admin/audio/2PvxKtNN.mp3', 'mp3', 54034, 'FvTcsXXinQ9fRdkzbS51o133c-xW', 'audio/mp3', '微信网页版_webwxgetvoice(40).mp3', 0, 0, 0, '0', 0, '2017-01-06 10:09:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (211, 'admin/image/2PspwE3R.jpg', 'http://static.xuehu365.com/admin/image/2PspwE3R.jpg', 'jpg', 75697, 'Fv62e3f6tr0uNEAT0QuLtjaFnuz_', 'image/jpeg', '16042209289492678.jpg', 0, 0, 0, '0', 0, '2017-02-08 11:57:35', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (213, 'app/file/2PspHTCL.xlsx', 'http://static.xuehu365.com/app/file/2PspHTCL.xlsx', 'xls', 22350, '127782952', 'application/xls', '安装、卸载、升级、兼容适配、前后台切换、交叉通用APP测试用例V0.1.xlsx', 0, 0, 0, '0', 0, '2017-02-08 14:39:28', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (215, 'app/file/2PspGvjn.txt', 'http://static.xuehu365.com/app/file/2PspGvjn.txt', 'txt', 31147, '155551307', 'application/txt', '3416a75f4cea9109507cacd8e2f2aefc.txt', 0, 0, 0, '0', 0, '2017-02-08 14:41:37', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (216, 'app/file/2PspFuWu.txt', 'http://static.xuehu365.com/app/file/2PspFuWu.txt', 'txt', 8033, '684526420', 'application/txt', 'log1.txt', 0, 0, 0, '0', 0, '2017-02-08 14:45:42', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (217, 'app/file/2PspFjYF.txt', 'http://static.xuehu365.com/app/file/2PspFjYF.txt', 'txt', 14658, '585609087', 'application/txt', 'log1.txt', 0, 0, 0, '0', 0, '2017-02-08 14:46:23', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (218, 'app/file/2PspED9j.doc', 'http://static.xuehu365.com/app/file/2PspED9j.doc', 'doc', 23552, '184896930', 'application/doc', '15082811554376060-1.doc', 0, 0, 0, '0', 0, '2017-02-08 14:52:25', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (219, 'admin/image/2PsolOxH.jpeg', 'http://static.xuehu365.com/admin/image/2PsolOxH.jpeg', 'jpeg', 13483, 'FiP6DPg2HqqntKrtX5Cmb3mcjab6', 'image/jpeg', 'b395718a468544f4ba4bc2aae71e9c8f20170208140739.jpeg', 0, 0, 0, '0', 0, '2017-02-08 16:46:51', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (226, 'app/file/2PsiyEL0.docx', 'http://static.xuehu365.com/app/file/2PsiyEL0.docx', 'doc', 103357, '241426880', 'application/doc', 'Mycat和Atlas数据架构介绍.docx', 0, 0, 0, '0', 0, '2017-02-09 16:33:48', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (228, 'app/file/2PsixzHU.xlsx', 'http://static.xuehu365.com/app/file/2PsixzHU.xlsx', 'xls', 34429, '45269595', 'application/xls', 'app-2.0-0921.xlsx', 0, 0, 0, '0', 0, '2017-02-09 16:34:29', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (229, 'app/file/2Psixpec.xls', 'http://static.xuehu365.com/app/file/2Psixpec.xls', 'xls', 47616, '227447898', 'application/xls', '平台迁移回归测试列表.xls', 0, 0, 0, '0', 1, '2017-02-09 16:35:08', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (232, 'app/file/2Psegq3C.xlsx', 'http://static.xuehu365.com/app/file/2Psegq3C.xlsx', 'xls', 22350, '3176804', 'application/xls', '安装、卸载、升级、兼容适配、前后台切换、交叉通用APP测试用例V0.1.xlsx', 0, 0, 0, '0', 1, '2017-02-10 10:07:42', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (242, 'admin/image/2PsdIrIo.jpg', 'http://static.xuehu365.com/admin/image/2PsdIrIo.jpg', 'jpg', 25793, 'FtFCkPbGIqAL2n8Mvb6us_d6ARAU', 'image/jpeg', '4b6f0cfa8efeb01447a296e304fe2564.jpg', 0, 0, 0, '0', 0, '2017-02-10 15:49:13', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (243, 'admin/image/2PsdIrI5.jpg', 'http://static.xuehu365.com/admin/image/2PsdIrI5.jpg', 'jpg', 26137, 'Fh3pwG2PQwcVWaPi_si4YxKpufwE', 'image/jpeg', '8bfed65f858338b1946b199751760ff4.jpg', 0, 0, 0, '0', 0, '2017-02-10 15:49:13', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (244, 'admin/image/2PsdIrHa.jpg', 'http://static.xuehu365.com/admin/image/2PsdIrHa.jpg', 'jpg', 24374, 'FkG2J1dbAJ8--ELfCt1FbxnQtk3U', 'image/jpeg', '64b10fb3fa0c4c148c104cb95ad8a9a5.jpg', 0, 0, 0, '0', 0, '2017-02-10 15:49:13', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (245, 'admin/image/2PsdIrH7.jpg', 'http://static.xuehu365.com/admin/image/2PsdIrH7.jpg', 'jpg', 18701, 'Fr31RPVf-2VzzVJq9rYYhOw9Yhuk', 'image/jpeg', '906cd9bfe71590f2a050a947c9d3b68e.jpg', 0, 0, 0, '0', 0, '2017-02-10 15:49:13', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (246, 'admin/image/2PsdIrG3.jpg', 'http://static.xuehu365.com/admin/image/2PsdIrG3.jpg', 'jpg', 19284, 'FoRJyX2V8zICr6umU7TGmEd3nMxE', 'image/jpeg', '49611722be6fabab75c2a61719eb1816.jpg', 0, 0, 0, '0', 0, '2017-02-10 15:49:13', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (247, 'admin/image/2PsdIrE9.jpg', 'http://static.xuehu365.com/admin/image/2PsdIrE9.jpg', 'jpg', 13653, 'FqLHizuEM3oQlQD448sIJD3lKXeB', 'image/jpeg', 'd7c44cb8819590a7c97554d8339fba0e.jpg', 0, 0, 0, '0', 0, '2017-02-10 15:49:13', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (248, 'admin/image/2PsdIrCz.jpg', 'http://static.xuehu365.com/admin/image/2PsdIrCz.jpg', 'jpg', 31042, 'FsqipZWXAylSkDQorTouFBZ_HoQl', 'image/jpeg', 'f97214986ccaee29e6fe8b5b9bafd105.jpg', 0, 0, 0, '0', 0, '2017-02-10 15:49:13', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (250, 'admin/image/2PsNJ64U.jpg', 'http://static.xuehu365.com/admin/image/2PsNJ64U.jpg', 'jpg', 6091, 'FmMdlIQ2UOzIrjYjA43T30n_Mtxm', 'image/jpeg', 'u=1149649067,2598408610&fm=21&gp=0.jpg', 0, 0, 0, '0', 0, '2017-02-13 09:28:38', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (251, 'admin/image/2PsNJ3au.jpg', 'http://static.xuehu365.com/admin/image/2PsNJ3au.jpg', 'jpg', 7954, 'FhNa-oRjaAj3by-ytW8S1zGUVH4m', 'image/jpeg', 'u=1151303498,4188597331&fm=21&gp=0.jpg', 0, 0, 0, '0', 0, '2017-02-13 09:28:47', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (268, 'app/file/2PsLm2XI.xlsx', 'http://static.xuehu365.com/app/file/2PsLm2XI.xlsx', 'xls', 34429, '76292201', 'application/xls', 'app-2.0-0921.xls', 0, 0, 0, '0', 0, '2017-06-28 23:27:46', NULL, '2017-06-28 23:27:46', NULL);
INSERT INTO `t_file_mapping` VALUES (269, 'app/file/2PsLm0Hb.xls', 'http://static.xuehu365.com/app/file/2PsLm0Hb.xls', 'xls', 47616, '225024166', 'application/xls', '平台迁移回归测试列表.xls', 0, 0, 0, '0', 0, '2017-02-13 15:46:22', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (272, 'app/file/2PsLk5vc.doc', 'http://static.xuehu365.com/app/file/2PsLk5vc.doc', 'doc', 43008, '253484886', 'application/doc', '2016教育年会行程 11.09.doc', 0, 0, 0, '0', 1, '2017-02-13 15:54:02', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (273, 'app/file/2PsLk20q.doc', 'http://static.xuehu365.com/app/file/2PsLk20q.doc', 'doc', 17408, '260473886', 'application/doc', '礼仪指引话术-附件2.doc', 0, 0, 0, '0', 0, '2017-02-13 15:54:17', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (278, 'app/file/2PsGDBJx.xlsx', 'http://static.xuehu365.com/app/file/2PsGDBJx.xlsx', 'xls', 23386, '626644954', 'application/xls', '跨界学院产品内容核对清单0610.xlsx', 0, 0, 0, '0', 1, '2017-02-14 14:36:01', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (280, 'app/file/2PsFxV5F.xlsx', 'http://static.xuehu365.com/app/file/2PsFxV5F.xlsx', 'xls', 22350, '138174084', 'application/xls', '通用APP测试用例.xls', 0, 0, 0, '0', 2, '2017-06-29 23:05:32', NULL, '2017-06-29 23:05:32', NULL);
INSERT INTO `t_file_mapping` VALUES (284, 'app/file/2PsFt6Cx.xlsx', 'http://static.xuehu365.com/app/file/2PsFt6Cx.xlsx', 'xls', 22350, '168704965', 'application/xls', '通用APP测试用例V0.1.xls', 0, 0, 0, '0', 0, '2017-06-28 23:29:04', NULL, '2017-06-28 23:29:04', NULL);
INSERT INTO `t_file_mapping` VALUES (297, 'admin/image/2PsBd88D.jpg', 'http://static.xuehu365.com/admin/image/2PsBd88D.jpg', 'jpg', 5839, 'FiX-GEJCjwTB9aMx9ofdDU7hPkpq', 'image/jpeg', 'u=4050684637,870783349&fm=21&gp=0.jpg', 0, 0, 0, '0', 0, '2017-02-15 09:24:19', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (298, 'admin/image/2PsBd6vt.jpg', 'http://static.xuehu365.com/admin/image/2PsBd6vt.jpg', 'jpg', 9978, 'Fq7J9IfZiDbSkxRmx9__HMKh0ObA', 'image/jpeg', 'u=4198594792,1119433812&fm=21&gp=0.jpg', 0, 0, 0, '0', 0, '2017-02-15 09:24:24', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (299, 'admin/image/2PsBcymA.jpg', 'http://static.xuehu365.com/admin/image/2PsBcymA.jpg', 'jpg', 9978, 'Fq7J9IfZiDbSkxRmx9__HMKh0ObA', 'image/jpeg', '见鬼了qqq.jpg', 0, 0, 0, '0', 0, '2017-06-28 23:30:10', NULL, '2017-06-28 23:30:10', NULL);
INSERT INTO `t_file_mapping` VALUES (335, 'app/file/学乎Android版上传视频帮助-1.docx', 'http://static.xuehu365.com/app/file/学乎Android版上传视频帮助-1.docx', 'docx', 13123, 'FtFXX-0ZXoragakBXQBgwpw0gUtq', 'application/docx', '学乎Android版上传视频帮助-1.docx', 0, 0, 0, '0', 0, '2017-02-15 14:12:53', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (349, 'app/file/平台迁移回归测试列表.xls', 'http://static.xuehu365.com/app/file/平台迁移回归测试列表.xls', 'xls', 47616, 'FvvDt4mT7i143yba_CIfifDTJFkO', 'application/xls', '平台迁移回归测试列表.xls', 0, 0, 0, '0', 0, '2017-02-15 14:30:29', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (350, 'app/file/安装、卸载、升级、兼容适配、前后台切换、交叉通用APP测试用例V0.1.xlsx', 'http://static.xuehu365.com/app/file/安装、卸载、升级、兼容适配、前后台切换、交叉通用APP测试用例V0.1.xlsx', '1.xlsx', 22350, 'FveVwmYDukWFo6CbSeOWgLTeB4ga', 'application/1.xlsx', '安装、卸载、升级、兼容适配、前后台切换、交叉通用APP测试用例V0.1.xlsx', 0, 0, 0, '0', 2, '2017-02-15 14:30:42', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (370, 'app/file/长音频处理目录 6.24更新-1.xlsx', 'http://static.xuehu365.com/app/file/长音频处理目录 6.24更新-1.xlsx', '24更新-1.xlsx', 29255, 'FodKQFHys7wQX3uMDakDCeULIYBe', 'application/24更新-1.xlsx', '长音频处理目录 6.24更新-1.xlsx', 0, 0, 0, '0', 2, '2017-02-15 15:09:31', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (455, 'app/file/2Ps9Ui1s.xlsx', 'http://static.xuehu365.com/app/file/2Ps9Ui1s.xlsx', 'xls', 10270, '1184467584', 'application/xls', '开发人员信息名单.xlsx', 0, 0, 0, '0', 2, '2017-02-15 18:10:20', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (459, 'admin/image/2Ps5UBrg.jpg', 'http://static.xuehu365.com/admin/image/2Ps5UBrg.jpg', 'jpg', 26074, 'Fh_0ffMD6LlUyInXS7zKEb1wrZ_w', 'image/jpeg', '2PwxwHY8.jpg', 0, 0, 0, '0', 0, '2017-02-16 10:37:28', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (460, 'admin/image/2Ps5UAq1.jpg', 'http://static.xuehu365.com/admin/image/2Ps5UAq1.jpg', 'jpg', 2129, 'FtDAXg81ft7ZOIcQ9yGd000ZPBRH', 'image/jpeg', '2PwswP9B.jpg', 0, 0, 0, '0', 0, '2017-02-16 10:37:32', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (465, 'admin/audio/2Ps5Tpkh.mp3', 'http://static.xuehu365.com/admin/audio/2Ps5Tpkh.mp3', 'mp3', 31533, 'Fvd65--yR2XlWuv5rgOdlIaMoWHo', 'audio/mp3', '纵横职场 (2) - 副本.mp3', 0, 0, 7, '0', 0, '2017-02-16 10:38:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (466, 'admin/audio/2Ps5Tphj.mp3', 'http://static.xuehu365.com/admin/audio/2Ps5Tphj.mp3', 'mp3', 31533, 'Fvd65--yR2XlWuv5rgOdlIaMoWHo', 'audio/mp3', '纵横职场 (2).mp3', 0, 0, 7, '0', 0, '2017-02-16 10:38:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (467, 'admin/audio/2Ps5TpdS.mp3', 'http://static.xuehu365.com/admin/audio/2Ps5TpdS.mp3', 'mp3', 46797, 'FsDIx9XnmWE4TSCSKAPpKVEA73xw', 'audio/mp3', '纵横职场 (3) - 副本.mp3', 0, 0, 11, '0', 0, '2017-02-16 10:38:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (468, 'admin/audio/2Ps5TpZa.mp3', 'http://static.xuehu365.com/admin/audio/2Ps5TpZa.mp3', 'mp3', 46797, 'FsDIx9XnmWE4TSCSKAPpKVEA73xw', 'audio/mp3', '纵横职场 (3).mp3', 0, 0, 11, '0', 0, '2017-02-16 10:38:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (520, 'admin/image/2Ps4fY0N.jpg', 'http://static.xuehu365.com/admin/image/2Ps4fY0N.jpg', 'jpg', 12634, 'Fvi9tnA7TrJlWL1WLBgh6v2ogvqp', 'image/jpeg', 'another self.jpg', 0, 0, 0, '0', 0, '2017-06-28 23:00:27', NULL, '2017-06-28 23:00:27', NULL);
INSERT INTO `t_file_mapping` VALUES (521, 'admin/image/2Ps4dgNc.jpg', 'http://static.xuehu365.com/admin/image/2Ps4dgNc.jpg', 'jpg', 49406, 'FlZC9KFSJ6pAeOypI1ED2aKYxMtH', 'image/jpeg', '123123213123313123.jpg', 0, 0, 0, '0', 0, '2017-06-28 23:14:37', NULL, '2017-06-28 23:14:37', NULL);
INSERT INTO `t_file_mapping` VALUES (522, 'app/file/2Ps3hYR9.xlsx', 'http://static.xuehu365.com/app/file/2Ps3hYR9.xlsx', 'xls', 10270, '1141240768', 'application/xls', '开发人员信息名单.xlsx', 0, 0, 0, '0', 2, '2017-02-16 17:56:57', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (524, 'app/file/2Prykdvj.xlsx', 'http://static.xuehu365.com/app/file/2Prykdvj.xlsx', 'xls', 10270, '1139107848', 'application/xls', '开发人员信息名单.xls', 0, 0, 0, '0', 0, '2017-06-28 23:27:42', NULL, '2017-06-28 23:27:42', NULL);
INSERT INTO `t_file_mapping` VALUES (534, 'app/file/学乎Android版上传视频帮助.docx', 'http://static.xuehu365.com/app/file/学乎Android版上传视频帮助.docx', 'docx', 13123, 'FtFXX-0ZXoragakBXQBgwpw0gUtq', 'application/docx', '学乎Android版上传视频帮助.docx', 0, 0, 0, '0', 0, '2017-02-18 16:16:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (542, 'admin/image/2PrgK3b4.jpg', 'http://static.xuehu365.com/admin/image/2PrgK3b4.jpg', 'jpg', 101103, 'Fv5SmLVPfUdOeWyKii-vW2H6cTo3', 'image/jpeg', '20140313101331_137.jpg', 0, 0, 0, '0', 0, '2017-02-20 17:54:31', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (543, 'admin/image/2PrgK1hK.jpg', 'http://static.xuehu365.com/admin/image/2PrgK1hK.jpg', 'jpg', 30208, 'Fj5QBNCSwsGo5trCRTt4EDFnkfMO', 'image/jpeg', '20140313101332_83.jpg', 0, 0, 0, '0', 0, '2017-02-20 17:54:39', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (544, 'admin/image/2PrgJ2ln.jpg', 'http://static.xuehu365.com/admin/image/2PrgJ2ln.jpg', 'jpg', 22349, 'FlYE3_grphom4x4QFmyPrT8o14cI', 'image/jpeg', '20140313101332_916.jpg', 0, 0, 0, '0', 0, '2017-02-20 17:58:33', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (547, 'app/file/开发人员信息名单.xlsx', 'http://static.xuehu365.com/app/file/开发人员信息名单.xlsx', 'xlsx', 10270, 'FqZ5ykY3cp46aLRvDEqT6jz-_T4S', 'application/xlsx', '开发人员信息名单.xlsx', 0, 0, 0, '0', 3, '2017-02-21 11:10:36', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (548, 'admin/image/2Prc6oW3.jpeg', 'http://static.xuehu365.com/admin/image/2Prc6oW3.jpeg', 'jpeg', 30592, 'FnuShgHVk6VVF7fz8-xfPb9pZtDw', 'image/jpeg', '取的什么名字啊.jpeg', 0, 0, 0, '0', 0, '2017-06-28 23:28:37', NULL, '2017-06-28 23:28:37', NULL);
INSERT INTO `t_file_mapping` VALUES (551, 'app/file/开发人员信息名单-2.xlsx', 'http://static.xuehu365.com/app/file/开发人员信息名单-2.xlsx', 'xlsx', 10270, 'FqZ5ykY3cp46aLRvDEqT6jz-_T4S', 'application/xlsx', '开发人员信息名单-2.xlsx', 0, 0, 0, '0', 0, '2017-02-21 14:37:14', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (563, 'app/file/学乎Android版上传视频帮助-3.docx', 'http://static.xuehu365.com/app/file/学乎Android版上传视频帮助-3.docx', 'docx', 13123, 'FtFXX-0ZXoragakBXQBgwpw0gUtq', 'application/docx', '学乎Android版上传视频帮助-3.docx', 0, 0, 0, '0', 0, '2017-02-21 15:29:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (565, 'app/file/学乎Android版上传视频帮助-2.docx', 'http://static.xuehu365.com/app/file/学乎Android版上传视频帮助-2.docx', 'docx', 13123, 'FtFXX-0ZXoragakBXQBgwpw0gUtq', 'application/docx', '学乎Android版上传视频帮助-2.docx', 0, 0, 0, '0', 0, '2017-02-21 15:31:22', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (567, 'app/file/学乎Android版上传视频帮助-4.docx', 'http://static.xuehu365.com/app/file/学乎Android版上传视频帮助-4.docx', 'docx', 13123, 'FtFXX-0ZXoragakBXQBgwpw0gUtq', 'application/docx', '学乎Android版上传视频帮助-4.docx', 0, 0, 0, '0', 0, '2017-02-21 15:32:08', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (568, 'app/file/学乎Android版上传视频帮助-5.docx', 'http://static.xuehu365.com/app/file/学乎Android版上传视频帮助-5.docx', 'docx', 13123, 'FtFXX-0ZXoragakBXQBgwpw0gUtq', 'application/docx', '学乎Android版上传视频帮助-5.docx', 0, 0, 0, '0', 0, '2017-02-21 15:32:40', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (569, 'app/file/学乎Android版上传视频帮助-6.docx', 'http://static.xuehu365.com/app/file/学乎Android版上传视频帮助-6.docx', 'docx', 13123, 'FtFXX-0ZXoragakBXQBgwpw0gUtq', 'application/docx', '学乎Android版上传视频帮助-6.docx', 0, 0, 0, '0', 0, '2017-02-21 15:32:51', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (570, 'app/file/学乎Android版上传视频帮助-7.docx', 'http://static.xuehu365.com/app/file/学乎Android版上传视频帮助-7.docx', 'docx', 13123, 'FtFXX-0ZXoragakBXQBgwpw0gUtq', 'application/docx', '学乎Android版上传视频帮助-7.docx', 0, 0, 0, '0', 0, '2017-02-21 15:33:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (571, 'app/file/学乎Android版上传视频帮助-8.docx', 'http://static.xuehu365.com/app/file/学乎Android版上传视频帮助-8.docx', 'docx', 13123, 'FtFXX-0ZXoragakBXQBgwpw0gUtq', 'application/docx', '学乎Android版上传视频帮助-8.docx', 0, 0, 0, '0', 0, '2017-02-21 15:33:16', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (585, 'app/file/学乎Android版上传视频帮助-9.docx', 'http://static.xuehu365.com/app/file/学乎Android版上传视频帮助-9.docx', 'docx', 13123, 'FtFXX-0ZXoragakBXQBgwpw0gUtq', 'application/docx', '学乎Android版上传视频帮助-9.docx', 0, 0, 0, '0', 0, '2017-02-21 15:39:25', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (586, 'app/file/学乎Android版上传视频帮助-10.docx', 'http://static.xuehu365.com/app/file/学乎Android版上传视频帮助-10.docx', 'docx', 13123, 'FtFXX-0ZXoragakBXQBgwpw0gUtq', 'application/docx', '学乎Android版上传视频帮助-10.docx', 0, 0, 0, '0', 0, '2017-02-21 15:39:40', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (587, 'app/file/2Prb18OC.xlsx', 'http://static.xuehu365.com/app/file/2Prb18OC.xlsx', 'xls', 10270, '1194888280', 'application/xls', '开发人员信息名单.xlsx', 0, 0, 0, '0', 0, '2017-02-21 15:41:09', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (594, 'app/file/2PrayGiM.xls', 'http://static.xuehu365.com/app/file/2PrayGiM.xls', 'xls', 47616, '85180102', 'application/xls', '平台迁移回归测试列表.xls', 0, 0, 0, '0', 0, '2017-02-21 15:52:24', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (595, 'app/file/2PrayD3F.xlsx', 'http://static.xuehu365.com/app/file/2PrayD3F.xlsx', 'xls', 34429, '169449497', 'application/xls', 'app-2.0-0921.xlsx', 0, 0, 0, '0', 0, '2017-02-21 15:52:38', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (602, 'app/file/2PrawJwN.xls', 'http://static.xuehu365.com/app/file/2PrawJwN.xls', 'xls', 47616, '171264601', 'application/xls', '平台迁移回归测试列表.xls', 0, 0, 0, '0', 1, '2017-02-21 16:00:08', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (603, 'app/file/2PrawHKj.xlsx', 'http://static.xuehu365.com/app/file/2PrawHKj.xlsx', 'xls', 34429, '116929736', 'application/xls', 'app-2.0-0921.xlsx', 0, 0, 0, '0', 5, '2017-02-21 16:00:18', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (615, 'app/file/2PrQeTx4.doc', 'http://static.xuehu365.com/app/file/2PrQeTx4.doc', 'doc', 17408, '75937983', 'application/doc', '礼仪指引话术-附件2.doc', 0, 0, 0, '0', 2, '2017-02-23 10:13:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (622, 'admin/image/2PqvqKKp.png', 'http://static.xuehu365.com/admin/image/2PqvqKKp.png', 'png', 10459, 'FtW7jaMByakAgb65IHO7RTIyb1zn', 'image/png', '二维码.png', 0, 0, 0, '0', 0, '2017-02-28 16:41:08', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (623, 'admin/image/2PqkQG1f.png', 'http://static.xuehu365.com/admin/image/2PqkQG1f.png', 'png', 20558, 'Frd4ab70NlNxKUMVYm8dc0Dvbkkv', 'image/png', 'QQ图片20170228101717.png', 0, 0, 0, '0', 0, '2017-03-02 15:33:41', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (626, 'app/file/2PqeDbjq.xls', 'http://static.xuehu365.com/app/file/2PqeDbjq.xls', 'xls', 47616, '148113044', 'application/xls', '平台迁移回归测试列表.xls', 0, 0, 0, '0', 1, '2017-03-03 17:01:34', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (628, 'app/file/2PqeDUzx.txt', 'http://static.xuehu365.com/app/file/2PqeDUzx.txt', 'txt', 476, '171925530', 'application/txt', '其他-0927-bug.txt', 0, 0, 0, '0', 0, '2017-03-03 17:02:00', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (629, 'admin/image/2PqIbNpK.jpeg', 'http://static.xuehu365.com/admin/image/2PqIbNpK.jpeg', 'jpeg', 31611, 'FhzXPsjOM91FHEO5pswKuDMZjfZD', 'image/jpeg', 'timg.jpeg', 0, 0, 0, '0', 0, '2017-03-07 09:45:07', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (631, 'admin/image/2PqIargF.jpeg', 'http://static.xuehu365.com/admin/image/2PqIargF.jpeg', 'jpeg', 46067, 'FvxysVA9dJta-Z3BlvMyQzVmkVOQ', 'image/jpeg', '20150515015506_5cxFy.thumb.700_0.jpeg', 0, 0, 0, '0', 0, '2017-03-07 09:47:11', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (632, 'admin/image/2Pq0yoZE.png', 'http://static.xuehu365.com/admin/image/2Pq0yoZE.png', 'png', 24071, 'Fvmka1EROkXKe7_OtfEzlLx896ON', 'image/png', 'bg_geren.png', 0, 0, 0, '0', 0, '2017-03-10 10:04:57', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (633, 'admin/image/2Pq0Z1Bq.jpg', 'http://static.xuehu365.com/admin/image/2Pq0Z1Bq.jpg', 'jpg', 39931, 'Fie6zPjXJM94inyCuTsjGBEv5b8s', 'image/jpeg', '马云：我与特朗普的会面及最近的思考1233.jpg', 0, 0, 0, '0', 0, '2017-06-28 23:14:42', NULL, '2017-06-28 23:14:42', NULL);
INSERT INTO `t_file_mapping` VALUES (635, 'admin/image/2PpQcUgL.jpg', 'http://static.xuehu365.com/admin/image/2PpQcUgL.jpg', 'jpg', 50620, 'FqQ4kaW_c5uT_hH47R6M75OGTf_h', 'image/jpeg', 'one 拷贝.jpg', 0, 0, 0, '0', 0, '2017-03-16 15:19:24', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (636, 'admin/image/2Pn4kakg.jpg', 'http://static.xuehu365.com/admin/image/2Pn4kakg.jpg', 'jpg', 65211, 'FntHwopCs9hdijMEn8JvyVdnoC9B', 'image/jpeg', 'webwxgetmsgimg (11).jpg', 0, 0, 0, '0', 0, '2017-04-10 14:03:00', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (637, 'admin/image/2PmoESW1.jpg', 'http://static.xuehu365.com/admin/image/2PmoESW1.jpg', 'jpg', 44155, 'FtZOuebjHmqu2GU7DM7NiPrnhM70', 'image/jpeg', 'webwxgetmsgimg (1).jpg', 0, 0, 0, '0', 0, '2017-04-13 09:50:59', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (638, 'admin/image/2PmKpGRl.jpg', 'http://static.xuehu365.com/admin/image/2PmKpGRl.jpg', 'jpg', 95652, 'FhKSbjSkWs6UAS6bbXMAYox5G4fg', 'image/jpeg', 'u46.jpg', 0, 0, 0, '0', 0, '2017-04-18 10:32:57', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (639, 'admin/image/2PmKn375.jpg', 'http://static.xuehu365.com/admin/image/2PmKn375.jpg', 'jpg', 95652, 'FhKSbjSkWs6UAS6bbXMAYox5G4fg', 'image/jpeg', 'u46.jpg', 0, 0, 0, '0', 0, '2017-04-18 10:41:45', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (640, 'admin/image/2PmKirbp.jpg', 'http://static.xuehu365.com/admin/image/2PmKirbp.jpg', 'jpg', 95652, 'FhKSbjSkWs6UAS6bbXMAYox5G4fg', 'image/jpeg', 'u46.jpg', 0, 0, 0, '0', 0, '2017-04-18 10:58:23', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (650, 'admin/image/2PmKc3th.jpg', 'http://static.xuehu365.com/admin/image/2PmKc3th.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 11:25:24', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (651, 'admin/image/2PmKbx8u.jpg', 'http://static.xuehu365.com/admin/image/2PmKbx8u.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 11:25:50', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (652, 'admin/image/2PmKb5fi.jpg', 'http://static.xuehu365.com/admin/image/2PmKb5fi.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 11:29:15', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (653, 'admin/image/2PmKZCyM.jpg', 'http://static.xuehu365.com/admin/image/2PmKZCyM.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 11:36:44', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (654, 'admin/image/2PmKYHX5.jpg', 'http://static.xuehu365.com/admin/image/2PmKYHX5.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 11:40:25', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (656, 'admin/image/2PmKXqxh.jpg', 'http://static.xuehu365.com/admin/image/2PmKXqxh.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 11:42:07', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (657, 'admin/image/2PmKXmlf.jpg', 'http://static.xuehu365.com/admin/image/2PmKXmlf.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 11:42:23', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (658, 'admin/image/2PmKXa9a.jpg', 'http://static.xuehu365.com/admin/image/2PmKXa9a.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 11:43:12', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (659, 'admin/image/2PmKXVcv.jpg', 'http://static.xuehu365.com/admin/image/2PmKXVcv.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 11:43:29', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (660, 'admin/image/2PmKXPR5.jpg', 'http://static.xuehu365.com/admin/image/2PmKXPR5.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 11:43:53', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (661, 'admin/image/2PmKWp1t.jpg', 'http://static.xuehu365.com/admin/image/2PmKWp1t.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 11:46:13', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (663, 'admin/image/2PmK3CbH.jpg', 'http://static.xuehu365.com/admin/image/2PmK3CbH.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 13:43:52', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (665, 'admin/image/2PmK2wKE.jpg', 'http://static.xuehu365.com/admin/image/2PmK2wKE.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 13:44:54', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (666, 'admin/image/2PmK2K4S.jpg', 'http://static.xuehu365.com/admin/image/2PmK2K4S.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 13:47:22', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (667, 'admin/image/2PmK28rc.jpg', 'http://static.xuehu365.com/admin/image/2PmK28rc.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 13:48:05', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (668, 'admin/image/2PmK247d.jpg', 'http://static.xuehu365.com/admin/image/2PmK247d.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 13:48:23', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (670, 'admin/image/2PmJUuuR.jpg', 'http://static.xuehu365.com/admin/image/2PmJUuuR.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 16:00:03', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (671, 'admin/image/2PmJUsSa.jpg', 'http://static.xuehu365.com/admin/image/2PmJUsSa.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 16:00:12', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (672, 'admin/image/2PmJNk0Z.jpg', 'http://static.xuehu365.com/admin/image/2PmJNk0Z.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 16:28:33', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (673, 'admin/image/2PmJNhGV.jpg', 'http://static.xuehu365.com/admin/image/2PmJNhGV.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 16:28:44', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (674, 'admin/image/2PmJM0I4.jpg', 'http://static.xuehu365.com/admin/image/2PmJM0I4.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 16:35:27', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (676, 'admin/image/2PmJKUj8.jpg', 'http://static.xuehu365.com/admin/image/2PmJKUj8.jpg', 'jpg', 18995, 'Fv55GBp4efBppNi2-Erwdd-4Or-r', 'image/jpeg', 'u=3813661029,557298991&fm=23&gp=0.jpg', 0, 0, 0, '0', 0, '2017-04-18 16:41:27', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (682, 'admin/image/2PlYPpn8.png', 'http://static.xuehu365.com/admin/image/2PlYPpn8.png', 'png', 75576, 'FuAOLHjldMHJwUqCDQ1ju3AMNQpI', 'image/png', '2.png', 0, 0, 0, '0', 0, '2017-04-26 17:15:02', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (683, 'admin/image/2PlYPeld.png', 'http://static.xuehu365.com/admin/image/2PlYPeld.png', 'png', 50297, 'Fpaahb0ituPdkaOHsQIhLpzT2XMH', 'image/png', '3.png', 0, 0, 0, '0', 0, '2017-04-26 17:15:45', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (684, 'admin/image/2PlYPWzD.png', 'http://static.xuehu365.com/admin/image/2PlYPWzD.png', 'png', 42212, 'FiIGTPYoVrV9G2hhCdM2zf2c2iQ-', 'image/png', '4.png', 0, 0, 0, '0', 0, '2017-04-26 17:16:14', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (685, 'admin/image/2PlYPPjt.png', 'http://static.xuehu365.com/admin/image/2PlYPPjt.png', 'png', 46430, 'FkThDKlQafCYhezeb2ixkxpZQDpl', 'image/png', '5.png', 0, 0, 0, '0', 0, '2017-04-26 17:16:42', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (686, 'admin/image/2PlYPKAk.png', 'http://static.xuehu365.com/admin/image/2PlYPKAk.png', 'png', 42839, 'Fs73kcDCwYz5VALkN-D_2XKHiTMi', 'image/png', '6.png', 0, 0, 0, '0', 0, '2017-04-26 17:17:04', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (687, 'admin/image/2PlUEBeH.jpg', 'http://static.xuehu365.com/admin/image/2PlUEBeH.jpg', 'jpg', 21773, 'Fo82IclDqqP95etGI6XsN856kCzx', 'image/jpeg', 'timg.jpg', 0, 0, 0, '0', 0, '2017-04-27 10:26:23', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (688, 'admin/image/2PlUDqEh.jpg', 'http://static.xuehu365.com/admin/image/2PlUDqEh.jpg', 'jpg', 23166, 'Fk_IBP_la7PaWImsAak8XbUjXNLV', 'image/jpeg', 'timg (1).jpg', 0, 0, 0, '0', 0, '2017-04-27 10:27:46', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (689, 'admin/image/2PlUDdGo.jpg', 'http://static.xuehu365.com/admin/image/2PlUDdGo.jpg', 'jpg', 15510, 'FhfyT2VH7ERJNMoOeNSyR1Ce0zf6', 'image/jpeg', 'timg (2).jpg', 0, 0, 0, '0', 0, '2017-04-27 10:28:36', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (690, 'admin/image/2PlUDB7Z.jpg', 'http://static.xuehu365.com/admin/image/2PlUDB7Z.jpg', 'jpg', 23802, 'FosmfMY9c5RRGFJZ8TA0OKgzXyWh', 'image/jpeg', 'timg (3).jpg', 0, 0, 0, '0', 0, '2017-04-27 10:30:24', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (692, 'admin/image/2PlUCbxZ.jpg', 'http://static.xuehu365.com/admin/image/2PlUCbxZ.jpg', 'jpg', 31536, 'Fqn8JvXh9knZxAAacpyXYTbQoS8W', 'image/jpeg', 'timg (5).jpg', 0, 0, 0, '0', 0, '2017-04-27 10:32:39', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (693, 'admin/image/2PlTx0Hs.png', 'http://static.xuehu365.com/admin/image/2PlTx0Hs.png', 'png', 3051, 'FiX59vJLvdy3a13NmwkdZHuSkA6u', 'image/png', 'index_icon_category_all.png', 0, 0, 0, '0', 0, '2017-04-27 11:34:39', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (694, 'admin/image/2PlTx0DJ.png', 'http://static.xuehu365.com/admin/image/2PlTx0DJ.png', 'png', 4190, 'FtH9GoPxa5NcxCjrHyt7gbSFWR72', 'image/png', 'index_icon_category_cw.png', 0, 0, 0, '0', 0, '2017-04-27 11:34:39', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (695, 'admin/image/2PlTx0Cn.png', 'http://static.xuehu365.com/admin/image/2PlTx0Cn.png', 'png', 4661, 'FleLRnAUKi6a_zD_09gQHcpNmj69', 'image/png', 'index_icon_category_hr.png', 0, 0, 0, '0', 0, '2017-04-27 11:34:39', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (696, 'admin/image/2PlTx0CE.png', 'http://static.xuehu365.com/admin/image/2PlTx0CE.png', 'png', 4324, 'FvvVgHHdw0ddULxEFBvmA_u7nTPo', 'image/png', 'index_icon_category_ldl.png', 0, 0, 0, '0', 0, '2017-04-27 11:34:39', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (697, 'admin/image/2PlTx0BT.png', 'http://static.xuehu365.com/admin/image/2PlTx0BT.png', 'png', 3763, 'FiMHGBJwpUiHlmkH2c-NTLN5l1FK', 'image/png', 'index_icon_category_yx.png', 0, 0, 0, '0', 0, '2017-04-27 11:34:39', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (698, 'admin/image/2PlT2lNJ.jpg', 'http://static.xuehu365.com/admin/image/2PlT2lNJ.jpg', 'jpg', 37654, 'FtelRtZ0Z7e6l-v4OwACHDDnTKqc', 'image/jpeg', 'banner1.jpg', 0, 0, 0, '0', 0, '2017-04-27 15:18:02', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (699, 'admin/image/2PlT22EF.jpg', 'http://static.xuehu365.com/admin/image/2PlT22EF.jpg', 'jpg', 36628, 'FktG_rAgE-reIZLiHJ4AXKTOPzuR', 'image/jpeg', '热点广告-1.jpg', 0, 0, 0, '0', 0, '2017-04-27 15:20:56', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (700, 'admin/image/2PlSh1K2.jpg', 'http://static.xuehu365.com/admin/image/2PlSh1K2.jpg', 'jpg', 14726, 'FonQ1bRO6sLvlkdtwJkA3gVPLW-i', 'image/jpeg', '问道-1.jpg', 0, 0, 0, '0', 0, '2017-04-27 16:44:24', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (701, 'admin/image/2PlSggqF.jpg', 'http://static.xuehu365.com/admin/image/2PlSggqF.jpg', 'jpg', 16699, 'FhYGxveegT3aXatxboUFKSWQe-7Z', 'image/jpeg', '问道-2.jpg', 0, 0, 0, '0', 0, '2017-04-27 16:45:43', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (702, 'admin/image/2PlSgTiw.jpg', 'http://static.xuehu365.com/admin/image/2PlSgTiw.jpg', 'jpg', 19976, 'FoklhYx-WaBLmcdjXEgqUzogyyBq', 'image/jpeg', '问道-3.jpg', 0, 0, 0, '0', 0, '2017-04-27 16:46:33', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (703, 'admin/image/2PlSgFCC.jpg', 'http://static.xuehu365.com/admin/image/2PlSgFCC.jpg', 'jpg', 15623, 'Fg3xhQQJ7Xs4JwHf4IPI8_hJNkGQ', 'image/jpeg', '问道-4.jpg', 0, 0, 0, '0', 0, '2017-04-27 16:47:29', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (704, 'admin/image/2PlSg2EX.jpg', 'http://static.xuehu365.com/admin/image/2PlSg2EX.jpg', 'jpg', 14408, 'FkPOgd9xO-uftSOK80Unu44Pwq2t', 'image/jpeg', '问道-5.jpg', 0, 0, 0, '0', 0, '2017-06-28 23:21:58', NULL, '2017-06-28 23:21:58', NULL);
INSERT INTO `t_file_mapping` VALUES (705, 'admin/image/2PlSf8bX.jpg', 'http://static.xuehu365.com/admin/image/2PlSf8bX.jpg', 'jpg', 24636, 'Fgl_59e5Kg6X8khTT5FnZWU-Xrfj', 'image/jpeg', 'd000baa1cd11728b66bf1c85c8fcc3cec2fd2c82.jpg', 0, 0, 0, '0', 0, '2017-04-27 16:51:53', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (706, 'admin/image/2PlSf6fq.jpg', 'http://static.xuehu365.com/admin/image/2PlSf6fq.jpg', 'jpg', 72085, 'FoRXrSFw61RqrOCVpHjO43i2SoOz', 'image/jpeg', '9d82d158ccbf6c81aa626234b93eb13532fa4090.jpg', 0, 0, 0, '0', 0, '2017-04-27 16:52:00', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (707, 'admin/image/2PlSaDKU.jpg', 'http://static.xuehu365.com/admin/image/2PlSaDKU.jpg', 'jpg', 24636, 'Fgl_59e5Kg6X8khTT5FnZWU-Xrfj', 'image/jpeg', 'd000baa1cd11728b66bf1c85c8fcc3cec2fd2c82.jpg', 0, 0, 0, '0', 0, '2017-04-27 17:11:26', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (709, 'admin/image/2PlSNkyA.jpg', 'http://static.xuehu365.com/admin/image/2PlSNkyA.jpg', 'jpg', 17501, 'Fj16f07kK5r_SgQ6WnSm1nwl1GCZ', 'image/jpeg', '混轻-1.jpg', 0, 0, 0, '0', 0, '2017-04-27 18:00:55', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (710, 'admin/image/2PlSJ1nq.jpg', 'http://static.xuehu365.com/admin/image/2PlSJ1nq.jpg', 'jpg', 16778, 'Fhc3AtHqhi-_qKuhPtRJuYQDw5B6', 'image/jpeg', '混轻-2.jpg', 0, 0, 0, '0', 0, '2017-04-27 18:19:42', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (711, 'admin/image/2PlSInIo.jpg', 'http://static.xuehu365.com/admin/image/2PlSInIo.jpg', 'jpg', 12362, 'FkCy86gHY02bK440UejZojhm84eg', 'image/jpeg', '混轻-3.jpg', 0, 0, 0, '0', 0, '2017-04-27 18:20:38', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (712, 'admin/image/2PlSIOt2.jpg', 'http://static.xuehu365.com/admin/image/2PlSIOt2.jpg', 'jpg', 14799, 'FnB5mVfE8J8vwvHIm2oir4pUyfIs', 'image/jpeg', '混轻-4.jpg', 0, 0, 0, '0', 0, '2017-04-27 18:22:12', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (715, 'admin/image/2PlSHFGW.jpg', 'http://static.xuehu365.com/admin/image/2PlSHFGW.jpg', 'jpg', 16658, 'FhwGjTfMQi2p2eIwutJpwCOTNjjI', 'image/jpeg', '解决方案-1.jpg', 0, 0, 0, '0', 0, '2017-04-27 18:26:47', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (716, 'admin/image/2PlSHAKc.jpg', 'http://static.xuehu365.com/admin/image/2PlSHAKc.jpg', 'jpg', 17393, 'Fn93Ymux2s4OfJfdLfIHbypm6J_w', 'image/jpeg', '解决方案-2.jpg', 0, 0, 0, '0', 0, '2017-04-27 18:27:06', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (717, 'admin/image/2PlSH5Cm.jpg', 'http://static.xuehu365.com/admin/image/2PlSH5Cm.jpg', 'jpg', 17497, 'FjfiXvAUG7Fw21pb-5RaZIVTnKDm', 'image/jpeg', '解决方案-3.jpg', 0, 0, 0, '0', 0, '2017-04-27 18:27:26', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (721, 'admin/image/2PlOKL24.jpg', 'http://static.xuehu365.com/admin/image/2PlOKL24.jpg', 'jpg', 17892, 'FvyTMByHUfbDTZxPHbvenoLsAwnm', 'image/jpeg', '2.jpg', 0, 0, 0, '0', 0, '2017-04-28 10:39:35', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (722, 'admin/image/2PlOKCn6.jpg', 'http://static.xuehu365.com/admin/image/2PlOKCn6.jpg', 'jpg', 16332, 'FtQPg-UjBwHAnpJ9m8gojYJRYBWb', 'image/jpeg', '3.jpg', 0, 0, 0, '0', 0, '2017-04-28 10:40:07', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (723, 'admin/image/2PlNXenO.jpg', 'http://static.xuehu365.com/admin/image/2PlNXenO.jpg', 'jpg', 22156, 'FtkcQEUh3_9yPLdR05r0mXXgijf3', 'image/jpeg', '16101352449034943.jpg', 0, 0, 0, '0', 0, '2017-04-28 13:52:58', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (724, 'admin/image/2PlNWYPc.jpg', 'http://static.xuehu365.com/admin/image/2PlNWYPc.jpg', 'jpg', 39103, 'FmpQQINdqHaDJSXcWp5ds-Nu6zBy', 'image/jpeg', '1493358909(1).jpg', 0, 0, 0, '0', 0, '2017-04-28 13:57:20', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (725, 'admin/image/2PlNVc3F.jpg', 'http://static.xuehu365.com/admin/image/2PlNVc3F.jpg', 'jpg', 36198, 'FhrF7aH3o7Z9k5LlxFkTaDAL9BJH', 'image/jpeg', '1493359153(1).jpg', 0, 0, 0, '0', 0, '2017-04-28 14:01:05', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (727, 'admin/image/2PlNURqI.jpg', 'http://static.xuehu365.com/admin/image/2PlNURqI.jpg', 'jpg', 50556, 'FmEGb9K4kmAgyjT91VLyLAuNEuA6', 'image/jpeg', '使用指南.jpg', 0, 0, 0, '0', 0, '2017-04-28 14:05:42', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (728, 'admin/image/2PlNUJWa.jpg', 'http://static.xuehu365.com/admin/image/2PlNUJWa.jpg', 'jpg', 46992, 'Fr_RtV-B6euN4tSk9esdBjbLjO8U', 'image/jpeg', '线下9大会场精彩分享-首页banner.jpg', 0, 0, 0, '0', 0, '2017-04-28 14:06:14', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (729, 'admin/image/2PlNUHfx.jpg', 'http://static.xuehu365.com/admin/image/2PlNUHfx.jpg', 'jpg', 96866, 'FvXqTkWAH3C6tPhSAJDR5f2Q6Iag', 'image/jpeg', 'APP广告图.jpg', 0, 0, 0, '0', 0, '2017-04-28 14:06:21', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (730, 'admin/image/2Pl1DLEL.png', 'http://static.xuehu365.com/admin/image/2Pl1DLEL.png', 'png', 3376, 'FjocPsD-4Z37tk5jDtuH43iH3kTR', 'image/png', 'index_logo_caidao.png', 0, 0, 0, '0', 0, '2017-05-02 09:31:39', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (731, 'admin/image/2Pl18ZG0.png', 'http://static.xuehu365.com/admin/image/2Pl18ZG0.png', 'png', 3376, 'FjocPsD-4Z37tk5jDtuH43iH3kTR', 'image/png', 'index_logo_caidao.png', 0, 0, 0, '0', 0, '2017-05-02 09:50:36', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (732, 'admin/image/2Pl18RYC.png', 'http://static.xuehu365.com/admin/image/2Pl18RYC.png', 'png', 3367, 'Flkld0gUaY6wEK64_0DsIrpD7BxD', 'image/png', 'index_logo_xuedao.png', 0, 0, 0, '0', 0, '2017-05-02 09:51:06', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (734, 'admin/image/2PkQk8tQ.jpg', 'http://static.xuehu365.com/admin/image/2PkQk8tQ.jpg', 'jpg', 31371, 'Fqu7Vs_E-U31TxBogjFvwMEzuw9t', 'image/jpeg', 'QQ图片20170508151150.jpg', 0, 0, 0, '0', 0, '2017-05-08 15:13:26', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (735, 'admin/image/2PkQk8qt.jpg', 'http://static.xuehu365.com/admin/image/2PkQk8qt.jpg', 'jpg', 101822, 'FrplVGujztm63E023ZWLy2jfAaaS', 'image/jpeg', 'QQ图片20170508151216.jpg', 0, 0, 0, '0', 0, '2017-05-08 15:13:26', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (736, 'admin/image/2PkQk8q4.jpg', 'http://static.xuehu365.com/admin/image/2PkQk8q4.jpg', 'jpg', 25346, 'Fh9SMAED05g2bVXQ0jAeWTyFUADz', 'image/jpeg', 'QQ图片20170508151226.jpg', 0, 0, 0, '0', 0, '2017-05-08 15:13:26', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (737, 'admin/image/2PkQk8pc.jpg', 'http://static.xuehu365.com/admin/image/2PkQk8pc.jpg', 'jpg', 12118, 'FkBxrGszNkdgov7lbodiC4bRwHW7', 'image/jpeg', 'QQ图片20170508151230.jpg', 0, 0, 0, '0', 0, '2017-05-08 15:13:26', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (738, 'admin/image/2PkQk8p3.jpg', 'http://static.xuehu365.com/admin/image/2PkQk8p3.jpg', 'jpg', 55202, 'FvFZdaFCwVN1uSm9xD9XC-RuP_pK', 'image/jpeg', 'QQ图片20170508151235.jpg', 0, 0, 0, '0', 0, '2017-05-08 15:13:26', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (739, 'admin/image/2PkQk8oZ.jpg', 'http://static.xuehu365.com/admin/image/2PkQk8oZ.jpg', 'jpg', 40098, 'Fs-aZRGkXH4yVIBtztE6G1jIme3Y', 'image/jpeg', 'QQ图片20170508151238.jpg', 0, 0, 0, '0', 0, '2017-05-08 15:13:26', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (741, 'admin/image/2PjhO3QI.svg', 'http://static.xuehu365.com/admin/image/2PjhO3QI.svg', 'svg', 414, 'FmUhtFwxPvR0cLMweL0LrftTi09L', 'image/svg+xml', 'default.svg', 0, 0, 0, '0', 0, '2017-05-16 09:23:25', NULL, '2017-06-24 13:46:56', NULL);
INSERT INTO `t_file_mapping` VALUES (990, NULL, NULL, NULL, 0, NULL, NULL, '时是肖蛤只人', 1, 0, 0, '0', 0, '2017-06-28 23:26:13', 21, '2017-06-28 23:26:13', NULL);
INSERT INTO `t_file_mapping` VALUES (991, NULL, NULL, NULL, 0, NULL, NULL, '我就笑了', 1, 0, 0, '0', 0, '2017-06-28 23:08:25', 21, '2017-06-28 23:08:25', NULL);

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限编码',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `icon` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标',
  `parent` int(11) DEFAULT 0 COMMENT '父权限ID',
  `ancestors` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '祖先ID路径',
  `level` tinyint(4) NOT NULL DEFAULT 1 COMMENT '层级',
  `priority` tinyint(5) NOT NULL DEFAULT 0 COMMENT '优先级',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 190 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单/权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES (2, 'permission', '权限', NULL, 'fa fa-lock', 0, '2', 1, 0);
INSERT INTO `t_permission` VALUES (3, 'role', '角色', NULL, 'fa fa-vcard', 0, '3', 1, 0);
INSERT INTO `t_permission` VALUES (4, 'user', '用户', NULL, 'fa fa-users', 0, '4', 1, 0);
INSERT INTO `t_permission` VALUES (5, 'department', '部门', NULL, 'fa fa-building-o', 0, '5', 1, 0);
INSERT INTO `t_permission` VALUES (6, 'dict', '数据字典', NULL, 'fa fa-book', 0, '6', 1, 0);
INSERT INTO `t_permission` VALUES (11, 'permission.view', '查看系统权限', NULL, 'fa fa-eye', 2, '2,11', 2, 0);
INSERT INTO `t_permission` VALUES (12, 'permission.add', '添加系统权限', NULL, 'fa fa-plus-square', 2, '2,12', 2, 0);
INSERT INTO `t_permission` VALUES (13, 'permission.edit', '编辑系统权限', NULL, 'fa fa-pencil', 2, '2,13', 2, 0);
INSERT INTO `t_permission` VALUES (14, 'permission.del', '删除系统权限', NULL, 'fa fa-trash', 2, '2,14', 2, 0);
INSERT INTO `t_permission` VALUES (15, 'role.view', '查看角色', NULL, 'fa fa-eye', 3, '3,15', 2, 0);
INSERT INTO `t_permission` VALUES (16, 'role.add', '添加角色', NULL, 'fa fa-plus-square', 3, '3,16', 2, 0);
INSERT INTO `t_permission` VALUES (17, 'role.edit', '编辑角色', NULL, 'fa fa-pencil', 3, '3,17', 2, 0);
INSERT INTO `t_permission` VALUES (18, 'role.del', '删除角色', NULL, 'fa fa-trash', 3, '3,18', 2, 0);
INSERT INTO `t_permission` VALUES (19, 'user.view', '查看用户', NULL, 'fa fa-eye', 4, '4,19', 2, 0);
INSERT INTO `t_permission` VALUES (20, 'user.add', '添加用户', NULL, 'fa fa-plus-square', 4, '4,20', 2, 0);
INSERT INTO `t_permission` VALUES (21, 'user.edit', '编辑用户', NULL, 'fa fa-pencil', 4, '4,21', 2, 0);
INSERT INTO `t_permission` VALUES (22, 'user.del', '删除用户', NULL, 'fa fa-trash', 4, '4,22', 2, 0);
INSERT INTO `t_permission` VALUES (23, 'department.view', '查看部门', NULL, 'fa fa-eye', 5, '5,23', 2, 0);
INSERT INTO `t_permission` VALUES (24, 'department.add', '添加部门', NULL, 'fa fa-plus-square', 5, '5,24', 2, 0);
INSERT INTO `t_permission` VALUES (25, 'department.edit', '编辑部门', NULL, 'fa fa-pencil', 5, '5,25', 2, 0);
INSERT INTO `t_permission` VALUES (26, 'department.del', '删除部门', NULL, 'fa fa-trash', 5, '5,26', 2, 0);
INSERT INTO `t_permission` VALUES (27, 'dict.view', '查看数据字典', NULL, 'fa fa-eye', 6, '6,27', 2, 0);
INSERT INTO `t_permission` VALUES (28, 'dict.add', '添加数据字典', NULL, 'fa fa-plus-square', 6, '6,28', 2, 0);
INSERT INTO `t_permission` VALUES (29, 'dict.edit', '编辑数据字典', NULL, 'fa fa-pencil', 6, '6,29', 2, 0);
INSERT INTO `t_permission` VALUES (30, 'dict.del', '删除数据字典', NULL, 'fa fa-trash', 6, '6,30', 2, 0);
INSERT INTO `t_permission` VALUES (100, 'files', '文件管理', NULL, 'fa fa-files-o', 0, '100', 1, 0);
INSERT INTO `t_permission` VALUES (101, 'folder', '文件夹', NULL, 'fa fa-folder-o', 100, '100,101', 2, 0);
INSERT INTO `t_permission` VALUES (102, 'file', '文件', NULL, 'fa fa-file-o', 100, '100,102', 2, 0);
INSERT INTO `t_permission` VALUES (103, 'folder.add', '创建文件夹', NULL, 'fa fa-plus-square', 101, '100,101,103', 3, 0);
INSERT INTO `t_permission` VALUES (104, 'folder.rename', '修改文件夹名称', NULL, 'fa fa-pencil', 101, '100,101,104', 3, 0);
INSERT INTO `t_permission` VALUES (105, 'folder.del', '删除文件夹', NULL, 'fa fa-trash', 101, '100,101,105', 3, 0);
INSERT INTO `t_permission` VALUES (106, 'folder.move', '移动文件夹', NULL, 'fa fa-sort-amount-desc', 101, '100,101,106', 3, 0);
INSERT INTO `t_permission` VALUES (107, 'file.upload', '上传文件', NULL, 'fa fa-cloud-upload', 102, '100,102,107', 3, 0);
INSERT INTO `t_permission` VALUES (108, 'file.download', '下载文件', NULL, 'fa fa-cloud-download', 102, '100,102,108', 3, 0);
INSERT INTO `t_permission` VALUES (109, 'file.update', '更新文件', NULL, 'fa fa-pencil', 102, '100,102,109', 3, 0);
INSERT INTO `t_permission` VALUES (110, 'file.rename', '修改文件名', NULL, 'fa fa-edit', 102, '100,102,110', 3, 0);
INSERT INTO `t_permission` VALUES (111, 'file.move', '移动文件', NULL, 'fa fa-sort-amount-desc', 102, '100,102,111', 3, 0);
INSERT INTO `t_permission` VALUES (112, 'file.copy', '复制文件', NULL, 'fa fa-copy', 102, '100,102,112', 3, 0);
INSERT INTO `t_permission` VALUES (113, 'file.del', '删除文件', NULL, 'fa fa-trash-o', 102, '100,102,113', 3, 0);
INSERT INTO `t_permission` VALUES (189, 'issue.add', '创建Issue', NULL, 'fa fa-bug', 0, '189', 1, 0);

-- ----------------------------
-- Table structure for t_reset_record
-- ----------------------------
DROP TABLE IF EXISTS `t_reset_record`;
CREATE TABLE `t_reset_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `reset_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '重置密钥',
  `valid` int(1) DEFAULT 1 COMMENT '是否有效, 1-是 0-否',
  `request_time` datetime(0) DEFAULT NULL COMMENT '申请时间',
  `expiration_time` datetime(0) DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_reset_record
-- ----------------------------
INSERT INTO `t_reset_record` VALUES (71, 'hunan_he@163.com', NULL, 'ed9800ccb9829046c2a54ff8ecb6a6ac', 0, '2016-02-27 21:53:40', '2016-02-28 21:53:40');
INSERT INTO `t_reset_record` VALUES (72, 'CLIPTHEME@CLIPTHEME.cn', NULL, 'bb08a3222452742c54029acf6d2ae0d1', 1, '2016-02-27 22:02:37', '2016-02-28 22:02:37');
INSERT INTO `t_reset_record` VALUES (73, 'CLIPTHEME@CLIPTHEME.cn', NULL, '123c1a4480b4a3c273de247b76e4dc81', 1, '2016-02-27 22:04:53', '2016-02-28 22:04:53');
INSERT INTO `t_reset_record` VALUES (74, 'CLIPTHEME@CLIPTHEME.cn', NULL, 'a9cb367b665412f930fc6d3ae6b4a942', 1, '2016-02-27 22:05:10', '2016-02-28 22:05:10');
INSERT INTO `t_reset_record` VALUES (75, 'CLIPTHEME@CLIPTHEME.cn', NULL, 'effa315f284c9826b56158deccdc2fcc', 1, '2016-02-27 22:06:09', '2016-02-28 22:06:09');
INSERT INTO `t_reset_record` VALUES (76, 'CLIPTHEME@CLIPTHEME.cn', NULL, 'ac1c1940489fccde8576ff8c522b3881', 1, '2016-02-27 22:06:37', '2016-02-28 22:06:37');
INSERT INTO `t_reset_record` VALUES (77, 'CLIPTHEME@CLIPTHEME.cn', NULL, '634f3d294ab80cb699524d8ebb00a020', 1, '2016-02-27 22:06:53', '2016-02-28 22:06:53');
INSERT INTO `t_reset_record` VALUES (78, 'CLIPTHEME@CLIPTHEME.cn', NULL, '9d976a269a031876dc65cf5ab032c05e', 1, '2016-02-27 22:08:39', '2016-02-28 22:08:39');
INSERT INTO `t_reset_record` VALUES (79, 'CLIPTHEME@CLIPTHEME.cn', NULL, '8cbd6ce0dadbd047168560a9dbbd9aec', 1, '2016-02-28 17:16:17', '2016-02-29 17:16:17');
INSERT INTO `t_reset_record` VALUES (80, 'CLIPTHEME1@123', NULL, '6ba9ecd9dcfa17f2db570deddc53a1be', 1, '2016-02-28 17:16:42', '2016-02-29 17:16:42');
INSERT INTO `t_reset_record` VALUES (81, 'CLIPTHEME1@123', NULL, '126edf303b6b4f78a906292014209087', 1, '2016-02-28 17:17:59', '2016-02-29 17:17:59');
INSERT INTO `t_reset_record` VALUES (82, 'CLIPTHEME1@123', NULL, 'd4dd50e1ebe9b573f428fea8b9c9a109', 1, '2016-02-28 17:18:15', '2016-02-29 17:18:15');
INSERT INTO `t_reset_record` VALUES (83, 'CLIPTHEME1@123', NULL, '3187a5d9d04bd29ea35c8b5a518ed73a', 1, '2016-02-28 17:18:50', '2016-02-29 17:18:50');
INSERT INTO `t_reset_record` VALUES (84, 'CLIPTHEME1@123', NULL, '0ab2f1eee170c7563bd46b5bae5ad5f1', 1, '2016-02-28 17:19:38', '2016-02-29 17:19:38');
INSERT INTO `t_reset_record` VALUES (85, 'CLIPTHEME1@123', NULL, 'be397305c64f6005fa9848da7c013634', 1, '2016-02-28 17:21:34', '2016-02-29 17:21:34');
INSERT INTO `t_reset_record` VALUES (86, 'hunan_he@163.com', NULL, '01e30568f7e2b8d439dca7997836ac91', 0, '2016-02-28 17:25:57', '2016-02-29 17:25:57');
INSERT INTO `t_reset_record` VALUES (87, 'hunan_he@163.com', NULL, 'fe27f8f1422172b5841a87c1841f4bbf', 0, '2016-02-28 17:26:17', '2016-02-29 17:26:17');
INSERT INTO `t_reset_record` VALUES (88, 'hunan_he@163.com', NULL, '5346a559083bc0f9957f22c7cb92ce9a', 0, '2016-02-28 17:28:43', '2016-02-29 17:28:43');
INSERT INTO `t_reset_record` VALUES (89, 'hunan_he@163.com', NULL, 'f09254d8e2214920c8e34ca8015666b3', 0, '2016-02-28 17:29:07', '2016-02-29 17:29:07');
INSERT INTO `t_reset_record` VALUES (90, 'hunan_he@163.com', NULL, '7b395c04278d7e1dcaabaa577fa2d21a', 0, '2016-02-28 17:30:18', '2016-02-29 17:30:18');
INSERT INTO `t_reset_record` VALUES (91, 'hunan_he@163.com', NULL, '5207ee766445dd28028d08bb193e6109', 0, '2016-02-28 17:41:04', '2016-02-29 17:41:04');
INSERT INTO `t_reset_record` VALUES (92, 'hunan.me@gmail.com', NULL, 'c09a9df69b0aef479d9e6a323f2e18ab', 1, '2016-03-02 20:06:12', '2016-03-03 20:06:12');
INSERT INTO `t_reset_record` VALUES (93, 'hunan.me@gmail.com', NULL, '2c48a1e60bc069509ac7b2786820c5ac', 1, '2016-03-02 20:06:26', '2016-03-03 20:06:26');
INSERT INTO `t_reset_record` VALUES (94, 'hunan.me@gmail.com', NULL, '7c5ef7c990dfaf933f7a793378d9760a', 1, '2016-03-02 20:06:48', '2016-03-03 20:06:48');
INSERT INTO `t_reset_record` VALUES (95, 'hunan.me@gmail.com', NULL, '8b2a04fd24c465bbcc103a0f17dc7e46', 1, '2016-03-02 20:07:17', '2016-03-03 20:07:17');
INSERT INTO `t_reset_record` VALUES (96, 'hunan.me@gmail.com', NULL, 'b214a474638af38640c0c84c32853291', 1, '2016-05-27 11:01:21', '2016-05-28 11:01:21');
INSERT INTO `t_reset_record` VALUES (97, 'hunan.me@gmail.com', NULL, 'a80ef0bb64cdb88e94ce6488769f6d9e', 1, '2016-05-27 11:01:51', '2016-05-28 11:01:51');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `description` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (-1, '系统管理员', '不可修改, 默认拥有所有权限', 1);
INSERT INTO `t_role` VALUES (0, '普通用户', '系统默认角色', 1);
INSERT INTO `t_role` VALUES (1, '客服', NULL, 1);
INSERT INTO `t_role` VALUES (2, '课程运营', NULL, 1);
INSERT INTO `t_role` VALUES (11, '学习顾问', NULL, 1);
INSERT INTO `t_role` VALUES (84, '哇哈哈', '哇另一个哈', 1);

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `perm_id` int(11) NOT NULL,
  `perm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 247 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES (91, 1, 2, 'permission');
INSERT INTO `t_role_permission` VALUES (92, 1, 11, 'permission.view');
INSERT INTO `t_role_permission` VALUES (93, 1, 12, 'permission.add');
INSERT INTO `t_role_permission` VALUES (94, 1, 13, 'permission.edit');
INSERT INTO `t_role_permission` VALUES (95, 1, 14, 'permission.del');
INSERT INTO `t_role_permission` VALUES (96, 84, 2, 'permission');
INSERT INTO `t_role_permission` VALUES (97, 84, 11, 'permission.view');
INSERT INTO `t_role_permission` VALUES (98, 84, 12, 'permission.add');
INSERT INTO `t_role_permission` VALUES (99, 84, 13, 'permission.edit');
INSERT INTO `t_role_permission` VALUES (100, 84, 14, 'permission.del');
INSERT INTO `t_role_permission` VALUES (207, 0, 2, 'permission');
INSERT INTO `t_role_permission` VALUES (208, 0, 3, 'role');
INSERT INTO `t_role_permission` VALUES (209, 0, 4, 'user');
INSERT INTO `t_role_permission` VALUES (210, 0, 5, 'department');
INSERT INTO `t_role_permission` VALUES (211, 0, 6, 'dict');
INSERT INTO `t_role_permission` VALUES (212, 0, 11, 'permission.view');
INSERT INTO `t_role_permission` VALUES (213, 0, 12, 'permission.add');
INSERT INTO `t_role_permission` VALUES (214, 0, 13, 'permission.edit');
INSERT INTO `t_role_permission` VALUES (215, 0, 14, 'permission.del');
INSERT INTO `t_role_permission` VALUES (216, 0, 15, 'role.view');
INSERT INTO `t_role_permission` VALUES (217, 0, 16, 'role.add');
INSERT INTO `t_role_permission` VALUES (218, 0, 17, 'role.edit');
INSERT INTO `t_role_permission` VALUES (219, 0, 18, 'role.del');
INSERT INTO `t_role_permission` VALUES (220, 0, 19, 'user.view');
INSERT INTO `t_role_permission` VALUES (221, 0, 20, 'user.add');
INSERT INTO `t_role_permission` VALUES (222, 0, 21, 'user.edit');
INSERT INTO `t_role_permission` VALUES (223, 0, 22, 'user.del');
INSERT INTO `t_role_permission` VALUES (224, 0, 23, 'department.view');
INSERT INTO `t_role_permission` VALUES (225, 0, 24, 'department.add');
INSERT INTO `t_role_permission` VALUES (226, 0, 25, 'department.edit');
INSERT INTO `t_role_permission` VALUES (227, 0, 26, 'department.del');
INSERT INTO `t_role_permission` VALUES (228, 0, 27, 'dict.view');
INSERT INTO `t_role_permission` VALUES (229, 0, 28, 'dict.add');
INSERT INTO `t_role_permission` VALUES (230, 0, 29, 'dict.edit');
INSERT INTO `t_role_permission` VALUES (231, 0, 30, 'dict.del');
INSERT INTO `t_role_permission` VALUES (232, 0, 100, 'files');
INSERT INTO `t_role_permission` VALUES (233, 0, 101, 'folder');
INSERT INTO `t_role_permission` VALUES (234, 0, 102, 'file');
INSERT INTO `t_role_permission` VALUES (235, 0, 103, 'folder.add');
INSERT INTO `t_role_permission` VALUES (236, 0, 104, 'folder.rename');
INSERT INTO `t_role_permission` VALUES (237, 0, 105, 'folder.del');
INSERT INTO `t_role_permission` VALUES (238, 0, 106, 'folder.move');
INSERT INTO `t_role_permission` VALUES (239, 0, 107, 'file.upload');
INSERT INTO `t_role_permission` VALUES (240, 0, 108, 'file.download');
INSERT INTO `t_role_permission` VALUES (241, 0, 109, 'file.update');
INSERT INTO `t_role_permission` VALUES (242, 0, 110, 'file.rename');
INSERT INTO `t_role_permission` VALUES (243, 0, 111, 'file.move');
INSERT INTO `t_role_permission` VALUES (244, 0, 112, 'file.copy');
INSERT INTO `t_role_permission` VALUES (245, 0, 113, 'file.del');
INSERT INTO `t_role_permission` VALUES (246, 0, 189, 'issue.add');

-- ----------------------------
-- Table structure for t_site
-- ----------------------------
DROP TABLE IF EXISTS `t_site`;
CREATE TABLE `t_site`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `domain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `theme` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `copyright` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `deleted` int(1) DEFAULT 0,
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '站点' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `pass` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `nick` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称姓名',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `role_id` int(11) NOT NULL DEFAULT -1 COMMENT '角色ID',
  `department_id` int(11) DEFAULT NULL COMMENT '所属部门',
  `gender` int(11) DEFAULT 0 COMMENT '性别0-女,1-男',
  `status` tinyint(1) DEFAULT NULL COMMENT '用户状态',
  `birthday` datetime(0) DEFAULT NULL COMMENT '生日',
  `visited` datetime(0) DEFAULT NULL COMMENT '最近访问时间',
  `created` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE,
  UNIQUE INDEX `uk_email`(`email`) USING BTREE,
  UNIQUE INDEX `uk_phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 161 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'taeyeon', '31DC1514F9974A1CB1201BC15FE6F498', 'Yoona', 'http://static.xuehu365.com/admin/img/2Q9G0QAs.jpg', 'taeyeon@mail.kr', '18927543078', -1, 19, 0, 1, '2014-12-24 00:00:00', '2017-06-11 16:36:49', '2013-07-09 21:26:54', '2017-06-11 16:36:49');
INSERT INTO `t_user` VALUES (17, 'admin', 'F6FDFFE48C908DEB0F4C3BD36C032E72', '管理员', 'http://static.xuehu365.com/admin/img/2Q5U2FsG.png', 'hunan.me@gmail.com', '13412317173', -1, 18, 1, 1, '2015-08-31 00:00:00', '2017-06-07 00:33:34', '2014-07-15 09:57:39', '2017-06-07 23:23:12');
INSERT INTO `t_user` VALUES (18, 'test', 'F6FDFFE48C908DEB0F4C3BD36C032E72', '测试用户', 'http://static.xuehu365.com/admin/img/2Q9G0QAs.jpg', 'test@test.test', '1234567890', 0, 19, 0, 1, NULL, NULL, '2016-06-06 14:18:59', '2017-06-02 00:17:36');
INSERT INTO `t_user` VALUES (20, 'a123', '02BDD5A0D322DE0BA211CF5E1C823E69', '无真实姓名', 'http://static.xuehu365.com/admin/img/2Q9G0QAs.jpg', '2651236249@qq.com', '15920501235', 0, NULL, 0, 1, NULL, '2016-09-20 17:05:40', '2016-09-19 17:07:47', '2016-09-20 17:05:40');
INSERT INTO `t_user` VALUES (21, 'backflow', 'B32CE2C5D088583FE2F44DA7F82592B4', '胡楠', 'http://static.xuehu365.com/admin/img/2Q9G0QAs.jpg', 'backflow@backflow.cn', '18027546311', 0, NULL, 0, 1, NULL, '2017-06-29 23:03:57', '2016-09-19 17:41:44', '2017-06-29 23:03:57');
INSERT INTO `t_user` VALUES (22, 'one1', '2CA28855C3D34EFF260CEC944FD6B70E', '', '/static/img/avatar.svg', '2961625156@qq.com', '18920701001', 2, 18, 0, 1, NULL, '2016-09-20 16:48:39', '2016-09-20 14:39:03', '2017-06-03 00:20:48');
INSERT INTO `t_user` VALUES (25, 'five', 'E722E929AC6C12F1A1BB043DA7CBE9B6', '', '/static/img/avatar.svg', '123@', '12', 0, 19, 0, 1, NULL, NULL, '2016-09-20 17:02:53', '2017-06-02 00:34:13');
INSERT INTO `t_user` VALUES (26, 'aaaa', 'A9C4020FA0FC89C333AFE8FA91228D28', '小明', '/static/img/avatar.svg', '12345678@qq.com', '12345678912', 11, 19, 0, 1, NULL, '2016-09-21 09:59:32', '2016-09-21 09:43:45', '2017-06-02 00:34:46');
INSERT INTO `t_user` VALUES (27, 'lazyone', 'ADEC27E3C7C9C73D8E5842684CE0A5A0', '', '/static/img/avatar.svg', '13@11.com', '13767676666', 0, 18, 0, 1, NULL, '2017-03-01 10:32:44', '2016-09-27 18:42:53', '2017-06-07 00:32:13');
INSERT INTO `t_user` VALUES (28, '张毅斐', '3D17AB9CEF539340BBB773AFB8317A63', '张毅斐', '/static/img/avatar.svg', NULL, '13601034390', 0, 19, 0, 1, NULL, NULL, NULL, '2017-06-02 00:35:55');
INSERT INTO `t_user` VALUES (29, '宋亚玲', 'BFCEE929CF63AA4C5BCEE3FE486247B5', '宋亚玲', '/static/img/avatar.svg', NULL, '18811218428', 0, 19, 0, 1, NULL, NULL, NULL, '2017-06-02 00:39:32');
INSERT INTO `t_user` VALUES (30, '赵心怡', 'F63CFF1FDF49AF020A317EE7375822A7', '赵心怡', '/static/img/avatar.svg', NULL, '18802733761', 0, 19, 0, 1, NULL, NULL, NULL, '2017-06-02 00:32:30');
INSERT INTO `t_user` VALUES (31, '甄亚茹', 'F96886E89966E0C3DD6FC2B5DDCE6275', '甄亚茹', '/static/img/avatar.svg', NULL, '13051763065', 11, 18, 0, 1, NULL, NULL, NULL, '2017-06-11 16:48:21');
INSERT INTO `t_user` VALUES (32, '桂露玲', '8002A0CDB60CF6873824ED2DCB34635B', '桂露玲', '/static/img/avatar.svg', NULL, '13661274869', 0, 19, 0, 1, NULL, NULL, NULL, '2017-06-02 00:17:18');
INSERT INTO `t_user` VALUES (33, '樊欢', 'DCB61B4DF243E461955D9FD68198A036', '樊欢', '/static/img/avatar.svg', NULL, '13439544614', 0, 19, 0, 1, NULL, NULL, NULL, '2017-06-02 00:15:52');
INSERT INTO `t_user` VALUES (34, '张超', '3020A99BDF49FE7D6720FE1F8D6F1EA1', '张超', '/static/img/avatar.svg', NULL, '18811718780', 0, 19, 0, 1, NULL, NULL, NULL, '2017-06-02 00:15:55');
INSERT INTO `t_user` VALUES (35, '赵婉', '762A64B662B071164D3433BD04C87887', '赵婉', '/static/img/avatar.svg', NULL, '15001056516', 0, 19, 0, 1, NULL, NULL, NULL, '2017-06-02 00:30:09');
INSERT INTO `t_user` VALUES (36, '严斌', '3C35E8AF2A7E0D20568C8EE1BA3A3FC6', '严斌', '/static/img/avatar.svg', NULL, '15910416381', 0, 19, 0, 1, NULL, NULL, NULL, '2017-06-02 00:29:58');
INSERT INTO `t_user` VALUES (37, '刘宇', 'E9972F0834F99F8BACFE9C61308A27DE', '刘宇', '/static/img/avatar.svg', NULL, '13901251351', 0, 19, 0, 1, NULL, NULL, NULL, '2017-06-02 00:40:04');
INSERT INTO `t_user` VALUES (38, '刘海灵', 'A56A0F6B31AF78BEFCFF113692CD6588', '刘海灵', '/static/img/avatar.svg', NULL, '18801037645', 0, NULL, 0, 1, NULL, '2017-05-21 14:40:06', NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (39, '符才锦', '0E15E56DDF37DFDCDC6C135E81CD617B', '符才锦', '/static/img/avatar.svg', NULL, '18510982669', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (40, '冯子伟', '4648DC424EFFA1FCC9AC7E3563CA7B5F', '冯子伟', '/static/img/avatar.svg', NULL, '13760746493', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (41, '林小凤', '7AD4A16C31A73207906C5D8F48811092', '林小凤', '/static/img/avatar.svg', NULL, '15975387375', 0, 10, 0, 1, NULL, '2016-09-28 16:48:55', NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (42, '尹璐', '3DE0B6E212BB6C8360A816D8CCD9CC1C', '尹璐', '/static/img/avatar.svg', NULL, '13538795725', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (43, '陈舒祺', 'CD3F389D9B7B33F0937A922CA09E7B02', '陈舒祺', '/static/img/avatar.svg', NULL, '17050097838', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (44, '周梅桂', '9020C115393D2080193F86A2178AA683', '周梅桂', '/static/img/avatar.svg', NULL, '15817064247', 0, 10, 0, 1, NULL, NULL, NULL, '2017-06-26 21:20:25');
INSERT INTO `t_user` VALUES (45, '张红', '195A07A44112F107C5B6DF5CAE0DD997', '张红', '/static/img/avatar.svg', NULL, '13824491253', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (46, '刘莉茹', '4D9314C8BCE2D732F915398D4B8CCE37', '刘莉茹', '/static/img/avatar.svg', NULL, '13544597937', 1, 10, 0, 1, NULL, NULL, NULL, '2017-06-26 21:20:27');
INSERT INTO `t_user` VALUES (47, '张纯燕', '4111B5E74AD22609D2EA5B7E9C64DA31', '张纯燕', '/static/img/avatar.svg', NULL, '15013126366', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (48, '唐存勇', '45688DE2C926126A748DC410AC20DC55', '唐存勇', '/static/img/avatar.svg', NULL, '13710251392', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (49, '曾富', '20BC6BA0E3A02D02A68D299986F7341E', '曾富', '/static/img/avatar.svg', NULL, '13798185862', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (50, '柳晓燕', '9627840EDBBE01BB1480623095EFB03A', '柳晓燕', '/static/img/avatar.svg', NULL, '13710921714', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (51, '钟雪芬', 'BAD99A881185B59DEED8194E953F6B56', '钟雪芬', '/static/img/avatar.svg', NULL, '13288830078', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (52, '黄燕', '77639F9528C7949473C597CE1DDBB487', '黄燕', '/static/img/avatar.svg', NULL, '13318881851', 2, 10, 0, 1, NULL, NULL, NULL, '2017-06-26 21:12:31');
INSERT INTO `t_user` VALUES (53, '庞宁', 'B979D2EF1ABFB00127482905993490E8', '庞宁', '/static/img/avatar.svg', NULL, '13512793972', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (54, '王丽娟', '82066F194006042D092EA6C084107EB9', '王丽娟', '/static/img/avatar.svg', NULL, '13544481102', 11, 10, 0, 1, NULL, NULL, NULL, '2017-06-26 21:19:21');
INSERT INTO `t_user` VALUES (55, '蔡日诚', 'DC68A980C02BC14FC7865938F7B49679', '蔡日诚', '/static/img/avatar.svg', NULL, '15918433074', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (56, '郑伟成', '2FED7D9D9BCF5459B593CE5C8AEB8B40', '郑伟成', '/static/img/avatar.svg', NULL, '13632325434', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (57, '叶善勇', '484A982211B80494D87C9032F965FCE1', '叶善勇', '/static/img/avatar.svg', NULL, '18122384801', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (58, '陈巧', '731E90CB80128AB967D677DFDEC590B9', '陈巧', '/static/img/avatar.svg', NULL, '15999943610', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (59, '何鹏', '55CE0FC48F7CA8BE49093D382E5068A2', '何鹏', '/static/img/avatar.svg', NULL, '15810789885', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (60, '韩宁宁', 'EA9E7B849584087795DC4FDBA75A3824', '韩宁宁', '/static/img/avatar.svg', NULL, '13553001608', 0, NULL, 0, 1, NULL, '2017-05-21 14:51:42', NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (61, '齐海萍', '77E374D5895DDB7038D12FAE2E399BC0', '齐海萍', '/static/img/avatar.svg', NULL, '15192691026', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (62, '张铎', 'A2CD97DA89144C89935E4D93585DE60F', '张铎', '/static/img/avatar.svg', NULL, '13206462848', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (63, '杜亭亭', 'A6E7F40511A704436A081FFEE249408D', '杜亭亭', '/static/img/avatar.svg', NULL, '15863046982', 2, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (64, '于馨甜', 'CEA976FD560BCA2F6B83BE71FE0FBB92', '于馨甜', '/static/img/avatar.svg', NULL, '18561917001', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (65, '胡改霞', 'F8AC61F35F0C56AF6F512070B8FB9E54', '胡改霞', '/static/img/avatar.svg', NULL, '15982106459', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (66, '苏航', 'CBEEAC3E33B91CE597B818BB3AEC6FEC', '苏航', '/static/img/avatar.svg', NULL, '15908990012', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (67, '刘欢', 'E8121926512BAA2934A4FF12A4495BB0', '刘欢', '/static/img/avatar.svg', NULL, '18562869488', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (68, '邱彩丽', '9447BFA0F031AC457000D20CB2E12A27', '邱彩丽', '/static/img/avatar.svg', NULL, '15689953297', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (69, '张小燕', '09B0EA670A96B2EDCC0F3B9B67BD057D', '张小燕', '/static/img/avatar.svg', NULL, '18650014307', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (70, '林芝', 'D78A37FABB07F5D7FE10FFDC7034D252', '林芝', '/static/img/avatar.svg', NULL, '13003925728', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (71, '胡璐津', 'FE7E31756EB696EFB60FEB69EA590C2A', '胡璐津', '/static/img/avatar.svg', NULL, '15160012523', 5, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (72, '许娇娇', '3056C191B3FC098D45812E735378E2C7', '许娇娇', '/static/img/avatar.svg', NULL, '13063069086', 2, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (73, '解语花', 'E6EB76497FBD369D52A423BF59F16603', '解语花', '/static/img/avatar.svg', NULL, '15860723282', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (74, '袁振', 'AF6E163EBBD4C663281262EDC5497F1E', '袁振', '/static/img/avatar.svg', NULL, '15907106231', 0, 17, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (75, '吴玲玲', 'CF50084D89501B55CED2A0A585DA9F6A', '吴玲玲', '/static/img/avatar.svg', NULL, '18627178105', 0, 17, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (76, '胡珩娟', '8A93DE2D0BEF190E5E5A703F9C5CAAB1', '胡珩娟', '/static/img/avatar.svg', NULL, '18571768960', 0, 17, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (77, '陈哲', '7458E80481A62A58A099B2E51DD29705', '陈哲', '/static/img/avatar.svg', NULL, '18674012992', 0, 17, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (78, '罗国兵', '0AF67B125EFFD04C15264561FD6DBFA2', '罗国兵', '/static/img/avatar.svg', NULL, '18207154014', 0, 17, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (79, '李丙兰', 'E71DDD67245858D410DAF9E667283148', '李丙兰', '/static/img/avatar.svg', NULL, '13487080805', 0, 17, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (80, '杜珍', '52B120D81306FB40657F9588816E0BBB', '杜珍', '/static/img/avatar.svg', NULL, '15007169749', 0, 17, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (81, '周仕琪', '7AAAAD8C7C6AAD37D0CEA8B008DACA7D', '周仕琪', '/static/img/avatar.svg', NULL, '18627758523', 0, 17, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (82, '马登强', '9BA56A3D906E4746A38787F1950F87A9', '马登强', '/static/img/avatar.svg', NULL, '18782287308', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (83, '刘家兴', '822CFCE20FEC282EAB2A8C839CEB28F7', '刘家兴', '/static/img/avatar.svg', NULL, '18382108188', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (84, '范楷', '7DA890B85C03DAB48C0E9F51A8852CE0', '范楷', '/static/img/avatar.svg', NULL, '18628389780', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (85, '雷雨', 'D9A8509AB067FA2E6DF17085EB598ABF', '雷雨', '/static/img/avatar.svg', NULL, '13683474120', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (86, '周晓娟', '25392B81B7CCD56922D5A714C20A5B79', '周晓娟', '/static/img/avatar.svg', NULL, '13982034166', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (87, '钟元斌', 'A47A101AFB676C14617B2C951752CA0F', '钟元斌', '/static/img/avatar.svg', NULL, '13882261750', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (88, '王金秀', '45002F9C3A744D2DB467F34BA36DB3ED', '王金秀', '/static/img/avatar.svg', NULL, '15982879701', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (89, '刘颖嫦', 'F57C54C5E8A54179A307DA75157BE36B', '刘颖嫦', '/static/img/avatar.svg', NULL, '13880103610', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (90, '钟膦榔', '534593F98A6065524BD0A9D3BDF76EE4', '钟膦榔', '/static/img/avatar.svg', NULL, '18628087952', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (91, '王静瑞', '359267716073E5FEBE51D6C9C41D1161', '王静瑞', '/static/img/avatar.svg', NULL, '13568814432', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (92, '方丽', '7727EF7C30D6B9D4DC1BAA38A07F02A2', '方丽', '/static/img/avatar.svg', NULL, '18502387600', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (93, '曾晶', 'F5F0DE8D38FE24F650919665A3A70171', '曾晶', '/static/img/avatar.svg', NULL, '13677672482', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (94, '成娜娜', '09E45FD502E5A720DDD894BB832F3768', '成娜娜', '/static/img/avatar.svg', NULL, '18323731353', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (95, '李勇', 'FD1007F3116238CA11DFA4936C275856', '李勇', '/static/img/avatar.svg', NULL, '15320436268', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (96, '孙庆', '46C1AEB855F3F53B4DEA4BBF9F30E8FD', '孙庆', '/static/img/avatar.svg', NULL, '18723954400', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (97, '朱丹', 'FC19385CF317D81AF88D8B1503690784', '朱丹', '/static/img/avatar.svg', NULL, '15111927620', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (98, '吴春霞', '29613CB09AF804B5DAA0A46C81F6CC87', '吴春霞', '/static/img/avatar.svg', NULL, '15102384023', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (99, '杨宇', '526D77200409C134AF1804F865696047', '杨宇', '/static/img/avatar.svg', NULL, '18030847238', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (100, '黄伟婕', '0E00CC92B9CDBE1257EEEE1E80E0166B', '黄伟婕', '/static/img/avatar.svg', NULL, '18523173193', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (101, '牟敏', '32E99691AEF0EF88E799C6A2779FA64D', '牟敏', '/static/img/avatar.svg', NULL, '13926522653', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (102, '莫愁', '7475070316EC14E4235E63A21ABF07AD', '莫愁', '/static/img/avatar.svg', NULL, '13602661900', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (103, '王琳', '61BBA0212E365FD6AA26317F1B69B476', '王琳', '/static/img/avatar.svg', NULL, '13480633406', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (104, '蓝逸娴', '7EFC0D20E184D9A0CB07E6666E7ED234', '蓝逸娴', '/static/img/avatar.svg', NULL, '13590285840', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (105, '周浩', 'B0920C5E8B31CBCD7336760D2088C681', '周浩', '/static/img/avatar.svg', NULL, '18948711630', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (106, '李刚', 'F9ADFA151DBF3C832082E6FCACFFDEDE', '李刚', '/static/img/avatar.svg', NULL, '18689498360', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (107, '王翠翠', '3B57DB79B36BC01D87AE5363AAD175A4', '王翠翠', '/static/img/avatar.svg', NULL, '15972920169', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (108, '郭俊', '34CA419A4F775AC8C4C2E0212A87BD27', '郭俊', '/static/img/avatar.svg', NULL, '13560747342', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (109, '李梦飞', '2225BEA84C2E159354AA326C409E1D14', '李梦飞', '/static/img/avatar.svg', NULL, '18617104918', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (110, '吴春草', '110E6519CEB3EFCBE9B783E276D41C1F', '吴春草', '/static/img/avatar.svg', NULL, '15099900836', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (111, '仇春燕', 'F42398B44CA66BF75876F7BA2FC1923D', '仇春燕', '/static/img/avatar.svg', NULL, '18718692697', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (112, '郭翠斌', 'B1A97F63240FF5C106F415761FA1C157', '郭翠斌', '/static/img/avatar.svg', NULL, '18611757734', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (113, '肖静', 'A27168198AF67840195496E9ECCCC044', '肖静', '/static/img/avatar.svg', NULL, '13926573147', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (114, '刘荣霞', 'E979CC2CF8FBCFD080010381204A9F9C', '刘荣霞', '/static/img/avatar.svg', NULL, '13662267126', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (115, '谢晓康', '9C9E9C277CBF055A57FF7D43801972DD', '谢晓康', '/static/img/avatar.svg', NULL, '18684989830', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (116, '唐小婷', '6CDA0F8EE10E904049E21B2B99F017EA', '唐小婷', '/static/img/avatar.svg', NULL, '18520852907', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (117, '李红旭', 'A9032FDA3135826C146EE781E88D0664', '李红旭', '/static/img/avatar.svg', NULL, '13168882221', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (118, '曾清', 'DAAC675A9D4CC8A46EC1988F35F610E4', '曾清', '/static/img/avatar.svg', NULL, '18382016125', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (119, '陈艳', 'C8B254EE400975D54DB84421B3A43630', '陈艳', '/static/img/avatar.svg', NULL, '17381803842', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (120, '关鹤麟', 'FEAB7B9333C9C44D065B257D0ED2BE45', '关鹤麟', '/static/img/avatar.svg', NULL, '18611475025', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (121, '杨慧', 'C4F9DD2644B199601EA1BAEAF03788C7', '杨慧', '/static/img/avatar.svg', NULL, '13811585237', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (122, '位乾乾', 'F7FC2C2622448C7C4400A6760985E496', '位乾乾', '/static/img/avatar.svg', NULL, '15311580256', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (123, '甘倩', 'FD9B59D2DA7F2C228EA1A63FD07C27DF', '甘倩', '/static/img/avatar.svg', NULL, '13659037060', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (124, '胡羽芬', '36A77D2F0BB1555FB53769B48F3DA56F', '胡羽芬', '/static/img/avatar.svg', NULL, '15296979562', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (125, '吴高敏', '240E77AB2A8B06B7021444C731D855C5', '吴高敏', '/static/img/avatar.svg', NULL, '18520098369', 2, 10, 0, 1, NULL, NULL, NULL, '2017-06-26 21:11:27');
INSERT INTO `t_user` VALUES (126, '梁丽贞', 'B49E21CDFA72CB0B4F77DAC64BFB00BA', '梁丽贞', '/static/img/avatar.svg', NULL, '13711202935', 2, 10, 0, 1, NULL, NULL, NULL, '2017-06-26 21:20:07');
INSERT INTO `t_user` VALUES (127, '刘焕仪', '6F891905B427FD9B690B889F729DF01B', '刘焕仪', '/static/img/avatar.svg', NULL, '15989051225', 11, 10, 0, 1, NULL, NULL, NULL, '2017-06-26 21:19:36');
INSERT INTO `t_user` VALUES (128, '陈可', '47AB42701E14ADD1C431F5360EFD21C7', '陈可', '/static/img/avatar.svg', NULL, '18588853692', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (129, '李娜', 'CC5B54C9A152225A256416BA6D871653', '李娜', '/static/img/avatar.svg', NULL, '15621461078', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (130, '吴闻卿', '42905384E6BF2434A76C079948D22A6B', '吴闻卿', '/static/img/avatar.svg', NULL, '13761122498', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (131, '蓝丽湘', 'AF02CE730CE47B8F6B01B5AA5BFFDA95', '蓝丽湘', '/static/img/avatar.svg', NULL, '15807694410', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (132, '王玲玲', '19401F99C25B8D17D7BE6FD5CF964867', '王玲玲', '/static/img/avatar.svg', NULL, '18948711631', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (133, '潘婷', 'A4889A67FDB5B5024DC0E6FF5CA32044', '潘婷', '/static/img/avatar.svg', NULL, '13260592758', 5, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (134, '陈素婷', 'AFCECE763B2E0160CF599A79609DEC67', '陈素婷', '/static/img/avatar.svg', NULL, '13611429379', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (135, '李淑珍', '25C1E99A9916EEA9F0B4F898E6B3796F', '李淑珍', '/static/img/avatar.svg', NULL, '18820979104', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (136, '彭勇', 'E4FF565688B94A48864F0C3128C2428A', '彭勇', '/static/img/avatar.svg', NULL, '15717507914', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (137, '彭琳', '067A0AA2AF8EB371B26B00C1A4BEC904', '彭琳', '/static/img/avatar.svg', NULL, '13720208750', 0, 17, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (138, '黄艳', 'FAC7C65CB4B07DFA5EDB892AD32E4F46', '黄艳', '/static/img/avatar.svg', NULL, '17786409259', 0, 17, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (139, '姜山', '18A8B7DE4071DF717FF049A8E1DB3F95', '姜山', '/static/img/avatar.svg', NULL, '18971250912', 0, 17, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (140, '崔静', 'CDBB9F922F8A4262A4603BA44D4AE992', '崔静', '/static/img/avatar.svg', NULL, '17701310163', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (141, '勾贺', '36AEF9A248194047C93EA4CA4D903C59', '勾贺', '/static/img/avatar.svg', NULL, '18811721712', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (142, '马政', 'FA2B9705A3A7BC13CA3FC72BEEF29BB2', '马政', '/static/img/avatar.svg', NULL, '15533677851', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (143, '可海鸥', 'CBC2044C928A654477976593B7D88820', '可海鸥', '/static/img/avatar.svg', NULL, '15910975274', 2, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (144, '李南楠', 'ED20E9DA2E4A3CD643A6DE6B1E2C700E', '李南楠', '/static/img/avatar.svg', NULL, '18328597183', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (145, '王雨嫣', 'E79BE8EA6DF069F5F86DFAA1EEE1E941', '王雨嫣', '/static/img/avatar.svg', NULL, '18202876506', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (146, '郭小琴', '656AE84197180EFE39D51BB8E2D02897', '郭小琴', '/static/img/avatar.svg', NULL, '18215626366', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (147, '郑超', '31D465274DD863C5B168E3A4AD74EAF2', '郑超', '/static/img/avatar.svg', NULL, '18224494207', 0, 14, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (148, '武琪', 'E89C2EC0BB54996B3BA56535AFA73B71', '武琪', '/static/img/avatar.svg', NULL, '15111825830', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (149, '江勇志', 'C4BDE73F97124BC9C768F796DAFCD785', '江勇志', '/static/img/avatar.svg', NULL, '15922701976', 0, 18, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (150, '林观凤', 'B48FD64511A628AF9AABCDF4FD31FDF6', '林观凤', '/static/img/avatar.svg', NULL, '18825125595', 0, 10, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (151, '陈凌琳', 'B171CB3C5C0638C7E8DFF1334DD83292', '陈凌琳', '/static/img/avatar.svg', NULL, '18559300671', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (152, '杨希', '113D4AAD11BAA2DF44559D4181F7E951', '杨希', '/static/img/avatar.svg', NULL, '13818722375', 0, NULL, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (153, '谢维', '61CD354796FC62FCDA5D3DD3105E8F50', '谢维', '/static/img/avatar.svg', NULL, '18948711635', 0, 11, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (154, '宋炼红', '424E091817409FB6D1A9BF0C6B82520B', '宋炼红', '/static/img/avatar.svg', NULL, '15327378577', 0, 17, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (155, '杜海莲', '0FAC0059185EEAFB0B6B7B07BD37BCDB', '杜海莲', '/static/img/avatar.svg', NULL, '13128225488', 0, 1, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (156, '周莹', '00E4E07E812EA049AAA5369EF160E218', '周莹', '/static/img/avatar.svg', NULL, '13570390640', 0, 1, 0, 1, NULL, NULL, NULL, '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (157, 'haha', '192298A586875DD11F885B0313C968D5', '', '/static/img/avatar.svg', '', '15900000001', 0, NULL, 0, 1, NULL, '2017-02-17 10:24:05', '2017-02-16 11:41:11', '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (158, 'erin', '14A5A0DC6A6B633AC8AB1A07CAE31FE5', '王丹君', '/static/img/avatar.svg', '12@1.com', '15920506789', 0, 1, 0, 1, NULL, '2017-03-10 14:46:19', '2017-03-10 14:46:05', '2017-05-16 09:26:16');
INSERT INTO `t_user` VALUES (159, '测试', '', NULL, '/static/img/avatar.svg', '222@11.com', '12312332123', 0, 19, 0, 1, NULL, NULL, '2017-05-21 16:15:11', '2017-06-02 00:39:57');
INSERT INTO `t_user` VALUES (160, 'nennen', 'D3F6589F0FBE066D196093782338D292', NULL, '/static/img/avatar.svg', 'nennen@renren.com', '18988754212', 0, 19, 0, 1, '1989-01-01 00:00:00', NULL, '2017-06-01 20:55:43', '2017-06-02 00:13:01');

-- ----------------------------
-- Table structure for t_user_department
-- ----------------------------
DROP TABLE IF EXISTS `t_user_department`;
CREATE TABLE `t_user_department`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_user_resource`;
CREATE TABLE `t_user_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `resource_id` int(11) NOT NULL COMMENT '资源ID',
  `resource_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源类型(类名)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 142 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_resource
-- ----------------------------
INSERT INTO `t_user_resource` VALUES (13, 12, 120, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (14, 20, 120, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (46, 1, 124, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (47, 20, 124, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (48, 22, 124, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (60, 21, 131, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (61, 25, 131, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (65, 20, 127, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (66, 1, 121, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (67, 18, 121, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (68, 22, 121, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (69, 26, 121, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (74, 22, 130, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (75, 26, 197, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (92, 28, 195, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (93, 21, 195, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (94, 18, 195, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (95, 1, 195, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (122, 1, 238, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (123, 53, 254, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (124, 53, 255, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (125, 53, 256, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (126, 53, 260, 'RoomTopic');
INSERT INTO `t_user_resource` VALUES (141, 79, 264, 'RoomTopic');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 224 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (21, 17, 1);
INSERT INTO `t_user_role` VALUES (26, 3, 2);
INSERT INTO `t_user_role` VALUES (27, 3, 3);
INSERT INTO `t_user_role` VALUES (39, 18, 1);
INSERT INTO `t_user_role` VALUES (46, 19, 4);
INSERT INTO `t_user_role` VALUES (53, 20, 4);
INSERT INTO `t_user_role` VALUES (55, 22, 4);
INSERT INTO `t_user_role` VALUES (57, 23, 4);
INSERT INTO `t_user_role` VALUES (58, 24, 4);
INSERT INTO `t_user_role` VALUES (59, 25, 4);
INSERT INTO `t_user_role` VALUES (60, 26, 4);
INSERT INTO `t_user_role` VALUES (61, 27, 7);
INSERT INTO `t_user_role` VALUES (71, 28, 13);
INSERT INTO `t_user_role` VALUES (72, 29, 13);
INSERT INTO `t_user_role` VALUES (73, 30, 13);
INSERT INTO `t_user_role` VALUES (74, 31, 13);
INSERT INTO `t_user_role` VALUES (75, 32, 13);
INSERT INTO `t_user_role` VALUES (76, 33, 13);
INSERT INTO `t_user_role` VALUES (77, 34, 13);
INSERT INTO `t_user_role` VALUES (78, 35, 13);
INSERT INTO `t_user_role` VALUES (79, 36, 13);
INSERT INTO `t_user_role` VALUES (80, 37, 13);
INSERT INTO `t_user_role` VALUES (81, 38, 13);
INSERT INTO `t_user_role` VALUES (82, 39, 13);
INSERT INTO `t_user_role` VALUES (83, 40, 13);
INSERT INTO `t_user_role` VALUES (84, 41, 13);
INSERT INTO `t_user_role` VALUES (85, 42, 13);
INSERT INTO `t_user_role` VALUES (86, 43, 13);
INSERT INTO `t_user_role` VALUES (87, 44, 13);
INSERT INTO `t_user_role` VALUES (88, 45, 13);
INSERT INTO `t_user_role` VALUES (89, 46, 13);
INSERT INTO `t_user_role` VALUES (90, 47, 13);
INSERT INTO `t_user_role` VALUES (91, 48, 13);
INSERT INTO `t_user_role` VALUES (92, 49, 13);
INSERT INTO `t_user_role` VALUES (93, 50, 13);
INSERT INTO `t_user_role` VALUES (94, 51, 13);
INSERT INTO `t_user_role` VALUES (95, 52, 13);
INSERT INTO `t_user_role` VALUES (96, 53, 13);
INSERT INTO `t_user_role` VALUES (97, 54, 13);
INSERT INTO `t_user_role` VALUES (98, 55, 13);
INSERT INTO `t_user_role` VALUES (99, 56, 13);
INSERT INTO `t_user_role` VALUES (100, 57, 13);
INSERT INTO `t_user_role` VALUES (101, 58, 13);
INSERT INTO `t_user_role` VALUES (102, 59, 13);
INSERT INTO `t_user_role` VALUES (103, 60, 13);
INSERT INTO `t_user_role` VALUES (104, 61, 13);
INSERT INTO `t_user_role` VALUES (105, 62, 13);
INSERT INTO `t_user_role` VALUES (106, 63, 13);
INSERT INTO `t_user_role` VALUES (107, 64, 13);
INSERT INTO `t_user_role` VALUES (108, 65, 13);
INSERT INTO `t_user_role` VALUES (109, 66, 13);
INSERT INTO `t_user_role` VALUES (110, 67, 13);
INSERT INTO `t_user_role` VALUES (111, 68, 13);
INSERT INTO `t_user_role` VALUES (112, 69, 13);
INSERT INTO `t_user_role` VALUES (113, 70, 13);
INSERT INTO `t_user_role` VALUES (114, 71, 13);
INSERT INTO `t_user_role` VALUES (115, 72, 13);
INSERT INTO `t_user_role` VALUES (116, 73, 13);
INSERT INTO `t_user_role` VALUES (117, 74, 13);
INSERT INTO `t_user_role` VALUES (118, 75, 13);
INSERT INTO `t_user_role` VALUES (119, 76, 13);
INSERT INTO `t_user_role` VALUES (120, 77, 13);
INSERT INTO `t_user_role` VALUES (121, 78, 13);
INSERT INTO `t_user_role` VALUES (122, 79, 13);
INSERT INTO `t_user_role` VALUES (123, 80, 13);
INSERT INTO `t_user_role` VALUES (124, 81, 13);
INSERT INTO `t_user_role` VALUES (125, 82, 14);
INSERT INTO `t_user_role` VALUES (126, 83, 14);
INSERT INTO `t_user_role` VALUES (127, 84, 14);
INSERT INTO `t_user_role` VALUES (128, 85, 14);
INSERT INTO `t_user_role` VALUES (129, 86, 14);
INSERT INTO `t_user_role` VALUES (130, 87, 14);
INSERT INTO `t_user_role` VALUES (131, 88, 14);
INSERT INTO `t_user_role` VALUES (132, 89, 14);
INSERT INTO `t_user_role` VALUES (133, 90, 14);
INSERT INTO `t_user_role` VALUES (134, 91, 14);
INSERT INTO `t_user_role` VALUES (135, 92, 14);
INSERT INTO `t_user_role` VALUES (136, 93, 14);
INSERT INTO `t_user_role` VALUES (137, 94, 14);
INSERT INTO `t_user_role` VALUES (138, 95, 14);
INSERT INTO `t_user_role` VALUES (139, 96, 14);
INSERT INTO `t_user_role` VALUES (140, 97, 14);
INSERT INTO `t_user_role` VALUES (141, 98, 14);
INSERT INTO `t_user_role` VALUES (142, 99, 14);
INSERT INTO `t_user_role` VALUES (143, 100, 14);
INSERT INTO `t_user_role` VALUES (144, 101, 14);
INSERT INTO `t_user_role` VALUES (145, 102, 14);
INSERT INTO `t_user_role` VALUES (146, 103, 14);
INSERT INTO `t_user_role` VALUES (147, 104, 14);
INSERT INTO `t_user_role` VALUES (148, 105, 14);
INSERT INTO `t_user_role` VALUES (149, 106, 14);
INSERT INTO `t_user_role` VALUES (150, 107, 14);
INSERT INTO `t_user_role` VALUES (151, 108, 14);
INSERT INTO `t_user_role` VALUES (152, 109, 14);
INSERT INTO `t_user_role` VALUES (153, 110, 14);
INSERT INTO `t_user_role` VALUES (154, 111, 14);
INSERT INTO `t_user_role` VALUES (155, 112, 14);
INSERT INTO `t_user_role` VALUES (156, 113, 14);
INSERT INTO `t_user_role` VALUES (157, 114, 14);
INSERT INTO `t_user_role` VALUES (158, 115, 14);
INSERT INTO `t_user_role` VALUES (159, 116, 14);
INSERT INTO `t_user_role` VALUES (160, 117, 14);
INSERT INTO `t_user_role` VALUES (161, 118, 11);
INSERT INTO `t_user_role` VALUES (162, 119, 11);
INSERT INTO `t_user_role` VALUES (163, 120, 11);
INSERT INTO `t_user_role` VALUES (164, 121, 11);
INSERT INTO `t_user_role` VALUES (165, 122, 11);
INSERT INTO `t_user_role` VALUES (166, 123, 11);
INSERT INTO `t_user_role` VALUES (167, 124, 11);
INSERT INTO `t_user_role` VALUES (168, 125, 11);
INSERT INTO `t_user_role` VALUES (169, 126, 11);
INSERT INTO `t_user_role` VALUES (170, 127, 11);
INSERT INTO `t_user_role` VALUES (171, 128, 11);
INSERT INTO `t_user_role` VALUES (172, 129, 11);
INSERT INTO `t_user_role` VALUES (173, 130, 11);
INSERT INTO `t_user_role` VALUES (174, 131, 11);
INSERT INTO `t_user_role` VALUES (175, 132, 11);
INSERT INTO `t_user_role` VALUES (176, 133, 11);
INSERT INTO `t_user_role` VALUES (177, 134, 11);
INSERT INTO `t_user_role` VALUES (178, 135, 11);
INSERT INTO `t_user_role` VALUES (179, 136, 11);
INSERT INTO `t_user_role` VALUES (180, 137, 11);
INSERT INTO `t_user_role` VALUES (181, 138, 11);
INSERT INTO `t_user_role` VALUES (182, 139, 11);
INSERT INTO `t_user_role` VALUES (183, 140, 12);
INSERT INTO `t_user_role` VALUES (184, 141, 12);
INSERT INTO `t_user_role` VALUES (185, 142, 12);
INSERT INTO `t_user_role` VALUES (186, 143, 12);
INSERT INTO `t_user_role` VALUES (187, 144, 12);
INSERT INTO `t_user_role` VALUES (188, 145, 12);
INSERT INTO `t_user_role` VALUES (189, 146, 12);
INSERT INTO `t_user_role` VALUES (190, 147, 12);
INSERT INTO `t_user_role` VALUES (191, 148, 12);
INSERT INTO `t_user_role` VALUES (192, 149, 12);
INSERT INTO `t_user_role` VALUES (193, 150, 12);
INSERT INTO `t_user_role` VALUES (194, 151, 12);
INSERT INTO `t_user_role` VALUES (195, 152, 12);
INSERT INTO `t_user_role` VALUES (196, 153, 12);
INSERT INTO `t_user_role` VALUES (197, 154, 12);
INSERT INTO `t_user_role` VALUES (198, 155, 15);
INSERT INTO `t_user_role` VALUES (199, 156, 15);
INSERT INTO `t_user_role` VALUES (200, 158, 1);
INSERT INTO `t_user_role` VALUES (220, 21, 4);
INSERT INTO `t_user_role` VALUES (221, 21, 3);
INSERT INTO `t_user_role` VALUES (222, 21, 2);
INSERT INTO `t_user_role` VALUES (223, 21, 1);

SET FOREIGN_KEY_CHECKS = 1;
