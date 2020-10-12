package com.mario.demo.controller;

import com.junicorn.mario.servlet.wrapper.Request;
import com.junicorn.mario.servlet.wrapper.Response;

public class Index {
	
	public void index(Request request, Response response){
		request.setAttr("name", "MVC-Mario FrameWork");
		response.render("index");
	}
	
	public void hello(Request request, Response response){
		response.text("hello");
	}
	
	public void html(Request request, Response response){
		response.html("<h1>hello world!</h1>");
	}
	
}
