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

	// ע��UserService
	@Resource(name = "UserService")
	private UserService userService;

	private Map<String, Object> sessionMap;

	/**
	 * ��ǰ̨��¼����
	 */
	public String toLoginPage() {
		return "loginPage";
	}
	

	/**
	 * ��¼����
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
	 * ���û��ύ�����ݽ��д���
	 * @return
	 */
	public String validateLogin() {
		String result=Action.INPUT;

		// �ǿ�
		if (ValidateUtil.isNull(model.getEmailAddress()))
			addActionError("���� ����Ϊ��");
		if (ValidateUtil.isNull(model.getPassword()))
			addActionError("���벻��Ϊ��");
		if (ValidateUtil.isNull(securityCode))
			addActionError("��֤����Ϊ��");
		if (hasErrors()) {
			return result;
		}
		// ��֤����֤
		String serverCode = null;
		serverCode = (String) sessionMap.get("SESSION_SECURITY_FRONTCODE");//��֤��
		if (!securityCode.equalsIgnoreCase(serverCode)) {
			addActionError("��֤�����");
			return result;
		}
		User user = userService.LoginCheck(model.getEmailAddress(),
				MD5Util.encode(model.getPassword()));
		if (user == null) {
			addActionError("����/�������");
			return result;
		} else {
			if(!user.getIsEnabled()){
				addActionError("���û�������");
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
