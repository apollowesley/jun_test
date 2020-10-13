package com.cn.zjut.b2c.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.zjut.b2c.sdk.LogUtil;
import com.cn.zjut.b2c.sdk.SDKConstants;
import com.cn.zjut.b2c.service.BackRcvResponseServiceInter;
import com.cn.zjut.b2c.service.component.RequestUtil;

@Controller
@RequestMapping("/ACP_B2C")
public class BackRcvResponseController extends BaseController{
	
	@Resource
	private BackRcvResponseServiceInter backRcvResponseService;
	
	@RequestMapping("/BackRcvResponse")
	public String toBackRcv(HttpServletRequest request,Model model) throws Exception{
		
		LogUtil.writeLog("BackRcvResponse接收后台通知开始");
		request.setCharacterEncoding("ISO-8859-1");
		String encoding = request.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = RequestUtil.getAllRequestParam(request);
		
		return backRcvResponseService.BackRcvValidate(encoding, reqParam);
	}

}
