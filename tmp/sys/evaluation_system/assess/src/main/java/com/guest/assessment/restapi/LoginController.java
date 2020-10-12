package com.guest.assessment.restapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.guest.assessment.emus.SysConf;
import com.guest.assessment.service.AdminService;
import com.guest.assessment.utils.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 登录控制器
 * @author xuzhixiang
 * @date 2018年10月5日15:06:46
 *
 */
@RestController()
@RequestMapping("/admin")
@Api(value="登录 restApi",tags={"AdminRestApi"})
public class LoginController {
	
	@Autowired
	AdminService adminService;
	
	@ApiOperation(value="用户登录", notes="用户登录")
	@PostMapping("/login")
	public String login(HttpServletRequest request, 
			@ApiParam(name = "username", value = "用户名", required = true) @RequestParam(name = "username", required = true) String username,
			@ApiParam(name = "password", value = "密码", required = true) @RequestParam(name = "password", required = true) String password) {
		
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return ResultUtil.result(SysConf.ERROR, "账号或密码不能为空");
		}		
		if(username.equals("admin") && password.equals("admin")) {
			Map<String, Object> result = new HashMap<>();
			result.put(SysConf.TOKEN, "admin");
			return ResultUtil.result(SysConf.SUCCESS, result);
		}
		return ResultUtil.result(SysConf.ERROR, "error");
	}
	
	@ApiOperation(value = "用户信息", notes = "用户信息", response = String.class)
	@GetMapping(value = "/info")
	public String info(@ApiParam(name = "token", value = "token令牌",required = false) @RequestParam(name = "token", required = false) String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SysConf.TOKEN, "admin");
		map.put(SysConf.AVATAR, "http://120.79.70.126:8600/blog/weixin/guet.jpg");
		List<String> list = new ArrayList<String>();
		list.add("admin");
		map.put("roles", list);		
		return ResultUtil.result(SysConf.SUCCESS, map);
	}
	
	@ApiOperation(value = "退出登录", notes = "退出登录", response = String.class)
	@PostMapping(value = "/logout")
	public String logout(@ApiParam(name = "token", value = "token令牌",required = false) @RequestParam(name = "token", required = false) String token) {	
		return ResultUtil.result(SysConf.SUCCESS, null);
	}


	
}
