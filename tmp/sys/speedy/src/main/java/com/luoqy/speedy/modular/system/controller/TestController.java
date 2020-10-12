package com.luoqy.speedy.modular.system.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoqy.speedy.common.ConfigManage;
import com.luoqy.speedy.util.Result;
/**
 * @author luoqy
 * @date 2019年6月2日
 * 测试控制器
 */
@Controller
@RequestMapping("/test")
public class TestController{
	@RequestMapping("")
	public String index(Model model){
		 model.addAttribute("name", 2);
	    return "WEB-INF/index";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Result list(){
		Result result=new Result();
		result.setData(ConfigManage.getFileList(null));
		return result;
	}
	@RequestMapping("/data")
	@ResponseBody
	public Result dataUpdate(String fileName,HttpServletRequest req){
		Result result=new Result();
		Enumeration<String> parames=req.getParameterNames();
		Map<String,String> map=new HashMap<String,String>();
		while (parames.hasMoreElements()) {
			String name = (String) parames.nextElement();
			map.put(name, req.getParameter(name));
		}
		ConfigManage.updatePropertie(fileName,map);
		result.setMessage("成功");
		return result;
	}
	@RequestMapping("/find")
	@ResponseBody
	public Result find(String fileName){
		Result result=new Result();
		result.setMessage("成功");
		result.setData(ConfigManage.findProperties(fileName));
		return result;
	}
}
