DROP TABLE IF EXISTS `iartisan_admin_template`.`system_log`;
CREATE TABLE `iartisan_admin_template`.`system_log` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` varchar(48) NOT NULL COMMENT '用户ID',
  `USER_NAME` varchar(50) NOT NULL COMMENT '用户名',
  `METHOD` varchar(150) DEFAULT NULL COMMENT '操作方法',
  `METHOD_DESC` varchar(50) DEFAULT NULL COMMENT '操作方法说明',
  `IP` varchar(17) DEFAULT NULL COMMENT '操作IP',
  `START_TIME` datetime DEFAULT NULL COMMENT '发起时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='日志表'