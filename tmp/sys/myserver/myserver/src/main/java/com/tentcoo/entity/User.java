package com.tentcoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user")
public class User extends IdEntity{
	@Column(name = "f_no")
	String userNo;
	@Column(name = "f_name")
	String userName;
	@Column(name = "f_sex")
	String userSex;
	@Column(name = "f_password")
	String userPassword;
	@Column(name = "f_birth")
	String userBirth;
	@Column(name = "f_email")
	String userEmail;
	@Column(name = "f_code")
	String userCode;
	@Column(name = "f_word")
	String userWord;
	@Column(name = "f_state")
	Integer userState;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String userNo, String userName, String userSex, String userPassword, String userBirth, String userEmail,
			String userCode, String userWord, Integer userState) {
		super();
		this.userNo = userNo;
		this.userName = userName;
		this.userSex = userSex;
		this.userPassword = userPassword;
		this.userBirth = userBirth;
		this.userEmail = userEmail;
		this.userCode = userCode;
		this.userWord = userWord;
		this.userState = userState;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserBirth() {
		return userBirth;
	}
	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserWord() {
		return userWord;
	}
	public void setUserWord(String userWord) {
		this.userWord = userWord;
	}
	public Integer getUserState() {
		return userState;
	}
	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	

	
}
