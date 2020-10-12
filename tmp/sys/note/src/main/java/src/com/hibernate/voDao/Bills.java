package com.hibernate.voDao;

import java.util.Date;

/**
 * bills entity. @author MyEclipse Persistence Tools
 */

public class Bills implements java.io.Serializable {

	// Fields

	private Integer bid;
	private String username;
	private Integer uid;
	private Date bdate;
	private Integer btype;
	private Integer bio;
	private double bamount;
	private String bcaption;
	private boolean bbetravelleader;
	private boolean bbetravelmember;
	private Integer btid;
	private String bimageid;
	private Integer bctype;
	private Integer replys;
	private Integer forwards;
	private Integer rootbid;
	private Integer frombid;
	private Integer fromuid;
	private String fromuname;
	private Integer fromw;
	private String fromip;
	private boolean isdeleted;

	// Constructors

	/** default constructor */
	public Bills() {
	}

	/** full constructor */
	public Bills(String username, Integer uid, Date bdate, Integer btype, Integer bio, double bamount, String bcaption, boolean bbetravelleader, boolean bbetravelmember,
			Integer btid, String bimageid, Integer bctype, Integer replys, Integer forwards, Integer rootbid, Integer frombid, Integer fromuid, String fromuname, Integer fromw,
			String fromip, boolean isdeleted) {
		this.username = username;
		this.uid = uid;
		this.bdate = bdate;
		this.btype = btype;
		this.bio = bio;
		this.bamount = bamount;
		this.bcaption = bcaption;
		this.bbetravelleader = bbetravelleader;
		this.bbetravelmember = bbetravelmember;
		this.btid = btid;
		this.bimageid = bimageid;
		this.bctype = bctype;
		this.replys = replys;
		this.forwards = forwards;
		this.rootbid = rootbid;
		this.frombid = frombid;
		this.fromuid = fromuid;
		this.fromuname = fromuname;
		this.fromw = fromw;
		this.fromip = fromip;
		this.isdeleted = isdeleted;
	}

	// Property accessors

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Date getBdate() {
		return bdate;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	public Integer getBtype() {
		return btype;
	}

	public void setBtype(Integer btype) {
		this.btype = btype;
	}

	public Integer getBio() {
		return bio;
	}

	public void setBio(Integer bio) {
		this.bio = bio;
	}

	public double getBamount() {
		return bamount;
	}

	public void setBamount(double bamount) {
		this.bamount = bamount;
	}

	public String getBcaption() {
		return bcaption;
	}

	public void setBcaption(String bcaption) {
		this.bcaption = bcaption;
	}

	public boolean getBbetravelleader() {
		return bbetravelleader;
	}

	public void setBbetravelleader(boolean bbetravelleader) {
		this.bbetravelleader = bbetravelleader;
	}

	public boolean getBbetravelmember() {
		return bbetravelmember;
	}

	public void setBbetravelmember(boolean bbetravelmember) {
		this.bbetravelmember = bbetravelmember;
	}

	public Integer getBtid() {
		return btid;
	}

	public void setBtid(Integer btid) {
		this.btid = btid;
	}

	public String getBimageid() {
		if (bimageid == null) {
			return "";
		} else {
			return bimageid;
		}
	}

	public void setBimageid(String bimageid) {
		this.bimageid = bimageid;
	}

	public Integer getBctype() {
		return bctype;
	}

	public void setBctype(Integer bctype) {
		this.bctype = bctype;
	}

	public Integer getReplys() {
		return replys;
	}

	public void setReplys(Integer replys) {
		this.replys = replys;
	}

	public Integer getForwards() {
		return forwards;
	}

	public void setForwards(Integer forwards) {
		this.forwards = forwards;
	}

	public Integer getRootbid() {
		return rootbid;
	}

	public void setRootbid(Integer rootbid) {
		this.rootbid = rootbid;
	}

	public Integer getFrombid() {
		return frombid;
	}

	public void setFrombid(Integer frombid) {
		this.frombid = frombid;
	}

	public Integer getFromuid() {
		return fromuid;
	}

	public void setFromuid(Integer fromuid) {
		this.fromuid = fromuid;
	}

	public String getFromuname() {
		return fromuname;
	}

	public void setFromuname(String fromuname) {
		this.fromuname = fromuname;
	}

	public Integer getFromw() {
		return fromw;
	}

	public void setFromw(Integer fromw) {
		this.fromw = fromw;
	}

	public String getFromip() {
		return fromip;
	}

	public void setFromip(String fromip) {
		this.fromip = fromip;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

}