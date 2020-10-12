package com.jason.utils.email.message;

import com.jason.utils.email.MailMessage;

/**
 * HTML消息，支持附件
 * @author jason
 *
 */
public class MultipartMessage extends MailMessage{
	/**
	 * 邮件内容
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}