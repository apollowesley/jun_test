package com.jason.utils.email.event;

import org.apache.log4j.Logger;

import com.jason.utils.email.MailMessage;

public class SimpleMailSendEventListener implements MailSendEventListener{

	private Logger logger=Logger.getLogger(getClass());
	
	@Override
	public void execute(MailSendEventObject event) {
		MailMessage message=(MailMessage) event.getSource();
		logger.debug(event.getFromAddress()+"-->"+message.getToAddress()+" send email success!");
	}

}