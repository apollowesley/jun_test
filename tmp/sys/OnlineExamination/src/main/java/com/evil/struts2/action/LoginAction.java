package com.evil.struts2.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.system.AdminUser;
import com.evil.service.AdminUserService;
import com.evil.service.RightService;
import com.evil.util.JsonUtil;
import com.evil.util.MD5Util;
import com.evil.util.ReturnMsg;
import com.evil.util.ValidateUtil;

@Component("loginAction")
@Scope("prototype")
public class LoginAction extends BaseAction<AdminUser> implements SessionAware {

	private static final long serialVersionUID = 1L;

	private String securityCode;

	// 注入UserService
	@Resource(name = "adminUserService")
	private AdminUserService adminUserService;
	@Resource
	private RightService rightService;

	private Map<String, Object> sessionMap;

	public String toLatarLoginPage() {
		return "LatarLoginPage";
	}

	/**
	 * 登录处理
	 */
	public String doLogin() {
		String result = "";
		result = validateLogin();
		if (!"RIGHT".equals(result)) {
			return result;
		}
		return "latarSuccess";

	}

	/**
	 * 回话过期在登录
	 */
	public void timeOutDologin() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if (ValidateUtil.isNull(model.getName())
					|| ValidateUtil.isNull(model.getPassword())) {
				throw new Exception("信息不能为空");
			} else {
				AdminUser adminUser = adminUserService.LoginCheck(
						model.getName(), MD5Util.encode(model.getPassword()));
				if (adminUser == null) {
					throw new Exception("用户名密码错误");
				} else {
					int maxPos = rightService.getMaxPos();
					adminUser.setRightSum(new long[maxPos + 1]);
					adminUser.calculateRightSum(); // 设置管理员的权限和
					sessionMap.put("adminUser", adminUser);
					rm.setMessage("登陆成功");
					rm.setCallbackType("closeCurrent");
				}
			}
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg(e.getMessage());
		} finally {
			JsonUtil.returnMsg(rm);
		}
	}

	public String doLoginOut() {
		sessionMap.remove("adminUser");
		return "LatarLoginPage";
	}

	/**
	 * 对用户提交的数据进行处理
	 * 
	 * @return
	 */
	public String validateLogin() {
		String result = "latarInput";

		// 非空
		if (ValidateUtil.isNull(model.getName()))
			addActionError("用户名不能为空");
		if (ValidateUtil.isNull(model.getPassword()))
			addActionError("密码不能为空");
		if (ValidateUtil.isNull(securityCode))
			addActionError("验证不能为空");
		if (hasErrors()) {
			return result;
		}
		// 验证码验证
		String serverCode = null;
		serverCode = (String) sessionMap.get("SESSION_SECURITY_LATARCODE");
		if (!securityCode.equalsIgnoreCase(serverCode)) {
			addActionError("验证码错误");
			return result;
		}
		AdminUser adminUser = adminUserService.LoginCheck(model.getName(),
				MD5Util.encode(model.getPassword()));
		if (adminUser == null) {
			addActionError("用户名密码错误");
			return result;
		} else {
			int maxPos = rightService.getMaxPos();
			adminUser.setRightSum(new long[maxPos + 1]);
			adminUser.calculateRightSum(); // 设置管理员的权限和
			sessionMap.put("adminUser", adminUser);
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
