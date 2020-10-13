package com.managementsystem.entity;
/**
 * 
 * @author cong.liu
 *
 */
public class User {
	/*
	 * 用户ID
	 */
	private Long id;
	/*
	 * 用户名称
	 */
	private String userName;
	/*
	 * 用户密码
	 */
	private String passWord;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
}
