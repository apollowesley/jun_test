package com.cn.zjut.b2c.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.zjut.b2c.service.FrontConsumeServiceInter;

@Controller
@RequestMapping("/B2C")
public class FrontConsumeController extends BaseController{
	
	@Resource
	private FrontConsumeServiceInter frontConsumeService;
	
	//通过配合@ResponseBody来将内容或者对象作为HTTP响应正文返回FrontConsumeController.java BackRcvResponse

	@RequestMapping("/FrontConsumeByJSP.do")
	public String toPayByJSP(HttpServletRequest request,Model model)throws Exception{
		
		//前台页面传过来的参数
		String merId = request.getParameter("merId");
		String txnAmt = request.getParameter("txnAmt");
		//封装参数
		model.addAttribute("map", frontConsumeService.getConsumeDataMap(merId, txnAmt));
		model.addAttribute("requestFrontUrl", frontConsumeService.getFrontRequestUrl());
		//跳转view
		return "ToConsume";
	}
	
	@RequestMapping("/FrontConsume.do")
	@ResponseBody
	@Deprecated
	public String toPay(HttpServletRequest request,Model model){
		//前台页面传过来的
		String merId = request.getParameter("merId");
		String txnAmt = request.getParameter("txnAmt");

		return frontConsumeService.getConsumeHtml(merId, txnAmt);
	}

}
