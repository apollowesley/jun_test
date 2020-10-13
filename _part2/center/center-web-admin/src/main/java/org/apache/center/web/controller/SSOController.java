package org.apache.center.web.controller;

import org.apache.center.model.User;
import org.apache.center.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.playframework.domain.EasyuiJsonResult;
import org.apache.playframework.util.JacksonUtils;
import org.apache.playframework.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;

@Controller
@RequestMapping("sso/")
public class SSOController extends BaseController {

	@Reference(registry = "centerRegistry")
	private UserService userService; // 服务层

	/**
	 * 登录 （注解跳过权限验证）
	 */
	@Login(action = Action.Skip)
	@GetMapping(value="/login")
	public String login(ModelMap modelMap) {
		System.out.println(request.getMethod());
		String clientType = getRequest().getParameter("clientType");
		if (StringUtils.equals("mobile", clientType)) {
			modelMap.put("text", JacksonUtils.writeValueAsString(EasyuiJsonResult.getFailureResult("您还没有登录,请登录以后操作")));
			return "common/outputText";
		} else {
			SSOToken st = SSOHelper.getToken(getRequest());
			if (st != null) {
				return redirectTo("/index");
			}
			return "login";
		}
	}

	/**
	 * 登录
	 * @param userName 用户名
	 * @param password 密码
	 * @param sessionKept 会话保持
	 * @param checkCode 验证码
	 * @return
	 */
	@Login(action = Action.Skip)
	@PostMapping(value="/login")
	public String login(@RequestParam String userName, @RequestParam String password,
			@RequestParam(required = false) String rememberMe, @RequestParam(required = false) String checkCode, ModelMap modelMap) {
		logger.debug("用户登录, userName:{}, rememberMe:{}, checkCode:{}", userName, rememberMe, checkCode);
		User user = new User();
		user.setUserName(userName);
		user = userService.selectOne(user);
		if (user != null) {
			/*
			 * authSSOCookie 设置 cookie 同时改变 jsessionId
			 */
			SSOToken st = new SSOToken(getRequest());
			st.setId(user.getId());
			st.setData(userName);
			// 记住密码，设置 cookie 时长 1 周 = 604800 秒
			if ("on".equals(rememberMe)) {
				request.setAttribute(SSOConfig.SSO_COOKIE_MAXAGE, 604800);
			}
			SSOHelper.setSSOCookie(getRequest(), getResponse(), st, true);
			/*
			 * 登录需要跳转登录前页面，自己处理 ReturnURL 使用 HttpUtil.decodeURL(xx) 解码后重定向
			 */
			return redirectTo("/index");
		}
		String errorMsg = "你输入的密码和账户名不匹配";
		modelMap.put("errorMsg", errorMsg);
		return "login";
	}

	/**
	 * 退出登录
	 */
	@Permission(action = Action.Skip)
	@RequestMapping("/logout")
	public String logout() {
		/**
		 * <p>
		 * SSO 退出，清空退出状态即可
		 * </p>
		 * 
		 * <p>
		 * 子系统退出 SSOHelper.logout(request, response); 注意 sso.properties 包含 退出到
		 * SSO 的地址 ， 属性 sso.logout.url 的配置
		 * </p>
		 */
		SSOHelper.clearLogin(getRequest(), getResponse());
		return redirectTo("login");
	}
}
