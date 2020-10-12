package com.hibernate.voDao.admin;

/**
 * sysmenu entity. @author MyEclipse Persistence Tools
 */

public class Sysmenu implements java.io.Serializable {

	// Fields

	private Integer id;
	private String selfkey;
	private String parentkey;
	private String name;
	private short priority;
	private Integer order;
	private String url;

	// Constructors

	/** default constructor */
	public Sysmenu() {
	}

	/** full constructor */
	public Sysmenu(String selfkey, String parentkey, String name, short priority, Integer order, String url) {
		this.selfkey = selfkey;
		this.parentkey = parentkey;
		this.name = name;
		this.priority = priority;
		this.order = order;
		this.url = url;
	}

	// Property accessors

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSelfkey() {
		return selfkey;
	}

	public void setSelfkey(String selfkey) {
		this.selfkey = selfkey;
	}

	public String getParentkey() {
		return parentkey;
	}

	public void setParentkey(String parentkey) {
		this.parentkey = parentkey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getPriority() {
		return priority;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}