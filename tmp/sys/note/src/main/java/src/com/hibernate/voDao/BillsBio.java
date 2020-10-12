package com.hibernate.voDao;

/**
 * billsBio entity. @author MyEclipse Persistence Tools
 */

public class BillsBio implements java.io.Serializable {

	// Fields

	private short bio;
	private String bioname;
	private String bsign;

	// Constructors

	/** default constructor */
	public BillsBio() {
	}

	/** full constructor */
	public BillsBio(String bioname, String bsign) {
		this.bioname = bioname;
		this.bsign = bsign;
	}

	// Property accessors

	public short getBio() {
		return bio;
	}

	public void setBio(short bio) {
		this.bio = bio;
	}

	public String getBioname() {
		return bioname;
	}

	public void setBioname(String bioname) {
		this.bioname = bioname;
	}

	public String getBsign() {
		return bsign;
	}

	public void setBsign(String bsign) {
		this.bsign = bsign;
	}

}