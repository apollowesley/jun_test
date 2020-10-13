/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : localhost:3306
 Source Schema         : crm_system

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 30/12/2019 17:16:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for client_service
-- ----------------------------
DROP TABLE IF EXISTS `client_service`;
CREATE TABLE `client_service`  (
  `service_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '服务编号',
  `service_type` varchar(26) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '服务类型',
  `outline` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '概要',
  `customer` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户',
  `state` int(2) DEFAULT NULL COMMENT '状态',
  `service_ask` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '服务请求',
  `creater` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `appoint_id` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分配给',
  `appoint_time` timestamp NOT NULL  COMMENT '分配时间',
  `service_deal` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '服务处理',
  `handler` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处理人',
  `handling_time` timestamp NOT NULL  COMMENT '处理时间',
  `handling_result` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处理结果',
  `satisfaction` int(2) DEFAULT NULL COMMENT '满意度',
  `customer_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户ID',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operate_time` timestamp NOT NULL  COMMENT '操作时间',
  PRIMARY KEY (`service_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '客户服务' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for customer_information
-- ----------------------------
DROP TABLE IF EXISTS `customer_information`;
CREATE TABLE `customer_information`  (
  `customer_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '客户编号',
  `customer_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户名称',
  `customer_manager` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户经理',
  `customer_grade` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户等级',
  `customer_satisfaction` int(2) DEFAULT NULL COMMENT '客户满意度',
  `satisfaction_credit_line` int(2) DEFAULT NULL COMMENT '客户信用度',
  `address` varchar(350) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `postcode` int(6) DEFAULT NULL COMMENT '邮编',
  `telephone` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `fax` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '传真',
  `url` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '网址',
  `business_license` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '营销执照注册号',
  `legal_person` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '法人',
  `registered_capital` float(10, 2) DEFAULT NULL COMMENT '注册资金/万元',
  `year_turnover` int(10) DEFAULT NULL COMMENT '年营业额',
  `bank_deposit` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '银行账号',
  `land_tax_register_number` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '地税登记号',
  `national_tax_register_number` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '国税登记号',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '客户信息' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for contact_record
-- ----------------------------
DROP TABLE IF EXISTS `contact_record`;
CREATE TABLE `contact_record`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `contact_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `contact_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '地点',
  `contact` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '概要',
  `detailed_information` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '详细信息',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `customer_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '交往客户ID',
  `operate_time` timestamp NOT NULL  COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_contactord_custome_id49F3`(`customer_id`) USING BTREE,
  CONSTRAINT `FK_contactord_custome_id49F3` FOREIGN KEY (`customer_id`) REFERENCES `customer_information` (`customer_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '交往记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for customer_contact
-- ----------------------------
DROP TABLE IF EXISTS `customer_contact`;
CREATE TABLE `customer_contact`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `sex` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `job` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '职位',
  `office_phone` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '办公电话',
  `mobile_phone` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `customer_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户信息ID',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_customeact_custome_id0269`(`customer_id`) USING BTREE,
  CONSTRAINT `FK_customeact_custome_id0269` FOREIGN KEY (`customer_id`) REFERENCES `customer_information` (`customer_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '客户联系人' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for customer_development_plan
-- ----------------------------
DROP TABLE IF EXISTS `customer_development_plan`;
CREATE TABLE `customer_development_plan`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `plan_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '计划日期',
  `plan_item` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '计划项',
  `execution_result` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行效果',
  `market_opportunity_id` int(11) DEFAULT NULL COMMENT '销售机会编号',
  `operate_time` timestamp NOT NULL  COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '客户开发计划' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for customer_loss_warn_record
-- ----------------------------
DROP TABLE IF EXISTS `customer_loss_warn_record`;
CREATE TABLE `customer_loss_warn_record`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `customer_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户ID',
  `customer_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户名称',
  `customer_mgr` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户经理',
  `previous_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次下单时间',
  `confirm_time` timestamp NOT NULL  COMMENT '确认流失时间',
  `state` decimal(2, 0) DEFAULT NULL COMMENT '状态',
  `reprieve` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '暂缓措施',
  `append_reprieve` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '追加暂缓措施',
  `loss_result` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '流失原因',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operate_time` timestamp NOT NULL  COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_customeord_custome_id9BF4`(`customer_id`) USING BTREE,
  CONSTRAINT `FK_customeord_custome_id9BF4` FOREIGN KEY (`customer_id`) REFERENCES `customer_information` (`customer_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '客户流失预警记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `data_dictionary`;
CREATE TABLE `data_dictionary`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `type` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '类别',
  `item` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条目',
  `value` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '值',
  `isEdit` int(1) DEFAULT NULL COMMENT '是否可编辑',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '数据字典' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for historical_order
-- ----------------------------
DROP TABLE IF EXISTS `historical_order`;
CREATE TABLE `historical_order`  (
  `order_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '日期',
  `delivery_addr` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '送货地址',
  `state` int(2) DEFAULT NULL COMMENT '状态',
  `customer_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '送货客户ID',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operate_time` timestamp NOT NULL  COMMENT '操作时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `FK_historider_custome_id8203`(`customer_id`) USING BTREE,
  CONSTRAINT `FK_historider_custome_id8203` FOREIGN KEY (`customer_id`) REFERENCES `customer_information` (`customer_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '历史订单' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for market_opportunity
-- ----------------------------
DROP TABLE IF EXISTS `market_opportunity`;
CREATE TABLE `market_opportunity`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `customer_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户名称',
  `opportunity_source` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '机会来源',
  `probability_success` int(2) DEFAULT NULL COMMENT '成功机率',
  `summary` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '概要',
  `linkman` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `link_phone` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `opportunit_describe` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '机会描述',
  `creater` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `appoint_id` int(11) DEFAULT NULL COMMENT '指派人',
  `appoint_time` timestamp NOT NULL  COMMENT '指派时间',
  `state` int(4) DEFAULT NULL COMMENT '状态 0未指派,1已指派,2 已制定计划项,3 执行中,4开发成功,5开发失败',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '营销机会' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of market_opportunity
-- ----------------------------
INSERT INTO `market_opportunity` VALUES (0, 'string', 'string', 0, 'string', 'string', 'string', 'string', 'string', '2019-12-19 23:39:29', 2, '2019-12-19 23:39:29', 0, 'string');
INSERT INTO `market_opportunity` VALUES (11, '11', '11', 11, '11', '11', '11', '1111', 'admin', '2019-12-13 21:57:37', NULL, '2019-12-13 21:57:37', NULL, NULL);
INSERT INTO `market_opportunity` VALUES (12, '11', '11', 11, '11', '11', '11', '1111', 'admin', '2019-12-13 21:57:37', NULL, '2019-12-13 21:57:37', NULL, NULL);
INSERT INTO `market_opportunity` VALUES (22, '222', '222', 22, '22222', '222', '2222', '2222', 'admin', '2019-12-13 22:13:57', NULL, '2019-12-13 22:13:57', NULL, NULL);
INSERT INTO `market_opportunity` VALUES (111, '111', '111', 11, '1111', '111', '1111111', '1111', 'admin', '2019-12-14 17:18:12', NULL, '2019-12-14 17:18:09', NULL, NULL);
INSERT INTO `market_opportunity` VALUES (122, '121', '111', 90, '1111', '111', '111111', '111', 'admin', '2019-12-14 17:39:48', NULL, '2019-12-11 17:39:46', NULL, NULL);
INSERT INTO `market_opportunity` VALUES (1111, '1111', '11111', 11, '1111', '1111', '1111111111', '1111', 'admin', '2019-12-14 17:39:53', NULL, '2019-12-31 17:39:50', NULL, NULL);

-- ----------------------------
-- Table structure for order_product
-- ----------------------------
DROP TABLE IF EXISTS `order_product`;
CREATE TABLE `order_product`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `order_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `product_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '产品号',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_order_puct_order_id63FA`(`order_id`) USING BTREE,
  CONSTRAINT `FK_order_puct_order_id63FA` FOREIGN KEY (`order_id`) REFERENCES `historical_order` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单-产品关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `product_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '产品名称',
  `model_number` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '型号',
  `batch_number` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '等级/批次',
  `units` varchar(6) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '单位',
  `price` float(10, 2) DEFAULT NULL COMMENT '单价/元',
  `warehouse` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '仓库',
  `goods_position` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '货位',
  `unit_number` int(5) DEFAULT NULL COMMENT '件数',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `operate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '产品表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `authority_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '权限名称',
  `authority_url` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '权限地址',
  `icon` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级模块',
  `sort_num` int(3) DEFAULT NULL COMMENT '排序号',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '权限' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `role_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '系统管理员', NULL, '2019-12-13 19:00:11');
INSERT INTO `sys_role` VALUES (2, '销售主管', NULL, '2019-12-13 19:00:17');
INSERT INTO `sys_role` VALUES (3, '客户经理', NULL, '2019-12-13 19:00:38');
INSERT INTO `sys_role` VALUES (4, '高管', NULL, '2019-12-13 19:00:43');

-- ----------------------------
-- Table structure for sys_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authority`;
CREATE TABLE `sys_role_authority`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `authority_id` int(11) DEFAULT NULL COMMENT '权限ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色-权限关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `real_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `login_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登录名',
  `login_password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `gender` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '刘颖', 'admin', '$2a$10$NS1ASsVXX7oYlD1ILwK2O.DkkGzwd7iK6i9Vuy64A/AMggS20oA8y', '0', NULL, '2019-12-27 10:52:01');
INSERT INTO `sys_user` VALUES (2, '码云', 'mayun', '$2a$10$K20LIjdBlKvDMetI/QK9je3P8dlZduvvkd21JGdTNHA9ooCsbuPEq', '0', NULL, '2019-12-27 10:52:30');
INSERT INTO `sys_user` VALUES (3, '马化腾', 'mahuateng', '$2a$10$K20LIjdBlKvDMetI/QK9je3P8dlZduvvkd21JGdTNHA9ooCsbuPEq', '0', NULL, '2019-12-27 10:52:32');
INSERT INTO `sys_user` VALUES (4, '高圆圆', 'gyy', '$2a$10$K20LIjdBlKvDMetI/QK9je3P8dlZduvvkd21JGdTNHA9ooCsbuPEq', '1', NULL, '2019-12-27 10:52:36');
INSERT INTO `sys_user` VALUES (5, NULL, NULL, NULL, NULL, NULL, '2019-12-14 15:13:49');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户角色关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, NULL, '2019-12-14 15:14:20');
INSERT INTO `sys_user_role` VALUES (2, 2, 2, NULL, '2019-12-14 15:14:29');
INSERT INTO `sys_user_role` VALUES (3, 3, 2, NULL, '2019-12-14 15:14:32');
INSERT INTO `sys_user_role` VALUES (4, 4, 2, NULL, '2019-12-14 15:14:39');

SET FOREIGN_KEY_CHECKS = 1;
