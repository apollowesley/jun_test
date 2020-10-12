create database student_manager;
use student_manager;

#用户表
create table user(
	user_id int primary key auto_increment,
	log_name varchar(32) not null,
	log_pwd varchar(32),
	log_status int(2) default 1, #1启用 0禁用
	user_sex varchar(8),#man  woman
	user_type varchar(8), #admin teacher student
	create_time timestamp default CURRENT_TIMESTAMP
);

insert into user(log_name,log_pwd,user_sex,user_type) values('admin','21232f297a57a5a743894a0e4a801fc3','man','admin');


#班级表
create table clazz(
	cla_id int primary key auto_increment,
	cla_name varchar(32) not null,
	cla_info varchar(200),
	create_time timestamp default current_timestamp
);

insert into clazz(cla_name,cla_info) values('606班','这是软件班');

#学生表
create table student(
 stu_id int primary key auto_increment,
 stu_name varchar(32) not null,
 cla_id int,
 user_id int,
 create_time timestamp default current_timestamp,
 constraint fk_clazz_id foreign key(cla_id) references clazz(cla_id),
 constraint fk_user_id foreign key(user_id) references user(user_id)
);


#教师表
create table teacher(
	tea_id int primary key auto_increment,
	tea_name varchar(32) not null,
	tea_course varchar(100) not null,
	create_time timestamp default current_timestamp
);

#教师班级关系表
create table tea_clazz(
	tea_id int ,
	cla_id int ,
	foreign key(tea_id) references teacher(tea_id),
	foreign key(cla_id) references clazz(cla_id),
	primary key(tea_id,cla_id)
);



