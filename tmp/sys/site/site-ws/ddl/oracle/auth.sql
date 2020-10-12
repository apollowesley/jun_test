create table t_auth_permission 
(
   id                   varchar2(32)                   primary key not null,
   menu_name            varchar2(50)                   null,
   menu_code            varchar2(50)                   not null,
   url                  varchar2(32)                   null,
   lev                  number(10)                     null,
   sort                 number(10)                     null,
   parent_id            varchar2(32)                   null,
   business_system      varchar2(20)                   null,
   remark               varchar2(200)                  null,
   create_time          timestamp                      not null,
   update_time          timestamp                      null,
   is_del               number(10)                     default '0' not null
);
comment on table t_auth_permission is '菜单许可表';
comment on column t_auth_permission.id is '主键：菜单许可ID';
comment on column t_auth_permission.menu_name is '菜单名';
comment on column t_auth_permission.menu_code is '菜单编码';
comment on column t_auth_permission.url is '菜单地址';
comment on column t_auth_permission.lev is '菜单等级';
comment on column t_auth_permission.sort is '父菜排序';
comment on column t_auth_permission.parent_id is '父菜单许可ID';
comment on column t_auth_permission.business_system is '业务系统';
comment on column t_auth_permission.remark is '备注';
comment on column t_auth_permission.create_time is '创建时间';
comment on column t_auth_permission.update_time is '编辑时间';
comment on column t_auth_permission.is_del is '是否删除:1=已删除;0=未删除';

create table t_auth_role 
(
   id                   varchar2(32)                   primary key not null,
   role_name            varchar2(50)                   null,
   role_code            varchar2(50)                   not null,
   business_system      varchar2(20)                   null,
   parent_id            varchar2(32)                   not null,
   remark               varchar2(200)                  null,
   create_time          timestamp                      not null,
   update_time          timestamp                      null,
   is_del               number(10)                     default '0' not null 
);
comment on table t_auth_role is '角色表';
comment on column t_auth_role.id is '主键：角色ID';
comment on column t_auth_role.role_name is '角色名';
comment on column t_auth_role.role_code is '编码';
comment on column t_auth_role.business_system is '业务系统';
comment on column t_auth_role.parent_id is '父角色ID';
comment on column t_auth_role.remark is '备注';
comment on column t_auth_role.create_time is '创建时间';
comment on column t_auth_role.update_time is '编辑时间';
comment on column t_auth_role.is_del is '是否删除:1=已删除;0=未删除';

create table t_auth_role_permission 
(
   id                   varchar2(32)                   primary key not null,
   role_id              varchar2(32)                   not null,
   permission_id        varchar2(32)                   null,
   business_system      varchar2(20)                   null,
   create_time          timestamp                      not null,
   update_time          timestamp                      null,
   is_del               number(10)                     default '0' not null 
);
comment on table t_auth_role_permission is '角色菜单许可关系表';
comment on column t_auth_role_permission.id is '主键：用户角色ID';
comment on column t_auth_role_permission.role_id is '外键：角色ID';
comment on column t_auth_role_permission.permission_id is '外键：菜单许可ID';
comment on column t_auth_role_permission.business_system is '业务系统';
comment on column t_auth_role_permission.create_time is '创建时间';
comment on column t_auth_role_permission.update_time is '编辑时间';
comment on column t_auth_role_permission.is_del is '是否删除:1=已删除;0=未删除';

create table t_auth_user 
(
   id                   varchar2(32)                   primary key not null,
   email                varchar2(50)                   null,
   login_name           varchar2(50)                   not null,
   user_name            varchar2(50)                   not null,
   pass_word            varchar2(50)                   not null,
   salt                 varchar2(50)                   null,
   create_time          timestamp                      not null,
   update_time          timestamp                      null,
   is_del               number(10)                     default '0' not null 
);
comment on table t_auth_user is '后台用户表';
comment on column t_auth_user.id is '主键：用户ID';
comment on column t_auth_user.email is '邮箱';
comment on column t_auth_user.login_name is '登录名';
comment on column t_auth_user.user_name is '用户名';
comment on column t_auth_user.pass_word is '密码';
comment on column t_auth_user.salt is 'salt码';
comment on column t_auth_user.create_time is '创建时间';
comment on column t_auth_user.update_time is '编辑时间';
comment on column t_auth_user.is_del is '是否删除:1=已删除;0=未删除';

create table t_auth_user_role 
(
   id                   varchar2(32)                   primary key not null,
   role_id              varchar2(32)                   null,
   user_id              varchar2(32)                   not null,
   create_time          timestamp                      not null,
   update_time          timestamp                      null,
   is_del               number(10)                     default '0' not null 
);
comment on table t_auth_user_role is '用户角色关系表';
comment on column t_auth_user_role.id is '主键：用户角色ID';
comment on column t_auth_user_role.role_id is '主键：角色ID';
comment on column t_auth_user_role.user_id is '外键：用户ID';
comment on column t_auth_user_role.create_time is '创建时间';
comment on column t_auth_user_role.update_time is '编辑时间';
comment on column t_auth_user_role.is_del is '是否删除:1=已删除;0=未删除';
