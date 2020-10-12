package com.tentcoo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {
	@RequestMapping(value= {"","/","index.html"})
	public String getIndexPage() {
		return "index"; // 通知Spring把请求交给 WEB-INF/index.jsp 处理
	}
	//@RequestMapping(value= {"index.html"})
	@RequestMapping(value= {"success.html"})
	public String getSendPage() {
		return "success"; // 通知Spring把请求交给 WEB-INF/send.jsp 处理
	}
	@RequestMapping(value= {"error.html"})
	public String geterrorPage() {
		return "success"; // 通知Spring把请求交给 WEB-INF/send.jsp 处理
	}
	@RequestMapping(value= {"exlogin.html"})
	public String getexloginPage() {
		return "exlogin"; // 通知Spring把请求交给 WEB-INF/send.jsp 处理
	}
	@RequestMapping(value= {"login.html"})
	public String getloginPage() {
		return "login"; // 通知Spring把请求交给 WEB-INF/send.jsp 处理
	}
	@RequestMapping(value= {"home.html"})
	public String gethomePage() {
		return "home"; // 通知Spring把请求交给 WEB-INF/send.jsp 处理
	}
	@RequestMapping(value= {"board.html"})
	public String getboardPage() {
		return "board"; // 通知Spring把请求交给 WEB-INF/send.jsp 处理
	}
}
