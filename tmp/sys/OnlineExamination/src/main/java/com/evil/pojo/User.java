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
	private String userName; // ����
	private String userType = "0"; // �������
	private String password; // ����
	private Integer userAge; // ����
	private String userUnit; // ��λ
	private String emailAddress; // ����
	private boolean isEnabled=true;  //�Ƿ�����  �����˵��û����ܵ�½
	private String isDelete = "1"; // �Ƿ�ɾ��
	
	private Set<Role> roles=new HashSet<Role>(); //���еĽ�ɫ�б�

	// �����޸�ע���ʱ��y
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