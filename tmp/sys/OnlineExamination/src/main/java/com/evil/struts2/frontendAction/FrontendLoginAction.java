package com.evil.struts2.frontendAction;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.User;
import com.evil.service.UserService;
import com.evil.struts2.action.BaseAction;
import com.evil.util.MD5Util;
import com.evil.util.ValidateUtil;
import com.opensymphony.xwork2.Action;

@Component("FrontendLoginAction")
@Scope("prototype")
public class FrontendLoginAction extends BaseAction<User> implements SessionAware {

	private static final long serialVersionUID = 1L;

	private String securityCode;

	// 注入UserService
	@Resource(name = "UserService")
	private UserService userService;

	private Map<String, Object> sessionMap;

	/**
	 * 到前台登录界面
	 */
	public String toLoginPage() {
		return "loginPage";
	}
	

	/**
	 * 登录处理
	 */
	public String doLogin() {
		String result="";
		result=validateLogin();
		if(!"RIGHT".equals(result)){
			return result;
		}
		return Action.SUCCESS;

	}

	public String doLoginOut() {
		sessionMap.remove("user");
		return "login";
	}
	/**
	 * 对用户提交的数据进行处理
	 * @return
	 */
	public String validateLogin() {
		String result=Action.INPUT;

		// 非空
		if (ValidateUtil.isNull(model.getEmailAddress()))
			addActionError("邮箱 不能为空");
		if (ValidateUtil.isNull(model.getPassword()))
			addActionError("密码不能为空");
		if (ValidateUtil.isNull(securityCode))
			addActionError("验证不能为空");
		if (hasErrors()) {
			return result;
		}
		// 验证码验证
		String serverCode = null;
		serverCode = (String) sessionMap.get("SESSION_SECURITY_FRONTCODE");//验证码
		if (!securityCode.equalsIgnoreCase(serverCode)) {
			addActionError("验证码错误");
			return result;
		}
		User user = userService.LoginCheck(model.getEmailAddress(),
				MD5Util.encode(model.getPassword()));
		if (user == null) {
			addActionError("邮箱/密码错误");
			return result;
		} else {
			if(!user.getIsEnabled()){
				addActionError("该用户被禁用");
				return result;
			}
			sessionMap.put("user", user);
			return "RIGHT";
		}
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}


}
