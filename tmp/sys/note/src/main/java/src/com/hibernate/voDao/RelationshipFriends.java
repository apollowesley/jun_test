package com.hibernate.voDao;

import java.util.Date;

/**
 * RelationshipFriends entity. @author MyEclipse Persistence Tools
 */

public class RelationshipFriends implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId1;
	private Integer userId2;
	private Date datetime;
	private short status;

	// Constructors

	/** default constructor */
	public RelationshipFriends() {
	}

	/** full constructor */
	public RelationshipFriends(Integer userId1, Integer userId2, Date datetime, short status) {
		this.userId1 = userId1;
		this.userId2 = userId2;
		this.datetime = datetime;
		this.status = status;
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

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

}