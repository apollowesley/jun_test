package org.coody.czone.web.user.controller;

import org.coody.czone.common.controller.BaseController;
import org.coody.framework.web.annotation.PathBinding;

@PathBinding("/user")
public class UserController extends BaseController {

	@PathBinding("login.do")
	public String login() {
		return "user/login.jsp";
	}

	@PathBinding("register.do")
	public String register() {
		return "user/register.jsp";
	}
}
