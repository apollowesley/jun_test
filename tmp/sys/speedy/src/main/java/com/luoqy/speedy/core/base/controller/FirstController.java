package com.luoqy.speedy.core.base.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoqy.speedy.common.ConfigManage;
import com.luoqy.speedy.common.FirstCreateBase;
import com.luoqy.speedy.common.LinuxDos;
import com.luoqy.speedy.common.WindowsDos;
import com.luoqy.speedy.util.Result;


@Controller
@RequestMapping("/first")
public class FirstController {
	@RequestMapping("/webFirst")
	public String webFirst(Model model) throws IOException{
		//这里应处理加载首页参数
			return "/view/index.html";
		
	}
	/**
	 * 后台登录校验是否第一次进入
	 * @throws IOException
	 */
	@RequestMapping("/checkFirst")
	public String checkFirst(Model model) throws IOException{
		//通过数据库查询是否能查询到
		Map<String,String> baseData=ConfigManage.findProperties("base");
		if(null==baseData){
			String message="欢迎第一次进入speedy管理系统,请进行数据信息初始化操作(可咨询客服人员QQ2629192332)";
			model.addAttribute("message", message);
			model.addAttribute("data", ConfigManage.findProperties("jdbc"));
			return "WEB-INF/index";
		}else{
			String first=baseData.get("first");
			String localhost=baseData.get("localhost");
			if("true".equals(localhost)&&"true".equals(first)){
				//如果是第一次，那么就初始化
				String message="欢迎第一次进入speedy管理系统,请进行数据信息初始化操作(可咨询客服人员QQ2629192332)";
				model.addAttribute("message", message);
				model.addAttribute("data", ConfigManage.findProp("jdbc"));
				return "WEB-INF/index";
			}else if("true".equals(first)){
				//如果是第一次，那么就初始化
				String message="欢迎第一次进入speedy管理系统,请进行数据信息初始化操作(可咨询客服人员QQ2629192332)";
				model.addAttribute("message", message);
				model.addAttribute("data", ConfigManage.findProperties("jdbc"));
				return "WEB-INF/index";
			}else{
				//这里实际是后台的校验
				return "WEB-INF/login";
			}
		}
		
		
	}
	@RequestMapping("/updateDataBase")
	@ResponseBody
	public Result updateDataBase(HttpServletRequest req){
		Result result=new Result();
		Enumeration<String> parames=req.getParameterNames();
		Map<String,String> map=new HashMap<String,String>();
		while(parames.hasMoreElements()){
			String name = (String)parames.nextElement();//调用nextElement方法获得元素
			String value=req.getParameter(name);
			if(!"id".equals(name)){
				if(null!=value&&!"".equals(value)){
					map.put(name, value);
				}
			}
		}
		if(FirstCreateBase.initBase(map)){
			result.setData(new String[0]);
			result.setMessage("初始化成功,即将跳转登录页面~");
			result.setState(1);
			return result;
		}else{
			result.setData(new String[0]);
			result.setMessage("数据源信息错误");
			result.setState(0);
			return result;
		}
	}
}
