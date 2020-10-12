package com.hibernate.voDao;

import java.util.Date;

/**
 * RelationshipShield entity. @author MyEclipse Persistence Tools
 */

public class RelationshipShield implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId1;
	private Integer userId2;
	private Date datetime;
	private short type;

	// Constructors

	/** default constructor */
	public RelationshipShield() {
	}

	/** full constructor */
	public RelationshipShield(Integer userId1, Integer userId2, Date datetime, short type) {
		this.userId1 = userId1;
		this.userId2 = userId2;
		this.datetime = datetime;
		this.type = type;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId1() {
		return this.userId1;
	}

	public void setUserId1(Integer userId1) {
		this.userId1 = userId1;
	}

	public Integer getUserId2() {
		return this.userId2;
	}

	public void setUserId2(Integer userId2) {
		this.userId2 = userId2;
	}

	public Date getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public short getType() {
		return this.type;
	}

	public void setType(short type) {
		this.type = type;
	}

}