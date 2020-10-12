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

	// ע��UserService
	@Resource(name = "adminUserService")
	private AdminUserService adminUserService;
	@Resource
	private RightService rightService;

	private Map<String, Object> sessionMap;

	public String toLatarLoginPage() {
		return "LatarLoginPage";
	}

	/**
	 * ��¼����
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
	 * �ػ������ڵ�¼
	 */
	public void timeOutDologin() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if (ValidateUtil.isNull(model.getName())
					|| ValidateUtil.isNull(model.getPassword())) {
				throw new Exception("��Ϣ����Ϊ��");
			} else {
				AdminUser adminUser = adminUserService.LoginCheck(
						model.getName(), MD5Util.encode(model.getPassword()));
				if (adminUser == null) {
					throw new Exception("�û����������");
				} else {
					int maxPos = rightService.getMaxPos();
					adminUser.setRightSum(new long[maxPos + 1]);
					adminUser.calculateRightSum(); // ���ù���Ա��Ȩ�޺�
					sessionMap.put("adminUser", adminUser);
					rm.setMessage("��½�ɹ�");
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
	 * ���û��ύ�����ݽ��д���
	 * 
	 * @return
	 */
	public String validateLogin() {
		String result = "latarInput";

		// �ǿ�
		if (ValidateUtil.isNull(model.getName()))
			addActionError("�û�������Ϊ��");
		if (ValidateUtil.isNull(model.getPassword()))
			addActionError("���벻��Ϊ��");
		if (ValidateUtil.isNull(securityCode))
			addActionError("��֤����Ϊ��");
		if (hasErrors()) {
			return result;
		}
		// ��֤����֤
		String serverCode = null;
		serverCode = (String) sessionMap.get("SESSION_SECURITY_LATARCODE");
		if (!securityCode.equalsIgnoreCase(serverCode)) {
			addActionError("��֤�����");
			return result;
		}
		AdminUser adminUser = adminUserService.LoginCheck(model.getName(),
				MD5Util.encode(model.getPassword()));
		if (adminUser == null) {
			addActionError("�û����������");
			return result;
		} else {
			int maxPos = rightService.getMaxPos();
			adminUser.setRightSum(new long[maxPos + 1]);
			adminUser.calculateRightSum(); // ���ù���Ա��Ȩ�޺�
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
