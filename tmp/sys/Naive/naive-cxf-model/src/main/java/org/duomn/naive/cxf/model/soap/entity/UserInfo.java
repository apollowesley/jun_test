package org.duomn.naive.cxf.model.soap.entity;

public class UserInfo {
	
	private String userName;
	
	private int age;
	
	public UserInfo() {
		
	}

	public UserInfo(String userName, int age) {
		super();
		this.userName = userName;
		this.age = age;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
}
