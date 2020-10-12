package com.jason.utils.email.event;

import java.util.EventObject;
import com.jason.utils.email.MailMessage;

public class MailSendEventObject extends EventObject{
	private static final long serialVersionUID = 1L;

	private String fromAddress;
	
	private String status="success";
	
	public MailSendEventObject(String fromAddress,MailMessage source) {
		super(source);
		this.fromAddress=fromAddress;
	}
	
	public String getFromAddress() {
		return fromAddress;
	}
	
	public String getStatus() {
		return status;
	}
	
}