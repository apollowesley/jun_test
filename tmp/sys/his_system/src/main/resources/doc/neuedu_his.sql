/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : neuedu_his

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 04/04/2020 16:41:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for his_check_apply
-- ----------------------------
DROP TABLE IF EXISTS `his_check_apply`;
CREATE TABLE `his_check_apply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `medical_id` int(11) DEFAULT NULL COMMENT '病历ID',
  `regist_id` int(11) DEFAULT NULL COMMENT '挂号ID',
  `apply_id` int(11) DEFAULT NULL COMMENT '申请检测项目ID',
  `item_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '检测项目名称',
  `is_urgent` int(1) DEFAULT NULL COMMENT '是否加急  1为加急 0为不加急',
  `num` int(3) DEFAULT NULL COMMENT '数量',
  `creation_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开立时间',
  `doctor_id` int(11) DEFAULT NULL COMMENT '开立医生ID',
  `check_oper_id` int(11) DEFAULT NULL COMMENT '检查人员ID',
  `result_oper_id` int(11) DEFAULT NULL COMMENT '结果录入人员ID',
  `check_time` datetime(0) DEFAULT NULL COMMENT '检查时间',
  `check_result` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '检查结果',
  `result_time` datetime(0) DEFAULT NULL COMMENT '结果时间',
  `state` int(11) DEFAULT NULL COMMENT '执行状态',
  `record_type` int(1) DEFAULT NULL COMMENT '记录类型 1-检查 2-检验 3-处置',
  `del_mark` int(2) DEFAULT NULL COMMENT '删除标记, 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '检查申请' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_check_detailed
-- ----------------------------
DROP TABLE IF EXISTS `his_check_detailed`;
CREATE TABLE `his_check_detailed`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `apply_id` int(11) DEFAULT NULL COMMENT '申请检查项目ID',
  `check_template_id` int(11) DEFAULT NULL COMMENT '项目/模板ID',
  `position` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '检查部位',
  `del_mark` int(2) DEFAULT NULL COMMENT '删除标记：废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '检查详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_check_item
-- ----------------------------
DROP TABLE IF EXISTS `his_check_item`;
CREATE TABLE `his_check_item`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `item_code` int(11) DEFAULT NULL COMMENT '项目编码',
  `item_type` int(1) DEFAULT NULL COMMENT '项目类型',
  `item_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目名称',
  `mnemonic_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '拼音助记码',
  `format` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '规格',
  `price` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目单价',
  `office_id` int(11) DEFAULT NULL COMMENT '执行科室ID',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `del_mark` int(2) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '检查项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_check_template
-- ----------------------------
DROP TABLE IF EXISTS `his_check_template`;
CREATE TABLE `his_check_template`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `template_name` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模板名称',
  `user_id` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医生ID',
  `scope` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用范围 全院/科室/个人',
  `record_type` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '记录类型 1-检查  2-检验 3-处置',
  `del_mark` decimal(2, 0) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '检查模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_constant_category
-- ----------------------------
DROP TABLE IF EXISTS `his_constant_category`;
CREATE TABLE `his_constant_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `constant_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '常数项编码',
  `constant_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '常数项名称',
  `constant_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '常数项类别',
  `del_mark` decimal(2, 0) DEFAULT 0 COMMENT '删除标记 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '常数类别 常数类别' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_constant_category
-- ----------------------------
INSERT INTO `his_constant_category` VALUES (1, 'KSFL', '中医科', '科室分类', 0, '', '2020-03-30 03:11:23');
INSERT INTO `his_constant_category` VALUES (2, 'KSFL', '内科', '科室分类', 0, '', '2020-03-30 03:12:12');
INSERT INTO `his_constant_category` VALUES (3, 'KSFL', '外科', '科室分类', 0, '', '2020-03-30 03:12:21');
INSERT INTO `his_constant_category` VALUES (4, 'KSFL', '骨科', '科室分类', 0, '', '2020-03-30 03:12:28');
INSERT INTO `his_constant_category` VALUES (5, 'KSLB', '医技科室', '科室类别', 0, '', '2020-03-30 11:27:59');
INSERT INTO `his_constant_category` VALUES (6, 'KSLB', '临床科室', '科室类别', 0, '', '2020-03-31 02:26:03');
INSERT INTO `his_constant_category` VALUES (7, 'KSLB', '财务科室', '科室类别', 0, '', '2020-04-02 02:12:34');
INSERT INTO `his_constant_category` VALUES (8, 'KSLB', '行政科室', '科室类别', 0, '', '2020-04-02 02:14:28');
INSERT INTO `his_constant_category` VALUES (9, 'YSZC', '主任医师', '医生职称', 0, '', '2020-04-02 02:51:14');
INSERT INTO `his_constant_category` VALUES (10, 'YSZC', '副主任医师', '医生职称', 0, '', '2020-04-02 06:06:59');
INSERT INTO `his_constant_category` VALUES (11, 'YHLB', '挂号收费员', '用户类别', 0, NULL, '2020-04-02 10:16:00');
INSERT INTO `his_constant_category` VALUES (12, 'YHLB', '门诊医生', '用户类别', 0, NULL, '2020-04-02 10:16:26');
INSERT INTO `his_constant_category` VALUES (13, 'YHLB', '医技医生', '用户类别', 0, NULL, '2020-04-02 10:16:39');
INSERT INTO `his_constant_category` VALUES (14, 'YHLB', '药房操作员', '用户类别', 0, NULL, '2020-04-02 10:16:52');
INSERT INTO `his_constant_category` VALUES (15, 'YHLB', '财务管理员', '用户类别', 0, NULL, '2020-04-02 10:17:05');
INSERT INTO `his_constant_category` VALUES (16, 'YHLB', '医院管理员', '用户类别', 0, NULL, '2020-04-02 10:17:16');
INSERT INTO `his_constant_category` VALUES (17, 'YSZC', '主治医师', '医生职称', 0, NULL, '2020-04-02 10:17:54');
INSERT INTO `his_constant_category` VALUES (18, 'YSZC', '住院医师', '医生职称', 0, NULL, '2020-04-02 10:18:02');
INSERT INTO `his_constant_category` VALUES (19, 'JSLB', '自费', '结算类别', 0, NULL, '2020-04-02 10:18:30');
INSERT INTO `his_constant_category` VALUES (20, 'JSLB', '医保', '结算类别', 0, NULL, '2020-04-02 10:18:50');
INSERT INTO `his_constant_category` VALUES (21, 'JSLB', '新农合', '结算类别', 0, NULL, '2020-04-02 10:19:01');
INSERT INTO `his_constant_category` VALUES (22, 'KSFL', '儿科', '科室分类', 0, NULL, '2020-04-02 10:38:03');
INSERT INTO `his_constant_category` VALUES (23, 'KSFL', '行政职能科室', '科室分类', 0, NULL, '2020-04-04 01:36:06');

-- ----------------------------
-- Table structure for his_disease_directory
-- ----------------------------
DROP TABLE IF EXISTS `his_disease_directory`;
CREATE TABLE `his_disease_directory`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `disease_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疾病编码 拼音助记码',
  `disease_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '疾病名称',
  `disease_icd` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '国际ICD编码',
  `disease_category_id` int(255) DEFAULT NULL COMMENT '疾病所属分类',
  `del_mark` decimal(2, 0) DEFAULT 0 COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '诊断目录管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_drugs
-- ----------------------------
DROP TABLE IF EXISTS `his_drugs`;
CREATE TABLE `his_drugs`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `drugs_code` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '药品编码 唯一',
  `drugs_name` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '药品名称',
  `drugs_format` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '药品规格',
  `drugs_dosage_Id` int(11) DEFAULT NULL COMMENT '药品剂型 针剂,片剂,胶囊',
  `drugs_type_id` int(11) DEFAULT NULL COMMENT '药品类型 西药,中草药',
  `drugs_price` double(8, 2) DEFAULT NULL COMMENT '药品单价',
  `mnemonic_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '拼音助记码',
  `drugs_unit` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '包装单位',
  `del_mark` decimal(2, 0) DEFAULT 0 COMMENT '删除标记 废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `IDU_his_drugs_drugs_code`(`drugs_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '非药品收费项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_herbal_detailed
-- ----------------------------
DROP TABLE IF EXISTS `his_herbal_detailed`;
CREATE TABLE `his_herbal_detailed`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `herbal_temp_id` int(11) DEFAULT NULL COMMENT '草药处方/模板ID',
  `herbal_id` int(11) DEFAULT NULL COMMENT '药品ID',
  `dosage` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用量',
  `unit` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '药品单位',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '草药详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_herbal_prescription
-- ----------------------------
DROP TABLE IF EXISTS `his_herbal_prescription`;
CREATE TABLE `his_herbal_prescription`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `medical_id` int(11) DEFAULT NULL COMMENT '病历ID',
  `regist_id` int(11) DEFAULT NULL COMMENT '挂号ID',
  `user_id` int(11) DEFAULT NULL COMMENT '开立医生ID',
  `prescription_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处方名称',
  `prescription_time` datetime(0) DEFAULT NULL COMMENT '开立时间',
  `prescriptio_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处方类型 水煎煮/酒泡/打粉/制丸/装胶囊等',
  `pay_number` decimal(3, 0) DEFAULT NULL COMMENT '付数',
  `frequency` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '频次',
  `drugs_usage` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用法',
  `therapy` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '治法',
  `detailed` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '治法详细',
  `advice` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医嘱',
  `state` decimal(1, 0) DEFAULT NULL COMMENT '状态',
  `del_mark` decimal(2, 0) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '草药处方' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_herbal_template
-- ----------------------------
DROP TABLE IF EXISTS `his_herbal_template`;
CREATE TABLE `his_herbal_template`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `template_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模板名称',
  `user_id` int(11) DEFAULT NULL COMMENT '医生ID',
  `prescriptio_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处方类型 水煎煮/酒泡/打粉/制丸/装胶囊等',
  `pay_number` decimal(3, 0) DEFAULT NULL COMMENT '付数',
  `frequency` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '频次',
  `drugs_usage` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用法',
  `therapy` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '治法',
  `detailed` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '治法详细',
  `advice` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医嘱',
  `scope` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用范围 全院/科室/个人',
  `del_mark` decimal(2, 0) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '草药模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_invoice
-- ----------------------------
DROP TABLE IF EXISTS `his_invoice`;
CREATE TABLE `his_invoice`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `invoice_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发票号码',
  `money` double(8, 2) DEFAULT NULL COMMENT '发票金额',
  `state` decimal(1, 0) DEFAULT NULL COMMENT '发票状态 1-正常  2-作废',
  `creation_time` datetime(0) DEFAULT NULL COMMENT '收/退费时间',
  `user_id` int(11) DEFAULT NULL COMMENT '收/退费人员ID',
  `regist_id` int(11) DEFAULT NULL COMMENT '挂号ID',
  `fee_type_id` int(11) DEFAULT NULL COMMENT '收费方式',
  `back` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '冲红发票号码',
  `invoice_state` decimal(1, 0) DEFAULT NULL COMMENT '发票状态  0-未日结  1-已提交  2-已审核',
  `del_mark` decimal(2, 0) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '发票' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_medical_disease
-- ----------------------------
DROP TABLE IF EXISTS `his_medical_disease`;
CREATE TABLE `his_medical_disease`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `medical_id` int(11) DEFAULT NULL COMMENT '病历ID',
  `regist_id` int(11) DEFAULT NULL COMMENT '挂号ID',
  `disease_id` int(11) DEFAULT NULL COMMENT '疾病ID',
  `diagnose_type` decimal(1, 0) DEFAULT NULL COMMENT '诊断类型 1-西医 2-中医',
  `get_sisk_date` datetime(0) DEFAULT NULL COMMENT '发病日期',
  `diagnose_cate` decimal(1, 0) DEFAULT NULL COMMENT '诊断种类 1-初诊 2-终诊',
  `del_mark` decimal(2, 0) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '疾病诊治' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_medical_record
-- ----------------------------
DROP TABLE IF EXISTS `his_medical_record`;
CREATE TABLE `his_medical_record`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `case_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病历号',
  `regist_id` int(11) DEFAULT NULL COMMENT '挂号ID',
  `readme` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主诉',
  `present` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '现病史',
  `present_treat` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '现病治疗情况',
  `history` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '既往史',
  `allergy` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '过敏史',
  `physique` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '体格检查',
  `proposal` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '检查建议',
  `careful` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '注意事项',
  `check_result` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '检查结果',
  `diagnosis` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '诊断结果',
  `handling` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处理意见',
  `case_state` decimal(1, 0) DEFAULT NULL COMMENT '病历状态 1-暂存 2-已提交 3-诊毕\'',
  `del_mark` decimal(2, 0) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '门诊病历首页 病历卡' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_office
-- ----------------------------
DROP TABLE IF EXISTS `his_office`;
CREATE TABLE `his_office`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `office_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '科室编码',
  `office_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '科室名称',
  `office_type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '科室分类 外科、骨科等',
  `office_category_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '科室类别 临床科室、医技科室等',
  `del_mark` decimal(2, 0) DEFAULT 0 COMMENT '删除标记 0:保留,1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '科室' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_office
-- ----------------------------
INSERT INTO `his_office` VALUES (1, 'XXGNK', '心血管内科', '2', '6', 1, NULL, NULL);
INSERT INTO `his_office` VALUES (2, 'XXGNK', '心血管内科', '2', '6', 0, NULL, '2020-04-02 07:32:12');
INSERT INTO `his_office` VALUES (3, 'SJNK', '神经内科', '2', '6', 0, NULL, '2020-04-02 07:36:19');
INSERT INTO `his_office` VALUES (4, 'XHNK', '消化内科', '2', '6', 0, NULL, '2020-04-02 10:34:03');
INSERT INTO `his_office` VALUES (5, 'HXNK', '呼吸内科', '2', '6', 0, NULL, '2020-04-02 10:34:21');
INSERT INTO `his_office` VALUES (6, 'XYNK', '血液内科', '2', '6', 0, NULL, '2020-04-02 10:35:27');
INSERT INTO `his_office` VALUES (7, 'GRNK', '感染内科', '2', '6', 0, NULL, '2020-04-02 10:36:47');
INSERT INTO `his_office` VALUES (8, 'MNWK', '泌尿外科', '3', '6', 0, NULL, '2020-04-02 10:37:02');
INSERT INTO `his_office` VALUES (9, 'SJWK', '神经外科', '3', '6', 0, NULL, '2020-04-02 10:37:16');
INSERT INTO `his_office` VALUES (10, 'XWK', '胸外科', '3', '6', 0, NULL, '2020-04-02 10:37:26');
INSERT INTO `his_office` VALUES (11, 'GDWK', '肝胆外科', '3', '6', 0, NULL, '2020-04-02 10:37:38');
INSERT INTO `his_office` VALUES (12, 'ETBJK', '儿童保健科', '22', '6', 0, NULL, '2020-04-02 10:39:17');
INSERT INTO `his_office` VALUES (13, 'XEHXK', '小儿呼吸科', '22', '6', 0, NULL, '2020-04-02 10:39:30');
INSERT INTO `his_office` VALUES (14, 'XEJZK', '小儿急诊科', '22', '6', 0, NULL, '2020-04-02 10:39:53');
INSERT INTO `his_office` VALUES (15, 'XESJNK', '小儿神经内科', '22', '6', 0, NULL, '2020-04-04 01:37:54');

-- ----------------------------
-- Table structure for his_prescription
-- ----------------------------
DROP TABLE IF EXISTS `his_prescription`;
CREATE TABLE `his_prescription`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `medical_id` int(11) DEFAULT NULL COMMENT '病历ID',
  `regist_id` int(11) DEFAULT NULL COMMENT '挂号ID',
  `user_id` int(11) DEFAULT NULL COMMENT '开立医生ID',
  `prescription_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '处方名称',
  `prescription_time` datetime(0) DEFAULT NULL COMMENT '开立时间',
  `prescription_state` decimal(1, 0) DEFAULT NULL COMMENT '处方状态',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '处方' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_prescription_detailed
-- ----------------------------
DROP TABLE IF EXISTS `his_prescription_detailed`;
CREATE TABLE `his_prescription_detailed`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `prescription_id` int(11) DEFAULT NULL COMMENT '成药处方/模板ID',
  `drugs_id` int(11) DEFAULT NULL COMMENT '药品ID',
  `drugs_Usage` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用法',
  `dosage` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用量',
  `frequency` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '频次',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '成药详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_prescription_template
-- ----------------------------
DROP TABLE IF EXISTS `his_prescription_template`;
CREATE TABLE `his_prescription_template`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `template_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模板名称',
  `user_id` int(11) DEFAULT NULL COMMENT '医生ID',
  `creation_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `scope` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '使用范围 全院/科室/个人',
  `del_mark` decimal(2, 0) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '成药模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_regist_work
-- ----------------------------
DROP TABLE IF EXISTS `his_regist_work`;
CREATE TABLE `his_regist_work`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `register_id` int(11) DEFAULT NULL COMMENT '收费人员ID',
  `start_time` datetime(0) DEFAULT NULL COMMENT '日结开始时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '日结结束时间',
  `del_mark` decimal(2, 0) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '收费员日结' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_registe_level
-- ----------------------------
DROP TABLE IF EXISTS `his_registe_level`;
CREATE TABLE `his_registe_level`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `regist_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '号别编码',
  `regist_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '号别名称',
  `regist_quota` decimal(3, 0) DEFAULT NULL COMMENT '挂号限额',
  `sequence_no` decimal(3, 0) DEFAULT NULL COMMENT '显示顺序号',
  `regist_fee` double(8, 2) DEFAULT NULL COMMENT '挂号费',
  `pay_type_id` int(11) DEFAULT NULL COMMENT '结算类别 自费、医保、新农合等',
  `del_mark` decimal(2, 0) DEFAULT 0 COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '挂号级别' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_register
-- ----------------------------
DROP TABLE IF EXISTS `his_register`;
CREATE TABLE `his_register`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `case_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '病历号 自动生成，唯一、必填',
  `rel_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '姓名 必填',
  `gender` int(11) DEFAULT NULL COMMENT '性别 必填',
  `age` decimal(3, 0) DEFAULT NULL COMMENT '年龄',
  `birthday` datetime(0) DEFAULT NULL COMMENT '出生日期 出生日期填入，年龄(周岁)自动计算',
  `pay_type_id` int(11) DEFAULT NULL COMMENT '结算类别 必填',
  `regist_level_id` int(11) DEFAULT NULL COMMENT '挂号级别 必填',
  `office_id` int(11) DEFAULT NULL COMMENT '挂号科室 必填',
  `user_id` int(11) DEFAULT NULL COMMENT '看诊医生 必填',
  `visit_date` datetime(0) DEFAULT NULL COMMENT '看诊时间',
  `noon` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '午别 上午，下午',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `home_address` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '家庭住址',
  `isbook` decimal(1, 0) DEFAULT NULL COMMENT '是否要病历本 如要单独收费 1 元',
  `amount_money` double(8, 2) DEFAULT NULL COMMENT '应收金额 由系统根据挂号的级别及看诊医生,是否要病历本,自动算出',
  `visit_state` decimal(1, 0) DEFAULT NULL COMMENT '患者状态 已挂号，未看诊，已收费，药品已发放，已检查....)',
  `regist_id` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '挂号员ID',
  `del_mark` decimal(2, 0) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '现场挂号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_rule
-- ----------------------------
DROP TABLE IF EXISTS `his_rule`;
CREATE TABLE `his_rule`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `rule_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '规则名称',
  `office_id` int(11) DEFAULT NULL COMMENT '科室',
  `user_id` int(11) DEFAULT NULL COMMENT '医生',
  `del_mark` decimal(2, 0) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '排班规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_rule_item
-- ----------------------------
DROP TABLE IF EXISTS `his_rule_item`;
CREATE TABLE `his_rule_item`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `week` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '星期',
  `noon` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '午别',
  `rule_id` int(11) DEFAULT NULL COMMENT '排班规则ID',
  `del_mark` decimal(2, 0) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '排班规则选项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_rule_scheduling
-- ----------------------------
DROP TABLE IF EXISTS `his_rule_scheduling`;
CREATE TABLE `his_rule_scheduling`  (
  `id` int(11) NOT NULL COMMENT '主键编号',
  `start_time` datetime(0) DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '结束时间',
  `his_rule_id` int(11) DEFAULT NULL COMMENT '规则ID',
  `del_mark` decimal(2, 0) DEFAULT NULL COMMENT '删除标记 废除 0:保留(默认),1:废除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '行程安排' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_user
-- ----------------------------
DROP TABLE IF EXISTS `his_user`;
CREATE TABLE `his_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `log_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登录名',
  `log_pwd` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `rel_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `by_office_id` int(11) DEFAULT NULL COMMENT '用户所在科室',
  `user_type_id` int(11) DEFAULT NULL COMMENT '用户类别 挂号收费员、门诊医生、医技医生等',
  `academic_info_id` int(11) DEFAULT NULL COMMENT '职称信息 主任医师、副主任医师等',
  `participate_arrange` decimal(2, 0) DEFAULT 0 COMMENT '是否参与排班 0:不参与,1:参与',
  `user_status` decimal(2, 0) DEFAULT 0 COMMENT '用户状态 0:启用,1：禁用',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_user
-- ----------------------------
INSERT INTO `his_user` VALUES (1, 'admin', 'admin', '管理员', 2, 16, -1, 0, 1, '', '2020-04-03 18:17:16');
INSERT INTO `his_user` VALUES (2, 'zhangsan', '123456', '张三', 2, 12, 9, 1, 0, '', '2020-04-03 18:17:20');
INSERT INTO `his_user` VALUES (4, 'zhangsan1', '123456', '张三1', 2, 11, -1, 0, 1, NULL, '2020-04-03 18:17:31');
INSERT INTO `his_user` VALUES (8, 'zhangsan2', '123456', '张三2', 3, 12, 9, 0, 0, NULL, '2020-04-03 18:17:34');
INSERT INTO `his_user` VALUES (9, 'zhangsan3', '123456', '张三3', 2, 11, -1, 0, 0, NULL, '2020-04-03 18:17:39');
INSERT INTO `his_user` VALUES (10, 'zhangsan4', '123456', '张三4', 2, 11, -1, 0, 0, NULL, '2020-04-03 18:17:41');
INSERT INTO `his_user` VALUES (12, 'guanliyuan2', '123456', '管理员2', 2, 11, -1, 0, 0, NULL, '2020-04-04 07:37:47');
INSERT INTO `his_user` VALUES (13, 'lisi', '123456', '李四', 3, 11, NULL, 0, 0, NULL, '2020-04-04 08:08:29');
INSERT INTO `his_user` VALUES (14, 'lisi2', '123456', '李四2', 2, 12, 10, 0, 0, NULL, '2020-04-04 08:12:52');
INSERT INTO `his_user` VALUES (15, 'wangwu', '123456', '王五', 2, 12, 17, 0, 0, NULL, '2020-04-04 08:19:17');
INSERT INTO `his_user` VALUES (16, 'wangwu2', '123456', '王五2', 2, 11, 10, 1, 0, NULL, '2020-04-04 08:21:29');

SET FOREIGN_KEY_CHECKS = 1;
