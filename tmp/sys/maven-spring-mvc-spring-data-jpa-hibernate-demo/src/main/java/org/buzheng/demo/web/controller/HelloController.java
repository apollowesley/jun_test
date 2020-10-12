package org.buzheng.demo.web.controller;

import javax.annotation.Resource;

import org.buzheng.demo.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	private static Logger logger = LoggerFactory.getLogger(HelloController.class);
	
	@Resource
	private HelloService helloService;
	
	@RequestMapping("/")
	//@ResponseBody
	public String index() {
		logger.debug("======= hello index");
		
//		return "It works.";
		return "index";
	}
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello(@RequestParam(value="name", defaultValue="buzheng") String name) {
		return this.helloService.hello(name);
	}
	
	@RequestMapping("/hello/{name}")
	@ResponseBody
	public String hello2(@PathVariable("name") String name) {
		return this.helloService.hello(name);
	}
}
