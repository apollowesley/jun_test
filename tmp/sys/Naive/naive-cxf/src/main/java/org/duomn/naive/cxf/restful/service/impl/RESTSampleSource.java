package org.duomn.naive.cxf.restful.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.duomn.naive.cxf.model.restful.entity.MapBean;
import org.duomn.naive.cxf.model.restful.entity.User;
import org.duomn.naive.cxf.model.restful.entity.Users;
import org.duomn.naive.cxf.model.restful.service.IRESTSample;

@Path("/sample")
public class RESTSampleSource implements IRESTSample {

	@Context
	private UriInfo uriInfo;

	@Context
	private Request request;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/")
	public String doGet() {
		return "this is get rest request";
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/request/{param}")
	public String doRequest(@PathParam("param") String param,
			@Context HttpServletRequest servletRequest,
			@Context HttpServletResponse servletResponse) {
		System.out.println(servletRequest);
		System.out.println(servletResponse);
		System.out.println(servletRequest.getParameter("param"));
		System.out.println(servletRequest.getContentType());
		System.out.println(servletResponse.getCharacterEncoding());
		System.out.println(servletResponse.getContentType());
		return "success";
	}

	@GET
	@Path("/bean/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User getBean(@PathParam("id") int id) {
		System.out.println("####getBean#####");
		System.out.println("id:" + id);
		System.out.println("Method:" + request.getMethod());
		System.out.println("uri:" + uriInfo.getPath());
		System.out.println(uriInfo.getPathParameters());

		User user = new User();
		user.setId(id);
		user.setName("JojO");
		return user;
	}

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Users getList() {
		System.out.println("####getList#####");
		System.out.println("Method:" + request.getMethod());
		System.out.println("uri:" + uriInfo.getPath());
		System.out.println(uriInfo.getPathParameters());

		List<User> list = new ArrayList<User>();
		User user = null;
		for (int i = 0; i < 4; i++) {
			user = new User();
			user.setId(i);
			user.setName("JojO-" + i);
			list.add(user);
		}
		Users users = new Users();
		users.setUsers(list);
		return users;
	}

	@GET
	@Path("/map")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public MapBean getMap() {
		System.out.println("####getMap#####");
		System.out.println("Method:" + request.getMethod());
		System.out.println("uri:" + uriInfo.getPath());
		System.out.println(uriInfo.getPathParameters());

		Map<String, User> map = new HashMap<String, User>();
		User user = null;
		for (int i = 0; i < 4; i++) {
			user = new User();
			user.setId(i);
			user.setName("JojO-" + i);
			map.put("key-" + i, user);
		}
		MapBean bean = new MapBean();
		bean.setMap(map);
		return bean;
	}

	/*
	 * @Consumes：声明该方法使用 HTML FORM。
	 * 
	 * @FormParam：注入该方法的 HTML 属性确定的表单输入。
	 * 
	 * @Response.created(uri).build()： 构建新的 URI
	 * 用于新创建的联系人（/contacts/{id}）并设置响应代码（201/created）。 您可以使用
	 * http://localhost:8080/Jersey/rest/contacts/<id> 访问新联系人
	 */
	@POST
	@Path("/postData")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User postData(User user) throws IOException {
		System.out.println(user);
		user.setName("jojo##12321321");
		return user;
	}

	@PUT
	@Path("/putData/{id}")
	@Produces({ MediaType.APPLICATION_XML })
	public User putData(@PathParam("id") int id, User user) {
		System.out.println("#####putData#####");
		System.out.println(user);
		user.setId(id);
		user.setAddress("hoojo#gz");
		user.setEmail("hoojo_@126.com");
		user.setName("hoojo");
		System.out.println(user);
		return user;
	}

	@DELETE
	@Path("/removeData/{id}")
	public void deleteData(@PathParam("id") int id) {
		System.out.println("#######deleteData#######" + id);
	}
}