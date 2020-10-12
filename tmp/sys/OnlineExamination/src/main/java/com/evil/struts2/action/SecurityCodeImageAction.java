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
	 * ����ǰ�˵�½�����õ���֤��
	 */
	public String frontendValidate() {
		// �������Hardģʽ�����Բ����ִ�Сд
		// String securityCode =
		// SecurityCode.getSecurityCode(4,SecurityCodeLevel.Hard,
		// false).toLowerCase();

		// ��ȡĬ���ѶȺͳ��ȵ���֤��
		String securityCode = SecurityCode.getSecurityCode(6,
				SecurityCodeLevel.Hard, false);
		imageStream = SecurityImage.getImageAsInputStream(securityCode);
		// ����session��
		ActionContext.getContext().getSession()
		.put("SESSION_SECURITY_FRONTCODE", securityCode);
		return SUCCESS;
	}
	
	/**
	 * ���ɺ�̨��½����֤��
	 * @return
	 */
	public String latarValidate() {
		String securityCode = SecurityCode.getSecurityCode(4,
				SecurityCodeLevel.Simple, false);
		imageStream = SecurityImage.getImageAsInputStream(securityCode);
		// ����session��
		ActionContext.getContext().getSession()
				.put("SESSION_SECURITY_LATARCODE", securityCode);
		return SUCCESS;
	}

}
