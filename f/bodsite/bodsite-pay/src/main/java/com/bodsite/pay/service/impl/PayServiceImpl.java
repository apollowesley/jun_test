package com.bodsite.pay.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bodsite.common.constant.PayConstants;
import com.bodsite.common.utils.VerificationUtil;
import com.bodsite.pay.dao.AlipayDao;
import com.bodsite.pay.dao.WxpayDao;
import com.bodsite.pay.service.PayService;
import com.bodsite.pay.vo.AlipayVo;
import com.bodsite.pay.vo.WxPrePayVo;
import com.bodsite.pay.vo.WxResultVo;

@Service("payService")
public class PayServiceImpl implements PayService {
	@Autowired
	private AlipayDao alipayDao;
	@Autowired
	private WxpayDao wxpayDao;

	// -------------------------------------支付宝-------------------------------------

	/**
	 * 建立支付宝支付form表单
	 * 
	 * @author bod
	 */
	@Override
	public String buildAlipayForm(AlipayVo alipayVo) {
		verification(alipayVo);
		return alipayDao.buildAlipayForm(alipayVo, getSing(alipayVo.getSign_type()));
	}

	/**
	 * 建立支付宝支付Map
	 * 
	 * @author bod
	 */
	@Override
	public Map<String, String> buildAlipayMap(AlipayVo alipayVo) {
		verification(alipayVo);
		return alipayDao.bulidAlipayMap(alipayVo);
	}

	/**
	 * 校验支付宝参数
	 * 
	 * @author bod
	 */
	private void verification(AlipayVo alipayVo) {
		VerificationUtil.notNull(alipayVo);
		VerificationUtil.isNotBlank(alipayVo.getOut_trade_no(), "商户订单号不能为空！");
		VerificationUtil.isNotBlank(alipayVo.getSubject(), "商品名称不能为空！");
		VerificationUtil.isNotBlank(alipayVo.getTotal_fee(), "交易金额不能为空！");
	}

	/**
	 * 验证支付宝回调签名是否正确
	 * 
	 * @author bod
	 */
	public boolean verify(Map<String, String> receiveMap, String singType) {
		return alipayDao.verify(receiveMap, getSing(singType));
	}

	/**
	 * 获取加密/解密类型
	 * 
	 * @author bod
	 */
	private PayConstants.SIGN getSing(String singType) {
		PayConstants.SIGN sing = PayConstants.SIGN.getSING(singType);
		VerificationUtil.notNull(sing);
		return sing;
	}

	// -------------------------------------微信-----------------------------------------

	/**
	 * 微信-预支付
	 * 
	 * @author bod
	 */
	public WxResultVo prepay(WxPrePayVo wxPrePayVo) {
		verificationWx(wxPrePayVo);
		return wxpayDao.prepay(wxPrePayVo);
	}

	/**
	 * 验证微信预支付类
	 * 
	 * @author bod
	 */
	private void verificationWx(WxPrePayVo wxPrePayVo) {
		VerificationUtil.notNull(wxPrePayVo);
		VerificationUtil.isNotBlank(wxPrePayVo.getBody(), "商户描述！");
		VerificationUtil.isNotBlank(wxPrePayVo.getOut_trade_no(), "商户订单号不能为空！");
		VerificationUtil.isNotBlank(wxPrePayVo.getTotal_fee(), "交易金额不能为空！");
		VerificationUtil.isNotBlank(wxPrePayVo.getNotify_url(), "回调地址不能为空！");
		VerificationUtil.isNotBlank(wxPrePayVo.getTrade_type(), "交易类型不能为空！");
		VerificationUtil.isNotBlank(wxPrePayVo.getSpbill_create_ip(), "终端IP不能为空！");
		checkWxType(wxPrePayVo);
	}

	/**
	 * 微信-检查交易类型，以及类型相关必传参数
	 * 
	 * @author bod
	 */
	private void checkWxType(WxPrePayVo wxPrePayVo) {
		PayConstants.WX_TRADE_TYPE wxTradeType = PayConstants.WX_TRADE_TYPE.getTRADE_TYPE(wxPrePayVo.getTrade_type());
		VerificationUtil.notNull(wxTradeType);
		if (PayConstants.WX_TRADE_TYPE.JSAPI.name().equals(wxTradeType.name())) {
			VerificationUtil.isNotBlank(wxPrePayVo.getOpenid(), "用户标示不能为空！");
		} else if (PayConstants.WX_TRADE_TYPE.NATIVE.name().equals(wxTradeType.name())) {
			VerificationUtil.isNotBlank(wxPrePayVo.getProduct_id(), "商品ID不能为空！");
		}
	}

	/**
	 * 获取微信交易类型
	 * 
	 * @author bod
	 */
	private PayConstants.WX_TRADE_TYPE getWxTradeType(String tradeType) {
		PayConstants.WX_TRADE_TYPE wxTradeType = PayConstants.WX_TRADE_TYPE.getTRADE_TYPE(tradeType);
		VerificationUtil.notNull(wxTradeType);
		return wxTradeType;
	}

	/**
	 * 校验微信回调签名
	 * 
	 * @author bod
	 */
	public boolean verifyWx(Map<String, String> pramMap, String tradeType) {
		return wxpayDao.verifyWx(pramMap, getWxTradeType(tradeType));
	}

}
