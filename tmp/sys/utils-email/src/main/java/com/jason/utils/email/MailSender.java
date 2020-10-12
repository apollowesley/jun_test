package com.jason.utils.email;

import java.util.Date;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.jason.utils.email.event.MailSendEventListener;
import com.jason.utils.email.event.MailSendEventObject;
import com.jason.utils.email.exception.AuthenticationException;
import com.jason.utils.email.exception.MailMessageException;
import com.jason.utils.email.exception.SenderInstanceException;
import com.jason.utils.email.message.MultipartMessage;
import com.jason.utils.email.message.SimpleMessage;

/**
 * Email发送器
 * @author jason
 *
 */
public final class MailSender {

	private Gateway gateway;

	private Account account;

	private Session session = null;
	
	private MailSendEventListener mailSendEventListener;

	public MailSender(Account account) {
		this.account=account;
		if(account!=null && account.getEmail()!=null){
			gateway=Gateway.mailSuffixAdaptation(account.getEmail());
			if(gateway==null){
				throw new SenderInstanceException(SenderInstanceException.GATEWAY_NULL);
			}
			account.setUserName(gateway.resolveAccount(account.getEmail()));
		}
		instance();
	}

	private void instance(){
		if(gateway==null){
			throw new SenderInstanceException(SenderInstanceException.GATEWAY_NULL);
		}
		try {
			if (gateway.isAuth()) {
				if (account == null || account.equals("")) {
					throw new AuthenticationException(AuthenticationException.ACCOUNT_NULL);
				}
				session = Session.getDefaultInstance(gateway.getProperties(), account);
			} else {
				session = Session.getDefaultInstance(gateway.getProperties());
			}
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
	}
	
	/**
	 * 发送一条简单的邮件
	 * @param toAddress
	 * @param subject
	 * @param text
	 * @throws MailMessageException
	 */
	public void send(String toAddress,String subject,String text) throws MailMessageException{
		if(toAddress==null){
			throw new MailMessageException("邮件接收者不能为空");
		}
		if(text==null){
			//throw new MailMessageException("邮件内容不能为空");
			text="";
		}
		if(subject==null){
			subject="";
		}
		
		SimpleMessage message=new SimpleMessage();
		message.setToAddress(toAddress);
		message.setSubject(subject);
		message.setText(text);
		send(message);
	}
	
	/**
	 * 发送邮件
	 * @param message
	 * @throws MailMessageException
	 */
	public void send(MailMessage message) throws MailMessageException{
		try {
			Message mailMessage =getMessage(message);
			// 发送邮件
			Transport.send(mailMessage);
			if(mailSendEventListener!=null){
				mailSendEventListener.execute(new MailSendEventObject(account.getEmail(), message));
			}
		} catch (MessagingException ex) {
			throw new MailMessageException(ex);
		}
	}
	
	private Message getMessage(MailMessage message) throws MessagingException,MailMessageException{
		if(message==null){
			throw new MailMessageException("message is null");
		}
		if(message.getToAddress()==null){
			throw new MailMessageException("message toAddress is null");
		}
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(session);
		
		// 创建邮件发送者地址
		Address from = new InternetAddress(account.getEmail());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);

		// 创建邮件的接收者地址，并设置到邮件消息中
		Address to = new InternetAddress(message.getToAddress());
		mailMessage.setRecipient(Message.RecipientType.TO, to);

		// 设置邮件消息的主题
		mailMessage.setSubject(message.getSubject());
		// 设置邮件消息发送的时间
		if(message.getSendTime()==null){
			mailMessage.setSentDate(new Date());
		}else{
			mailMessage.setSentDate(message.getSendTime());
		}
		
		// 设置邮件消息的主要内容
		if (message instanceof MultipartMessage) {
			mailMessage=setContent(mailMessage,(MultipartMessage) message);
		}else{
			mailMessage=setText(mailMessage,(SimpleMessage) message);
		}
		return mailMessage;
	}
	
	private Message setText(Message mailMessage, SimpleMessage message) throws MessagingException {
		if(message.getText()==null){
			message.setText("");
		}
		mailMessage.setText(message.getText());
		return mailMessage;
	}

	private Message setContent(Message mailMessage,MultipartMessage message) throws MessagingException {
	      if(message.getContent()==null){
	    	  message.setContent("");
	      }
		  // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
	      Multipart mainPart = new MimeMultipart();
	      // 创建一个包含HTML内容的MimeBodyPart
	      BodyPart html = new MimeBodyPart();
	      // 设置HTML内容
	      html.setContent(message.getContent(), "text/html; charset=utf-8");
	      mainPart.addBodyPart(html);
	      // 将MiniMultipart对象设置为邮件内容    
	      mailMessage.setContent(mainPart);
	      return mailMessage;
	}

	public Account getAccount() {
		return account;
	}

	public Gateway getGateway() {
		return gateway;
	}

	public Session getSession() {
		return session;
	}
	
	public void setAccount(Account account) {
		this.account = account;
		instance();
	}
	
	public void setSendEventListener(MailSendEventListener mailSendEventListener) {
		this.mailSendEventListener = mailSendEventListener;
	}
	
	public void removeSendEventListener() {
		this.mailSendEventListener = null;
	}
}