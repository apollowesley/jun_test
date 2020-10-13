package com.bodsite.pay.dao;

import java.util.Map;

import com.bodsite.common.constant.PayConstants;
import com.bodsite.pay.vo.AlipayVo;

public interface AlipayDao {
	public boolean verify(Map<String, String> receiveMap,PayConstants.SIGN signType);
	public String buildAlipayForm(AlipayVo alipayVo,PayConstants.SIGN signType);
	Map<String, String> bulidAlipayMap(AlipayVo alipayVo);
}
