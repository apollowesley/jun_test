package com.hibernate.voDao;

import java.util.Date;

/**
 * catchException entity. @author MyEclipse Persistence Tools
 */

public class CatchException implements java.io.Serializable {

	// Fields

	private Integer id;
	private String exception;
	private Date date;

	// Constructors

	/** default constructor */
	public CatchException() {
	}

	/** full constructor */
	public CatchException(String exception, Date date) {
		this.exception = exception;
		this.date = date;
	}

	// Property accessors

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}