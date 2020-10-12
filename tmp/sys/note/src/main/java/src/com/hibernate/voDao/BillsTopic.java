package com.hibernate.voDao;

import java.util.Date;

/**
 * billsTopic entity. @author MyEclipse Persistence Tools
 */

public class BillsTopic implements java.io.Serializable {

	// Fields

	private Integer btpid;
	private String btpname;
	private Integer uid;
	private Date btptime;
	private Date btplasttime;
	private Integer usercount;
	private Integer topiccount;

	// Constructors

	/** default constructor */
	public BillsTopic() {
	}

	/** full constructor */
	public BillsTopic(String btpname, Integer uid, Date btptime, Date btplasttime, Integer usercount, Integer topiccount) {
		this.btpname = btpname;
		this.uid = uid;
		this.btptime = btptime;
		this.btplasttime = btplasttime;
		this.usercount = usercount;
		this.topiccount = topiccount;
	}

	// Property accessors

	public Integer getBtpid() {
		return btpid;
	}

	public void setBtpid(Integer btpid) {
		this.btpid = btpid;
	}

	public String getBtpname() {
		return btpname;
	}

	public void setBtpname(String btpname) {
		this.btpname = btpname;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Date getBtptime() {
		return btptime;
	}

	public void setBtptime(Date btptime) {
		this.btptime = btptime;
	}

	public Date getBtplasttime() {
		return btplasttime;
	}

	public void setBtplasttime(Date btplasttime) {
		this.btplasttime = btplasttime;
	}

	public Integer getUsercount() {
		return usercount;
	}

	public void setUsercount(Integer usercount) {
		this.usercount = usercount;
	}

	public Integer getTopiccount() {
		return topiccount;
	}

	public void setTopiccount(Integer topiccount) {
		this.topiccount = topiccount;
	}

}