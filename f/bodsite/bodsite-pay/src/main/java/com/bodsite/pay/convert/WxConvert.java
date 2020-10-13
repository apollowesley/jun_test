package com.bodsite.pay.convert;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bodsite.common.constant.PayConstants;
import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;
import com.bodsite.common.utils.BeanUtil;
import com.bodsite.pay.vo.WxPrePayVo;
import com.bodsite.pay.vo.WxResultVo;
import com.bodsite.pay.vo.WxReturnPrepayVo;
import com.bodsite.pay.wxpay.common.Configure;
import com.bodsite.pay.wxpay.common.RandomStringGenerator;
import com.bodsite.pay.wxpay.common.Signature;

@Repository
public class WxConvert {
	Logger logger = LoggerFactory.getLogger(WxConvert.class);

	/**
	 * 初始化预支付配置参数 
	* @author bod     
	* @throws
	 */
	public String initWxPrePayVo(WxPrePayVo wxPrePayVo) {
		String key;
		if (wxPrePayVo.getTrade_type().equals(PayConstants.WX_TRADE_TYPE.APP.name())) {
			wxPrePayVo.setAppid(Configure.openAppID);
			wxPrePayVo.setMch_id(Configure.openMchID);
			key = Configure.openKey;
		} else {
			wxPrePayVo.setAppid(Configure.appID);
			wxPrePayVo.setMch_id(Configure.mchID);
			key = Configure.key;
		}
		wxPrePayVo.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
		return key;
	}

	/**
	 * 转换返回结果类
	* @author bod     
	* @throws
	 */
	public WxResultVo prepayToResultVo(WxReturnPrepayVo wxReturnPrepayVo) {
		WxResultVo wxResultVo = new WxResultVo();
		switch (PayConstants.WX_TRADE_TYPE.getTRADE_TYPE(wxReturnPrepayVo.getTrade_type())) {
		case JSAPI:
			initJsResultVo(wxReturnPrepayVo,wxResultVo);
			break;
		case NATIVE:
			wxResultVo.setCodeUrl(wxReturnPrepayVo.getCode_url());
			break;
		case APP:
			initAppResultVo(wxReturnPrepayVo,wxResultVo);
			break;
		default:
			return null;
		}
		return wxResultVo;
	}
	
	/**
	 * 封装app支付返回结果
	 * api:https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12&index=2
	* @author bod     
	* @throws
	 */
	private void initAppResultVo(WxReturnPrepayVo wxReturnPrepayVo,WxResultVo wxResultVo){
		wxResultVo.setAppid(Configure.openAppID);
		wxResultVo.setPartnerid(Configure.openMchID);
		wxResultVo.setTimestamp(String.valueOf(System.currentTimeMillis()/1000));
		wxResultVo.setNoncestr(RandomStringGenerator.getRandomStringByLength(32));
		wxResultVo.setPrepayid(wxReturnPrepayVo.getPrepay_id());
		Map<String,String> map = BeanUtil.bean2MapStr(wxResultVo);
		wxResultVo.setPackages("Sign=WXPay");//APP 固定值
		map.put("package", wxResultVo.getPackages());//关键字不能再类中定义
		String sign = Signature.getSign(map, Configure.openKey);
		wxResultVo.setSign(sign);
	}

	/**
	 * 封装jsapi支付返回结果
	 * api:https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7&index=6
	* @author bod     
	* @throws
	 */
	private void initJsResultVo(WxReturnPrepayVo wxReturnPrepayVo,WxResultVo wxResultVo){
		wxResultVo.setAppid(Configure.appID);
		wxResultVo.setTimestamp(String.valueOf(System.currentTimeMillis()/1000));
		wxResultVo.setSignType(PayConstants.SIGN.MD5.name());
		Map<String,String> map = BeanUtil.bean2MapStr(wxResultVo);
		wxResultVo.setPackages("prepay_id="+wxReturnPrepayVo.getPrepay_id());
		map.put("package", wxResultVo.getPackages());
		wxResultVo.setNoncestr(RandomStringGenerator.getRandomStringByLength(32));
		map.put("nonceStr", wxResultVo.getNoncestr());//jsapi是大写的Str app支付为小写!
		String sign = Signature.getSign(BeanUtil.bean2Map(wxResultVo), Configure.key);
		wxResultVo.setSign(sign);
	}
}
