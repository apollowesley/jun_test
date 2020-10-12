package com.antdsp.data.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.antdsp.data.entity.AbstractEntity;
import com.antdsp.data.entityeenum.MenuType;

@Table(name="tb_system_menu")
@Entity
public class SystemMenu extends AbstractEntity {

	@Column(name="menu_name" , nullable=false , length=32)
	private String menuName;
	
	@Column(name="parent_id" , nullable = false )
	private Long parentId;
	
	@Column(name="url" , length=64)
	private String url;
	
	@Column(name="permission" , length=64)
	private String permission;
	
	@Column(name="icon" , length=64)
	private String icon;
	
	@Column(name="hide" , length=1)
	private boolean hideInMenu;
	
	@Column(name="menu_type" , length=12)
	@Enumerated(value=EnumType.STRING)
	private MenuType menuType;
	
	@Column(name="reamrk" , length=64)
	private String reamrk;

	public String getMenuName() {
		return menuName;
	}

	public Long getParentId() {
		return parentId;
	}

	public String getUrl() {
		return url;
	}

	public String getPermission() {
		return permission;
	}

	public String getIcon() {
		return icon;
	}

	public String getReamrk() {
		return reamrk;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setReamrk(String reamrk) {
		this.reamrk = reamrk;
	}

	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

	public boolean isHideInMenu() {
		return hideInMenu;
	}

	public void setHideInMenu(boolean hideInMenu) {
		this.hideInMenu = hideInMenu;
	}
	
}
