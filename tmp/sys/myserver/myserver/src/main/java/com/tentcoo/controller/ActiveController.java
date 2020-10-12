package com.tentcoo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tentcoo.entity.User;
import com.tentcoo.service.UserService;

@Controller
public class ActiveController {

	@Resource
	UserService userService;
	
	@RequestMapping(produces = "application/json;charset=UTF-8",value="ActiveServlet",method = {RequestMethod.GET})
	public String ActiveUser(HttpServletRequest request, HttpServletResponse response) {
		String code=request.getParameter("code");
	
		User user=userService.getUserByUserCode(code);
		
		if(user!=null) {
			user.setUserState(1);
			user.setUserCode(null);
			userService.updateUserInfo(user);
			request.setAttribute( "message","激活成功！");
			
			
		}
		else {
			request.setAttribute( "message","激活失败！");
			return "error";
			
			
		}
		
		
		
		
		return "success";
	}
	
	
	
	
}
