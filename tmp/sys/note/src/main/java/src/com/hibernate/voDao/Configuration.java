package com.hibernate.voDao;

/**
 * configuration entity. @author MyEclipse Persistence Tools
 */

public class Configuration implements java.io.Serializable {

	// Fields

	private String ckey;
	private String cvalue;

	// Constructors

	/** default constructor */
	public Configuration() {
	}

	/** full constructor */
	public Configuration(String cvalue) {
		this.cvalue = cvalue;
	}

	// Property accessors

	public String getCkey() {
		return ckey;
	}

	public void setCkey(String ckey) {
		this.ckey = ckey;
	}

	public String getCvalue() {
		return cvalue;
	}

	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}

}