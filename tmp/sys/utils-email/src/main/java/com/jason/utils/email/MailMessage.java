package com.jason.utils.email;

import java.util.Date;

/**
 * 邮件消息抽象类
 * @author jason
 *
 */
public abstract class MailMessage {

	/**
	 * 接收者邮箱
	 */
	private String toAddress;

	/**
	 * 邮件主题
	 */
	private String subject;
	
	/**
	 * 发送时间
	 */
	private Date sendTime;

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * 发送时间，当为null时表示立即发送
	 * @param sendTime
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	
}