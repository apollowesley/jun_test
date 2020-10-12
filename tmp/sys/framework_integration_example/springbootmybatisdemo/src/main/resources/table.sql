use test;
drop table `user`;
create table `user` (
   `id` int(10) NOT NULL AUTO_INCREMENT,
   `name` varchar(25) NOT NULL,
   `password` varchar(25) NOT NULL,
   PRIMARY KEY (`id`)
 );
insert into `user` (`id`, `name`, `password`) values('1','xieke','999999');
insert into `user` (`id`, `name`, `password`) values('2','43434','324233');
insert into `user` (`id`, `name`, `password`) values('3','34344','343434');