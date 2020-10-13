package com.bodsite.pay.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bodsite.common.constant.PayConstants;
import com.bodsite.pay.alipay.util.AlipayNotify;
import com.bodsite.pay.alipay.util.AlipaySubmit;
import com.bodsite.pay.convert.AlipayConvert;
import com.bodsite.pay.dao.AlipayDao;
import com.bodsite.pay.vo.AlipayVo;

@Repository
public class AlipayDaoImpl implements AlipayDao{
	@Autowired
	private AlipayConvert alipayConvert;
	
	/**
	 * 校验签名 
	* @author bod
	 */
	@Override
	public boolean verify(Map<String, String> params,PayConstants.SIGN signType) {
		return AlipayNotify.verify(params,signType.name());
	}
	
	/**
	 * 建立支付宝支付form表单
	* @author bod
	 */
	@Override
	public String buildAlipayForm(AlipayVo alipayVo,PayConstants.SIGN signType) {
		Map<String, String> paramMap = bulidAlipayMap(alipayVo);
		return AlipaySubmit.buildRequest(paramMap, "post", "确认",signType.name());
	}
	
	/**
	 * 建立支付宝支付Map
	* @author bod
	 */
	@Override
	public Map<String, String> bulidAlipayMap(AlipayVo alipayVo) {
		Map<String,String> paramMap = alipayConvert.aliPayVoToMap(alipayVo);
		return paramMap;
	}

}
