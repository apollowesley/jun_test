package com.xieke.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xieke.test.model.User;
import com.xieke.test.service.UserService;



@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/all")
	public String allUser(Model model) {
		List<User> list = userService.findAllUser();
		model.addAttribute("users", list);
		return "/user_list";
	}

	@RequestMapping("/index")
	public String userIndex() {
		return "/user_index";
	}

	@RequestMapping("/find")
	public @ResponseBody List<User> findList() {
		return userService.findAllUser();
	}

}
