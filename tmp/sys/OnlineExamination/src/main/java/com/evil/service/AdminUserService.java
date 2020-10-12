package com.evil.service;

import java.util.Map;

import com.evil.pojo.system.AdminUser;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface AdminUserService extends BaseService<AdminUser> {
	
	/**
	 * 验证管理员登陆
	 */
	AdminUser LoginCheck(String name, String encode);

	/**
	 * 查询管理员的并分页
	 * @param page  分页信息
	 * @param map   查询限制条件
	 * @param sortfields 排序信息
	 * @return
	 */
	PageResult findAdminUserPage(Page page, Map<String, Object> map,
			String...sortfields);

	void batchDeleteAdminUsers(String[] strSplit);
	/**
	 * 判断管理员名是否被占用
	 * @param name
	 * @return
	 */
	boolean isNameOccupy(String name);
	
	/**
	 * 修改管理员的授权信息
	 * @param model
	 * @param ownRoleIds
	 */
	void updateAmdinUserAuthorize(AdminUser model, String[] ownRoleIds);


}
