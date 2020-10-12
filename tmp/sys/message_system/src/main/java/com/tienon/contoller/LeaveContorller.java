package com.tienon.contoller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tienon.config.SecurityInterceptor;
import com.tienon.service.DelService;
import com.tienon.service.LeaveService;
import com.tienon.service.QueryAllService;

@Controller
@RequestMapping("/leave")
public class LeaveContorller {

	@Autowired
	private LeaveService leave;
	@Autowired
	private QueryAllService query;
	@Autowired
	private DelService del;

	@RequestMapping(value = "/leavemessage", method = RequestMethod.POST)
	public String leave(@RequestParam String topic, @RequestParam String message) {
		String username=SecurityInterceptor.getName();
		if (username == null || username == "" || topic == null || topic == "" || topic == null || topic == "") {
			return "redirect:/leavefail.html";
		} else {
			boolean flag = leave.leave(username, topic, message);
			if (flag) {
				return "redirect:/message.html";
			} else {
				return "redirect:/leavefail.html";
			}
		}
	}

	@ResponseBody
	@RequestMapping("/all")	
	public  Map<String, Object> leaveAll(@RequestParam Integer currentpage,@RequestParam Integer rows){
		Map<String, Object> map=query.selectAlldata(currentpage);
		return map;
	} 
	
	
	@RequestMapping("/del")
	public void del(@RequestParam int id) {
		System.out.println("我到这里了吗");
		System.out.println("id");
		del.del(id);
	}
	
	
}


