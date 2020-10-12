package com.evil.pojo.system;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.evil.pojo.BaseEntity;
/**
 * @author frank_evil
 *角色实体
 */
public class Role extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String id;
	private String roleName; //角色名
	private String roleDesc; //角色的描述
	private String roleValue;//角色的值(当为-1的时候 管理员具备该角色则为超级管理员)
	
	// 不能修改注册时间
	private Date regdate = new Date();
	
	private Set<Right> rights=new HashSet<Right>();  //具备的权限列表
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id=id;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getRoleValue() {
		return roleValue;
	}
	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}
	public Set<Right> getRights() {
		return rights;
	}
	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	

}
