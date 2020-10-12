package com.freedom.mysql.myrwsplit.interfaces;

import com.freedom.mysql.myrwsplit.bean.Role;


public interface RoleMapper {
	public Role getRole(int id);
	public Role getRole0(int id);
	public void insertRole(Role r);
	
	public void deleteRole(String id);
	public void deleteRole(Role r);
}
