package com.bodsite.pay.convert;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;
import com.bodsite.common.utils.BeanUtil;
import com.bodsite.pay.alipay.config.AlipayConfig;
import com.bodsite.pay.alipay.util.AlipaySubmit;
import com.bodsite.pay.vo.AlipayVo;

@Repository
public class AlipayConvert {
	Logger logger = LoggerFactory.getLogger(AlipayConvert.class);

	/**
	 * 支付类转换为Map
	 * 
	 * @author bod
	 */
	public Map<String, String> aliPayVoToMap(AlipayVo alipayVo) {
		if (alipayVo == null) {
			return null;
		}
		initAlipayVo(alipayVo);
		return BeanUtil.bean2Map(alipayVo);
	}
	
	private void initAlipayVo(AlipayVo alipayVo) {
		try {
			alipayVo.setService(AlipayConfig.service);
			alipayVo.setPartner(AlipayConfig.partner);
			alipayVo.setSeller_id(AlipayConfig.partner);// 当签约账号就是收款账号时，请务必使用参数seller_id，即seller_id的值与partner的值相同。
			alipayVo.setPayment_type(AlipayConfig.payment_type);
			alipayVo.set_input_charset(AlipayConfig.input_charset);
			alipayVo.setAnti_phishing_key(AlipaySubmit.query_timestamp());
		} catch (Exception e) {
			logger.error("AlipaySubmit.query_timestamp error...", e);
		}
	}

}
