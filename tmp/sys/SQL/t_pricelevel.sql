/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.49-community 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `t_pricelevel` (
	`id` int ,
	`priceLevel` int ,
	`price` float ,
	`description` varchar (300)
); 
insert into `t_pricelevel` (`id`, `priceLevel`, `price`, `description`) values('1','1','80.00','价格贵的书');
insert into `t_pricelevel` (`id`, `priceLevel`, `price`, `description`) values('2','2','60.00','价格适中的书');
insert into `t_pricelevel` (`id`, `priceLevel`, `price`, `description`) values('3','3','40.00','价格便宜的书');



SELECT * FROM t_book WHERE booktypeId IN (SELECT id FROM t_booktype);

SELECT * FROM t_book WHERE booktypeId NOT IN (SELECT id FROM t_booktype);



SELECT * FROM t_book WHERE price>=(SELECT price FROM t_pricelevel WHERE priceLevel=1);


SELECT * FROM t_book WHERE EXISTS (SELECT * FROM t_booktype);

SELECT * FROM t_book WHERE NOT EXISTS (SELECT * FROM t_booktype);

SELECT * FROM t_book WHERE price>= ANY (SELECT price FROM t_pricelevel);

SELECT * FROM t_book WHERE price>= ALL (SELECT price FROM t_pricelevel);

