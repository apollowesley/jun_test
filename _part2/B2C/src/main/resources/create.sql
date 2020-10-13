CREATE  TABLE IF NOT EXISTS `acp`.`response_message` (
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT ,
  `version` VARCHAR(50) NULL COMMENT '版本号' ,
  `encoding` VARCHAR(100) NULL COMMENT '编码方式' ,
  `certId` VARCHAR(100) NULL COMMENT '证书ID' ,
  `signature` VARCHAR(400) NULL COMMENT '签名' ,
  `signMethod` VARCHAR(50) NULL COMMENT '签名方法' ,
  `txnType` VARCHAR(50) NULL COMMENT '交易类型' ,
  `txnSubType` VARCHAR(50) NULL COMMENT '交易子类' ,
  `bizType` VARCHAR(50) NULL COMMENT '产品类型', 
  `accessType` VARCHAR(50) NULL COMMENT '接入类型' ,
  `merId` VARCHAR(50) NULL COMMENT '商户代码' ,
  `orderId` VARCHAR(50) NULL COMMENT '商户订单号' ,
  `origQryId` VARCHAR(50) NULL COMMENT '原始交易流水号' ,
  `txnTime` VARCHAR(50) NULL COMMENT '订单发送时间' ,
  `txnAmt` VARCHAR(50) NULL COMMENT '交易金额' ,
  `reqReserved` VARCHAR(50) NULL COMMENT '请求方保留域' ,
  `reserved` VARCHAR(50) NULL COMMENT '保留域' ,
  `queryId` VARCHAR(50) NULL COMMENT '银联交易流水号' ,
  `respCode` VARCHAR(50) NULL COMMENT '响应码' ,
  `respMsg` VARCHAR(50) NULL COMMENT '应答信息' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '应答报文';