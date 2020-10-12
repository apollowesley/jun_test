package com.hibernate.voDao;

import java.util.Date;

/**
 * UserLoginLog entity. @author MyEclipse Persistence Tools
 */

public class UserLoginLog implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer fkUid;
	private Date loginTime;
	private String loginIp;
	private String remark;

	// Constructors

	/** default constructor */
	public UserLoginLog() {
	}

	/** full constructor */
	public UserLoginLog(Integer fkUid, Date loginTime, String loginIp, String remark) {
		this.fkUid = fkUid;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
		this.remark = remark;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFkUid() {
		return this.fkUid;
	}

	public void setFkUid(Integer fkUid) {
		this.fkUid = fkUid;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}