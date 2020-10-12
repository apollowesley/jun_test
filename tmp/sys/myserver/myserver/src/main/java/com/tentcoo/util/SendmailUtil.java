package com.tentcoo.util;

import java.util.Properties;

import javax.mail.Message;

import javax.mail.MessagingException;

import javax.mail.Session;

import javax.mail.Transport;

import javax.mail.internet.AddressException;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMessage.RecipientType;

public class SendmailUtil {    

        public static void sendEmail(String toStr,String code) throws AddressException,MessagingException {       

					  Properties properties = new Properties();
					
					  properties.put("mail.transport.protocol", "smtp");
					
					  properties.put("mail.smtp.host", "smtp.qq.com");
					
					  properties.put("mail.smtp.port", 465);
					
					  properties.put("mail.smtp.auth", "true");        
					
					  properties.put("mail.smtp.ssl.enable", "true");
					
					  properties.put("mail.debug", "true");
		
						
						
					  Session session = Session.getInstance(properties);        
						
					
						
					  Message message = new MimeMessage(session);        
						
					
						
					  message.setFrom(new InternetAddress("knownothing.xyz@qq.com"));       
						
					
					  message.setRecipients(RecipientType.TO, new InternetAddress[] { new InternetAddress(toStr) });       
						
					
						
					  message.setSubject("knownothing网站激活邮件");        
						
					
						
					  message.setContent("<h1>请不要相信tx，点击此链接激活账户，若不能打开，请复制此链接至浏览器打开即可:</h1><h3><a href='http://localhost:8080/myserver/ActiveServlet?code="+code+"'>http://localhost:8080/myserver/ActiveServlet?code="+code+"</a></h3>", "text/html;charset=UTF-8");
						
					
						
					  Transport transport = session.getTransport();        
						
					
						
					  transport.connect("smtp.qq.com", "knownothing.xyz@qq.com","khuooewhtafpiccg" );
					  
						
						
					  transport.sendMessage(message, message.getAllRecipients());    

        }

 }