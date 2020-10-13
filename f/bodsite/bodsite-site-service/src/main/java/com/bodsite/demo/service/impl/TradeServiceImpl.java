package com.bodsite.demo.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bodsite.common.config.properties.SystemProperty;
import com.bodsite.common.constant.PayConstants;
import com.bodsite.common.constant.PayConstants.ALI_TRADE_STATUS;
import com.bodsite.common.constant.PayConstants.PAY_TYPE;
import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;
import com.bodsite.common.utils.BeanUtil;
import com.bodsite.common.utils.BigDecimalUtils;
import com.bodsite.demo.service.TradeService;
import com.bodsite.demo.vo.OrderVo;
import com.bodsite.pay.service.PayService;
import com.bodsite.pay.vo.AlipayReturnVo;
import com.bodsite.pay.vo.AlipayVo;
import com.bodsite.pay.vo.WxPrePayVo;
import com.bodsite.pay.vo.WxResultVo;
import com.bodsite.pay.vo.WxReturnVo;

/**
 * 
 * @Description: 交易demo（原则上应该建立交易服务模块,这里用于测试说明。。。）
 * @author bod
 * @date 2017年1月5日 上午10:41:04
 *
 */
@Service("tradeService")
public class TradeServiceImpl implements TradeService {
	private static final Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);
	private static final String notifyUrl = SystemProperty.getProperty("pay.notify.url");// 异步回调地址
	private static final String returnUrl = SystemProperty.getProperty("pay.return.url");// 同步回调地址
	@Autowired
	private PayService paySrevice;

	/**
	 * 购买商品
	 * 
	 * @param orderVo
	 * @author bod
	 */
	@Transactional(timeout = 8000, propagation = Propagation.REQUIRED)
	@Override
	public Map<String, String> toOrder(OrderVo orderVo) {
		// 处理订单相关逻辑
		// 校验orderVo数据是否完整
		// 校验商品、商家是否有效，获取商品信息计算价格
		// 如果有优惠券红包相关，做相关价格运算
		// 生成唯一支付单号（单号要有意义，包含日期，当前订单数等信息）
		// 保存订单、快照相关
		// 如果是购物车购买，标记为已购买
		// 。。。。。。。
		// 生成支付相关。。
		return toPrePay(orderVo);
	}

	/**
	 * 生成支付相关
	* @author bod     
	* @throws
	 */
	private Map<String, String> toPrePay(OrderVo orderVo) {
		PAY_TYPE patType = PAY_TYPE.getPAY_TYPE(orderVo.getPayType().toUpperCase());
		switch (patType) {
		case AILPAY_PC:
		case AILPAY_PC_R:
			return aliPayForm("测试商品", "0.01", String.valueOf(System.currentTimeMillis()),patType);
		case AILPAY_APP:
			return aliPayMap("测试商品", "0.01", String.valueOf(System.currentTimeMillis()),patType);
		case WEIXIN_JSAPI:
		case WEIXIN_NATIVE:
		case WEIXIN_APP:
			return wxPrePay("测试商品", "0.01", String.valueOf(System.currentTimeMillis()),"127.0.0.1",patType);
		case BACNK:
			// 暂无
			return null;
		default:
			return null;
		}
	}

	/**
	 * 支付相关信息，获取支付form
	 * 
	 * @author bod
	 */
	public Map<String,String> aliPayForm(String title, String money, String tradeNo, PayConstants.PAY_TYPE payType) {
		AlipayVo alipayVo = new AlipayVo();
		alipayVo.setSubject(title);
		alipayVo.setTotal_fee(money);
		alipayVo.setOut_trade_no(tradeNo);
		alipayVo.setNotify_url(notifyUrl + payType.name().toLowerCase());
		alipayVo.setReturn_url(returnUrl + payType.name().toLowerCase());
		alipayVo.setSign_type(payType.getValue());
		Map<String, String> buyMap = new HashMap<String, String>();
		buyMap.put("payForm",paySrevice.buildAlipayForm(alipayVo));
		return buyMap;
	}

	/**
	 * APP - RSA 加密
	* @author bod     
	* @throws
	 */
	public Map<String,String> aliPayMap(String title, String money, String tradeNo, PayConstants.PAY_TYPE payType){
		AlipayVo alipayVo = new AlipayVo();
		alipayVo.setSubject(title);
		alipayVo.setTotal_fee(money);
		alipayVo.setOut_trade_no(tradeNo);
		alipayVo.setNotify_url(notifyUrl + payType.name().toLowerCase());
		alipayVo.setSign_type(PayConstants.SIGN.RSA.name());
		return paySrevice.buildAlipayMap(alipayVo);
	}
	
	
	/**
	 * 微信预支付
	* @author bod     
	* @throws
	 */
	public Map<String,String> wxPrePay(String title, String money, String tradeNo,String ip, PayConstants.PAY_TYPE payType) {
		WxPrePayVo wxPrePayVo = new WxPrePayVo();
		wxPrePayVo.setTotal_fee(String.valueOf(BigDecimalUtils.mul(money, 100).intValue()));
		wxPrePayVo.setBody(title);
		wxPrePayVo.setOut_trade_no(String.valueOf(System.currentTimeMillis()));
		wxPrePayVo.setNotify_url(notifyUrl + payType.name().toLowerCase());
		wxPrePayVo.setSpbill_create_ip(ip);
		
		wxPrePayVo.setTrade_type(payType.getValue());
		wxPrePayVo.setProduct_id("18888");//NATIVE 需要
		//wxPrePayVo.setOpenid("11111");;//JSAPI 需要
		WxResultVo WxResultVo = paySrevice.prepay(wxPrePayVo);
		return BeanUtil.bean2Map(WxResultVo);
	}
	
	




	/**
	 * 支付回调校验
	* @author bod
	 */
	@Override
	public String callBackTrade(Map<String, String> pramMap, String payType) {
		PAY_TYPE patType = PAY_TYPE.getPAY_TYPE(payType);
		switch (patType) {
		case AILPAY_PC:
		case AILPAY_PC_R:
		case AILPAY_APP:
			return callBackTraedAli(pramMap);
		case WEIXIN_JSAPI:
		case WEIXIN_NATIVE:
		case WEIXIN_APP:
			return callBackTradeWx(pramMap);
		case BACNK:
			// 暂无
			return null;
		default:
			return null;
		}
	}

	/**
	 * 支付宝回调校验
	* @author bod     
	* @throws
	 */
	private String callBackTraedAli(Map<String, String> pramMap) {
		AlipayReturnVo returnVo = new AlipayReturnVo();
		try {
			BeanUtils.populate(returnVo, pramMap);
		} catch (Exception e) {
			logger.error("error..", e);
		}
		if (ALI_TRADE_STATUS.TRADE_FINISHED.equals(returnVo.getTrade_status())
				|| ALI_TRADE_STATUS.TRADE_SUCCESS.equals(returnVo.getTrade_status())) {
			logger.error("非支付通知!");
			return "fail";
		}
		if (paySrevice.verify(pramMap, returnVo.getSign_type())) {
			return "success";
		}
		throw new RuntimeException("验证失败!");
	}
	
	/**
	 * 微信回调校验
	* @author bod     
	* @throws
	 */
	private String callBackTradeWx(Map<String, String> pramMap) {
		WxReturnVo wxReturnVo = new WxReturnVo();
		try {
			BeanUtils.populate(wxReturnVo, pramMap);
		} catch (Exception e) {
			logger.error("error..", e);
		}
		if (!(PayConstants.RESULT_CODE.SUCCESS.name().equals(wxReturnVo.getResult_code()))) {
			logger.error("call Back Trade " + wxReturnVo.toString());
			throw new RuntimeException("非支付通知!");
		}
		if (paySrevice.verifyWx(pramMap, wxReturnVo.getTrade_type())) {
			return "<xml>\n" + "<return_code><![CDATA[SUCCESS]]></return_code>\n"
					+ "<return_msg><![CDATA[OK]]></return_msg>\n" + "</xml>";
		}
		throw new RuntimeException("验证失败!");
	}
}
