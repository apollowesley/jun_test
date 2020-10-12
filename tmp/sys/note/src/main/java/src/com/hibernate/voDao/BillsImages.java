package com.hibernate.voDao;

import java.util.Date;

/**
 * billsImages entity. @author MyEclipse Persistence Tools
 */

public class BillsImages implements java.io.Serializable {

	// Fields

	private Integer biid;
	private Integer bibillid;
	private String bidir;
	private String bioriginalname;
	private Integer uid;
	private String username;
	private Date bitime;
	private String filename;
	private String filesize;

	// Constructors

	/** default constructor */
	public BillsImages() {
	}

	/** full constructor */

	public BillsImages(Integer biid, Integer bibillid, String bidir, String bioriginalname, Integer uid, String username, Date bitime, String filename, String filesize) {
		super();
		this.biid = biid;
		this.bibillid = bibillid;
		this.bidir = bidir;
		this.bioriginalname = bioriginalname;
		this.uid = uid;
		this.username = username;
		this.bitime = bitime;
		this.filename = filename;
		this.filesize = filesize;
	}

	// Property accessors

	public Integer getBiid() {
		return biid;
	}

	public void setBiid(Integer biid) {
		this.biid = biid;
	}

	public Integer getBibillid() {
		return bibillid;
	}

	public void setBibillid(Integer bibillid) {
		this.bibillid = bibillid;
	}

	public String getBidir() {
		return bidir;
	}

	public void setBidir(String bidir) {
		this.bidir = bidir;
	}

	public String getBioriginalname() {
		return bioriginalname;
	}

	public void setBioriginalname(String bioriginalname) {
		this.bioriginalname = bioriginalname;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBitime() {
		return bitime;
	}

	public void setBitime(Date bitime) {
		this.bitime = bitime;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

}