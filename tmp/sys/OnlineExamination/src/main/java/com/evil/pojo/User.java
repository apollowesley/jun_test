package com.evil.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.evil.pojo.system.Role;

// default package

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User extends BaseEntity {
	private static final long serialVersionUID = 643156225229025854L;

	private String id;
	private String userName; // 姓名
	private String userType = "0"; // 身份类型
	private String password; // 密码
	private Integer userAge; // 年龄
	private String userUnit; // 单位
	private String emailAddress; // 邮箱
	private boolean isEnabled=true;  //是否启用  启用了的用户才能登陆
	private String isDelete = "1"; // 是否删除
	
	private Set<Role> roles=new HashSet<Role>(); //具有的角色列表

	// 不能修改注册的时间y
	private Date regdate = new Date();

	public User() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserAge() {
		return this.userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserUnit() {
		return this.userUnit;
	}

	public void setUserUnit(String userUnit) {
		this.userUnit = userUnit;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public boolean getIsEnabled(){
		return isEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	

	
	

}