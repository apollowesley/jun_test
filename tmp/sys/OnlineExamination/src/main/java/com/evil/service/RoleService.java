package com.evil.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.evil.pojo.system.Role;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface RoleService extends BaseService<Role> {
	
	/**
	 * ��ѯ��ɫ�б���ҳ
	 * @param page  ��ҳ��Ϣ
	 * @param map   ��ѯ��������
	 * @param sortfields ������Ϣ
	 * @return
	 */
	PageResult findRolesPage(Page page, Map<String, Object> map,
			String...sortfields);
	/**
	 * ����ɾ����ɫ
	 */
	void batchDeleteRoles(String[] rids);
	
	/**
	 *��ѯ��զ��Χ��Ľ�ɫ 
	 * @param roles
	 */
	List<Role> findRolesNotInRange(Set<Role> roles);
	
	/**
	 * ����/���½�ɫ
	 * @param model
	 */
	void saveOrUpdateRole(Role model,String[] ownRightIds);

	
}
