package com.bodsite.pay.wxpay.common;

import java.util.Map;

import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;
import com.bodsite.pay.exception.PayException;
import com.bodsite.pay.wxpay.common.Signature;

public class WxNotify {
	private static Logger logger = LoggerFactory.getLogger(WxNotify.class);
	 /**
     * 验证微信回调
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verify(Map<String, String> params,String key) {
    	try {
			return Signature.checkIsSignValidFromMap(params, key);
		} catch (Exception e) {
			logger.error("wx notify verify error...",e);
			throw new PayException(PayException.PAY_EXPECTION.PAY_RETURN_ERROR, e);
		}
    }


}
