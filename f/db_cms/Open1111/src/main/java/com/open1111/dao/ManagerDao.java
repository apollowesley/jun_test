package com.open1111.dao;

import com.open1111.entity.Manager;

/**
 * ����ԱDao�ӿ�
 * @author user
 *
 */
public interface ManagerDao {

	/**
	 * ͨ���û�����ѯ�û�
	 * @param userName
	 * @return
	 */
	public Manager getByUserName(String userName);
	
	/**
	 * ���¹���Ա��Ϣ
	 * @param manager
	 * @return
	 */
	public Integer update(Manager manager);
	
}
