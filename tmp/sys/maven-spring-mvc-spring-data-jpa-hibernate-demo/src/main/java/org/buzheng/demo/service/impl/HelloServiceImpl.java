package org.buzheng.demo.service.impl;

import org.buzheng.demo.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public String hello(String name) {
		return "Hello " + name + ".";
	}

}
