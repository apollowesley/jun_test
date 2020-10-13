package org.apache.center.web.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.center.model.Resources;
import org.apache.center.service.ResourcesService;
import org.apache.center.service.RoleService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.playframework.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;

@Controller
public class CommonController extends BaseController {
 
	@Reference(registry="centerRegistry")
	private RoleService roleService; //角色服务层
	
	@Reference(registry="centerRegistry")
	private ResourcesService resourcesService; //资源服务层
	
	/**
	 * 用户无权限访问地址  页面
	 * @return 
	 */
	@Login(action = Action.Skip)
	@RequestMapping(value="accessDenied**")
	public String accessDenied() {
		return "common/403";
	}
	
	/**
	 * 用户无权限访问地址  页面
	 * @return
	 */
	@Login(action = Action.Skip)
	@RequestMapping(value="systemError**")
	public String systemError() {
		return "common/500";
	}
	
	/**
	 * 首页  页面
	 * @param modelMap
	 * @return
	 */
	@Permission(action = Action.Skip)
	@RequestMapping(value="index**",method=RequestMethod.GET)
	public String index(ModelMap modelMap) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getValue());
		}
		SSOToken token = getSSOToken();
		String userName = token.getData();
		modelMap.put("userName", userName);
		List<Resources> resourcesList = resourcesService.selectByUserId(userName, NumberUtils.toLong(String.valueOf(token.getId())));
		modelMap.put("resourcesList", resourcesList);
		return "index";
	}
	 
	
	/**
	 * session超时
	 * @param modelMap
	 * @return
	 */
	@Login(action = Action.Skip)
	@RequestMapping(value="timeout**",method=RequestMethod.GET)
	public String timeout() {
		return "common/timeout";
	}
	 
}
