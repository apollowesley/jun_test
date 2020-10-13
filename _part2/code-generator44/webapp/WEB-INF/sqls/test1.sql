-- test
create table IF NOT EXISTS `my_table1`(
    `f1`      bigint(20) unsigned not null comment '主键',
    `f2`      int(32) not null comment '注释',
    `f3`      varchar(32) not null comment '注释',
    `f4`      varchar(32) not null comment '注释',
    `f5`      varchar(32) not null comment '注释',
    `f6`      varchar(32) not null comment '注释',
    `f7`      varchar(32) not null comment '注释',
    `f8`      varchar(32) not null comment '注释',
    `f9`      datetime not null comment '注释',
    `f10`     datetime not null comment '注释',
    primary key (f1)
);

-- test
create table IF NOT EXISTS `my_table2`(
    `f1`      bigint(20) unsigned not null comment '主键',
    `f2`      datetime not null comment '注释',
    `f3`      datetime not null comment '注释',
    primary key (f1)
);

-- test
create table IF NOT EXISTS `my_table3`(
    `f1`      bigint(20) unsigned not null comment '主键',
    `f2`      datetime not null comment '注释',
    `f3`      datetime not null comment '注释',
    primary key (f1)
);