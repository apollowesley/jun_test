package com.laycms.sys.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.laycms.base.entity.BaseEntity;

/**
 * RoleMenu entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name="rolemenu")
public class RoleMenu extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5943960961422074520L;
	// Fields

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer roleId;
	private Integer menuId;
	private String description;

	// Constructors

	
	

	public Integer getRoleId() {
		return this.roleId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}