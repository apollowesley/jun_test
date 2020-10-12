package org.duomn.naive.cxf.soap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.duomn.naive.cxf.model.soap.entity.UserInfo;
import org.duomn.naive.cxf.model.soap.service.IHelloWorld;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-cxf-soap-client.xml" })
public class TestSOAPClient {

	@Autowired
	private IHelloWorld helloWorld;
	
	@Test
	public void invokeBySpring() {
		List<UserInfo> users = new ArrayList<UserInfo>();
		users.add(new UserInfo("vicky", 23));
		users.add(new UserInfo("caty", 23));
		users.add(new UserInfo("ivy", 23));
		users.add(new UserInfo("kelly", 23));
		String helloAll = helloWorld.sayHelloToAll(users);
		
		System.out.println(helloAll);
	}
	
	@Test
	public void invoke() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IHelloWorld.class);
		factory.setAddress("http://localhost:8080/naive-cxf/cxf/HelloWorld");
		IHelloWorld helloWorld = (IHelloWorld) factory.create();
		System.out.println("invoke helloworld webservice...");
		String hello = helloWorld.sayHello("vickey");
		
		System.out.println(hello);
	}
}
