package com.hibernate.voDao;

import java.util.Date;

/**
 * joinAsAPartner entity. @author MyEclipse Persistence Tools
 */

public class JoinAsAPartner implements java.io.Serializable {

	// Fields

	private Integer jaapid;
	private Integer uid;
	private String username;
	private boolean jaapissponsor;
	private String jaaptitle;
	private String jaapcontent;
	private double jaapcost;
	private String jaapadvice;
	private Integer jaapthenumberofperson;
	private short jaappermission;
	private short jaapjoinstate;
	private Integer jaapapplyid;
	private Date jaappublicdate;
	private Date jaapdeadline;
	private boolean jaapisfinish;
	private Integer bid;
	private short jaapevaluate;
	private String jaapsummary;

	// Constructors

	/** default constructor */
	public JoinAsAPartner() {
	}

	/** full constructor */
	public JoinAsAPartner(Integer uid, String username, boolean jaapissponsor, String jaaptitle, String jaapcontent, double jaapcost, String jaapadvice,
			Integer jaapthenumberofperson, short jaappermission, short jaapjoinstate, Integer jaapapplyid, Date jaappublicdate, Date jaapdeadline, boolean jaapisfinish, Integer bid,
			short jaapevaluate, String jaapsummary) {
		this.uid = uid;
		this.username = username;
		this.jaapissponsor = jaapissponsor;
		this.jaaptitle = jaaptitle;
		this.jaapcontent = jaapcontent;
		this.jaapcost = jaapcost;
		this.jaapadvice = jaapadvice;
		this.jaapthenumberofperson = jaapthenumberofperson;
		this.jaappermission = jaappermission;
		this.jaapjoinstate = jaapjoinstate;
		this.jaapapplyid = jaapapplyid;
		this.jaappublicdate = jaappublicdate;
		this.jaapdeadline = jaapdeadline;
		this.jaapisfinish = jaapisfinish;
		this.bid = bid;
		this.jaapevaluate = jaapevaluate;
		this.jaapsummary = jaapsummary;
	}

	// Property accessors

	public Integer getJaapid() {
		return jaapid;
	}

	public void setJaapid(Integer jaapid) {
		this.jaapid = jaapid;
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

	public boolean getJaapissponsor() {
		return jaapissponsor;
	}

	public void setJaapissponsor(boolean jaapissponsor) {
		this.jaapissponsor = jaapissponsor;
	}

	public String getJaaptitle() {
		return jaaptitle;
	}

	public void setJaaptitle(String jaaptitle) {
		this.jaaptitle = jaaptitle;
	}

	public String getJaapcontent() {
		return jaapcontent;
	}

	public void setJaapcontent(String jaapcontent) {
		this.jaapcontent = jaapcontent;
	}

	public double getJaapcost() {
		return jaapcost;
	}

	public void setJaapcost(double jaapcost) {
		this.jaapcost = jaapcost;
	}

	public String getJaapadvice() {
		return jaapadvice;
	}

	public void setJaapadvice(String jaapadvice) {
		this.jaapadvice = jaapadvice;
	}

	public Integer getJaapthenumberofperson() {
		return jaapthenumberofperson;
	}

	public void setJaapthenumberofperson(Integer jaapthenumberofperson) {
		this.jaapthenumberofperson = jaapthenumberofperson;
	}

	public short getJaappermission() {
		return jaappermission;
	}

	public void setJaappermission(short jaappermission) {
		this.jaappermission = jaappermission;
	}

	public short getJaapjoinstate() {
		return jaapjoinstate;
	}

	public void setJaapjoinstate(short jaapjoinstate) {
		this.jaapjoinstate = jaapjoinstate;
	}

	public Integer getJaapapplyid() {
		return jaapapplyid;
	}

	public void setJaapapplyid(Integer jaapapplyid) {
		this.jaapapplyid = jaapapplyid;
	}

	public Date getJaappublicdate() {
		return jaappublicdate;
	}

	public void setJaappublicdate(Date jaappublicdate) {
		this.jaappublicdate = jaappublicdate;
	}

	public Date getJaapdeadline() {
		return jaapdeadline;
	}

	public void setJaapdeadline(Date jaapdeadline) {
		this.jaapdeadline = jaapdeadline;
	}

	public boolean getJaapisfinish() {
		return jaapisfinish;
	}

	public void setJaapisfinish(boolean jaapisfinish) {
		this.jaapisfinish = jaapisfinish;
	}

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public short getJaapevaluate() {
		return jaapevaluate;
	}

	public void setJaapevaluate(short jaapevaluate) {
		this.jaapevaluate = jaapevaluate;
	}

	public String getJaapsummary() {
		return jaapsummary;
	}

	public void setJaapsummary(String jaapsummary) {
		this.jaapsummary = jaapsummary;
	}

}