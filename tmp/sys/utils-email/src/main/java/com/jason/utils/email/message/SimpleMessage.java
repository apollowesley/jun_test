package com.jason.utils.email.message;

import com.jason.utils.email.MailMessage;

/**
 * 简单消息，不能带附件
 * @author jason
 *
 */
public class SimpleMessage extends MailMessage{
	/**
	 * 邮件内容
	 */
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}