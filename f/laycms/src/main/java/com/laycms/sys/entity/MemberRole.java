package com.laycms.sys.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.laycms.base.entity.BaseEntity;

/**
 * UserRole entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name="memberrole")
public class MemberRole extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2670739780676007270L;
	// Fields

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer memberId;
	private Integer roleId;
	private String description;

	// Constructors

	/** default constructor */
	public MemberRole() {
	}

	/** minimal constructor */
	public MemberRole(Integer memberId, Integer roleId) {
		this.memberId = memberId;
		this.roleId = roleId;
	}

	/** full constructor */
	public MemberRole(Integer memberId, Integer roleId, String description) {
		this.memberId = memberId;
		this.roleId = roleId;
		this.description = description;
	}

	// Property accessors


	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

}