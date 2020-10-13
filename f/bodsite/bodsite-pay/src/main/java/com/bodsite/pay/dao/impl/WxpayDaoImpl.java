package com.bodsite.pay.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bodsite.common.constant.PayConstants.WX_TRADE_TYPE;
import com.bodsite.pay.convert.WxConvert;
import com.bodsite.pay.dao.WxpayDao;
import com.bodsite.pay.vo.WxPrePayVo;
import com.bodsite.pay.vo.WxResultVo;
import com.bodsite.pay.vo.WxReturnPrepayVo;
import com.bodsite.pay.wxpay.common.Configure;
import com.bodsite.pay.wxpay.common.WxNotify;
import com.bodsite.pay.wxpay.common.WxSubmit;


@Repository
public class WxpayDaoImpl implements WxpayDao {
	@Autowired
	private WxConvert wxConvert;
	/**
	 * 生成预支付结果 
	* @author bod
	 */
	@Override
	public WxResultVo prepay(WxPrePayVo wxPrePayVo) {
		WxReturnPrepayVo wxReturnPrepayVo =  WxSubmit.prepay(wxPrePayVo, wxConvert.initWxPrePayVo(wxPrePayVo));
		return wxConvert.prepayToResultVo(wxReturnPrepayVo);
	}
	
	/**
	 * 校验微信回调签名
	 * 
	 * @author bod
	 */
	@Override
	public boolean verifyWx(Map<String, String> pramMap, WX_TRADE_TYPE wxTradeType) {
		return WxNotify.verify(pramMap,getKeyByTreadType(wxTradeType));
	}
	
	private String getKeyByTreadType(WX_TRADE_TYPE wxTradeType){
		if(WX_TRADE_TYPE.APP.name().equals(wxTradeType.name())){
			return Configure.openKey;
		}else{
			return Configure.key;
		}
	}

}
