/*
 Navicat Premium Data Transfer

 Source Server         : PGCrab
 Source Server Type    : PostgreSQL
 Source Server Version : 110000
 Source Host           : 47.104.25.225:5432
 Source Catalog        : crab
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 110000
 File Encoding         : 65001

 Date: 15/03/2019 22:16:54
*/


-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS "public"."article";
CREATE TABLE "public"."article" (
  "id" int8 NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "discuss" int8 NOT NULL DEFAULT 0,
  "praise" int8 NOT NULL DEFAULT 0,
  "deleted" int2 NOT NULL DEFAULT 0,
  "operator" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(0) NOT NULL
)
;
ALTER TABLE "public"."article" OWNER TO "postgres";
COMMENT ON COLUMN "public"."article"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."article"."name" IS '名称';
COMMENT ON COLUMN "public"."article"."content" IS '内容';
COMMENT ON COLUMN "public"."article"."discuss" IS '评';
COMMENT ON COLUMN "public"."article"."praise" IS '赞';
COMMENT ON COLUMN "public"."article"."deleted" IS '删除 0、否 1、是';
COMMENT ON COLUMN "public"."article"."operator" IS '操作人';
COMMENT ON COLUMN "public"."article"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."article" IS '文章表';

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS "public"."link";
CREATE TABLE "public"."link" (
  "id" int8 NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "summary" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted" int8 NOT NULL DEFAULT 0,
  "operator" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(0) NOT NULL
)
;
ALTER TABLE "public"."link" OWNER TO "postgres";
COMMENT ON COLUMN "public"."link"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."link"."name" IS '名称';
COMMENT ON COLUMN "public"."link"."url" IS '链接地址';
COMMENT ON COLUMN "public"."link"."summary" IS '摘要';
COMMENT ON COLUMN "public"."link"."deleted" IS '删除 0、否 1、是';
COMMENT ON COLUMN "public"."link"."operator" IS '操作人';
COMMENT ON COLUMN "public"."link"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."link" IS '友情链接表';

-- ----------------------------
-- Records of link
-- ----------------------------
BEGIN;
INSERT INTO "public"."link" VALUES (0, 'baidu', 'www.baidu.com', 'baidu', 0, 'admin', '2019-02-08 22:24:30');
INSERT INTO "public"."link" VALUES (1, 'google', 'www.google.com', 'google', 0, 'admin', '2019-02-08 22:24:51');
INSERT INTO "public"."link" VALUES (2, 'oschina', 'www.oschina.net', 'oschina', 0, 'admin', '2019-02-08 22:25:16');
INSERT INTO "public"."link" VALUES (3, '163', 'www.163.com', '163', 0, 'admin', '2019-02-08 22:25:39');
INSERT INTO "public"."link" VALUES (4, 'mp', 'mp.baomidou.com', 'mp', 0, 'admin', '2019-02-08 22:25:57');
INSERT INTO "public"."link" VALUES (5, 'qq', 'www.qq.com', 'qq', 0, 'admin', '2019-02-08 22:26:17');
COMMIT;

-- ----------------------------
-- Table structure for sys_company
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_company";
CREATE TABLE "public"."sys_company" (
  "id" int8 NOT NULL,
  "pid" int8 NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "initial" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2 NOT NULL DEFAULT 0,
  "sort" int4,
  "deleted" int2 NOT NULL DEFAULT 0,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "operator" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL
)
;
ALTER TABLE "public"."sys_company" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_company"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."sys_company"."pid" IS '父 ID';
COMMENT ON COLUMN "public"."sys_company"."name" IS '名称';
COMMENT ON COLUMN "public"."sys_company"."initial" IS '首字母';
COMMENT ON COLUMN "public"."sys_company"."status" IS '状态 -1、禁用 0、正常';
COMMENT ON COLUMN "public"."sys_company"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_company"."deleted" IS '删除 0、否 1、是';
COMMENT ON COLUMN "public"."sys_company"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_company"."operator" IS '操作人';
COMMENT ON COLUMN "public"."sys_company"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."sys_company" IS '系统公司表';

-- ----------------------------
-- Records of sys_company
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_company" VALUES (2, 1, '青岛分公司', 'qdfgs', 0, NULL, 0, '分公司', 'admin', '2018-11-07 23:22:40');
INSERT INTO "public"."sys_company" VALUES (3, 1, '北京分公司', 'bjfgs', 0, 1, 0, '分公司', 'admin', '2018-11-07 23:22:42');
INSERT INTO "public"."sys_company" VALUES (1, 0, '大唐集团', 'dtjt', 0, 0, 0, '集团公司', 'admin', '2018-11-07 23:22:36');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict";
CREATE TABLE "public"."sys_dict" (
  "id" int8 NOT NULL,
  "pid" int8 NOT NULL DEFAULT 0,
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "initial" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "content" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "sys" int2 NOT NULL DEFAULT 0,
  "sort" int4,
  "status" int2 NOT NULL DEFAULT 0,
  "deleted" int2 NOT NULL DEFAULT 0,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "operator" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL
)
;
ALTER TABLE "public"."sys_dict" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_dict"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."sys_dict"."pid" IS '父 ID';
COMMENT ON COLUMN "public"."sys_dict"."name" IS '名称';
COMMENT ON COLUMN "public"."sys_dict"."initial" IS '首字母';
COMMENT ON COLUMN "public"."sys_dict"."code" IS '编码';
COMMENT ON COLUMN "public"."sys_dict"."content" IS '内容';
COMMENT ON COLUMN "public"."sys_dict"."sys" IS '系统字典 0、否 1、是';
COMMENT ON COLUMN "public"."sys_dict"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_dict"."status" IS '状态 -1、禁用 0、正常';
COMMENT ON COLUMN "public"."sys_dict"."deleted" IS '删除 0、否 1、是';
COMMENT ON COLUMN "public"."sys_dict"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_dict"."operator" IS '操作人';
COMMENT ON COLUMN "public"."sys_dict"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."sys_dict" IS '系统字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_dict" VALUES (1075772159534788610, 0, '岗位分类', 'gwfl', 'post-type', '岗位分类', 1, NULL, 0, 0, '系统岗位分类', 'admin', '2018-12-20 23:17:24.385');
INSERT INTO "public"."sys_dict" VALUES (1075772555066044418, 1075772159534788610, '普通员工', 'ptyg', '0', '普通员工', 1, NULL, 0, 0, '普通员工', 'admin', '2018-12-20 23:18:58.681');
INSERT INTO "public"."sys_dict" VALUES (1060171572177006593, 1059827204995907586, '1111', '1111', '1111', '11', 0, NULL, 0, 0, '1111as', 'admin', '2018-11-07 22:06:15');
INSERT INTO "public"."sys_dict" VALUES (1075769322138443777, 1059827204995907586, '22', '22', '222', '222', 0, NULL, 0, 0, '222', 'admin', '2018-12-20 23:06:07.903');
INSERT INTO "public"."sys_dict" VALUES (1075758761875017730, 1059827204995907586, '000', '000', '1111', '1112', 0, NULL, 0, 0, '1aaa', 'admin', '2018-12-20 22:24:10.123');
INSERT INTO "public"."sys_dict" VALUES (1075758086751457282, 1059827204995907586, 'sss', 'sss', 'sss', 'sss', 0, NULL, 0, 0, 'sss11', 'admin', '2018-12-20 22:21:29.193');
INSERT INTO "public"."sys_dict" VALUES (1059827204995907586, 0, '字典参数', 'zdss,zdcs', '12112', '11111', 0, NULL, 0, 0, '000000', 'admin', '2018-11-06 23:17:51');
INSERT INTO "public"."sys_dict" VALUES (1075036872877785090, 0, '333', '333', '333', '331', 1, NULL, -1, 0, '333', 'admin', '2018-12-18 22:35:38.375');
INSERT INTO "public"."sys_dict" VALUES (1075759345839575042, 1075036872877785090, 'sss', 'sss', 'sss', 'sss', 1, NULL, -1, 0, 'sss', 'admin', '2018-12-20 22:26:29.355');
INSERT INTO "public"."sys_dict" VALUES (1075759057128853505, 1075036872877785090, 'service', 'service', 'sss', 'sss', 1, NULL, 0, 0, 'sss11', 'admin', '2018-12-20 22:25:20.514');
INSERT INTO "public"."sys_dict" VALUES (1076494373452193794, 1075772159534788610, '高管', 'gg', '1', '高管', 1, NULL, 0, 0, '公司高管', 'admin', '2018-12-22 23:07:13.598');
INSERT INTO "public"."sys_dict" VALUES (1076494504717131778, 1075772159534788610, '临时工', 'lsg', '2', '兼职', 1, NULL, 0, 0, '编外勤杂工', 'admin', '2018-12-22 23:07:44.885');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_log";
CREATE TABLE "public"."sys_log" (
  "id" int8 NOT NULL,
  "user_id" int8,
  "username" varchar(30) COLLATE "pg_catalog"."default",
  "uri" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ip" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "params" text COLLATE "pg_catalog"."default" NOT NULL,
  "remark" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL
)
;
ALTER TABLE "public"."sys_log" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_log"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_log"."user_id" IS '用户 ID';
COMMENT ON COLUMN "public"."sys_log"."username" IS '用户名';
COMMENT ON COLUMN "public"."sys_log"."uri" IS '请求URI';
COMMENT ON COLUMN "public"."sys_log"."ip" IS 'IP 地址';
COMMENT ON COLUMN "public"."sys_log"."params" IS '请求参数';
COMMENT ON COLUMN "public"."sys_log"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_log"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."sys_log" IS '系统日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_log" VALUES (1048943164042383361, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123456"],"code":["w653"],"_method":["POST"]}', '登录', '2018-10-07 22:28:33');
INSERT INTO "public"."sys_log" VALUES (1048943263367696385, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-07 22:28:57');
INSERT INTO "public"."sys_log" VALUES (1048943286528643073, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123456"],"code":["8q75"],"_method":["POST"]}', '登录', '2018-10-07 22:29:03');
INSERT INTO "public"."sys_log" VALUES (1048956205270237186, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-07 23:20:23');
INSERT INTO "public"."sys_log" VALUES (1048956247683039234, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123456"],"code":["d193"],"_method":["POST"]}', '登录', '2018-10-07 23:20:33');
INSERT INTO "public"."sys_log" VALUES (1049659473990389762, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123456"],"code":["c6k2"],"_method":["POST"]}', '登录', '2018-10-09 21:54:55');
INSERT INTO "public"."sys_log" VALUES (1049673296709369858, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123456"],"code":["3i17"],"_method":["POST"]}', '登录', '2018-10-09 22:49:51');
INSERT INTO "public"."sys_log" VALUES (1049685670132670465, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123456"],"code":["j14c"],"_method":["POST"]}', '登录', '2018-10-09 23:39:01');
INSERT INTO "public"."sys_log" VALUES (1050002288742219778, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123456"],"code":["h289"],"_method":["POST"]}', '登录', '2018-10-10 20:37:08');
INSERT INTO "public"."sys_log" VALUES (1050026224368443394, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-10 22:12:15');
INSERT INTO "public"."sys_log" VALUES (1050026261504811010, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["ss"],"password":["123"],"code":["z9w6"],"_method":["POST"]}', '登录', '2018-10-10 22:12:24');
INSERT INTO "public"."sys_log" VALUES (1050029782253899778, 1050005461879996417, 'ss', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-10 22:26:23');
INSERT INTO "public"."sys_log" VALUES (1050029937065660418, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["test"],"password":["123"],"code":["is3l"],"_method":["POST"]}', '登录', '2018-10-10 22:27:00');
INSERT INTO "public"."sys_log" VALUES (1050029998839369729, 1, 'test', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-10 22:27:15');
INSERT INTO "public"."sys_log" VALUES (1050030040824352770, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["x600"],"_method":["POST"]}', '登录', '2018-10-10 22:27:25');
INSERT INTO "public"."sys_log" VALUES (1050740017985712129, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["8b6a"],"_method":["POST"]}', '登录', '2018-10-12 21:28:37');
INSERT INTO "public"."sys_log" VALUES (1051094227793104898, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["57rk"],"_method":["POST"]}', '登录', '2018-10-13 20:56:07');
INSERT INTO "public"."sys_log" VALUES (1051290659899588609, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["ui4r"],"_method":["POST"]}', '登录', '2018-10-14 09:56:40');
INSERT INTO "public"."sys_log" VALUES (1051485931884986369, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-14 22:52:36');
INSERT INTO "public"."sys_log" VALUES (1051485981805592578, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["ljjb"],"_method":["POST"]}', '登录', '2018-10-14 22:52:48');
INSERT INTO "public"."sys_log" VALUES (1051833172193660929, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["04zu"],"_method":["POST"]}', '登录', '2018-10-15 21:52:25');
INSERT INTO "public"."sys_log" VALUES (1051847463261171714, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-15 22:49:12');
INSERT INTO "public"."sys_log" VALUES (1051847588737970177, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["412j"],"_method":["POST"]}', '登录', '2018-10-15 22:49:42');
INSERT INTO "public"."sys_log" VALUES (1052182774323216386, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["187r"],"_method":["POST"]}', '登录', '2018-10-16 21:01:37');
INSERT INTO "public"."sys_log" VALUES (1052184196699783169, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["o800"],"_method":["POST"]}', '登录', '2018-10-16 21:07:16');
INSERT INTO "public"."sys_log" VALUES (1053638408696725506, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["6dpz"],"_method":["POST"]}', '登录', '2018-10-20 21:25:47');
INSERT INTO "public"."sys_log" VALUES (1054000950371676162, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["uc6c"],"_method":["POST"]}', '登录', '2018-10-21 21:26:24');
INSERT INTO "public"."sys_log" VALUES (1054362977350922241, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["4b6c"],"_method":["POST"]}', '登录', '2018-10-22 21:24:58');
INSERT INTO "public"."sys_log" VALUES (1054392303656054786, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-22 23:21:29');
INSERT INTO "public"."sys_log" VALUES (1054736032149454850, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["1103"],"_method":["POST"]}', '登录', '2018-10-23 22:07:21');
INSERT INTO "public"."sys_log" VALUES (1055083701292183553, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["m253"],"_method":["POST"]}', '登录', '2018-10-24 21:08:52');
INSERT INTO "public"."sys_log" VALUES (1055106768437448706, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["3eop"],"_method":["POST"]}', '登录', '2018-10-24 22:40:31');
INSERT INTO "public"."sys_log" VALUES (1055107092283854849, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["s636"],"_method":["POST"]}', '登录', '2018-10-24 22:41:48');
INSERT INTO "public"."sys_log" VALUES (1055454754229178370, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["s909"],"_method":["POST"]}', '登录', '2018-10-25 21:43:17');
INSERT INTO "public"."sys_log" VALUES (1055467239208263682, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["nq62"],"_method":["POST"]}', '登录', '2018-10-25 22:32:54');
INSERT INTO "public"."sys_log" VALUES (1055473038496342017, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-25 22:55:57');
INSERT INTO "public"."sys_log" VALUES (1055473145132326914, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["qz8s"],"_method":["POST"]}', '登录', '2018-10-25 22:56:22');
INSERT INTO "public"."sys_log" VALUES (1055481010966474753, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["3b1b"],"_method":["POST"]}', '登录', '2018-10-25 23:27:38');
INSERT INTO "public"."sys_log" VALUES (1055805423658156033, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["32n6"],"_method":["POST"]}', '登录', '2018-10-26 20:56:44');
INSERT INTO "public"."sys_log" VALUES (1056177825823039490, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["x803"],"_method":["POST"]}', '登录', '2018-10-27 21:36:31');
INSERT INTO "public"."sys_log" VALUES (1056355172530503682, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["8h2j"],"_method":["POST"]}', '登录', '2018-10-28 09:21:14');
INSERT INTO "public"."sys_log" VALUES (1056364023858315265, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["99x8"],"_method":["POST"]}', '登录', '2018-10-28 09:56:24');
INSERT INTO "public"."sys_log" VALUES (1056556970528702465, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["wp2m"],"_method":["POST"]}', '登录', '2018-10-28 22:43:06');
INSERT INTO "public"."sys_log" VALUES (1056822919995555842, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["6fyg"],"_method":["POST"]}', '登录', '2018-10-29 16:19:54');
INSERT INTO "public"."sys_log" VALUES (1057263469462790145, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["ngk6"],"_method":["POST"]}', '登录', '2018-10-30 21:30:29');
INSERT INTO "public"."sys_log" VALUES (1057264159962669057, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-30 21:33:13');
INSERT INTO "public"."sys_log" VALUES (1057264178354692097, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["qiqz"],"_method":["POST"]}', '登录', '2018-10-30 21:33:18');
INSERT INTO "public"."sys_log" VALUES (1057264343564132353, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-30 21:33:57');
INSERT INTO "public"."sys_log" VALUES (1057264361075351554, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["8x81"],"_method":["POST"]}', '登录', '2018-10-30 21:34:01');
INSERT INTO "public"."sys_log" VALUES (1057264725392596993, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["cr9w"],"_method":["POST"]}', '登录', '2018-10-30 21:35:28');
INSERT INTO "public"."sys_log" VALUES (1057272790351323137, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["1vcv"],"_method":["POST"]}', '登录', '2018-10-30 22:07:31');
INSERT INTO "public"."sys_log" VALUES (1057294482142339074, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-30 23:33:43');
INSERT INTO "public"."sys_log" VALUES (1057294633426690049, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["5i6t"],"_method":["POST"]}', '登录', '2018-10-30 23:34:19');
INSERT INTO "public"."sys_log" VALUES (1057297551278153729, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-10-30 23:45:55');
INSERT INTO "public"."sys_log" VALUES (1057623685404471297, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["03z3"],"_method":["POST"]}', '登录', '2018-10-31 21:21:51');
INSERT INTO "public"."sys_log" VALUES (1058003333384753154, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["22m2"],"_method":["POST"]}', '登录', '2018-11-01 22:30:26');
INSERT INTO "public"."sys_log" VALUES (1058350713418248194, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["wc2k"],"_method":["POST"]}', '登录', '2018-11-02 21:30:48');
INSERT INTO "public"."sys_log" VALUES (1058370209273049089, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-11-02 22:48:16');
INSERT INTO "public"."sys_log" VALUES (1058370255842406401, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["3127"],"_method":["POST"]}', '登录', '2018-11-02 22:48:27');
INSERT INTO "public"."sys_log" VALUES (1059046327093252098, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["5824"],"_method":["POST"]}', '登录', '2018-11-04 19:34:55');
INSERT INTO "public"."sys_log" VALUES (1059073772148219905, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-11-04 21:23:59');
INSERT INTO "public"."sys_log" VALUES (1059073822932852738, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["nm5z"],"_method":["POST"]}', '登录', '2018-11-04 21:24:11');
INSERT INTO "public"."sys_log" VALUES (1059440451277492225, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["96f7"],"_method":["POST"]}', '登录', '2018-11-05 21:41:02');
INSERT INTO "public"."sys_log" VALUES (1059466480524136449, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-11-05 23:24:28');
INSERT INTO "public"."sys_log" VALUES (1059466499054571521, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["5893"],"_method":["POST"]}', '登录', '2018-11-05 23:24:32');
INSERT INTO "public"."sys_log" VALUES (1059805971180920834, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["ke36"],"_method":["POST"]}', '登录', '2018-11-06 21:53:28');
INSERT INTO "public"."sys_log" VALUES (1060163399399469057, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["2w2z"],"_method":["POST"]}', '登录', '2018-11-07 21:33:46');
INSERT INTO "public"."sys_log" VALUES (1060174681154514946, 0, 'admin', '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"code":["ypts"],"_method":["POST"]}', '登录', '2018-11-07 22:18:36');
INSERT INTO "public"."sys_log" VALUES (1060176385052766209, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-11-07 22:25:22');
INSERT INTO "public"."sys_log" VALUES (1060182607151755265, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"signal":["102400"],"code":["3p16"],"_method":["POST"]}', '登录', '2018-11-07 22:50:05');
INSERT INTO "public"."sys_log" VALUES (1060183799005179905, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-11-07 22:54:50');
INSERT INTO "public"."sys_log" VALUES (1060183964432723970, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"signal":["102400"],"code":["9839"],"_method":["POST"]}', '登录', '2018-11-07 22:55:29');
INSERT INTO "public"."sys_log" VALUES (1060184942468919298, 0, 'admin', '/sso/logout', '0:0:0:0:0:0:0:1', 'logout:{}', '退出', '2018-11-07 22:59:22');
INSERT INTO "public"."sys_log" VALUES (1060186858552815618, NULL, NULL, '/sso/login', '0:0:0:0:0:0:0:1', 'login:{"username":["admin"],"password":["123"],"signal":["102400"],"code":["r1a8"],"_method":["POST"]}', '登录', '2018-11-07 23:06:59');
COMMIT;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_org";
CREATE TABLE "public"."sys_org" (
  "id" int8 NOT NULL,
  "pid" int8 NOT NULL,
  "company_id" int8,
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "initial" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "sort" int4,
  "deleted" int2 NOT NULL DEFAULT 0,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "operator" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL
)
;
ALTER TABLE "public"."sys_org" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_org"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."sys_org"."pid" IS '父 ID';
COMMENT ON COLUMN "public"."sys_org"."company_id" IS '企业 ID';
COMMENT ON COLUMN "public"."sys_org"."name" IS '名称';
COMMENT ON COLUMN "public"."sys_org"."initial" IS '首字母';
COMMENT ON COLUMN "public"."sys_org"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_org"."deleted" IS '删除 0、否 1、是';
COMMENT ON COLUMN "public"."sys_org"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_org"."operator" IS '操作人';
COMMENT ON COLUMN "public"."sys_org"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."sys_org" IS '系统机构表';

-- ----------------------------
-- Records of sys_org
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_org" VALUES (2, 1, 2, '开发部', 'kfb', 0, 0, NULL, 'admin', '2018-12-29 22:09:52');
INSERT INTO "public"."sys_org" VALUES (3, 1, 2, '产品部', 'cpb', 0, 0, NULL, 'admin', '2018-12-29 22:10:54');
INSERT INTO "public"."sys_org" VALUES (1079038881389740034, 1, 2, '测试部', 'csb', NULL, 0, '负责公司产品测试', 'admin', '2018-12-29 23:38:11.556');
INSERT INTO "public"."sys_org" VALUES (1, 0, 2, '青岛分公司', 'qdfgs', 0, 0, '青岛分公司', 'admin', '2018-11-07 23:36:14');
INSERT INTO "public"."sys_org" VALUES (6, 0, 1, '大唐集团', 'aa', NULL, 0, '111', 'admin', '2018-12-29 23:38:51.043');
INSERT INTO "public"."sys_org" VALUES (5, 0, 3, '北京分公司', 'wxkfz', NULL, 0, '微信开发组', 'admin', '2018-12-29 22:29:05.447');
INSERT INTO "public"."sys_org" VALUES (1079048977712910337, 6, 1, '财务部', 'cwb', NULL, 0, '集团财务部', 'admin', '2018-12-30 00:18:18.708');
INSERT INTO "public"."sys_org" VALUES (1079258882206056449, 5, 3, '产品部', 'cpb', NULL, 0, '产品部', 'admin', '2018-12-30 14:12:23.847');
INSERT INTO "public"."sys_org" VALUES (1079259026011963394, 5, 3, '技术部', 'jsb,jzb', NULL, 0, '技术部', 'admin', '2018-12-30 14:12:58.124');
INSERT INTO "public"."sys_org" VALUES (1079259179733204993, 6, 1, '运营部', 'yyb', NULL, 0, '集团运营部', 'admin', '2018-12-30 14:13:34.773');
INSERT INTO "public"."sys_org" VALUES (1079259288793497602, 1079259026011963394, 3, 'CRM', 'CRM', NULL, 0, 'CRM 组', 'admin', '2018-12-30 14:14:00.777');
INSERT INTO "public"."sys_org" VALUES (1079378125933203457, 6, 1, '法律部', 'flb', NULL, 0, '集团法律部', 'admin', '2018-12-30 22:06:13.768');
INSERT INTO "public"."sys_org" VALUES (1079259363062038530, 1079259026011963394, 3, 'ERP', 'ERP', NULL, 0, 'ERP 组', 'admin', '2018-12-30 14:14:18.484');
INSERT INTO "public"."sys_org" VALUES (1079043315691917313, 3, 2, 'UE 体验', 'UEby,UEty', NULL, 0, '产品用户体验相关工作', 'admin', '2018-12-29 23:55:48.778');
INSERT INTO "public"."sys_org" VALUES (1079022624909262849, 3, 2, 'UI 组', 'UIz', NULL, 0, 'UI 组', 'admin', '2018-12-29 22:33:35.707');
INSERT INTO "public"."sys_org" VALUES (1079023617533263873, 3, 2, 'UI 设计', 'UIsj', NULL, 0, '系统 UI 设计', 'admin', '2018-12-29 22:37:32.382');
COMMIT;

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_param";
CREATE TABLE "public"."sys_param" (
  "id" int8 NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "initial" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "content" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "sys" int2 NOT NULL DEFAULT 0,
  "deleted" int2 NOT NULL DEFAULT 0,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "operator" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL
)
;
ALTER TABLE "public"."sys_param" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_param"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."sys_param"."name" IS '参数名称';
COMMENT ON COLUMN "public"."sys_param"."initial" IS '首字母';
COMMENT ON COLUMN "public"."sys_param"."code" IS '编码';
COMMENT ON COLUMN "public"."sys_param"."content" IS '内容';
COMMENT ON COLUMN "public"."sys_param"."sys" IS '系统参数 0、否 1、是';
COMMENT ON COLUMN "public"."sys_param"."deleted" IS '删除 0、否 1、是';
COMMENT ON COLUMN "public"."sys_param"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_param"."operator" IS '操作人';
COMMENT ON COLUMN "public"."sys_param"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."sys_param" IS '系统参数表';

-- ----------------------------
-- Records of sys_param
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_param" VALUES (0, '框架皮肤', 'kjpf', 'sys.index.skin', 'blue', 1, 0, '系统皮肤样式', '', '2018-10-31 23:25:47');
INSERT INTO "public"."sys_param" VALUES (1059823019088904193, '默认密码', 'mrmm', 'sys.default.password', '123456', 1, 0, '系统用户默认密码', '', '2018-11-06 23:01:13');
INSERT INTO "public"."sys_param" VALUES (1055109198495551490, '测试参数', 'csss,cscs', 'test.code', '测试', 0, 0, '测试备注', '', '2018-10-31 23:25:53');
COMMIT;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_post";
CREATE TABLE "public"."sys_post" (
  "id" int8 NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "initial" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "classify" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2 NOT NULL DEFAULT 0,
  "sort" int4,
  "deleted" int2 NOT NULL DEFAULT 0,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "operator" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL
)
;
ALTER TABLE "public"."sys_post" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_post"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."sys_post"."name" IS '名称';
COMMENT ON COLUMN "public"."sys_post"."initial" IS '首字母';
COMMENT ON COLUMN "public"."sys_post"."code" IS '编码';
COMMENT ON COLUMN "public"."sys_post"."classify" IS '分类';
COMMENT ON COLUMN "public"."sys_post"."status" IS '状态 -1、禁用 0、正常';
COMMENT ON COLUMN "public"."sys_post"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_post"."deleted" IS '删除 0、否 1、是';
COMMENT ON COLUMN "public"."sys_post"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_post"."operator" IS '操作人';
COMMENT ON COLUMN "public"."sys_post"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."sys_post" IS '系统岗位表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_post" VALUES (1074675810118615042, '1111', '1111', '1111', '1111', 0, 1, 1, '0001111', 'admin', '2018-12-17 22:40:54.323');
INSERT INTO "public"."sys_post" VALUES (0, '执行总裁', 'zxzc', 'CEO', '高管', 0, 0, 0, '企业负责人', 'admin', '2018-10-31 23:26:17');
INSERT INTO "public"."sys_post" VALUES (1, '技术总监', 'jszj', 'CTO', '高管', 0, 0, 0, '技术负责人', 'admin', '2018-10-31 23:26:20');
INSERT INTO "public"."sys_post" VALUES (1059048674070614018, 'HR', 'HR', '1000', '管理', 0, 1, 0, '公司管理', 'admin', '2018-11-04 19:44:15');
INSERT INTO "public"."sys_post" VALUES (1059469257874804738, '财务总监', 'cwzj', '1000', '管理', 0, 1, 0, '公司管理', 'admin', '2018-11-05 23:35:30');
INSERT INTO "public"."sys_post" VALUES (1057653859395264513, '员工', 'yg', '1000', '普通员工', 0, 1, 0, '普通基层工作人员', 'admin', '2018-10-31 23:26:23');
INSERT INTO "public"."sys_post" VALUES (1076507755379138561, '清洁工', 'qjg', '10001', '兼职', 0, NULL, 0, '勤杂人员', 'admin', '2018-12-23 00:00:24.1');
COMMIT;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_resource";
CREATE TABLE "public"."sys_resource" (
  "id" int8 NOT NULL,
  "pid" int8 NOT NULL,
  "name" varchar(60) COLLATE "pg_catalog"."default" NOT NULL,
  "initial" varchar(60) COLLATE "pg_catalog"."default" NOT NULL,
  "type" int2 NOT NULL DEFAULT 0,
  "code" varchar(100) COLLATE "pg_catalog"."default",
  "uri" varchar(100) COLLATE "pg_catalog"."default",
  "path" varchar(100) COLLATE "pg_catalog"."default",
  "icon" varchar(30) COLLATE "pg_catalog"."default",
  "status" int2 NOT NULL DEFAULT 0,
  "sort" int4 NOT NULL DEFAULT 0,
  "deleted" int2 NOT NULL DEFAULT 0,
  "operator" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL
)
;
ALTER TABLE "public"."sys_resource" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_resource"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."sys_resource"."pid" IS '父 ID';
COMMENT ON COLUMN "public"."sys_resource"."name" IS '名称';
COMMENT ON COLUMN "public"."sys_resource"."initial" IS '首字母';
COMMENT ON COLUMN "public"."sys_resource"."type" IS '类型 0、菜单 1、按钮';
COMMENT ON COLUMN "public"."sys_resource"."code" IS '编码';
COMMENT ON COLUMN "public"."sys_resource"."uri" IS 'URI';
COMMENT ON COLUMN "public"."sys_resource"."path" IS '文件路径';
COMMENT ON COLUMN "public"."sys_resource"."icon" IS '图标';
COMMENT ON COLUMN "public"."sys_resource"."status" IS '状态 -1、禁用 0、正常';
COMMENT ON COLUMN "public"."sys_resource"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_resource"."deleted" IS '删除 0、否 1、是';
COMMENT ON COLUMN "public"."sys_resource"."operator" IS '操作人';
COMMENT ON COLUMN "public"."sys_resource"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."sys_resource" IS '系统资源表';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_resource" VALUES (17, 8, '二级菜单', 'ejcd', 0, NULL, 'javascript:;', '', 'layui-icon-share', 0, 0, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (16, 1, '我的订单', 'wddd', 0, NULL, '#sys-order', 'sys/order.html', 'layui-icon-form', 0, 0, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (18, 17, '三级菜单', 'sjcd', 0, NULL, '#sys-three-menu', 'sys/third-menu.html', 'layui-icon-about', 0, 0, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (1087745295125831681, 0, '设置', 'sz', 0, '', '', '', '', 0, 2, 0, 'admin', '2019-01-23 00:14:22.35');
INSERT INTO "public"."sys_resource" VALUES (20, 0, '财务', 'cw', 0, '0', '', NULL, NULL, 0, 1, 0, 'admin', '2019-01-23 00:09:05');
INSERT INTO "public"."sys_resource" VALUES (10, 0, '系统', 'xt', 0, '0', '', NULL, NULL, 0, 10, 0, 'admin', '2019-01-17 23:11:25');
INSERT INTO "public"."sys_resource" VALUES (30, 0, '营销', 'yx', 0, '0', '', NULL, NULL, 0, 0, 0, 'admin', '2019-01-23 00:10:08');
INSERT INTO "public"."sys_resource" VALUES (19, 1, '商业授权', 'sysq', 0, NULL, '#sys-auth', 'sys/auth.html', 'layui-icon-auz', 0, 2, 1, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (1088466016198066177, 0, 'API 文档', 'APIwd', 0, '', '/swagger-ui.html', '', 'layui-icon-flag', 0, 0, 0, 'admin', '2019-01-24 23:58:15.656');
INSERT INTO "public"."sys_resource" VALUES (3, 10, '系统管理', 'xtgl', 0, NULL, 'javascript:;', NULL, 'layui-icon-set', 0, 3, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (1088094398196203522, 1088093207932420097, '积分管理', 'jfgl', 0, '', '#sys-role', 'sys/role.html', 'layui-icon-form', 0, 0, 0, 'admin', '2019-01-23 23:21:35.009');
INSERT INTO "public"."sys_resource" VALUES (8, 10, '系统监控', 'xtjk', 0, NULL, 'javascript:;', NULL, 'layui-icon-chart-screen', 0, 0, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (1, 10, '控制台', 'kzt', 0, '0', 'javascript:;', '', 'layui-icon-home', 0, 10, 0, 'admin', '2018-10-31 23:26:46');
INSERT INTO "public"."sys_resource" VALUES (1088094149134237698, 1088093207932420097, '佣金管理', 'yjgl', 0, '', '#sys-org', 'sys/org.html', 'layui-icon-fonts-del', 0, 0, 0, 'admin', '2019-01-23 23:20:35.625');
INSERT INTO "public"."sys_resource" VALUES (1088468597569880066, 30, '网络推广', 'wlta,wltg', 0, '', 'javascript:;', '', 'layui-icon-rate', 0, 0, 0, 'admin', '2019-01-25 00:08:31.091');
INSERT INTO "public"."sys_resource" VALUES (1088093930506141698, 1088093207932420097, '余额管理', 'yegl,tegl', 0, '', '#sys-order', 'sys/order.html', 'layui-icon-dollar', 0, 0, 0, 'admin', '2019-01-23 23:19:43.499');
INSERT INTO "public"."sys_resource" VALUES (1088093616549904386, 1088093353206333441, '网站 SEO', 'wzSEO', 0, '', '#sys-seo', 'sys/seo.html', 'layui-icon-dollar', 0, 0, 0, 'admin', '2019-01-23 23:18:28.652');
INSERT INTO "public"."sys_resource" VALUES (1088468341188853762, 1088468597569880066, 'SEO 设置', 'SEOsz', 0, '', '#sys-seo', 'sys/seo.html', 'layui-icon-dollar', 0, 0, 0, 'admin', '2019-01-25 00:07:29.962');
INSERT INTO "public"."sys_resource" VALUES (1088093207932420097, 20, '资金管理', 'zjgl', 0, '', 'javascript:;', '', 'layui-icon-face-smile', 0, 0, 0, 'admin', '2019-01-23 23:16:51.235');
INSERT INTO "public"."sys_resource" VALUES (1088093353206333441, 1087745295125831681, '系统设置', 'jtsz,xtsz', 0, '', 'javascript:;', '', 'layui-icon-set', 0, 0, 0, 'admin', '2019-01-23 23:17:25.863');
INSERT INTO "public"."sys_resource" VALUES (2, 1, '主页', 'zy', 0, NULL, '#console', 'console.html', 'layui-icon-home', 0, 3, 0, 'admin', '2018-10-31 23:26:49');
INSERT INTO "public"."sys_resource" VALUES (4, 3, '用户管理', 'yhgl', 0, NULL, '#sys-user', 'sys/user.html', 'layui-icon-username', 0, 10, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (5, 3, '角色管理', 'jsgl', 0, NULL, '#sys-role', 'sys/role.html', 'layui-icon-link', 0, 6, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (6, 3, '权限管理', 'qxgl', 0, NULL, '#sys-resource', 'sys/resource.html', 'layui-icon-note', 0, 5, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (7, 8, '访问日志', 'fwrz', 0, NULL, '#sys-log', 'sys/log.html', 'layui-icon-survey', 0, 3, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (9, 8, '服务器监控', 'fwqjk', 0, NULL, '#sys-server-monitor', 'sys/server-monitor.html', 'layui-icon-chart-screen', 0, 0, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (11, 3, '机构管理', 'jggl', 0, NULL, '#sys-org', 'sys/org.html', 'layui-icon-flag', 0, 8, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (12, 3, '参数设置', 'cssz', 0, NULL, '#sys-param', 'sys/param.html', 'layui-icon-util', 0, 3, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (13, 3, '字典管理', 'zdgl', 0, NULL, '#sys-dict', 'sys/dict.html', 'layui-icon-table', 0, 4, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (14, 3, '公司管理', 'gsgl', 0, NULL, '#sys-company', 'sys/company.html', 'layui-icon-rate', 0, 7, 0, 'admin', '2018-10-31 23:26:52');
INSERT INTO "public"."sys_resource" VALUES (15, 3, '岗位管理', 'gwgl', 0, NULL, '#sys-post', 'sys/post.html', 'layui-icon-user', 0, 9, 0, 'admin', '2018-10-31 23:26:52');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role" (
  "id" int8 NOT NULL,
  "name" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "initial" varchar(30) COLLATE "pg_catalog"."default",
  "status" int2 NOT NULL,
  "sort" int4 NOT NULL,
  "deleted" int2 NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "operator" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL
)
;
ALTER TABLE "public"."sys_role" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_role"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."sys_role"."name" IS '名称';
COMMENT ON COLUMN "public"."sys_role"."initial" IS '首字母';
COMMENT ON COLUMN "public"."sys_role"."status" IS '状态 -1、禁用 0、正常';
COMMENT ON COLUMN "public"."sys_role"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_role"."deleted" IS '删除 0、否 1、是';
COMMENT ON COLUMN "public"."sys_role"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_role"."operator" IS '操作人';
COMMENT ON COLUMN "public"."sys_role"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."sys_role" IS '系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_role" VALUES (1, '普通会员', 'pthy', 0, 0, 0, NULL, 'admin', '2018-10-31 23:27:41');
INSERT INTO "public"."sys_role" VALUES (3, '铂金会员', 'pjhy', 0, 0, 0, NULL, 'admin', '2018-10-31 23:27:41');
INSERT INTO "public"."sys_role" VALUES (5, 'SUPER 会员', 'SUPER hy', 0, 0, 0, NULL, 'admin', '2018-10-31 23:27:41');
INSERT INTO "public"."sys_role" VALUES (0, '管理员', 'gly', 0, 0, 0, NULL, 'admin', '2018-10-31 23:27:41');
INSERT INTO "public"."sys_role" VALUES (4, '钻石会员', 'zshy', -1, 0, 0, NULL, 'admin', '2018-10-31 23:27:41');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_resource";
CREATE TABLE "public"."sys_role_resource" (
  "id" int8 NOT NULL,
  "role_id" int8 NOT NULL,
  "resource_id" int8 NOT NULL
)
;
ALTER TABLE "public"."sys_role_resource" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_role_resource"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."sys_role_resource"."role_id" IS '角色 ID';
COMMENT ON COLUMN "public"."sys_role_resource"."resource_id" IS '资源 ID';
COMMENT ON TABLE "public"."sys_role_resource" IS '系统角色资源关联表';

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_role_resource" VALUES (1044958741588574209, 1043875844706267138, 8);
INSERT INTO "public"."sys_role_resource" VALUES (1044958741617934337, 1043875844706267138, 9);
INSERT INTO "public"."sys_role_resource" VALUES (1044958741626322945, 1043875844706267138, 1);
INSERT INTO "public"."sys_role_resource" VALUES (1044958741638905858, 1043875844706267138, 2);
INSERT INTO "public"."sys_role_resource" VALUES (1044958741647294466, 1043875844706267138, 3);
INSERT INTO "public"."sys_role_resource" VALUES (1044958741655683073, 1043875844706267138, 4);
INSERT INTO "public"."sys_role_resource" VALUES (1046402760902422529, 5, 3);
INSERT INTO "public"."sys_role_resource" VALUES (1046402760927588353, 5, 4);
INSERT INTO "public"."sys_role_resource" VALUES (1046402760935976962, 5, 5);
INSERT INTO "public"."sys_role_resource" VALUES (1046402760944365569, 5, 6);
INSERT INTO "public"."sys_role_resource" VALUES (1046402760961142786, 5, 7);
INSERT INTO "public"."sys_role_resource" VALUES (1046403677630443522, 4, 8);
INSERT INTO "public"."sys_role_resource" VALUES (1046403677663997953, 4, 9);
INSERT INTO "public"."sys_role_resource" VALUES (1046403677680775170, 4, 3);
INSERT INTO "public"."sys_role_resource" VALUES (1046403677684969473, 4, 5);
INSERT INTO "public"."sys_role_resource" VALUES (1050029661285978114, 3, 1);
INSERT INTO "public"."sys_role_resource" VALUES (1050029661319532546, 3, 2);
INSERT INTO "public"."sys_role_resource" VALUES (1050029661336309761, 3, 8);
INSERT INTO "public"."sys_role_resource" VALUES (1050029661361475586, 3, 9);
INSERT INTO "public"."sys_role_resource" VALUES (1060867062363291649, 2, 8);
INSERT INTO "public"."sys_role_resource" VALUES (1060867062401040386, 2, 7);
INSERT INTO "public"."sys_role_resource" VALUES (1060867062417817602, 2, 17);
INSERT INTO "public"."sys_role_resource" VALUES (1060867062426206209, 2, 18);
INSERT INTO "public"."sys_role_resource" VALUES (1060867062442983426, 2, 9);
INSERT INTO "public"."sys_role_resource" VALUES (1062341011617140738, 1, 1);
INSERT INTO "public"."sys_role_resource" VALUES (1062341011621335041, 1, 2);
INSERT INTO "public"."sys_role_resource" VALUES (1062341011621335042, 1, 8);
INSERT INTO "public"."sys_role_resource" VALUES (1062341011621335043, 1, 7);
INSERT INTO "public"."sys_role_resource" VALUES (1062341011621335044, 1, 17);
INSERT INTO "public"."sys_role_resource" VALUES (1062341011621335045, 1, 18);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724850229249, 0, 10);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724871200770, 0, 1);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724875395074, 0, 2);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724879589378, 0, 16);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724883783682, 0, 3);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724887977985, 0, 4);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724892172289, 0, 15);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724896366593, 0, 11);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724900560897, 0, 14);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724908949506, 0, 5);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724925726722, 0, 6);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724929921025, 0, 13);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724934115330, 0, 12);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724938309633, 0, 8);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724942503937, 0, 7);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724946698241, 0, 9);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724950892546, 0, 17);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724955086849, 0, 18);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724959281154, 0, 1087745295125831681);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724959281155, 0, 1088093353206333441);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724967669762, 0, 1088093616549904386);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724971864065, 0, 20);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724988641281, 0, 1088093207932420097);
INSERT INTO "public"."sys_role_resource" VALUES (1088468724997029889, 0, 1088093930506141698);
INSERT INTO "public"."sys_role_resource" VALUES (1088468725001224193, 0, 1088094149134237698);
INSERT INTO "public"."sys_role_resource" VALUES (1088468725005418497, 0, 1088094398196203522);
INSERT INTO "public"."sys_role_resource" VALUES (1088468725009612802, 0, 30);
INSERT INTO "public"."sys_role_resource" VALUES (1088468725013807106, 0, 1088468597569880066);
INSERT INTO "public"."sys_role_resource" VALUES (1088468725018001409, 0, 1088468341188853762);
INSERT INTO "public"."sys_role_resource" VALUES (1088468725018001410, 0, 1088466016198066177);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user";
CREATE TABLE "public"."sys_user" (
  "id" int8 NOT NULL,
  "username" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "salt" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "real_name" varchar(100) COLLATE "pg_catalog"."default",
  "initial" varchar(100) COLLATE "pg_catalog"."default",
  "nick_name" varchar(20) COLLATE "pg_catalog"."default",
  "avatar" varchar(200) COLLATE "pg_catalog"."default",
  "sex" char(1) COLLATE "pg_catalog"."default" NOT NULL,
  "phone" varchar(11) COLLATE "pg_catalog"."default",
  "phone_verified" int2 NOT NULL DEFAULT 0,
  "email" varchar(100) COLLATE "pg_catalog"."default",
  "email_verified" int2 NOT NULL DEFAULT 0,
  "status" int2 NOT NULL DEFAULT 0,
  "deleted" int2 NOT NULL DEFAULT 0,
  "operator" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL
)
;
ALTER TABLE "public"."sys_user" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_user"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."sys_user"."username" IS '账号';
COMMENT ON COLUMN "public"."sys_user"."password" IS '密码';
COMMENT ON COLUMN "public"."sys_user"."salt" IS '随机盐';
COMMENT ON COLUMN "public"."sys_user"."real_name" IS '真实名称';
COMMENT ON COLUMN "public"."sys_user"."initial" IS '首字母';
COMMENT ON COLUMN "public"."sys_user"."nick_name" IS '昵称';
COMMENT ON COLUMN "public"."sys_user"."avatar" IS '头像';
COMMENT ON COLUMN "public"."sys_user"."sex" IS '性别';
COMMENT ON COLUMN "public"."sys_user"."phone" IS '手机号';
COMMENT ON COLUMN "public"."sys_user"."phone_verified" IS '手机号是否验证 0、否 1、是';
COMMENT ON COLUMN "public"."sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "public"."sys_user"."email_verified" IS '邮箱是否验证 0、否 1、是';
COMMENT ON COLUMN "public"."sys_user"."status" IS '状态 -1、冻结 0、正常';
COMMENT ON COLUMN "public"."sys_user"."deleted" IS '删除 0、否 1、是';
COMMENT ON COLUMN "public"."sys_user"."operator" IS '操作人';
COMMENT ON COLUMN "public"."sys_user"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."sys_user" IS '系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_user" VALUES (1, 'test', 'd9ca79821352e41004361602df6466cd', 'E13ra5k3', '李四', 'ls', '测试员', NULL, '女', '13681527318', 0, NULL, 0, 0, 0, 'admin', '2018-09-18 23:21:07');
INSERT INTO "public"."sys_user" VALUES (1048230031262875650, 'abc', '9d37de73bea2ce4c57a39859db08a87e', 'W7UZy0p0', '刘一', 'ly', '测试', NULL, '男', '13681527318', 0, NULL, 0, 0, 0, 'admin', '2018-10-05 23:14:49');
INSERT INTO "public"."sys_user" VALUES (1048228915527680001, 'test1', '002f8fa8a2577c3d04fe47ebce92bb07', 'UVTFN47v', '王五', 'ww', '测试', NULL, '男', '13681527317', 0, NULL, 0, 0, 0, 'admin', '2018-10-05 23:10:23');
INSERT INTO "public"."sys_user" VALUES (0, 'admin', 'd24ebf1f3086bb2099e62437a916f394', '689348G0', '悟空', 'wk', '管理员', NULL, '男', '13681527318', 0, 'abc@abc.cn', 0, 0, 0, 'admin', '2018-09-16 23:21:55');
INSERT INTO "public"."sys_user" VALUES (1050002990151483393, 'yezi', '7c4ae8a6a0d72541e102a67d1f7cbd80', 'PCUq77N6', '叶二', 'xe,ye', '叶子', NULL, '男', '13681527318', 0, NULL, 0, -1, 0, 'admin', '2018-10-10 20:39:56');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_role";
CREATE TABLE "public"."sys_user_role" (
  "id" int8 NOT NULL,
  "user_id" int8 NOT NULL,
  "role_id" int8 NOT NULL
)
;
ALTER TABLE "public"."sys_user_role" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_user_role"."user_id" IS '用户 ID';
COMMENT ON COLUMN "public"."sys_user_role"."role_id" IS '角色 ID';
COMMENT ON TABLE "public"."sys_user_role" IS '系统用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_user_role" VALUES (1048230031434842114, 1048230031262875650, 4);
INSERT INTO "public"."sys_user_role" VALUES (1050017869335879682, 1050005461879996417, 0);
INSERT INTO "public"."sys_user_role" VALUES (1050017869352656898, 1050005461879996417, 4);
INSERT INTO "public"."sys_user_role" VALUES (1050017869361045505, 1050005461879996417, 3);
INSERT INTO "public"."sys_user_role" VALUES (1050019239694622721, 1050002990151483393, 3);
INSERT INTO "public"."sys_user_role" VALUES (1050019303385128962, 1048228915527680001, 5);
INSERT INTO "public"."sys_user_role" VALUES (1050019427775602689, 0, 0);
INSERT INTO "public"."sys_user_role" VALUES (1050029764746874882, 1, 3);
INSERT INTO "public"."sys_user_role" VALUES (1057639528565792770, 1057639417110552578, 5);
INSERT INTO "public"."sys_user_role" VALUES (1059048038142824449, 1059047772018429953, 1);
COMMIT;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS "public"."tag";
CREATE TABLE "public"."tag" (
  "id" int8 NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "icon" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "summary" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "deleted" int2 NOT NULL DEFAULT 0,
  "operator" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(0) NOT NULL
)
;
ALTER TABLE "public"."tag" OWNER TO "postgres";
COMMENT ON COLUMN "public"."tag"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."tag"."name" IS '名称';
COMMENT ON COLUMN "public"."tag"."icon" IS '图标';
COMMENT ON COLUMN "public"."tag"."summary" IS '摘要';
COMMENT ON COLUMN "public"."tag"."content" IS '内容';
COMMENT ON COLUMN "public"."tag"."deleted" IS '删除 0、否 1、是';
COMMENT ON COLUMN "public"."tag"."operator" IS '创建人 ID';
COMMENT ON COLUMN "public"."tag"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."tag" IS '标签表';

-- ----------------------------
-- Records of tag
-- ----------------------------
BEGIN;
INSERT INTO "public"."tag" VALUES (0, 'java', 'java', 'java', 'java', 0, 'admin', '2019-02-08 11:09:07');
INSERT INTO "public"."tag" VALUES (1, 'java', 'java', 'java', 'java', 0, 'admin', '2019-02-08 11:09:07');
INSERT INTO "public"."tag" VALUES (2, 'java', 'java', 'java', 'java', 0, 'admin', '2019-02-08 11:09:07');
INSERT INTO "public"."tag" VALUES (3, 'java', 'java', 'java', 'java', 0, 'admin', '2019-02-08 11:09:07');
INSERT INTO "public"."tag" VALUES (4, 'java', 'java', 'java', 'java', 0, 'admin', '2019-02-08 11:09:07');
INSERT INTO "public"."tag" VALUES (5, 'java', 'java', 'java', 'java', 0, 'admin', '2019-02-08 11:09:07');
INSERT INTO "public"."tag" VALUES (6, 'java', 'java', 'java', 'java', 0, 'admin', '2019-02-08 11:09:07');
INSERT INTO "public"."tag" VALUES (7, 'java', 'java', 'java', 'java', 0, 'admin', '2019-02-08 11:09:07');
COMMIT;

-- ----------------------------
-- Primary Key structure for table article
-- ----------------------------
ALTER TABLE "public"."article" ADD CONSTRAINT "article_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table link
-- ----------------------------
ALTER TABLE "public"."link" ADD CONSTRAINT "link_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_company
-- ----------------------------
ALTER TABLE "public"."sys_company" ADD CONSTRAINT "sys_company_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_dict
-- ----------------------------
ALTER TABLE "public"."sys_dict" ADD CONSTRAINT "sys_dict_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_log
-- ----------------------------
ALTER TABLE "public"."sys_log" ADD CONSTRAINT "sys_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_org
-- ----------------------------
ALTER TABLE "public"."sys_org" ADD CONSTRAINT "sys_org_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_param
-- ----------------------------
ALTER TABLE "public"."sys_param" ADD CONSTRAINT "sys_param_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_post
-- ----------------------------
ALTER TABLE "public"."sys_post" ADD CONSTRAINT "sys_post_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_resource
-- ----------------------------
ALTER TABLE "public"."sys_resource" ADD CONSTRAINT "sys_resource_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role_resource
-- ----------------------------
ALTER TABLE "public"."sys_role_resource" ADD CONSTRAINT "sys_role_resource_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_user_role
-- ----------------------------
CREATE INDEX "FK_sys_user_role" ON "public"."sys_user_role" USING btree (
  "user_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "FK_sys_user_role_role" ON "public"."sys_user_role" USING btree (
  "role_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_user_role
-- ----------------------------
ALTER TABLE "public"."sys_user_role" ADD CONSTRAINT "sys_user_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tag
-- ----------------------------
ALTER TABLE "public"."tag" ADD CONSTRAINT "tag_pkey" PRIMARY KEY ("id");
