package com.bodsite.demo.service;

import java.util.Map;


import com.bodsite.demo.vo.OrderVo;

public interface TradeService{
	public Map<String, String> toOrder(OrderVo orderVo);
	
	public String callBackTrade(Map<String, String> pramMap, String payType);
	
}
