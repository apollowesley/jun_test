package com.evil.struts2.frontendAction;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.User;
import com.evil.service.UserService;
import com.evil.struts2.action.BaseAction;
import com.evil.util.MD5Util;
import com.evil.util.ValidateUtil;

@Component("regAction")
@Scope("prototype")
public class RegAction extends BaseAction<User> {


	private static final long serialVersionUID = 1L;
	private String confirmPassword;
	
	@Resource(name="UserService")
	private UserService userService;
	
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 *��ע��ҳ�� 
	 */
	@SkipValidation
	public String toRegPage(){
		return "regPage";
	}
	
	public String doReg(){
		model.setPassword(MD5Util.encode(model.getPassword()));
		userService.saveOrUpdateEntity(model);
		return SUCCESS;
	}
	
	public void validate() {
		// �ǿ�
		if (ValidateUtil.isNull(model.getUserName())) {
			addFieldError("nickName", "��������");
		}
		if (ValidateUtil.isNull(model.getEmailAddress())) {
			addFieldError("emailAddress", "�������");
		}
		if (ValidateUtil.isNull(model.getPassword())) {
			addFieldError("password", "�������");
		}
		if (hasErrors()) {
			return;
		}
		// ����һ����
		if (!model.getPassword().equals(confirmPassword)) {
			addFieldError("password", "�����������һ��");
			return;
		}
		// email�Ƿ�ռ��
		if (userService.isRegisted(model.getEmailAddress())) {
			addFieldError("emailAddress", "������ռ��");
			return;
		}
	}

}
