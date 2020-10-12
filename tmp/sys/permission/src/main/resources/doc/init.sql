-- sys_user
/*
警告: 字段名可能非法 - status
*/
create table  `sys_user`
(
       `id`              INT auto_increment primary key not null comment '编号',
       `rel_name`        VARCHAR(16) not null comment '真实名',
       `user_name`       VARCHAR(32) not null comment '登录名',
       `tel`             VARCHAR(11) not null comment '联系方式',
       `email`           VARCHAR(32) not null comment '邮箱',
       `pwd`             VARCHAR(64) not null comment '密码',
       `remark`          VARCHAR(64) comment '备注',
       `dept_id`         INT not null comment '部门编号',
       `status`          INT(2) default 0 not null comment '状态',
       `operator`        VARCHAR(16) not null comment '操作人',
       `operator_time`   TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null comment '操作时间',
       `operator_ip`     VARCHAR(32) not null comment '操作ip'
);
alter table `sys_user` comment= '用户表';

-- sys_dept
create table  `sys_dept`
(
       `id`              INT auto_increment primary key not null comment '编号',
       `name`            VARCHAR(16) not null comment '名称',
       `level`           VARCHAR(64) not null comment '层级',
       `seq`             INT(4) not null comment '层级排序号',
       `remark`          VARCHAR(128) comment '备注',
       `parent_id`       INT not null comment '上级编号',
       `operator`        VARCHAR(16) not null comment '操作人',
       `operator_time`   TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null comment '最后一次操作时间',
       `operator_ip`     VARCHAR(18) not null comment '操作ip'
);
alter table `sys_dept` comment= '部门表';

-- sys_acl_module
/*
警告: 字段名可能非法 - status
*/
create table  `sys_acl_module`
(
       `id`              INT auto_increment primary key not null comment '编号',
       `name`            VARCHAR(64) not null comment '名称',
       `parent_id`       INT not null comment '上级编号',
       `level`           VARCHAR(32) not null comment '模块层级',
       `status`          INT(2) default 0 not null comment '状态',
       `seq`             INT(3) not null comment '排序号',
       `remark`          VARCHAR(200) comment '备注',
       `operator`        VARCHAR(16) not null comment '操作人',
       `operator_time`   TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null comment '操作时间',
       `operator_ip`     VARCHAR(32) not null comment '操作ip'
);
alter table `sys_acl_module` comment= '权限模块表';

-- sys_acl
/*
警告: 字段名可能非法 - type
警告: 字段名可能非法 - status
*/
create table  `sys_acl`
(
       `id`              INT auto_increment primary key not null comment '编号',
       `name`            VARCHAR(64) not null comment '名称',
       `code`            VARCHAR(64) not null comment '权限码',
       `aclModuleId`     INT not null comment '权限模块编号',
       `url`             VARCHAR(64) not null comment '地址',
       `type`            VARCHAR(32) not null comment '权限类型,菜单,按钮,其他',
       `status`          INT(2) default 0 not null comment '状态',
       `seq`             INT(3) not null comment '排序号',
       `remark`          VARCHAR(64) comment '备注',
       `operator`        VARCHAR(16) not null comment '操作人',
       `operator_time`   TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null comment '操作时间',
       `operator_ip`     VARCHAR(32) not null comment '操作ip'
);
alter table `sys_acl` comment= '权限表';

-- sys_role
/*
警告: 字段名可能非法 - type
警告: 字段名可能非法 - status
*/
create table  `sys_role`
(
       `id`              INT auto_increment primary key not null comment '编号',
       `name`            VARCHAR(64) not null comment '名称',
       `type`            int(4) not null comment '类型,区分快速查询，备用',
       `status`          INT(2) default 0 not null comment '状态',
       `remark`          VARCHAR(64) comment '备注',
       `operator`        VARCHAR(16) not null comment '操作人',
       `operator_time`   TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null comment '操作时间',
       `operator_ip`     VARCHAR(32) not null comment '操作ip'
);
alter table `sys_role` comment= '角色表';

-- sys_role_user
create table  `sys_role_user`
(
       `id`              INT auto_increment primary key not null comment '编号',
       `role_id`         INT comment '角色编号',
       `user_id`         VARCHAR(4000) comment '用户编号',
       `operator`        VARCHAR(16) not null comment '操作人',
       `operator_time`   TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null comment '操作时间',
       `operator_ip`     VARCHAR(32) not null comment '操作ip'
);
alter table `sys_role_user` comment= '角色用户关系表';

-- sys_role_acl
create table  `sys_role_acl`
(
       `id`              INT auto_increment primary key not null comment '编号',
       `role_id`         INT comment '角色编号',
       `acl_id`          VARCHAR(4000) comment '权限编号',
       `operator`        VARCHAR(16) not null comment '操作人',
       `operator_time`   TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null comment '操作时间',
       `operator_ip`     VARCHAR(32) not null comment '操作ip'
);
alter table `sys_role_acl` comment= '角色权限关系表';

-- sys_log
/*
警告: 字段名可能非法 - type
*/
create table  `sys_log`
(
       `id`              INT auto_increment primary key not null comment '编号',
       `type`            VARCHAR(64) not null comment '表类型',
       `target_id`       INT not null comment '更改表主键',
       `old_val`         VARCHAR(64) not null comment '旧值',
       `new_val`         VARCHAR(64) not null comment '新值',
       `operator`        VARCHAR(16) not null comment '操作人',
       `operator_time`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null comment '操作时间',
       `operator_ip`     VARCHAR(32) not null comment '操作ip'
);
alter table `sys_log` comment= '权限更新日志表';

alter  table `sys_user`
       add constraint `FK_sys_user_dept_id` foreign key (`dept_id`)
       references `sys_dept`(`id`);

alter  table `sys_acl`
       add constraint `FK_sys_acl_aclModuleId` foreign key (`aclModuleId`)
       references `sys_acl_module`(`id`);

alter  table `sys_role_user`
       add constraint `FK_sys_role_user_role_id` foreign key (`role_id`)
       references `sys_role`(`id`);

alter  table `sys_role_acl`
       add constraint `FK_sys_role_acl_role_id` foreign key (`role_id`)
       references `sys_acl_module`(`id`);
alter  table `sys_role_acl`
       add constraint `FK_sys_role_acl_acl_id` foreign key (`acl_id`)
       references `sys_acl`(`id`);

