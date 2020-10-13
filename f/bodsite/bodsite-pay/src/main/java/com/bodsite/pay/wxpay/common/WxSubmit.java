package com.bodsite.pay.wxpay.common;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.BeanUtils;
import org.xml.sax.SAXException;

import com.bodsite.common.constant.PayConstants;
import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;
import com.bodsite.common.utils.BeanUtil;
import com.bodsite.pay.exception.PayException;
import com.bodsite.pay.vo.WxPrePayVo;
import com.bodsite.pay.vo.WxReturnPrepayVo;
import com.bodsite.pay.wxpay.common.Configure;
import com.bodsite.pay.wxpay.common.HttpsRequest;
import com.bodsite.pay.wxpay.common.Signature;
import com.bodsite.pay.wxpay.common.XMLParser;

/**
 * 微信预支付提交类
* @author bod
* @date 2017年1月7日 上午10:24:22 
*
 */
public class WxSubmit {
	private static final Logger logger = LoggerFactory.getLogger(WxSubmit.class);

	/**
	 * 预支付 
	* @author bod     
	* @throws
	 */
	public static WxReturnPrepayVo prepay(WxPrePayVo wxPrePayVo, String key) {
		try {
			String sign = Signature.getSign(BeanUtil.bean2Map(wxPrePayVo), key);
			wxPrePayVo.setSign(sign);
			return check(new HttpsRequest().httpXmlRequest(Configure.prepayUrl, wxPrePayVo), key);
		} catch (Exception e) {
			logger.error("wx prepay create sign error...", e);
			throw new PayException(PayException.PAY_EXPECTION.PAY_PERPAY_ERROR, e);
		}
	}

	/**
	 * 校验返回信息
	* @author bod     
	* @throws
	 */
	private static WxReturnPrepayVo check(String responseString, String key) throws ParserConfigurationException,
			IOException, SAXException, IllegalAccessException, InvocationTargetException {
		Map<String, String> resultmap = XMLParser.getMapFromXML(responseString);
		WxReturnPrepayVo wxReturnPrepayVo = new WxReturnPrepayVo();
		BeanUtils.populate(wxReturnPrepayVo, resultmap);
		checkSign(resultmap, key, wxReturnPrepayVo);
		return wxReturnPrepayVo;
	}

	private static void checkSign(Map<String, String> resultmap, String key, WxReturnPrepayVo wxReturnPrepayVo)
			throws ParserConfigurationException, IOException, SAXException {
		if (!(PayConstants.RESULT_CODE.SUCCESS.name().equals(wxReturnPrepayVo.getResult_code())
				&& PayConstants.RESULT_CODE.SUCCESS.name().equals(wxReturnPrepayVo.getResult_code()))) {
			logger.error("wx prepay result msg: " + wxReturnPrepayVo.toString());
			throw new PayException(PayException.PAY_EXPECTION.PAY_PERPAY_ERROR);
		}
		if (!Signature.checkIsSignValidFromMap(resultmap, key)) {
			logger.error("wx prepay result checkIsSignValid error...");
			throw new PayException(PayException.PAY_EXPECTION.PAY_PERPAY_ERROR);
		}
	}

}
