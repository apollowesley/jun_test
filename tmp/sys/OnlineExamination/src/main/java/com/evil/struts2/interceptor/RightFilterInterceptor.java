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
		// ȡ��������ص�ActionContextʵ��
		ActionProxy proxy = invocation.getProxy();
		String ns = proxy.getNamespace();
		String actionName = proxy.getActionName();
		if ("/".equals(ns) || ValidateUtil.isNull(ns)) {
			ns = "";
		}
		// ���������ȥ������
		if (actionName.contains("?")) {
			actionName = actionName.substring(0, actionName.indexOf("?"));
		}
		String url = ns + "/" + actionName;
		if (ValidateUtil.hasRight(url)) {
			return invocation.invoke();
		} else {
			// ȥ��½
			ReturnMsg rm = new ReturnMsg();
			rm.setStatusCode(ReturnMsg.ERROR + "");
			rm.setMessage("��û��Ȩ�ޣ��벻ҪΥ�����");
			rm.setCallbackType("");
			JsonUtil.returnMsg(rm);
			return null;
		}
	}
}
