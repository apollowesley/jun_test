/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.49-community 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `t_student` (
	`id` double ,
	`stuName` varchar (60),
	`age` double ,
	`sex` varchar (30),
	`gradeName` varchar (60)
); 
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('1','张三','23','男','一年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('2','张三丰','25','男','二年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('3','李四','23','男','一年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('4','王五','22','男','三年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('5','珍妮','21','女','一年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('6','李娜','26','女','二年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('7','王峰','20','男','三年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('8','梦娜','21','女','二年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('9','小黑','22','男','一年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('10','追风','25','男','二年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('11','小小张三','21',NULL,'二年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('12','小张三','23','男','二年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('13','张三锋小','24',NULL,'二年级');



SELECT id,stuName,age,sex,gradeName FROM t_student ;

SELECT stuName,id,age,sex,gradeName FROM t_student ;

SELECT * FROM t_student;

SELECT stuName,gradeName FROM t_student;

SELECT * FROM t_student WHERE id=1;

SELECT * FROM t_student WHERE age>22;

SELECT * FROM t_student WHERE age IN (21,23);
SELECT * FROM t_student WHERE age NOT IN (21,23);

SELECT * FROM t_student WHERE age BETWEEN 21 AND 24;
SELECT * FROM t_student WHERE age NOT BETWEEN 21 AND 24;

SELECT * FROM t_student WHERE stuName LIKE '张三';
SELECT * FROM t_student WHERE stuName LIKE '张三%';
SELECT * FROM t_student WHERE stuName LIKE '张三__';
SELECT * FROM t_student WHERE stuName LIKE '%张三%';

SELECT * FROM t_student WHERE sex IS NULL;
SELECT * FROM t_student WHERE sex IS NOT NULL;

SELECT * FROM t_student WHERE gradeName='一年级' AND age=23
SELECT * FROM t_student WHERE gradeName='一年级' OR age=23

SELECT DISTINCT gradeName FROM t_student;

SELECT * FROM t_student ORDER BY age ASC;
SELECT * FROM t_student ORDER BY age DESC;

SELECT * FROM t_student GROUP BY gradeName;

SELECT gradeName,GROUP_CONCAT(stuName) FROM t_student GROUP BY gradeName;

SELECT gradeName,COUNT(stuName) FROM t_student GROUP BY gradeName;

SELECT gradeName,COUNT(stuName) FROM t_student GROUP BY gradeName HAVING COUNT(stuName)>3;

SELECT gradeName,COUNT(stuName) FROM t_student GROUP BY gradeName WITH ROLLUP;
SELECT gradeName,GROUP_CONCAT(stuName) FROM t_student GROUP BY gradeName WITH ROLLUP;

SELECT * FROM t_student LIMIT 0,5;
SELECT * FROM t_student LIMIT 5,5;
SELECT * FROM t_student LIMIT 10,5;


