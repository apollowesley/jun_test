package com.antdsp.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.antdsp.data.entityeenum.UserStatus;

@Table(name="tb_user")
@Entity
public class User extends AbstractEntity{

	@Column(name="login_name" , nullable=false, length=64)
	private String loginname;
	
	@Column(name="real_name" , nullable=false, length=64)
	private String realname;
	
	@Column(name="password" , nullable=false, length=32)
	private String password;
	
	@Column(name="avatar", length=64)
	private String avatar;
	
	@Column(name="email", length=64 )
	private String email;
	
	@Column(name="qq", length=64)
	private String qq;
	
	@Column(name="status" , nullable=false, length=16)
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	public String getLoginname() {
		return loginname;
	}

	public String getRealname() {
		return realname;
	}

	public String getPassword() {
		return password;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getEmail() {
		return email;
	}

	public String getQq() {
		return qq;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	
}
