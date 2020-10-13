package com.managementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.managementsystem.entity.Resources;
import com.managementsystem.service.ResourcesService;
@Controller
@RequestMapping("/")
public class ResourcesControl {
	@Autowired
	private ResourcesService resourcesService;
	
    @RequestMapping(value = "/getResources", method = RequestMethod.GET)
	public void getResources() {
		@SuppressWarnings("unused")
		List<Resources> resources=resourcesService.getAll();
		System.out.println("nh");
	}
}
