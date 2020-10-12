package com.evil.struts2.interceptor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.util.JsonUtil;
import com.evil.util.ReturnMsg;
import com.evil.util.ValidateUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component("RightFilterInterceptor")
@Scope("prototype")
public class RightFilterInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
		// 取得请求相关的ActionContext实例
		ActionProxy proxy = invocation.getProxy();
		String ns = proxy.getNamespace();
		String actionName = proxy.getActionName();
		if ("/".equals(ns) || ValidateUtil.isNull(ns)) {
			ns = "";
		}
		// 如果带参数去掉参数
		if (actionName.contains("?")) {
			actionName = actionName.substring(0, actionName.indexOf("?"));
		}
		String url = ns + "/" + actionName;
		if (ValidateUtil.hasRight(url)) {
			return invocation.invoke();
		} else {
			// 去登陆
			ReturnMsg rm = new ReturnMsg();
			rm.setStatusCode(ReturnMsg.ERROR + "");
			rm.setMessage("你没有权限，请不要违规操作");
			rm.setCallbackType("");
			JsonUtil.returnMsg(rm);
			return null;
		}
	}
}
