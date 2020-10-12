package com.hibernate.voDao;

/**
 * bills_type entity. @author MyEclipse Persistence Tools
 */

public class BillsType implements java.io.Serializable {

	// Fields

	private Integer btypeid;
	private String btypename;
	private Integer btypeuserid;

	// Constructors

	/** default constructor */
	public BillsType() {
	}

	/** full constructor */
	public BillsType(String btypename, Integer btypeuserid) {
		this.btypename = btypename;
		this.btypeuserid = btypeuserid;
	}

	// Property accessors

	public Integer getBtypeid() {
		return this.btypeid;
	}

	public void setBtypeid(Integer btypeid) {
		this.btypeid = btypeid;
	}

	public String getBtypename() {
		return this.btypename;
	}

	public void setBtypename(String btypename) {
		this.btypename = btypename;
	}

	public Integer getBtypeuserid() {
		return this.btypeuserid;
	}

	public void setBtypeuserid(Integer btypeuserid) {
		this.btypeuserid = btypeuserid;
	}

}