CREATE DATABASE IF NOT EXISTS ambari  CHARACTER SET 'utf8'  COLLATE 'utf8_general_ci';  
use ambari;
create table IF NOT EXISTS test (
id   INT UNSIGNED AUTO_INCREMENT comment '主键',
title VARCHAR(100) NOT NULL comment '标题',
author   VARCHAR(40)  NOT NULL comment '作者',
PRIMARY KEY (id)
 
)engine=innodb default charset=utf8 auto_increment=1 comment='依赖关系';

