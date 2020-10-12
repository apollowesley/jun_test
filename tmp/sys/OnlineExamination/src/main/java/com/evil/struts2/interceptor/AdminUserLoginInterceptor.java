package com.evil.struts2.interceptor;

import com.evil.pojo.system.AdminUser;
import com.evil.struts2.action.BaseAction;
import com.evil.struts2.action.LoginAction;
import com.evil.util.JsonUtil;
import com.evil.util.ReturnMsg;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("rawtypes")
public class AdminUserLoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
		// 取得请求相关的ActionContext实例
		BaseAction action = (BaseAction) invocation.getAction();
		if (action instanceof LoginAction) {
			return invocation.invoke();
		} else {
			AdminUser adminUser = (AdminUser) invocation.getInvocationContext().getSession()
					.get("adminUser");
			if (adminUser == null) {
				// 去登陆
				ReturnMsg rm=new ReturnMsg();
				rm.setStatusCode(ReturnMsg.TIME_OUT+"");
				rm.setMessage("会话已过期!请重新登录!");
				rm.setCallbackType("");
				JsonUtil.returnMsg(rm);
                return null;
			} else {
				// 放行
				return invocation.invoke();
			}
		}
	}
}
