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
	 *到注册页面 
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
		// 非空
		if (ValidateUtil.isNull(model.getUserName())) {
			addFieldError("nickName", "姓名必填");
		}
		if (ValidateUtil.isNull(model.getEmailAddress())) {
			addFieldError("emailAddress", "邮箱必填");
		}
		if (ValidateUtil.isNull(model.getPassword())) {
			addFieldError("password", "密码必填");
		}
		if (hasErrors()) {
			return;
		}
		// 密码一致性
		if (!model.getPassword().equals(confirmPassword)) {
			addFieldError("password", "两次密码必须一致");
			return;
		}
		// email是否占用
		if (userService.isRegisted(model.getEmailAddress())) {
			addFieldError("emailAddress", "邮箱已占用");
			return;
		}
	}

}
