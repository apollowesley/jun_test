package com.tienon.contoller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.tienon.service.CheckService;
import com.tienon.service.LoginService;
import com.tienon.service.RegistService;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private RegistService regist;
	@Autowired
	private LoginService login;
	@Autowired
	private CheckService check;


	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regist(@RequestParam String username, @RequestParam String password) {
		if (username != null && username != "" && password != null && password != "") {
			boolean flag = check.check(username);
			if (flag) {
				boolean flag1 = regist.regist(username, password);
				if (flag1) {
					return "redirect:/login.html";
				} else {
					return "redirect:/registfail.html";
				}
			} else {
				return "redirect:/registfail.html";
			}
		} else {
			return "redirect:/registfail.html";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam String username, @RequestParam String password,HttpSession session) {
		if (username != null && username != "" && password != null && password != "") {
			boolean flag = login.login(username, password);
			if (flag) {
				session.setAttribute("username", username);
				return "redirect:/leave-message.html";
			} else {
				return "redirect:/loginfail.html";
			}
		} else {
			return "redirect:/loginfail.html";
		}
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
				session.removeAttribute("username");
				return "redirect:/login.html";

	}
	
}
