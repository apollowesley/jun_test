package com.evil.service;

import java.util.Map;

import com.evil.pojo.system.AdminUser;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface AdminUserService extends BaseService<AdminUser> {
	
	/**
	 * ��֤����Ա��½
	 */
	AdminUser LoginCheck(String name, String encode);

	/**
	 * ��ѯ����Ա�Ĳ���ҳ
	 * @param page  ��ҳ��Ϣ
	 * @param map   ��ѯ��������
	 * @param sortfields ������Ϣ
	 * @return
	 */
	PageResult findAdminUserPage(Page page, Map<String, Object> map,
			String...sortfields);

	void batchDeleteAdminUsers(String[] strSplit);
	/**
	 * �жϹ���Ա���Ƿ�ռ��
	 * @param name
	 * @return
	 */
	boolean isNameOccupy(String name);
	
	/**
	 * �޸Ĺ���Ա����Ȩ��Ϣ
	 * @param model
	 * @param ownRoleIds
	 */
	void updateAmdinUserAuthorize(AdminUser model, String[] ownRoleIds);


}
