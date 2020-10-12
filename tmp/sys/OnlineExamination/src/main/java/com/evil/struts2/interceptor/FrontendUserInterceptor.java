package com.evil.struts2.interceptor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.User;
import com.evil.struts2.UserAware;
import com.evil.struts2.action.BaseAction;
import com.evil.struts2.frontendAction.FormalTestLoginAction;
import com.evil.struts2.frontendAction.FrontendLoginAction;
import com.evil.struts2.frontendAction.RegAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component("FrontendUserInterceptor")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class FrontendUserInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
		// ȡ��������ص�ActionContextʵ��
		BaseAction action = (BaseAction) invocation.getAction();
		if (action instanceof FrontendLoginAction
				|| action instanceof RegAction
				|| action instanceof FormalTestLoginAction) {
			return invocation.invoke();
		} else {
			User user = (User) invocation.getInvocationContext().getSession()
					.get("user");
			if (user == null) {
				// ȥ��½
				return "login";
			} else {
				if (action instanceof UserAware) {
					((UserAware) action).setUser(user);
				}
				// ����
				return invocation.invoke();
			}
		}
	}
}
