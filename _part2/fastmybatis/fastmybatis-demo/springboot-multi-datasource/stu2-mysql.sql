
CREATE DATABASE IF NOT EXISTS `stu2` DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE `stu2`;

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(128) NOT NULL DEFAULT '' COMMENT '商品名称',
  `goods_price` int(11) NOT NULL DEFAULT '0' COMMENT '商品价格（分）',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='商品表';

INSERT INTO `goods` (`id`, `goods_name`, `goods_price`, `gmt_create`, `gmt_modified`) VALUES
	(1,'iPhoneX',50000,'2019-12-28 11:07:46','2019-12-28 11:07:46'),
	(2,'HuaweiP30',40000,'2019-12-28 11:07:59','2019-12-28 11:07:59');

