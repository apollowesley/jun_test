package com.suma.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model) {

		model.addAttribute("name", "中文测试");
		model.addAttribute("age", 12);
		return "user/user2";
	}

}
