use test;
drop table user;
create table user (
	`id` int(10) NOT NULL,
	`name` varchar(25) NOT NULL,
	`password` varchar(25) NOT NULL,
	PRIMARY KEY (`id`)
); 
insert into user (`id`, `password`, `name`) values('1','999999','xieke');
insert into user (`id`, `password`, `name`) values('2','324233','43434');
insert into user (`id`, `password`, `name`) values('3','343434','34344');