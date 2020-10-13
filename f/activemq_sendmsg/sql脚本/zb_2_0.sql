/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.1.73-log : Database - zb_2_0
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`zb_2_0` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `zb_2_0`;

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `sort` varchar(100) DEFAULT NULL,
  `icon` varchar(30) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `permission_code` varchar(50) NOT NULL COMMENT '所需权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`name`,`url`,`parent_id`,`sort`,`icon`,`type`,`permission_code`) values (1,'系统管理','#',0,'1','icon-cogs','1','sys:mgt'),(2,'用户管理','/user/toUserListView',1,'2',NULL,'1','user:mgt'),(4,'角色管理','/role/toRoleListView',1,'4',NULL,'1','role:mgt'),(5,'权限管理','/permission/toListView',1,'3',NULL,'1','permission:mgt');

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '权限名称',
  `code` varchar(50) NOT NULL COMMENT '权限代码',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态，0：可用，1：不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='权限表';

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`name`,`code`,`description`,`parent_id`,`status`) values (1,'系统管理','sys:mgt','对系统模块的管理',-1,0),(2,'人员管理','user:mgt','对人员进行管理',1,0),(3,'角色管理','role:mgt','对角色进行管理',1,0),(4,'权限管理','permission:mgt','对权限进行管理',1,0),(9,'查看用户列表','user:list','可以查看用户列表',2,0),(10,'编辑用户信息','user:edit','可以编辑用户信息',9,0),(11,'删除用户','user:delete','可以删除用户',9,0),(12,'查看角色列表','role:list','可以查看角色列表',3,0),(13,'查看权限列表','permission:list',NULL,4,0),(14,'新增角色','role:add','新增角色信息',12,0),(15,'编辑角色','role:edit','编辑角色信息',12,0),(16,'删除角色','role:delete','可以删除角色',12,0),(17,'新增权限','permission:add','可以添加新权限',13,0),(18,'编辑权限','permission:edit','修改权限信息',13,0),(19,'删除权限','permission:delete','可以删除权限数据',13,0),(20,'添加用户','user:add','可以添加新的用户',9,0);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(24) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`description`,`status`,`update_time`,`create_time`) values (1,'超级管理员','最高级别权限管理员',0,'2016-08-01 15:17:52','2016-08-01 15:17:53'),(2,'用户','基层员工',0,'2016-08-01 15:18:21','2016-08-01 15:18:23'),(3,'财务','财务部角色',1,'2016-08-03 13:21:56','2016-08-03 13:21:58');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=226 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`id`,`role_id`,`permission_id`) values (185,1,-1),(186,1,1),(187,1,2),(188,1,9),(189,1,10),(190,1,11),(191,1,20),(192,1,3),(193,1,12),(194,1,14),(195,1,15),(196,1,16),(197,1,4),(198,1,13),(199,1,17),(200,1,18),(201,1,19),(220,3,-1),(221,3,1),(222,3,2),(223,2,-1),(224,2,1),(225,2,2);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL COMMENT '登录用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '账号状态',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`user_name`,`password`,`real_name`,`status`,`create_time`,`update_time`) values (1,'admin','e10adc3949ba59abbe56e057f20f883e','管理员',0,'2016-08-03 13:22:46','2016-09-29 14:48:26'),(3,'caiwu','e10adc3949ba59abbe56e057f20f883e','财务',0,'2016-08-03 13:23:32','2016-08-21 14:05:33'),(8,'user','e10adc3949ba59abbe56e057f20f883e','用户',0,'2016-08-20 16:46:32','2016-09-29 14:47:53');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`) values (40,3,3),(48,8,2),(49,1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
