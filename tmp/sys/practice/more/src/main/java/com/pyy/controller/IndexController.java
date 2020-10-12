package com.pyy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyy.common.DataContextHolder;
import com.pyy.common.DataSourceType;
import com.pyy.model.Book;
import com.pyy.model.User;
import com.pyy.service.UserService;

@Controller
@RequestMapping("/")
public class IndexController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;

	@RequestMapping("")
	public String index(ModelMap modelMap) {
		
		log.info("start to access index");
		
		modelMap.addAttribute("test", "hello freemarker");
		return "index";
	}
	
	@RequestMapping(value="user/info")
	public String getUserInfo(ModelMap modelMap) {
		
		List<User> userList = userService.getUserList();
		modelMap.addAttribute("userList", userList);
		
		return "userInfo";
	}
	
	@RequestMapping(value = "dynamic")
	@ResponseBody
	public Map<String, Object> getDynamicInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> userList = userService.getUserList();
		log.info("user list is {}", userList);
		map.put("userList", userList);
		
		DataContextHolder.setDbType(DataSourceType.DATASOURCE_TYPE_DYNAMIC.getName());
		List<Book> bookList = userService.getBookList();
		log.info("book list is {}", bookList);
		map.put("bookList", bookList);
		
		return map;
	}
}
