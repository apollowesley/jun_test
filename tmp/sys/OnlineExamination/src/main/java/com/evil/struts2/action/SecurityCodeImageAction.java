package com.evil.struts2.action;

import java.io.ByteArrayInputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.util.SecurityCode;
import com.evil.util.SecurityCode.SecurityCodeLevel;
import com.evil.util.SecurityImage;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Component("SecurityCodeImageAction")
@Scope("prototype")
public class SecurityCodeImageAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6421871251815086368L;
	private ByteArrayInputStream imageStream;

	public ByteArrayInputStream getImageStream() {
		return imageStream;
	}

	public void setImageStream(ByteArrayInputStream imageStream) {
		this.imageStream = imageStream;
	}

	/**
	 * 生成前端登陆界面用的验证码
	 */
	public String frontendValidate() {
		// 如果开启Hard模式，可以不区分大小写
		// String securityCode =
		// SecurityCode.getSecurityCode(4,SecurityCodeLevel.Hard,
		// false).toLowerCase();

		// 获取默认难度和长度的验证码
		String securityCode = SecurityCode.getSecurityCode(6,
				SecurityCodeLevel.Hard, false);
		imageStream = SecurityImage.getImageAsInputStream(securityCode);
		// 放入session中
		ActionContext.getContext().getSession()
		.put("SESSION_SECURITY_FRONTCODE", securityCode);
		return SUCCESS;
	}
	
	/**
	 * 生成后台登陆的验证码
	 * @return
	 */
	public String latarValidate() {
		String securityCode = SecurityCode.getSecurityCode(4,
				SecurityCodeLevel.Simple, false);
		imageStream = SecurityImage.getImageAsInputStream(securityCode);
		// 放入session中
		ActionContext.getContext().getSession()
				.put("SESSION_SECURITY_LATARCODE", securityCode);
		return SUCCESS;
	}

}
