package com.hibernate.voDao;

import java.util.Date;

/**
 * feedbacks entity. @author MyEclipse Persistence Tools
 */

public class Feedbacks implements java.io.Serializable {

	// Fields

	private Integer fid;
	private String username;
	private Integer uid;
	private Date fdate;
	private String contents;
	private boolean replystatus;
	private String rusername;
	private String ruid;
	private Date frdate;
	private String rcontents;

	// Constructors

	/** default constructor */
	public Feedbacks() {
	}

	/** full constructor */
	public Feedbacks(String username, Integer uid, Date fdate, String contents,
			boolean replystatus, String rusername, String ruid, Date frdate,
			String rcontents) {
		this.username = username;
		this.uid = uid;
		this.fdate = fdate;
		this.contents = contents;
		this.replystatus = replystatus;
		this.rusername = rusername;
		this.ruid = ruid;
		this.frdate = frdate;
		this.rcontents = rcontents;
	}

	// Property accessors

	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Date getFdate() {
		return this.fdate;
	}

	public void setFdate(Date fdate) {
		this.fdate = fdate;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public boolean getReplystatus() {
		return this.replystatus;
	}

	public void setReplystatus(boolean replystatus) {
		this.replystatus = replystatus;
	}

	public String getRusername() {
		return this.rusername;
	}

	public void setRusername(String rusername) {
		this.rusername = rusername;
	}

	public String getRuid() {
		return this.ruid;
	}

	public void setRuid(String ruid) {
		this.ruid = ruid;
	}

	public Date getFrdate() {
		return this.frdate;
	}

	public void setFrdate(Date frdate) {
		this.frdate = frdate;
	}

	public String getrcontents() {
		return this.rcontents;
	}

	public void setrcontents(String rcontents) {
		this.rcontents = rcontents;
	}

}