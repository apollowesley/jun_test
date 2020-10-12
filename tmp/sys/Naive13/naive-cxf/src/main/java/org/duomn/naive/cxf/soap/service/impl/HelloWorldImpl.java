package org.duomn.naive.cxf.soap.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.duomn.naive.cxf.model.soap.entity.UserInfo;
import org.duomn.naive.cxf.model.soap.service.IHelloWorld;


@WebService(endpointInterface="org.duomn.naive.cxf.model.soap.service.IHelloWorld")
public class HelloWorldImpl implements IHelloWorld {

	@Override
	public String sayHello(String text) {
		
		return "Hello" + text;
	}

	@Override
	public String sayHelloToAll(List<UserInfo> users) {
		String hello = "hello ";
		for (UserInfo user : users) {
			hello += user.getUserName();
		}
		hello += " , everybody.";
		return hello;
	}
	
}
