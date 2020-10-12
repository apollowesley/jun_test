package com.tentcoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tb_message")
public class UserMessage extends IdEntity{

	@Column(name="f_no")
	String userNo;
	@Column(name="f_name")
	String userName;
	@Column(name="f_Time")
	String userTime;
	@Column(name="f_text")
	String userWords;

	public UserMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserMessage(String userNo, String userName, String userTime, String userWords) {
		super();
		this.userNo = userNo;
		this.userName = userName;
		this.userTime = userTime;
		this.userWords = userWords;
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

	public String getUserTime() {
		return userTime;
	}

	public void setUserTime(String userTime) {
		this.userTime = userTime;
	}

	public String getUserWords() {
		return userWords;
	}

	public void setUserWords(String userWords) {
		this.userWords = userWords;
	}
	
	
	
	
}
