CREATE TABLE `biz_dept` (
  `DEPT_ID` varchar(48) NOT NULL COMMENT '主键',
  `DEPT_NAME` varchar(50) NOT NULL COMMENT '部门名',
  `DEPT_PATH` varchar(50) DEFAULT NULL COMMENT '部门树',
  `DEPT_PARENT` varchar(50) DEFAULT NULL COMMENT '上一级部门',
  `STATUS` varchar(1) NOT NULL DEFAULT 'E' COMMENT '数据状态',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`DEPT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

INSERT INTO iartisan_admin_template.biz_dept (DEPT_ID, DEPT_NAME, DEPT_PATH, DEPT_PARENT, STATUS, CREATE_TIME, UPDATE_TIME) VALUES ('1', 'iartisan', '1', null, 'E', null, null);

alter table biz_dept add column
DEPT_LEADER VARCHAR(32) DEFAULT NULL COMMENT '部门领导'
after DEPT_NAME;