package com.luoqy.speedy.core.base.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoqy.speedy.data.MySqldbUtil;
import com.luoqy.speedy.util.Result;
@Controller
@RequestMapping("/common/main")
public class MainController {
	@RequestMapping("")
	public String index(Model model){
		Object object=MySqldbUtil.mysqlSelect("select * from speedy_menu", "list",null);
		model.addAttribute("list", object);
		return "WEB-INF/main";
	}
	@RequestMapping("/toPage")
	public String toPage(String page){
		return "WEB-INF/"+page;
	}
	public static void main(String[] args) {
		List<Map<String,Object>> list=(List<Map<String,Object>>)MySqldbUtil.mysqlSelect("select * from speedy_menu where parent=0", "list",null);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	@RequestMapping("/loadTableName")
	@ResponseBody
	public Result loadTableName(String tablename){
		Result result=new Result();
		Object tableTitle=MySqldbUtil.mysqlSelect("select column_name as name,COLUMN_COMMENT as context from information_schema.columns where table_name  = '"+tablename+"'", "list",null);
		result.setData(tableTitle);
		result.setMessage("加载成功");
		result.setState(1);
		return result;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadMenu") 
	@ResponseBody
	public Result loadMenu(HttpServletRequest req){
		Result result=new Result();
		Map<String,Object> user=(Map<String, Object>) req.getSession().getAttribute("user");
		List<Map<String,Object>> list=(List<Map<String, Object>>) user.get("menu");
		//这里应查询是否在权限内，加入各自的栏目里
		String role=user.get("role").toString();  
		for(int i=0;i<list.size();i++){
			Object id=list.get(i).get("id");
			Object sonList=MySqldbUtil.mysqlSelect("select * from speedy_menu where find_in_set(id,(select menuids from speedy_role where id="+role+")) and parent="+id, "list",null);
			list.get(i).put("menuSon",sonList);
		}
		result.setData(list);
		result.setState(1);
		return result;
	}
}
