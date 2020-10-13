package com.cn.zjut.b2c.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;


@Controller
public class BaseController {

	private final static Logger logger = LoggerFactory.getLogger(BaseController.class);


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("YYYYMMDDhhmmss"), true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@ExceptionHandler(Exception.class )
	public String handlerException(Exception e,HttpServletRequest request) {
		logger.error(e.getMessage(), e);
//		model.addAttribute("Exception", e); //ExceptionHandler处理异常时，Model，是不能用的，否则会不起作用，这里用了HttpServletRequest  
		request.setAttribute("err", e.getMessage()); 
		return "exception/error";
	}

}
