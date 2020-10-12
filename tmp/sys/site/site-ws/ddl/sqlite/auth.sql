create table t_auth_permission (
	id varchar(19) not null,
	menu_name varchar(50) null,
	menu_code varchar(50) not null,
	url varchar(19) null,
	lev integer null,
	sort integer null,
	parent_id varchar(19) null,
	remark varchar(200) null,
	create_time timestamp not null,
	update_time timestamp null,
	is_del integer not null default 0,
	primary key (id)
);

create table t_auth_role (
	id varchar(19) not null,
	role_name varchar(50) null,
	role_code varchar(50) not null,
	parent_id varchar(19) not null,
	remark varchar(200) null,
	create_time timestamp not null,
	update_time timestamp null,
	is_del integer not null default 0,
	primary key (id)
);

create table t_auth_role_permission (
	id varchar(19) not null,
	role_id text not null,
	permission_id varchar(19) null,
	create_time timestamp not null,
	update_time timestamp null,
	is_del integer not null default 0,
	primary key (id)
);

create table t_auth_user (
	id varchar(19) not null,
	email varchar(50) null,
	login_name varchar(50) not null,
	user_name varchar(50) not null,
	pass_word varchar(50) not null,
	salt varchar(50) null,
	create_time timestamp not null,
	update_time timestamp null,
	is_del integer not null default 0,
	primary key (id)
);

create table t_auth_user_role (
	id varchar(19) not null,
	role_id varchar(19) null,
	user_id varchar(19) not null,
	create_time timestamp not null,
	update_time timestamp null,
	is_del integer not null default 0,
	primary key (id)
);

