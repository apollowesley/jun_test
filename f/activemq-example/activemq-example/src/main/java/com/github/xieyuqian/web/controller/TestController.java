package com.github.xieyuqian.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.xieyuqian.jms.simple.NotifyMessageProducer;
import com.github.xieyuqian.jms.simple02.SamlProducer;
import com.github.xieyuqian.jms.simple03.MlaProducer;

@Controller
public class TestController {

	@Autowired
	private NotifyMessageProducer notifyMessageProducer;
	@Autowired
	private SamlProducer samlProducer;
	@Autowired
	private MlaProducer mlaProducer;
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/send1")
	public String sendMessage1(Model model) {
		String phone = "15581600392";
		String sms = "通知短信测试!!!";
		model.addAttribute("phone", phone);
		model.addAttribute("sms", sms);
		notifyMessageProducer.sendQueue(phone, sms);
		return "index";
	}
	
	@RequestMapping("/send2")
	public String sendMessage2(Model model) {
		String message = "send2 消息测试...";
		samlProducer.sendMessage(message);
		model.addAttribute("message", message);
		return "index";
	}
	
	@RequestMapping("/send3")
	public String sendMessage3(Model model) {
		String message = "send3 消息测试...";
		mlaProducer.sendMessage(message);
		model.addAttribute("message", message);
		return "index";
	}
}
