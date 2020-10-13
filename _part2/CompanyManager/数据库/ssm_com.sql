-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- 主机: 127.0.0.1
-- 生成日期: 2016 �?10 �?25 �?18:38
-- 服务器版本: 5.6.11
-- PHP 版本: 5.5.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `ssm_com`
--
CREATE DATABASE IF NOT EXISTS `ssm_com` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `ssm_com`;

-- --------------------------------------------------------

--
-- 表的结构 `mvc_dept_info`
--

CREATE TABLE IF NOT EXISTS `mvc_dept_info` (
  `dept_id` varchar(6) NOT NULL COMMENT 'id',
  `dept_no` varchar(32) DEFAULT NULL COMMENT '部门编号',
  `dept_name` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `establish_time` date DEFAULT NULL COMMENT '成立时间',
  `dept_manager` varchar(32) DEFAULT NULL COMMENT '部门经理',
  `super_id` varchar(6) DEFAULT NULL COMMENT '上级部门',
  `dept_desc` varchar(1000) DEFAULT NULL COMMENT '部门描述',
  `if_leaf` varchar(1) DEFAULT NULL COMMENT '是否叶子节点，1：是，0：否',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `mvc_dept_info`
--

INSERT INTO `mvc_dept_info` (`dept_id`, `dept_no`, `dept_name`, `establish_time`, `dept_manager`, `super_id`, `dept_desc`, `if_leaf`) VALUES
('d10000', '10000', '集团公司', '2014-12-18', '', '0', '公司', '0'),
('d10001', '1000001', '软件开发部', '2015-08-20', NULL, 'd10000', NULL, '0'),
('d10002', '100002', '软件销售部', '2015-08-20', NULL, 'd10000', NULL, '1'),
('d10003', '100000101', 'java开发组', '2016-10-11', '2ddcb567939611e6a8df142d27fd7e9e', 'd10001', 'java开发组', '1');

-- --------------------------------------------------------

--
-- 表的结构 `mvc_dictionary`
--

CREATE TABLE IF NOT EXISTS `mvc_dictionary` (
  `dictionary_id` varchar(32) NOT NULL,
  `dictionary_code` varchar(32) DEFAULT NULL,
  `dictionary_name` varchar(32) DEFAULT NULL,
  `dictionary_status` varchar(1) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `creator` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`dictionary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `mvc_dictionary`
--

INSERT INTO `mvc_dictionary` (`dictionary_id`, `dictionary_code`, `dictionary_name`, `dictionary_status`, `create_time`, `creator`) VALUES
('355c597026d711e5ade8ace010142f18', 'memorStatus', '备忘录状态', '0', '2015-07-16 14:30:31', '2b988330115011e4b54e1205e26038f6');

-- --------------------------------------------------------

--
-- 表的结构 `mvc_dictionary_detail`
--

CREATE TABLE IF NOT EXISTS `mvc_dictionary_detail` (
  `dictionary_detail_id` varchar(32) NOT NULL,
  `dictionary_id` varchar(32) DEFAULT NULL,
  `dictionary_detail_code` varchar(32) DEFAULT NULL,
  `dictionary_detail_name` varchar(32) DEFAULT NULL,
  `dictionary_detail_status` varchar(1) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `creator` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`dictionary_detail_id`),
  KEY `dictionary_id` (`dictionary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `mvc_dictionary_detail`
--

INSERT INTO `mvc_dictionary_detail` (`dictionary_detail_id`, `dictionary_id`, `dictionary_detail_code`, `dictionary_detail_name`, `dictionary_detail_status`, `create_time`, `creator`) VALUES
('15a8c05c2b8111e5a5c0ace010142f18', '355c597026d711e5ade8ace010142f18', 'memorStatus0', '待办', '0', '2015-07-17 08:53:13', '2b988330115011e4b54e1205e26038f6'),
('93f7601d98ed11e6b100142d27fd7e9e', '355c597026d711e5ade8ace010142f18', 'memorStatus1', '已完成', '0', '2016-10-23 14:54:46', '2b988330115011e4b54e1205e26038f6');

-- --------------------------------------------------------

--
-- 表的结构 `mvc_loginuser`
--

CREATE TABLE IF NOT EXISTS `mvc_loginuser` (
  `loginuser_id` varchar(50) DEFAULT '0',
  `loginuser_ip` varchar(50) DEFAULT NULL,
  `loginuser_address` varchar(255) DEFAULT NULL,
  `loginuser_logintime` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `mvc_loginuser`
--

INSERT INTO `mvc_loginuser` (`loginuser_id`, `loginuser_ip`, `loginuser_address`, `loginuser_logintime`) VALUES
('c1ccf254992011e6b100142d27fd7e9e', '117.136.31.226', '中国,华南,广东省,广州市,,移动', '2016-10-23 21:01:07'),
('14711e37992111e6b100142d27fd7e9e', '117.136.31.226', NULL, '2016-10-23 21:03:26'),
('15176572992111e6b100142d27fd7e9e', '117.136.31.226', NULL, '2016-10-23 21:03:27'),
('1535314c992311e6b100142d27fd7e9e', '117.136.31.226', '网络较差，查询不到', '2016-10-23 21:17:46'),
('160987b0992311e6b100142d27fd7e9e', '117.136.31.226', '网络较差，查询不到', '2016-10-23 21:17:48'),
('572d34c5992411e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '网络较差，查询不到', '2016-10-23 21:26:46'),
('a5fba762992411e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-23 21:28:59'),
('a2c8c872992511e6b100142d27fd7e9e', '172.16.150.169', '未分配或者内网IP,,,,,', '2016-10-23 21:36:03'),
('b955a862992511e6b100142d27fd7e9e', '172.16.150.169', '未分配或者内网IP,,,,,', '2016-10-23 21:36:41'),
('ca1d2f23992511e6b100142d27fd7e9e', '172.16.150.169', '网络较差，查询不到地理位置', '2016-10-23 21:37:09'),
('def21779992511e6b100142d27fd7e9e', '172.16.150.39', '未分配或者内网IP,,,,,', '2016-10-23 21:37:44'),
('e7f96be1992511e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-23 21:37:59'),
('1a0c68e9992611e6b100142d27fd7e9e', '172.16.150.169', '未分配或者内网IP,,,,,', '2016-10-23 21:39:23'),
('7c756639992611e6b100142d27fd7e9e', '172.16.4.90', '未分配或者内网IP,,,,,', '2016-10-23 21:42:08'),
('7db0e7f5992611e6b100142d27fd7e9e', '172.16.150.137', '未分配或者内网IP,,,,,', '2016-10-23 21:42:10'),
('a3b35ecf993811e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-23 23:52:04'),
('a4d461d8993811e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '网络较差，查询不到地理位置', '2016-10-23 23:52:07'),
('62bd285c999411e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-24 10:48:50'),
('efd84edb999511e6b100142d27fd7e9e', '172.16.150.39', '未分配或者内网IP,,,,,', '2016-10-24 10:59:57'),
('01c0d95b999611e6b100142d27fd7e9e', '172.16.150.39', '未分配或者内网IP,,,,,', '2016-10-24 11:00:27'),
('051501ef999611e6b100142d27fd7e9e', '172.16.150.39', '网络较差，查询不到地理位置', '2016-10-24 11:00:33'),
('0522c16c999611e6b100142d27fd7e9e', '172.16.150.39', '未分配或者内网IP,,,,,', '2016-10-24 11:00:33'),
('0bdb5fd3999611e6b100142d27fd7e9e', '172.16.150.39', '未分配或者内网IP,,,,,', '2016-10-24 11:00:44'),
('15a3ba3a999611e6b100142d27fd7e9e', '172.16.150.39', '网络较差，查询不到地理位置', '2016-10-24 11:01:00'),
('16b973d9999611e6b100142d27fd7e9e', '172.16.150.39', '网络较差，查询不到地理位置', '2016-10-24 11:01:02'),
('4a9e0844999611e6b100142d27fd7e9e', '172.16.150.39', '未分配或者内网IP,,,,,', '2016-10-24 11:02:29'),
('542f735e999611e6b100142d27fd7e9e', '172.16.150.39', '网络较差，查询不到地理位置', '2016-10-24 11:02:45'),
('65c03bd4999611e6b100142d27fd7e9e', '172.16.150.39', '未分配或者内网IP,,,,,', '2016-10-24 11:03:15'),
('6e5bb089999611e6b100142d27fd7e9e', '172.16.150.39', '网络较差，查询不到地理位置', '2016-10-24 11:03:29'),
('74ecf0f9999611e6b100142d27fd7e9e', '172.16.150.39', '未分配或者内网IP,,,,,', '2016-10-24 11:03:40'),
('8335ed68999611e6b100142d27fd7e9e', '172.16.150.39', '网络较差，查询不到地理位置', '2016-10-24 11:04:04'),
('4410d477999711e6b100142d27fd7e9e', '172.16.150.39', '未分配或者内网IP,,,,,', '2016-10-24 11:09:28'),
('cdb68fdc999711e6b100142d27fd7e9e', '172.16.150.39', '网络较差，查询不到地理位置', '2016-10-24 11:13:19'),
('f8f9ae36999711e6b100142d27fd7e9e', '172.16.177.17', '未分配或者内网IP,,,,,', '2016-10-24 11:14:31'),
('fa25e43b999711e6b100142d27fd7e9e', '172.16.177.17', '网络较差，查询不到地理位置', '2016-10-24 11:14:33'),
('fb2a683e999711e6b100142d27fd7e9e', '172.16.150.175', '未分配或者内网IP,,,,,', '2016-10-24 11:14:35'),
('fb3df18c999711e6b100142d27fd7e9e', '172.16.150.175', '网络较差，查询不到地理位置', '2016-10-24 11:14:35'),
('ff7f32a1999711e6b100142d27fd7e9e', '172.16.176.60', '网络较差，查询不到地理位置', '2016-10-24 11:14:42'),
('ffa220a9999711e6b100142d27fd7e9e', '172.16.176.60', '未分配或者内网IP,,,,,', '2016-10-24 11:14:42'),
('0182048d999811e6b100142d27fd7e9e', '172.16.150.175', '网络较差，查询不到地理位置', '2016-10-24 11:14:46'),
('0595c8f6999811e6b100142d27fd7e9e', '172.16.176.60', '网络较差，查询不到地理位置', '2016-10-24 11:14:52'),
('0e0a5ae8999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:15:07'),
('0eac1dfd999811e6b100142d27fd7e9e', '172.16.177.14', '网络较差，查询不到地理位置', '2016-10-24 11:15:08'),
('11bb6553999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:15:13'),
('123cca95999811e6b100142d27fd7e9e', '172.16.176.60', '未分配或者内网IP,,,,,', '2016-10-24 11:15:14'),
('13002318999811e6b100142d27fd7e9e', '172.16.176.147', '未分配或者内网IP,,,,,', '2016-10-24 11:15:15'),
('131acdc4999811e6b100142d27fd7e9e', '172.16.150.43', '网络较差，查询不到地理位置', '2016-10-24 11:15:15'),
('13f8b138999811e6b100142d27fd7e9e', '172.16.178.24', '未分配或者内网IP,,,,,', '2016-10-24 11:15:17'),
('140afc5e999811e6b100142d27fd7e9e', '172.16.178.24', '网络较差，查询不到地理位置', '2016-10-24 11:15:17'),
('1444eb49999811e6b100142d27fd7e9e', '172.16.176.166', '网络较差，查询不到地理位置', '2016-10-24 11:15:17'),
('1572b209999811e6b100142d27fd7e9e', '172.16.178.24', '网络较差，查询不到地理位置', '2016-10-24 11:15:19'),
('15f67821999811e6b100142d27fd7e9e', '172.16.178.24', '网络较差，查询不到地理位置', '2016-10-24 11:15:20'),
('1751aa9d999811e6b100142d27fd7e9e', '172.16.150.175', '未分配或者内网IP,,,,,', '2016-10-24 11:15:22'),
('18d5b3c2999811e6b100142d27fd7e9e', '172.16.178.80', '网络较差，查询不到地理位置', '2016-10-24 11:15:25'),
('19ced113999811e6b100142d27fd7e9e', '172.16.176.166', '未分配或者内网IP,,,,,', '2016-10-24 11:15:26'),
('19df6c77999811e6b100142d27fd7e9e', '172.16.176.75', '网络较差，查询不到地理位置', '2016-10-24 11:15:27'),
('1f1d81e6999811e6b100142d27fd7e9e', '172.16.150.168', '网络较差，查询不到地理位置', '2016-10-24 11:15:35'),
('20b59a35999811e6b100142d27fd7e9e', '172.16.178.43', '网络较差，查询不到地理位置', '2016-10-24 11:15:38'),
('21f70ae3999811e6b100142d27fd7e9e', '172.16.177.19', '网络较差，查询不到地理位置', '2016-10-24 11:15:40'),
('236495a1999811e6b100142d27fd7e9e', '172.16.178.24', '网络较差，查询不到地理位置', '2016-10-24 11:15:42'),
('23a3013c999811e6b100142d27fd7e9e', '172.16.176.166', '网络较差，查询不到地理位置', '2016-10-24 11:15:43'),
('26010521999811e6b100142d27fd7e9e', '172.16.178.23', '网络较差，查询不到地理位置', '2016-10-24 11:15:47'),
('261f2397999811e6b100142d27fd7e9e', '172.16.178.23', '网络较差，查询不到地理位置', '2016-10-24 11:15:47'),
('2754ff58999811e6b100142d27fd7e9e', '172.16.178.23', '网络较差，查询不到地理位置', '2016-10-24 11:15:49'),
('2e27a46b999811e6b100142d27fd7e9e', '172.16.104.47', '网络较差，查询不到地理位置', '2016-10-24 11:16:01'),
('3762e743999811e6b100142d27fd7e9e', '172.16.176.166', '网络较差，查询不到地理位置', '2016-10-24 11:16:16'),
('3a9d38f9999811e6b100142d27fd7e9e', '172.16.176.75', '网络较差，查询不到地理位置', '2016-10-24 11:16:21'),
('3c4bcbc9999811e6b100142d27fd7e9e', '172.16.213.45', '网络较差，查询不到地理位置', '2016-10-24 11:16:24'),
('3dcc144d999811e6b100142d27fd7e9e', '172.16.213.45', '网络较差，查询不到地理位置', '2016-10-24 11:16:27'),
('4182201c999811e6b100142d27fd7e9e', '172.16.176.75', '网络较差，查询不到地理位置', '2016-10-24 11:16:33'),
('41a90ede999811e6b100142d27fd7e9e', '172.16.178.43', '网络较差，查询不到地理位置', '2016-10-24 11:16:33'),
('42e7bda9999811e6b100142d27fd7e9e', '172.16.176.75', '网络较差，查询不到地理位置', '2016-10-24 11:16:35'),
('49046f79999811e6b100142d27fd7e9e', '172.16.213.45', '未分配或者内网IP,,,,,', '2016-10-24 11:16:46'),
('4b9fef3d999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:16:50'),
('512067df999811e6b100142d27fd7e9e', '172.16.176.166', '网络较差，查询不到地理位置', '2016-10-24 11:16:59'),
('54413fbe999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:17:04'),
('58bcc390999811e6b100142d27fd7e9e', '172.16.176.75', '网络较差，查询不到地理位置', '2016-10-24 11:17:12'),
('5e3cf19c999811e6b100142d27fd7e9e', '172.16.176.75', '网络较差，查询不到地理位置', '2016-10-24 11:17:21'),
('68baf461999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:17:39'),
('6bbbfa58999811e6b100142d27fd7e9e', '172.16.213.45', '未分配或者内网IP,,,,,', '2016-10-24 11:17:44'),
('7a3bc60e999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:18:08'),
('7d1d1431999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:18:13'),
('864adc2a999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:18:28'),
('8812f239999811e6b100142d27fd7e9e', '172.16.178.80', '网络较差，查询不到地理位置', '2016-10-24 11:18:31'),
('8828e119999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:18:32'),
('884fdb83999811e6b100142d27fd7e9e', '172.16.176.75', '网络较差，查询不到地理位置', '2016-10-24 11:18:32'),
('9448cbc7999811e6b100142d27fd7e9e', '172.16.179.14', '未分配或者内网IP,,,,,', '2016-10-24 11:18:52'),
('9813b5c6999811e6b100142d27fd7e9e', '172.16.179.14', '未分配或者内网IP,,,,,', '2016-10-24 11:18:58'),
('9d40c2b8999811e6b100142d27fd7e9e', '172.16.176.75', '网络较差，查询不到地理位置', '2016-10-24 11:19:07'),
('9d9b4839999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:19:08'),
('9e8338cc999811e6b100142d27fd7e9e', '172.16.176.75', '网络较差，查询不到地理位置', '2016-10-24 11:19:09'),
('a2cf1bab999811e6b100142d27fd7e9e', '172.16.176.75', '网络较差，查询不到地理位置', '2016-10-24 11:19:16'),
('a3eedea6999811e6b100142d27fd7e9e', '172.16.176.75', '网络较差，查询不到地理位置', '2016-10-24 11:19:18'),
('a7584a60999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:19:24'),
('b1c57ebb999811e6b100142d27fd7e9e', '172.16.176.60', '未分配或者内网IP,,,,,', '2016-10-24 11:19:41'),
('ce1a27c7999811e6b100142d27fd7e9e', '172.16.213.204', '未分配或者内网IP,,,,,', '2016-10-24 11:20:29'),
('d0c240f2999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:20:33'),
('d2a9c869999811e6b100142d27fd7e9e', '172.16.178.24', '未分配或者内网IP,,,,,', '2016-10-24 11:20:37'),
('d63cee3c999811e6b100142d27fd7e9e', '172.16.178.24', '未分配或者内网IP,,,,,', '2016-10-24 11:20:43'),
('dc16a920999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:20:52'),
('ed11af82999811e6b100142d27fd7e9e', '172.16.176.75', '网络较差，查询不到地理位置', '2016-10-24 11:21:21'),
('f57e41f2999811e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:21:35'),
('fca356be999811e6b100142d27fd7e9e', '172.16.213.45', '未分配或者内网IP,,,,,', '2016-10-24 11:21:47'),
('1b52b898999911e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:22:38'),
('328289c3999911e6b100142d27fd7e9e', '172.16.150.39', '未分配或者内网IP,,,,,', '2016-10-24 11:23:17'),
('45f6193b999911e6b100142d27fd7e9e', '172.16.178.80', '未分配或者内网IP,,,,,', '2016-10-24 11:23:50'),
('47a062c8999911e6b100142d27fd7e9e', '172.16.213.204', '网络较差，查询不到地理位置', '2016-10-24 11:23:53'),
('5d5be91f999911e6b100142d27fd7e9e', '172.16.176.53', '网络较差，查询不到地理位置', '2016-10-24 11:24:29'),
('60a96258999911e6b100142d27fd7e9e', '172.16.150.175', '未分配或者内网IP,,,,,', '2016-10-24 11:24:35'),
('f0351af0999911e6b100142d27fd7e9e', '172.16.176.47', '未分配或者内网IP,,,,,', '2016-10-24 11:28:36'),
('07f516f1999a11e6b100142d27fd7e9e', '172.16.176.60', '未分配或者内网IP,,,,,', '2016-10-24 11:29:15'),
('102be9bd999a11e6b100142d27fd7e9e', '172.16.213.45', '网络较差，查询不到地理位置', '2016-10-24 11:29:29'),
('1fb88f6e999a11e6b100142d27fd7e9e', '172.16.178.24', '未分配或者内网IP,,,,,', '2016-10-24 11:29:55'),
('24899972999a11e6b100142d27fd7e9e', '172.16.178.24', '未分配或者内网IP,,,,,', '2016-10-24 11:30:03'),
('8344fa99999a11e6b100142d27fd7e9e', '172.16.178.24', '未分配或者内网IP,,,,,', '2016-10-24 11:32:42'),
('f7017d0f999a11e6b100142d27fd7e9e', '172.16.150.43', '未分配或者内网IP,,,,,', '2016-10-24 11:35:57'),
('47167df3999c11e6b100142d27fd7e9e', '172.16.213.152', '未分配或者内网IP,,,,,', '2016-10-24 11:45:20'),
('4f492048999c11e6b100142d27fd7e9e', '172.16.176.75', '未分配或者内网IP,,,,,', '2016-10-24 11:45:34'),
('ac35242d999c11e6b100142d27fd7e9e', '172.16.150.175', '未分配或者内网IP,,,,,', '2016-10-24 11:48:10'),
('a85a1180999d11e6b100142d27fd7e9e', '172.16.176.47', '未分配或者内网IP,,,,,', '2016-10-24 11:55:13'),
('254535d399a311e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-24 12:34:30'),
('f76370dc99ac11e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-24 13:44:48'),
('fc7d74db99ac11e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-24 13:44:57'),
('9a4a4a589a0211e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-24 23:57:50'),
('079fb2b49a0311e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 0:00:53'),
('1526306e9a0311e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '网络较差，查询不到地理位置', '2016-10-25 0:01:16'),
('a639b9199a0311e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 0:05:19'),
('c50cc2e29a0311e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 0:06:11'),
('3d6ec3cc9a0411e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 0:09:33'),
('47d569c79a0411e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 0:09:50'),
('550b9e7b9a0411e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 0:10:13'),
('7fd2d9f89a0411e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 0:11:24'),
('241aa10f9a0611e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 0:23:09'),
('374c86c49a0611e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 0:23:42'),
('885cb32f9a0611e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 0:25:58'),
('7e0d7cbd9a4211e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 7:35:11'),
('34518acd9a4911e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 8:23:14'),
('bf2570c29a8311e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 15:22:18'),
('c348316d9a8311e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '网络较差，查询不到地理位置', '2016-10-25 15:22:25'),
('a160e7809a8511e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 15:35:47'),
('d42604249a8511e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 15:37:12'),
('da6eb7f09a8511e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '网络较差，查询不到地理位置', '2016-10-25 15:37:23'),
('9a4139de9a8611e6b100142d27fd7e9e', '127.0.0.1', '未分配或者内网IP,,,,,', '2016-10-25 15:42:45'),
('185aebf29a8f11e6b100142d27fd7e9e', '127.0.0.1', '未分配或者内网IP,,,,,', '2016-10-25 16:43:32'),
('a0111fe99a9811e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '网络较差，查询不到地理位置', '2016-10-25 17:51:46'),
('eeb9544c9a9911e6b100142d27fd7e9e', '172.16.4.19', '未分配或者内网IP,,,,,', '2016-10-25 18:01:07'),
('510bf6679a9c11e6b100142d27fd7e9e', '172.16.4.19', '未分配或者内网IP,,,,,', '2016-10-25 18:18:11'),
('73d9645e9aa211e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '网络较差，查询不到地理位置', '2016-10-25 19:02:06'),
('fba7f73e9aaa11e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '网络较差，查询不到地理位置', '2016-10-25 20:03:10'),
('320294699aac11e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '0', '2016-10-25 20:11:51'),
('42d0aa869aac11e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '网络较差，查询不到地理位置', '2016-10-25 20:12:19'),
('e762336b9aad11e6b100142d27fd7e9e', '0:0:0:0:0:0:0:1', '网络较差，查询不到地理位置', '2016-10-25 20:24:05'),
('47cac24b9ad111e6b100142d27fd7e9e', '127.0.0.1', '未分配或者内网IP,,,,,', '2016-10-26 0:37:19');

-- --------------------------------------------------------

--
-- 表的结构 `mvc_manage_money`
--

CREATE TABLE IF NOT EXISTS `mvc_manage_money` (
  `manage_id` varchar(32) NOT NULL,
  `in_or_out` int(11) DEFAULT NULL,
  `how_much` double DEFAULT NULL,
  `incident` varchar(500) DEFAULT NULL,
  `take_time` datetime DEFAULT NULL,
  `if_take` int(11) DEFAULT NULL,
  `take_id` varchar(32) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`manage_id`),
  KEY `FK_manage_money` (`take_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `mvc_memorandum`
--

CREATE TABLE IF NOT EXISTS `mvc_memorandum` (
  `MEMORANDUM_ID` varchar(32) NOT NULL,
  `MEMORANDUM_DATE` date DEFAULT NULL,
  `MEMORANDUM_COMPLETE` varchar(32) DEFAULT NULL,
  `MEMORANDUM_TITLE` varchar(100) DEFAULT NULL,
  `MEMORANDUM_CONTENT` varchar(500) DEFAULT NULL,
  `CREATE_USER` varchar(32) DEFAULT NULL,
  `CREATE_TIME` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`MEMORANDUM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `mvc_memorandum`
--

INSERT INTO `mvc_memorandum` (`MEMORANDUM_ID`, `MEMORANDUM_DATE`, `MEMORANDUM_COMPLETE`, `MEMORANDUM_TITLE`, `MEMORANDUM_CONTENT`, `CREATE_USER`, `CREATE_TIME`) VALUES
('2cb3ab6198ce11e6b100142d27fd7e9e', '2016-10-12', 'memorStatus0', '跑步', '跑步', '2b988330115011e4b54e1205e26038f6', '2016-10-23 03:09:58'),
('78ad111698f311e6b100142d27fd7e9e', '2016-10-23', 'memorStatus0', '完成项目开发', '完成项目开发', '2b988330115011e4b54e1205e26038f6', '2016-10-23 07:36:57'),
('fa8df4589aad11e6b100142d27fd7e9e', '2016-10-25', 'memorStatus0', '跑步', '跑步', '2b988330115011e4b54e1205e26038f6', '2016-10-25 12:24:38');

-- --------------------------------------------------------

--
-- 表的结构 `mvc_menu_info`
--

CREATE TABLE IF NOT EXISTS `mvc_menu_info` (
  `menu_id` varchar(32) NOT NULL,
  `menu_name` varchar(32) DEFAULT NULL,
  `menu_uri` varchar(255) DEFAULT NULL,
  `menu_order` int(11) DEFAULT NULL,
  `menu_desc` varchar(200) DEFAULT NULL,
  `menu_type` varchar(32) DEFAULT NULL,
  `menu_icon` varchar(32) DEFAULT NULL,
  `creator` varchar(32) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `mvc_menu_info`
--

INSERT INTO `mvc_menu_info` (`menu_id`, `menu_name`, `menu_uri`, `menu_order`, `menu_desc`, `menu_type`, `menu_icon`, `creator`, `create_time`) VALUES
('0b74913998ef11e6b100142d27fd7e9e', '此项目的git源码', 'https://git.oschina.net/xi_fan/CompanyManager', 21, 'https://git.oschina.net/xi_fan/CompanyManager', 'web', NULL, '2b988330115011e4b54e1205e26038f6', '2016-10-23 07:05:16'),
('2581da703cab11e4a4e5f952ac6806e4', '部门管理', '/dept/turnToDeptTree', 4, '部门管理', 'system', 'icon-organisation', '2b988330115011e4b54e1205e26038f6', '2015-08-20 03:54:18'),
('3ede571516ff11e4bcc83003807be50a', '用户管理', '/user/queryUser', 1, '用户信息管理', 'system', 'icon-man', '2b988330115011e4b54e1205e26038f6', '2015-08-20 03:54:18'),
('56e0db6a98e611e6b100142d27fd7e9e', '字典管理', '/dictionary/initDictionary', 19, '字典管理', 'system', NULL, '2b988330115011e4b54e1205e26038f6', '2016-10-23 06:02:57'),
('5abf94088a6b11e48ada8b3e942d2329', '可可云', 'http://www.kekeyun.com/', 13, 'http://www.kekeyun.com/', 'web', 'icon-globe', '2b988330115011e4b54e1205e26038f6', '2015-08-20 03:54:18'),
('5aeaa3db28df11e48a79935d1967ba6c', '理财管理', '/manageMoney/queryManageMoney', 5, '理财管理', 'system', 'icon-money', '2b988330115011e4b54e1205e26038f6', '2015-08-20 03:54:18'),
('75e9c9d625e211e5ade8ace010142f18', '待办管理', '/memorandum/initMemorandum', 15, '待办管理', 'system', 'icon-note', '2b988330115011e4b54e1205e26038f6', '2015-08-20 03:54:18'),
('76a70ce499ad11e6b100142d27fd7e9e', 'EasyUI demo', 'http://www.jeasyui.com/demo/main/index.php', 25, 'EasyUI demo', 'web', NULL, '2b988330115011e4b54e1205e26038f6', '2016-10-24 05:48:22'),
('8f5f48112f5511e487e35a38088274be', '理财报表', '/manageMoney/manageMoneyReport', 6, '理财报表', 'system', 'icon-money', '2b988330115011e4b54e1205e26038f6', '2015-08-20 03:54:18'),
('e836b5f118b811e49846a77e8008a423', '菜单管理', '/menu/queryMenu', 3, '旧菜单管理', 'system', 'icon-menu', '2b988330115011e4b54e1205e26038f6', '2015-08-20 03:54:18'),
('e8c5e60198ee11e6b100142d27fd7e9e', 'easyui教程', 'http://www.runoob.com/jeasyui/jeasyui-form-form1.html', 20, 'http://www.runoob.com/jeasyui/jeasyui-form-form1.html', 'web', NULL, '2b988330115011e4b54e1205e26038f6', '2016-10-23 07:04:18'),
('ef4c55bd990611e6b100142d27fd7e9e', '登录用户', '/loginUser/loginuser', 24, '查询登录的用户', 'system', 'icon-role', '2b988330115011e4b54e1205e26038f6', '2016-10-23 09:56:17'),
('f6c2e9a8751611e484f9492f0fbf919a', '小图标下载', 'http://www.easyicon.net/language.zh-cn/', 10, 'http://www.easyicon.net/language.zh-cn/', 'web', 'icon-globe', '2b988330115011e4b54e1205e26038f6', '2015-08-20 03:54:18');

-- --------------------------------------------------------

--
-- 表的结构 `mvc_role_info`
--

CREATE TABLE IF NOT EXISTS `mvc_role_info` (
  `role_id` varchar(32) NOT NULL,
  `role_name` varchar(100) DEFAULT NULL,
  `role_desc` varchar(500) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `creator` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `mvc_role_info`
--

INSERT INTO `mvc_role_info` (`role_id`, `role_name`, `role_desc`, `create_time`, `creator`) VALUES
('1', '超级用户', '此用户厉害了', NULL, NULL),
('2', '管理员', '此用户可以管理系统', NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `mvc_role_menu`
--

CREATE TABLE IF NOT EXISTS `mvc_role_menu` (
  `role_menu_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `menu_id` varchar(32) DEFAULT NULL COMMENT '菜单id',
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `mvc_user_info`
--

CREATE TABLE IF NOT EXISTS `mvc_user_info` (
  `user_id` varchar(32) NOT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `password` varchar(16) DEFAULT '12345678',
  `true_name` varchar(50) DEFAULT NULL,
  `mail` varchar(200) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `dept_id` varchar(32) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_level` varchar(1) DEFAULT NULL,
  `file_name` varchar(50) DEFAULT NULL,
  `file_byte` longblob,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `mvc_user_info`
--

INSERT INTO `mvc_user_info` (`user_id`, `user_name`, `password`, `true_name`, `mail`, `phone`, `dept_id`, `create_time`, `update_time`, `user_level`, `file_name`, `file_byte`) VALUES
('2b988330115011e4b54e1205e26038f6', 'system', '123', 'system', 'dfc668@sina.com', '15010183134', '', '2014-07-22 03:27:56', '2015-08-13 06:06:57', '0', '', ''),
('2ddcb567939611e6a8df142d27fd7e9e', 'xifan2016', '12345678', '李锡钒', '1359704355@qq.com', '18814128405', 'd10003', '2016-10-16 11:46:31', '2016-10-24 03:13:38', '0', NULL, NULL),
('8abb70619aac11e6b100142d27fd7e9e', 'testtest', '12345678', 'test', '1359704355@qq.com', '18814128405', 'd10002', '2016-10-25 12:14:20', '2016-10-25 12:14:20', '1', NULL, NULL),
('a5c3ac5d999c11e6b100142d27fd7e9e', '1      1', '12345678', '111', '122312@q12.r', '  2342 34', 'd10002', '2016-10-24 03:48:00', '2016-10-24 03:48:00', '0', NULL, NULL),
('f10c3ad694fb11e68ac8142d27fd7e9e', 'rice888', '12345678', '8560025', '1359704355@qq.com', '18814128405', 'd10002', '2016-10-18 06:27:29', '2016-10-20 07:55:23', '0', 'update_user.php', 0x3c3f7068700d0a0d0a246964203d20696e7476616c28245f524551554553545b276964275d293b0d0a2466697273746e616d65203d20245f524551554553545b2766697273746e616d65275d3b0d0a246c6173746e616d65203d20245f524551554553545b276c6173746e616d65275d3b0d0a2470686f6e65203d20245f524551554553545b2770686f6e65275d3b0d0a24656d61696c203d20245f524551554553545b27656d61696c275d3b0d0a0d0a696e636c7564652027636f6e6e2e706870273b0d0a0d0a2473716c203d2022757064617465207573657273207365742066697273746e616d653d272466697273746e616d65272c6c6173746e616d653d27246c6173746e616d65272c70686f6e653d272470686f6e65272c656d61696c3d2724656d61696c272077686572652069643d246964223b0d0a24726573756c74203d20406d7973716c5f7175657279282473716c293b0d0a6966202824726573756c74297b0d0a096563686f206a736f6e5f656e636f6465286172726179282773756363657373273d3e7472756529293b0d0a7d20656c7365207b0d0a096563686f206a736f6e5f656e636f646528617272617928276d7367273d3e27536f6d65206572726f7273206f6363757265642e2729293b0d0a7d0d0a3f3e),
('f8340488948411e6bf97142d27fd7e9e', 'lixifan', 'lixifan', 'lixifan', '1359704355@qq.com', '18814128405', 'd10001', '2016-10-17 16:15:51', '2016-10-20 07:41:17', '0', NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `mvc_user_role`
--

CREATE TABLE IF NOT EXISTS `mvc_user_role` (
  `user_role_id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`user_role_id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `mvc_user_role`
--

INSERT INTO `mvc_user_role` (`user_role_id`, `user_id`, `role_id`) VALUES
('6e64191694fb11e68ac8142d27fd7e9e', '2ddcb567939611e6a8df142d27fd7e9e', '1');

--
-- 限制导出的表
--

--
-- 限制表 `mvc_dictionary_detail`
--
ALTER TABLE `mvc_dictionary_detail`
  ADD CONSTRAINT `mvc_dictionary_detail_ibfk_1` FOREIGN KEY (`dictionary_id`) REFERENCES `mvc_dictionary` (`dictionary_id`);

--
-- 限制表 `mvc_manage_money`
--
ALTER TABLE `mvc_manage_money`
  ADD CONSTRAINT `FK_manage_money` FOREIGN KEY (`take_id`) REFERENCES `mvc_user_info` (`user_id`);

--
-- 限制表 `mvc_role_menu`
--
ALTER TABLE `mvc_role_menu`
  ADD CONSTRAINT `mvc_role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `mvc_role_info` (`role_id`),
  ADD CONSTRAINT `mvc_role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `mvc_menu_info` (`menu_id`);

--
-- 限制表 `mvc_user_role`
--
ALTER TABLE `mvc_user_role`
  ADD CONSTRAINT `mvc_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `mvc_user_info` (`user_id`),
  ADD CONSTRAINT `mvc_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `mvc_role_info` (`role_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
