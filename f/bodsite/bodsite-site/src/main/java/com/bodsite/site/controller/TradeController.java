package com.bodsite.site.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bodsite.common.constant.PayConstants;
import com.bodsite.common.constant.PayConstants.PAY_TYPE;
import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;
import com.bodsite.common.utils.StringUtils;
import com.bodsite.core.rest.BaseRestController;
import com.bodsite.core.rest.http.RequestUtil;
import com.bodsite.core.rest.http.ResponseUtil;
import com.bodsite.demo.service.TradeService;
import com.bodsite.demo.vo.OrderVo;

/**
 * 交易类
* @author bod
* @date 2017年1月7日 下午5:11:47 
*
 */
@Controller
@RequestMapping("trade")
public class TradeController extends BaseRestController {
	Logger logger = LoggerFactory.getLogger(TradeController.class);
	@Autowired
	private TradeService tradeService;

	/**
	 * 下订单
	* @author bod     
	* @throws
	 */
	@RequestMapping(value = "toOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String toOrder(@Valid @RequestBody OrderVo orderVo) {
		Map<String, String> buyMap = tradeService.toOrder(orderVo);
		return ResponseUtil.buildSuccessJsonStr(buyMap);
	}

	/**
	 * 异步回调
	* @author bod     
	* @throws
	 */
	@RequestMapping(value = "notify/{payType}")
	public void notify(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("payType") String payType) {
		logger.info("notify call back :"+payType);
		callBackTrade(request,response,payType);
	}

	/**
	 * 同步回调
	* @author bod     
	* @throws
	 */
	@RequestMapping("return/{payType}")
	public String synReturn(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("payType") String payType) {
		logger.info("return call back :"+payType);
		callBackTrade(request,response,payType);
		return "redirect:http://www.baidu.com";
	}
	
	private void callBackTrade(HttpServletRequest request, HttpServletResponse response,String payType){
		payType = payType.toUpperCase();
		Map<String, String> pramMap = getMapByRequest(request, payType);
		String result = tradeService.callBackTrade(pramMap, payType);
		returnCallBack(response,result,payType);
		
	}

	/**
	 * 返回信息给第三方
	* @author bod     
	* @throws
	 */
	private void returnCallBack(HttpServletResponse response, String result, String paytype) {
		logger.info("return Call Back result:"+result+ " payType:"+paytype);
		if(StringUtils.isBlank(result)){
			return;
		}
		PayConstants.PAY_TYPE payType = PayConstants.PAY_TYPE.getPAY_TYPE(paytype);
		if (payType == PAY_TYPE.WEIXIN_APP || payType == PAY_TYPE.WEIXIN_JSAPI || payType == PAY_TYPE.WEIXIN_NATIVE) {
			response.setContentType("text/xml");
		}
		try {
			response.getWriter().print(result);
		} catch (IOException e) {
			logger.error("return call back payType:"+paytype, e);
		}
	}

	/**
	 * 获取回调返回参数
	 *  @author bod
	 */
	private Map<String, String> getMapByRequest(HttpServletRequest request, String paytype) {
		PayConstants.PAY_TYPE payType = PayConstants.PAY_TYPE.getPAY_TYPE(paytype);
		switch (payType) {
		case AILPAY_APP:
		case AILPAY_PC:
		case AILPAY_PC_R:
			return RequestUtil.getParameterMap(request);
		case WEIXIN_APP:
		case WEIXIN_JSAPI:
		case WEIXIN_NATIVE:
			try {
				return RequestUtil.getInputStreamMap(request);
			} catch (Exception e) {
				logger.error("wx call back get map By request error... payType:"+paytype, e);
			}
		default:
			break;
		}
		return null;
	}
}
