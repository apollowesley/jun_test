package com.kiss.jbpm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kiss.jbpm.service.IJbpmService;
import com.kiss.web.spring3.controller.MultiActionControllerImpl;


@RequestMapping("/jbpm")
@Controller
public class JbpmController extends MultiActionControllerImpl{

	@Autowired
	private IJbpmService jbpmService; 
	
	@RequestMapping("/init")
	public void init(){
		jbpmService.initMinaServer();
	}
}
