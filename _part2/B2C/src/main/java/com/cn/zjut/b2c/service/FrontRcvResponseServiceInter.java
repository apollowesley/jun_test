package com.cn.zjut.b2c.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.cn.zjut.b2c.pojo.ResponseMessage;

public interface FrontRcvResponseServiceInter {
	/**
	 * 根据编码获取对应编码的jsp可以避免乱码
	 * @param encoding
	 * @return
	 */
	public String getResultPageURL(String encoding);
	/**
	 * 获取页面参数的html文本
	 * @param respParam
	 * @param encoding
	 * @return 页面参数的html文本
	 * @throws UnsupportedEncodingException
	 */
	@Deprecated
	public String getResultPageParam(Map<String, String> respParam, String encoding) throws UnsupportedEncodingException;

	/**
	 * 保存响应参数到数据库中
	 * @param responseMessage
	 */
	public void insertResponseMessage(ResponseMessage responseMessage);

}
