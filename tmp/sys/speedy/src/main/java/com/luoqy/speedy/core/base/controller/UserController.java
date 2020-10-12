package com.luoqy.speedy.core.base.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoqy.speedy.data.MySqldbUtil;
import com.luoqy.speedy.util.Result;
/**
 * 用户管理
 */
@Controller
@RequestMapping("/common/user")
public class UserController{
	private String PREFIX = "WEB-INF/common/user/";

	/**
	 * @return
	 */
	@RequestMapping("")
	public String index(){
		return PREFIX+"user";
	}
	@RequestMapping("/addPage")
	public String addPage(){
		return PREFIX+"userAdd";
	}
	@RequestMapping("/updatePage")
	public String updatePage(Model model,String id){
		Object user=MySqldbUtil.mysqlSelect("select * from speedy_user where id="+id, "none",null);
		model.addAttribute("user", user);
		return PREFIX+"userUpdate";
	}
	/** 页面跳转逻辑结束*/
	@RequestMapping("/roleset")
	@ResponseBody
	public Result rtoleset(String roleids,String userids){
		Result result=new Result();
		int call=MySqldbUtil.mysqlExecute("UPDATE speedy_user set role='"+roleids+"' where find_in_set(id,'"+userids+"')",null);
		if(call>0){
			result.setMessage("设置成功");
			result.setState(1);
			result.setData(new String[0]);
		}else{
			result.setMessage("设置失败");
			result.setState(0);
			result.setData(new String[0]);
		}
		return result;
	}

	@RequestMapping("/loadPage")
	@ResponseBody
	public Result list(String page,String pageSize){
		Result result=new Result();
		int pages=0;
		int pageSizes=20;
		if(null!=pageSize){
			pageSizes=Integer.parseInt(pageSize);
		}
		if(page!=null&&pageSize!=null&&!"".equals(page)&&!"".equals(pageSize)){
			pages=Integer.valueOf(page);
			pageSizes=Integer.valueOf(pageSize);
			pages=pages*pageSizes;
		}
		Object menu=MySqldbUtil.mysqlSelect("select u.*,(select GROUP_CONCAT(name) from speedy_role r where find_in_set(r.id,u.role)) rolename from speedy_user u limit "+pages+","+pageSize, "list",null);
		result.setData(menu);
		result.setMessage("数据加载成功");
		result.setState(1);
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Result update(HttpServletRequest req,String id){
		Result result=new Result();
		Enumeration<String> parames=req.getParameterNames();
		String set=" set ";
		while(parames.hasMoreElements()){
			String name = (String)parames.nextElement();//调用nextElement方法获得元素
			String value=req.getParameter(name);
			if(!"id".equals(name)){
				if(null!=value&&!"".equals(value)){
					set+=name+"='"+value+"',";
				}
			}
		}
		if("set".equals(set.trim())){
			result.setData(null);
			result.setMessage("请传入需要更新的参数");
			result.setState(0);
		}else{
			set=set.substring(0, set.length()-1);
			int call=MySqldbUtil.mysqlExecute("update speedy_user"+set+" where id="+id,null);
			if(call>0){
				result.setData(null);
				result.setMessage("更新成功");
				result.setState(1);
			}else{
				result.setData(null);
				result.setMessage("更新失败");
				result.setState(0);
			}
			
		}
		return result;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Result add(HttpServletRequest req){
		Result result=new Result();
		Enumeration<String> parames=req.getParameterNames();
		String set=" set ";
		while(parames.hasMoreElements()){
			String name = (String)parames.nextElement();//调用nextElement方法获得元素
			String value=req.getParameter(name);
			if(!"id".equals(name)){
				if(null!=value&&!"".equals(value)){
					set+=name+"='"+value+"',";
				}
			}
		}
		if("set".equals(set.trim())){
			result.setData(null);
			result.setMessage("请传入需要保存的参数");
			result.setState(0);
		}else{
			set=set.substring(0, set.length()-1);
			int call=MySqldbUtil.mysqlExecute("insert into speedy_user"+set,null);
			if(call>0){
				result.setData(null);
				result.setMessage("保存成功");
				result.setState(1);
			}else{
				result.setData(null);
				result.setMessage("保存失败");
				result.setState(0);
			}
			
		}
		return result;
	}
}