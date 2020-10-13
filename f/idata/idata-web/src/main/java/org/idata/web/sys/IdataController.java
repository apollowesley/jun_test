package org.idata.web.sys;

import org.idata.core.support.BaseController;
import org.idata.mybatis.generator.model.SysUser;
import org.idata.service.sys.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/")
public class IdataController extends BaseController {
	@Autowired
	private SysUserService sysUserService;
	// 登录
	@RequestMapping(value = "/test")
	public String test(ModelMap modelMap) {
		modelMap.put("122", 1213);
		return "login";
	}
	@RequestMapping(value = "/test1")
	@ResponseBody
	public String test1() {
		SysUser user=sysUserService.queryById(1);
		return JSON.toJSONString(user);
	}
}
