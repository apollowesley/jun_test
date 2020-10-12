-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.26 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 account.netease_user 结构
CREATE TABLE IF NOT EXISTS `netease_user` (
  `fID` varchar(50) NOT NULL COMMENT 'ID',
  `fType` varchar(50) DEFAULT NULL COMMENT 'QQ;weibo;weixin;sim',
  `fName` varchar(50) DEFAULT NULL COMMENT '姓名',
  `passwd` varchar(50) DEFAULT NULL COMMENT '密码',
  `fPhoneNumber` varchar(20) DEFAULT NULL COMMENT '电话',
  `fAddress` varchar(200) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`fID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
