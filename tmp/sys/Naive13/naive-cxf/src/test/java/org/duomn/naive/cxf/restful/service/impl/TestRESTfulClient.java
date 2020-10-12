package org.duomn.naive.cxf.restful.service.impl;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.duomn.naive.cxf.model.restful.entity.MapBean;
import org.duomn.naive.cxf.model.restful.entity.User;
import org.duomn.naive.cxf.model.restful.entity.Users;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-cxf-restful-client.xml" })
public class TestRESTfulClient {

	@Autowired
	@Qualifier("webClient")
	private WebClient client;

	@After
	public void reset() {
		client.reset();
	}
	
	@Test
	public void testGet() {
		String str = client.path("sample").accept(MediaType.TEXT_PLAIN)
				.get(String.class);
		System.out.println(str);
	}

	@Test
	public void testRequest() {
		String str = client.path("sample/request/234234")
				.accept(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println(str);
	}

	@Test
	public void testBean() {
		User user = client.path("sample/bean/{id}", 25)
				.accept(MediaType.APPLICATION_XML).get(User.class);
		System.out.println(user);
	}

	@Test
	public void testList() {
		System.out.println(client.path("sample/list")
				.accept(MediaType.APPLICATION_XML).get(Users.class).getUsers());
	}

	@Test
	public void testMap() {
		System.out.println(client.path("sample/map")
				.accept(MediaType.APPLICATION_XML).get(MapBean.class).getMap());
	}

	@Test
	public void testPostData() {
		User user = new User();
		user.setId(21432134);
		user.setAddress("hoojo#gz");
		user.setEmail("hoojo_@126.com");
		user.setName("hoojo");
		System.out.println(client.path("sample/postData")
				.accept(MediaType.APPLICATION_XML).post(user, User.class));
	}

	@Test
	public void testPutData() {
		User user = new User();
		user.setId(21432134);
		System.out.println(client.path("sample/putData/1")
				.accept(MediaType.APPLICATION_XML).put(user).getEntity());
	}
}
