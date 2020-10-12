package com.jason.utils.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Account extends Authenticator {
	
	private String userName;
	private String password;
	private String email;
	
	public Account(String email,String password) {
		this.password=password;
		this.email=email;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}
	
}