package com.xin.dream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xin.dream.service.UserService;

@RestController
//@RequestMapping("/user")
public class UserController {
	@Autowired
	public UserService userService;
	
	@RequestMapping(value = "/user",method = RequestMethod.GET)
	public Object getDoctorInfo(long id) {
		return userService.selectByPrimaryKey(id);
	}
}
