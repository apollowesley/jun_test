package com.managementsystem.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.managementsystem.entity.User;

@Controller
@RequestMapping("/")
public class LoginController {
	    @RequestMapping(value = "/login", method = RequestMethod.GET)
	    public String get(ModelMap model,String id){
		    model.addAttribute("message", "cowele!");
	        return "login";  //返回指向login.html页面
	    }
	    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
	    public ModelAndView getUser(User user) {
	    	User user1=new User();
	    	user1.setId(new Long(1));
	    	user1.setUserName("liucong");
	    	user1.setPassWord("123");
	    	ModelAndView view=new ModelAndView("successful", "command",user1 );
	        return view;
	    }
	    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	    public ModelAndView getUsers() {
	    	ArrayList<User> users=new ArrayList<User>();
	    	int i=1;
	    	while(i>5) {
	        	User user=new User();
		    	user.setId(new Long(i));
		    	user.setUserName("liucong");
		    	user.setPassWord("123");
		    	users.add(user);
		    	i++;
	    	}
	    	ModelAndView view=new ModelAndView("successful", "command", users);
	        return view;
	    }
	    @RequestMapping(method=RequestMethod.POST)
	    public String post(User user){  //用来处理用户的登陆请求
	        return "login_success";
	    }

}
