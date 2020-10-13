package com.cn.zjut.b2c.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.zjut.b2c.pojo.User;
import com.cn.zjut.b2c.service.UserServiceInter;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Resource
	private UserServiceInter userService;
	
	@RequestMapping("/showUser.html")
	public String toIndex(HttpServletRequest request,Model model)throws Exception{
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}
}
