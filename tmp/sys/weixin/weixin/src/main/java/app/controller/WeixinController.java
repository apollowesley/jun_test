package app.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import weixin.core.ValidatePojo;
import weixin.core.message.BaseReqMessage;
import weixin.core.message.BaseRespMessage;
import weixin.core.message.ReqMessageFactory;
import weixin.core.util.MessageUtil;
import weixin.core.util.SignUtil;
import app.service.MainMessageService;

/**
 * 微信公众平台
 */
@Controller
public class WeixinController {
	// 提供给微信公众平台的token
	private static final String TOKEN = "7DHWY6D6XYHE";
	
	@Autowired
	private MainMessageService mainMessageService;

	/**
	 * 处理微信服务器发来的验证请求
	 * 
	 * @param validatePojo
	 * @return 验证成功返回随机字符串
	 */
	@RequestMapping(value = "service.do", method = RequestMethod.GET
	// 这句一定要加,如果不加默认会返回Content-Type=text/html导致token认证失败
	, produces = "text/plain")
	public @ResponseBody
	String validate(ValidatePojo validatePojo) {
		if (SignUtil.checkSignature(validatePojo, TOKEN)) {
			return validatePojo.getEchostr();
		}
		return "";
	}

	/**
	 * 处理微信服务器发来的请求
	 * 
	 * @return 返回响应消息
	 */
	@RequestMapping(value = "service.do", method = RequestMethod.POST)
	public @ResponseBody
	BaseRespMessage service(HttpServletRequest request) {
		Map<String, String> xmlMap = MessageUtil.parseXml(request);
		
		BaseReqMessage reqMessage = ReqMessageFactory.build(xmlMap);
		
		return mainMessageService.service(reqMessage);
	}

}
