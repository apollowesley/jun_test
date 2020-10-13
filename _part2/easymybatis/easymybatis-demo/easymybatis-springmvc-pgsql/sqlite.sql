/*
Navicat PGSQL Data Transfer

Source Server         : localhost
Source Server Version : 90606
Source Host           : localhost:5432
Source Database       : stu
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90606
File Encoding         : 65001

Date: 2017-12-25 16:36:49
*/


-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_user";
CREATE TABLE "public"."t_user" (
"id" int8 DEFAULT nextval('t_user_id_seq'::regclass) NOT NULL,
"username" varchar(255) COLLATE "default",
"state" int2,
"isdel" int2,
"remark" text COLLATE "default",
"add_time" timestamp(6),
"money" numeric(10,2),
"left_money" float4
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."t_user" IS '用户表';
COMMENT ON COLUMN "public"."t_user"."id" IS 'ID';
COMMENT ON COLUMN "public"."t_user"."username" IS '用户名';
COMMENT ON COLUMN "public"."t_user"."state" IS '状态';
COMMENT ON COLUMN "public"."t_user"."isdel" IS '是否删除';
COMMENT ON COLUMN "public"."t_user"."remark" IS '备注';
COMMENT ON COLUMN "public"."t_user"."add_time" IS '添加时间';
COMMENT ON COLUMN "public"."t_user"."money" IS '金额';
COMMENT ON COLUMN "public"."t_user"."left_money" IS '剩下的钱';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO "public"."t_user" VALUES ('1', '王五', '1', '0', '批量修改备注', '2017-02-21 10:37:44', '101.10', '22.1');
INSERT INTO "public"."t_user" VALUES ('2', '张三', '1', '0', '批量修改备注', '2017-02-21 10:40:11', '100.50', '22.1');
INSERT INTO "public"."t_user" VALUES ('3', '王五', '2', '0', '备注', '2017-02-21 10:40:11', '100.70', '22.1');
INSERT INTO "public"."t_user" VALUES ('4', '张三', '1', '0', '批量修改备注', '2017-02-21 10:40:11', '100.50', '22.1');
INSERT INTO "public"."t_user" VALUES ('5', '张三', '1', '0', '批量修改备注', '2017-02-21 10:40:11', '100.50', '22.1');
INSERT INTO "public"."t_user" VALUES ('6', '张三', '1', '0', '批量修改备注', '2017-02-21 10:40:11', '100.50', '22.1');
-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table t_user
-- ----------------------------
ALTER TABLE "public"."t_user" ADD PRIMARY KEY ("id");

CREATE SEQUENCE t_user_id_seq
    START WITH 7
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
alter table t_user alter column id set default nextval('t_user_id_seq');



-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_info";
CREATE TABLE "public"."user_info" (
"id" int4 NOT NULL,
"user_id" int4 NOT NULL,
"city" varchar(50) COLLATE "default",
"address" varchar(100) COLLATE "default",
"status" varchar(4) COLLATE "default",
"create_time" timestamp(6) NOT NULL
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."user_info" IS '用户信息表';
COMMENT ON COLUMN "public"."user_info"."id" IS '自增主键';
COMMENT ON COLUMN "public"."user_info"."user_id" IS 't_user外键';
COMMENT ON COLUMN "public"."user_info"."city" IS '城市';
COMMENT ON COLUMN "public"."user_info"."address" IS '街道';
COMMENT ON COLUMN "public"."user_info"."status" IS '类型';
COMMENT ON COLUMN "public"."user_info"."create_time" IS '添加时间';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO "public"."user_info" VALUES ('1', '1', '杭州', '延安路', '0', '2017-12-19 11:18:45');
INSERT INTO "public"."user_info" VALUES ('2', '2', '杭州', '延安路', '0', '2017-12-19 11:18:45');
INSERT INTO "public"."user_info" VALUES ('3', '3', '杭州', '延安路', '0', '2017-12-19 11:18:45');
INSERT INTO "public"."user_info" VALUES ('4', '4', '杭州', '延安路', '0', '2017-12-19 11:18:45');
INSERT INTO "public"."user_info" VALUES ('5', '5', '杭州', '延安路', '0', '2017-12-19 11:18:45');
INSERT INTO "public"."user_info" VALUES ('6', '6', '杭州', '延安路', '0', '2017-12-19 11:18:45');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table user_info
-- ----------------------------
ALTER TABLE "public"."user_info" ADD PRIMARY KEY ("id");