package com.foo.common.base.utils;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailHelper {

	private final static String email_sending_addr = "";
    private final static String email_server = "";
    private final static String email_user = "";
    private final static String email_password = "";

	public static void main(String[] args) throws Exception {

		String receiver1 = "";
		String receiver2 = "";
		String subject = "测试主题";
		String content = "测试内容";

		// check email args end.

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.auth", "true");

		javax.mail.Session session = javax.mail.Session.getInstance(properties);

		MimeMessage message = new MimeMessage(session);
		InternetAddress from = new InternetAddress(email_sending_addr);
		// InternetAddress from = new InternetAddress(
		// email_sending_addr);
		message.setFrom(from);

		// for (String to : Splitter.on(",").omitEmptyStrings()
		// .split(sysMail.getReceiver())) {
		// message.addRecipient(Message.RecipientType.TO, new InternetAddress(
		// to));
		// }
		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress(receiver1));
		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress(receiver2));

		message.setSubject(subject);
		message.setText(content);

		Transport transport = session.getTransport("smtp");
		transport.connect(email_server, email_user, email_password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();

	}
}