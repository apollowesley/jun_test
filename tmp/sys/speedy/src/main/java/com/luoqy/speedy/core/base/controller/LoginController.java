package com.luoqy.speedy.core.base.controller;

import com.luoqy.speedy.data.MySqldbUtil;
import com.luoqy.speedy.util.Result;
import com.luoqy.speedy.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/common/login")
public class LoginController {
	@RequestMapping("")
	public String loginPage(Model model){
		return "WEB-INF/login";
	}


	@SuppressWarnings("unchecked")
	@RequestMapping("/login")
	@ResponseBody
	public Result login(String account,String password,Model model,HttpServletRequest request){
		Result result=new Result();
		//redirect:/system/login
		//handle ，登录分配权限
		HttpSession session=request.getSession(true);
		Object user=MySqldbUtil.mysqlSelect("select * from speedy_user where account='"+account+"' and password='"+password+"'", "none",null);
		if(null==user){
			result.setData(null);	
			result.setMessage("用户账号或密码错误");
			result.setState(0);
		}else{
			Map<String,Object> userMap=(Map<String, Object>) user;
			String role=userMap.get("role").toString();
			String userid=userMap.get("id").toString();
			//
			Object userRoleObject=MySqldbUtil.mysqlSelect("SELECT roleids from speedy_roleuser where userid="+userid,"none",null);
			if(null==userRoleObject){
				result.setMessage("无权限账号");
				result.setState(0);
				result.setData(new String[0]);
				return result;
			}
			Map<String,String> userRole=(Map<String, String>)userRoleObject;
			List<Map<String,String>> listHandle=(List<Map<String, String>>) MySqldbUtil.mysqlSelect("select * from speedy_menu where find_in_set(id,(select GROUP_CONCAT(menuids) from speedy_role where find_in_set(id,'"+userRole.get("roleids")+"'))) and ismenu=0", "list",null);
			List<Map<String,String>> menuList=(List<Map<String, String>>) MySqldbUtil.mysqlSelect("select * from speedy_menu where find_in_set(id,(select GROUP_CONCAT(menuids) from speedy_role where find_in_set(id,'"+userRole.get("roleids")+"'))) and ismenu=1 and parent=0", "list",null);
			//菜单列
			userMap.put("menu", menuList);
			//权限列表
			userMap.put("handle", listHandle);
			session.setAttribute("handle",SessionUtil.class);
			session.setAttribute("user", userMap);
			//YYYY-MM-dd HH:mm-ss
			String time=new SimpleDateFormat("YYYY-MM-dd HH:mm-ss").format(new Date());
			MySqldbUtil.mysqlExecute("update speedy_user set lasttime='"+time+"' where id="+userMap.get("id"),null);
			result.setData(user);
			result.setMessage("登录成功");
			result.setState(1);
		}
		return result;
	}
	@RequestMapping("/loginexit")
	public String loginexit(HttpServletRequest request){
		HttpSession session=request.getSession(true);
		session.removeAttribute("user");
		session.removeAttribute("handle");
		return "WEB-INF/login";
	}
}
