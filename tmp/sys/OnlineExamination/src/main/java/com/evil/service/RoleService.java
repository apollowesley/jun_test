package com.evil.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.evil.pojo.system.Role;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface RoleService extends BaseService<Role> {
	
	/**
	 * 查询角色列表并分页
	 * @param page  分页信息
	 * @param map   查询限制条件
	 * @param sortfields 排序信息
	 * @return
	 */
	PageResult findRolesPage(Page page, Map<String, Object> map,
			String...sortfields);
	/**
	 * 批量删除角色
	 */
	void batchDeleteRoles(String[] rids);
	
	/**
	 *查询不咋范围类的角色 
	 * @param roles
	 */
	List<Role> findRolesNotInRange(Set<Role> roles);
	
	/**
	 * 保存/更新角色
	 * @param model
	 */
	void saveOrUpdateRole(Role model,String[] ownRightIds);

	
}
