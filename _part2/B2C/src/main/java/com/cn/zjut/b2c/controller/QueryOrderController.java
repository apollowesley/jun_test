package com.cn.zjut.b2c.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.zjut.b2c.service.QueryOrderServiceInter;
import com.cn.zjut.b2c.service.component.DemoBase;

@Controller
@RequestMapping("/B2C")
public class QueryOrderController extends BaseController{
	@Resource
	private QueryOrderServiceInter queryOrderService;
	
//	@RequestMapping("/Query.do")
//	@ResponseBody
	@Deprecated
	public String toQuery(HttpServletRequest req,Model model){
		
		String orderId = req.getParameter("orderId");
		String txnTime = req.getParameter("txnTime");
		
		Map<String, String> reqData = queryOrderService.getRequestData(orderId, txnTime);
		Map<String, String> rspData = queryOrderService.getResponseData(reqData);
		
		String reqMessage = DemoBase.genHtmlResult(reqData);
		String rspMessage = DemoBase.genHtmlResult(rspData);
		
		
		String respResult = ("</br>请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
		return respResult;
		
	}
	@RequestMapping("/Query.do")
	public String toQueryByJSP(HttpServletRequest req,Model model){
		
		String orderId = req.getParameter("orderId");
		String txnTime = req.getParameter("txnTime");
		
		Map<String, String> reqData = queryOrderService.getRequestData(orderId, txnTime);
		Map<String, String> rspData = queryOrderService.getResponseData(reqData);
		
		model.addAttribute("rspData", rspData);
		
		//String respResult = ("</br>请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
		return "QueryResult";
		
	}

}
