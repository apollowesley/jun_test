package com.cn.zjut.b2c.service;

import java.util.Map;

public interface QueryOrderServiceInter {
	
	public Map<String, String> getRequestData(String orderId,String txnTime);
	
	public Map<String, String> getResponseData(Map<String, String> reqData);

}
