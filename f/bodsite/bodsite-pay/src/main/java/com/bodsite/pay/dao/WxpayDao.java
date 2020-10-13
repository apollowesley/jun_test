package com.bodsite.pay.dao;

import java.util.Map;

import com.bodsite.common.constant.PayConstants.WX_TRADE_TYPE;
import com.bodsite.pay.vo.WxPrePayVo;
import com.bodsite.pay.vo.WxResultVo;

public interface WxpayDao {

	WxResultVo prepay(WxPrePayVo wxPrePayVo);

	boolean verifyWx(Map<String, String> pramMap, WX_TRADE_TYPE wxTradeType);

}
