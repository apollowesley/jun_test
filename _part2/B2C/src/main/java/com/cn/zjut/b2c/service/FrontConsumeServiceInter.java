package com.cn.zjut.b2c.service;

import java.util.Map;

public interface FrontConsumeServiceInter {
	/**
	 * 获取封装好的html文件的string字符串，基于sdk
	 * @param merId 商家id
	 * @param txnAmt 交易金额
	 * @return 封装好的html文件的string字符串，
	 */
	public String getConsumeHtml(String merId,String txnAmt);
	/**
	 * 封装付款的接口参数
	 * @param merId 商家id
	 * @param txnAmt 交易金额
	 * @return Map<String, String> 得到付款的接口参数
	 */
	public Map<String, String> getConsumeDataMap(String merId,String txnAmt);
	/**
	 * 从配置文档中得到付款地址
	 * @return 付款地址
	 */
	public String getFrontRequestUrl();

}
