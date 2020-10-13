package org.coody.czone.web.user.controller;

import org.coody.framework.web.annotation.PathBinding;

@PathBinding("/test")
public class TestController {

	@PathBinding("/hello.do")
	public String hello() {

		return "hello.jsp";
	}
}
