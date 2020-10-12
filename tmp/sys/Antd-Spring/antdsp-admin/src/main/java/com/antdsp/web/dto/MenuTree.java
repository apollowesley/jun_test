package com.antdsp.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuTree implements Serializable{

	private Long id;
	
	private Long parentId;
	
	private String name;
	
	private String path;
	
	private String icon;
	
	private String permission;
	
	private String type;
	
	private boolean hideInMenu;
	
	private List<MenuTree> children;

	public Long getId() {
		return id;
	}

	public Long getParentId() {
		return parentId;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getIcon() {
		return icon;
	}

	public String getPermission() {
		return permission;
	}

	public String getType() {
		return type;
	}

	public List<MenuTree> getChildren() {
		return children;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}
	
	public void appendChildren(MenuTree childMen) {
		if(children == null) {
			children = new ArrayList<>();
		}
		children.add(childMen);
	}

	public boolean isHideInMenu() {
		return hideInMenu;
	}

	public void setHideInMenu(boolean hideInMenu) {
		this.hideInMenu = hideInMenu;
	}
}
