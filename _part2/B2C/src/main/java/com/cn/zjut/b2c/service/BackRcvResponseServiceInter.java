package com.cn.zjut.b2c.service;

import java.util.Map;


public interface BackRcvResponseServiceInter {
	/**
	 * 
	 * @param encoding
	 * @param reqParam
	 * @return
	 * @throws Exception
	 */
	public String BackRcvValidate(String encoding,Map<String, String> reqParam) throws Exception;

}
