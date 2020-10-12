package model;

import java.util.Set;

public class Users {	
	
	private int userid;	
	private String password;	
	private String username;
	
	//这里一个用户可以发送多个消息，也可以接收多个消息. one-to-many
	private Set<Message> sendMessages;
	private Set<Message>  getMessages;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Set<Message> getSendMessages() {
		return sendMessages;
	}
	public void setSendMessages(Set<Message> sendMessages) {
		this.sendMessages = sendMessages;
	}
	public Set<Message> getGetMessages() {
		return getMessages;
	}
	public void setGetMessages(Set<Message> getMessages) {
		this.getMessages = getMessages;
	}
	
}
