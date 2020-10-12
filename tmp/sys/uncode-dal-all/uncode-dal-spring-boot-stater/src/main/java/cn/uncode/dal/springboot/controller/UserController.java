package cn.uncode.dal.springboot.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.uncode.dal.springboot.dto.User;
import cn.uncode.dal.springboot.service.UserService;

/**
 * Created by KevinBlandy on 2017/2/28 15:51
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "list",method = RequestMethod.GET)
	public User users(){
		return userService.users();
	}
	
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public User get(){
		User user = userService.get(1);
		return user;
	}
	
}
