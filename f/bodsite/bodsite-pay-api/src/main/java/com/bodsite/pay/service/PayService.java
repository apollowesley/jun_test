package com.bodsite.pay.service;

import java.util.Map;

import com.bodsite.pay.vo.AlipayVo;
import com.bodsite.pay.vo.WxPrePayVo;
import com.bodsite.pay.vo.WxResultVo;

public interface PayService {

	public String buildAlipayForm(AlipayVo alipayVo);

	Map<String, String> buildAlipayMap(AlipayVo alipayVo);
	
	public boolean verify(Map<String, String> receiveMap,String singType);
	
	public WxResultVo prepay(WxPrePayVo wxPrePayVo);

	public boolean verifyWx(Map<String, String> pramMap, String tradeType);

}
