package org.duomn.naive.cxf.model.restful.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.duomn.naive.cxf.model.restful.entity.MapBean;
import org.duomn.naive.cxf.model.restful.entity.User;
import org.duomn.naive.cxf.model.restful.entity.Users;


@Path("/sample")
public interface IRESTSample {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	String doGet();
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	String doRequest(@PathParam("param") String param,
			@Context HttpServletRequest reqeust,
			@Context HttpServletResponse response);
	
	@GET
	@Path("/bean/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	User getBean(@PathParam("id") int id);
	
	@GET
	@Path("/list")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Users getList();
	
	@GET
	@Path("/map")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	MapBean getMap();
	
	@POST
	@Path("/postData")
	User postData(User user) throws IOException;
	
	@PUT
	@Path("/putData/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	User putData(@PathParam("id") int id, User user);
	
	@DELETE
	@Path("/removeData/{id}")
	void deleteData(@PathParam("id") int id);
}
