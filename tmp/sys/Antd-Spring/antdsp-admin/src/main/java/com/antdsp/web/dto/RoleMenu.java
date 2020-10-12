package com.antdsp.web.dto;

public class RoleMenu {
	private Long id;
	private Long roleId;
	private Long menuId;
	
	public RoleMenu() {
		super();
	}

	public RoleMenu(Long id, Long roleId, Long menuId) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.menuId = menuId;
	}

	public Long getId() {
		return id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	
}
