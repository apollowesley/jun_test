package com.bodsite.pay.privoder;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bodsite.common.constant.PayConstants;
import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;
import com.bodsite.pay.service.PayService;
import com.bodsite.pay.vo.WxPrePayVo;
import com.bodsite.pay.vo.WxResultVo;

public class PayConsumer {
	private static Logger logger = LoggerFactory.getLogger(PayConsumer.class);
	private ClassPathXmlApplicationContext context;

	public void setUp() {
		String[] files = { "classpath*:/spring/application-service.xml" };
		context = new ClassPathXmlApplicationContext(files);
	}
	
	
	public void handler(){
		PayService payService = (PayService) context.getBean("payService");
		WxPrePayVo wxPrePayVo = new WxPrePayVo();
		wxPrePayVo.setTotal_fee("1");
		wxPrePayVo.setBody("测试描述");
		wxPrePayVo.setOut_trade_no(String.valueOf(System.currentTimeMillis()));
		wxPrePayVo.setNotify_url("http://127.0.0.1/trade/notify/weixin");
		wxPrePayVo.setSpbill_create_ip("127.0.0.1");
		
		wxPrePayVo.setTrade_type(PayConstants.WX_TRADE_TYPE.NATIVE.name());
		//wxPrePayVo.setProduct_id("18888");//NATIVE需要
		//wxPrePayVo.setOpenid("213111");//JSAPI需要
		WxResultVo wxResultVo= payService.prepay(wxPrePayVo);
		logger.info(wxResultVo.toString());
	}
	
    public void setDown() {
    	context.stop();
    	context.close();
    }

	public static void main(String[] args) {
		PayConsumer payConsumer = new PayConsumer();
		payConsumer.setUp();
		payConsumer.handler();
		payConsumer.setDown();
	}
}
