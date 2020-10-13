package com.cn.zjut.b2c.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.zjut.b2c.pojo.ResponseMessage;
import com.cn.zjut.b2c.sdk.LogUtil;
import com.cn.zjut.b2c.sdk.SDKConstants;
import com.cn.zjut.b2c.service.FrontRcvResponseServiceInter;
import com.cn.zjut.b2c.service.component.RequestUtil;

@Controller
@RequestMapping("/ACP_B2C")
public class FrontRcvResponseController extends BaseController{
	
	@Resource
	private FrontRcvResponseServiceInter frontRcvResponseService;
	
	@RequestMapping("/frontRcvResponse")
	public String toFrontRcv(HttpServletRequest req, HttpServletResponse resp,Model model) throws Exception{
		
		LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");
		//服务器发回来的是"ISO-8859-1"编码？
		req.setCharacterEncoding("ISO-8859-1");
		//参数里有之前传的编码方式
		String encoding = req.getParameter(SDKConstants.param_encoding);
		
		String pageResult = frontRcvResponseService.getResultPageURL(encoding);

		ResponseMessage responseMessage= new ResponseMessage();
		responseMessage.setEncoding(encoding);
		responseMessage.setTxntype(req.getParameter("txnType"));
		responseMessage.setBiztype(req.getParameter("bizType"));
		responseMessage.setMerid(req.getParameter("merId"));
		responseMessage.setOrderid(req.getParameter("orderId"));
		responseMessage.setOrigqryid(req.getParameter("origQryId"));
		responseMessage.setTxntime(req.getParameter("txnTime"));
		responseMessage.setTxnamt(req.getParameter("txnAmt"));
		responseMessage.setQueryid(req.getParameter("queryId"));
		responseMessage.setSignature(req.getParameter("signature")); 
		responseMessage.setRespmsg(req.getParameter("respMsg"));
		frontRcvResponseService.insertResponseMessage(responseMessage);
		
//		参照sdk内容的写法，已改为jsp动态展现
//		String page = frontRcvResponseService.getResultPageParam(respParam,encoding);
//		model.addAttribute("result", page);
		Map<String, String> respParam = RequestUtil.getAllRequestParam(req);
		model.addAttribute("map", respParam);
		LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");
		return pageResult;
		
	}
}
