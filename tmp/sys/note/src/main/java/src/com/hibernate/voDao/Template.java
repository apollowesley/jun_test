package com.hibernate.voDao;

/**
 * template entity. @author MyEclipse Persistence Tools
 */

public class Template implements java.io.Serializable {

	// Fields

	private Integer tid;
	private String tname;
	private String tdirectory;
	private String tcopyright;
	private short ttype;

	// Constructors

	/** default constructor */
	public Template() {
	}

	/** full constructor */
	public Template(String tname, String tdirectory, String tcopyright, short ttype) {
		this.tname = tname;
		this.tdirectory = tdirectory;
		this.tcopyright = tcopyright;
		this.ttype = ttype;
	}

	// Property accessors

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTdirectory() {
		return tdirectory;
	}

	public void setTdirectory(String tdirectory) {
		this.tdirectory = tdirectory;
	}

	public String getTcopyright() {
		return tcopyright;
	}

	public void setTcopyright(String tcopyright) {
		this.tcopyright = tcopyright;
	}

	public short getTtype() {
		return ttype;
	}

	public void setTtype(short ttype) {
		this.ttype = ttype;
	}

}