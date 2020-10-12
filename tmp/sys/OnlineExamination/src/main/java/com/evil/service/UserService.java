package com.evil.service;

import java.util.List;
import java.util.Map;

import com.evil.pojo.User;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface UserService extends BaseService<User> {
	//��¼У����
	public User LoginCheck(String email,String password);
	//��ȡ����ѧ�����б�
	public List<User> findAll();

	//����ѧ���������ȡѧ��
	public User findByEmail(String email);
	/**
	 * �ж������Ƿ�ռ�� 
	 */
	public boolean isRegisted(String emailAddress);
	
	/**
	 * ���ݷ�ҳ��Ϣ������ѯuser
	 * @param page ��ҳ��Ϣ
	 * @param map ��������
	 * @return
	 */
	PageResult findPageUsers(Page page, Map<String, Object> map,String...sortfields);
	
	/**
	 *��������û�
	 */
	public void batchDeleteUsers(String[] strSplit);
	
	/**
	 *����/���û�
	 */
	public void toggleUserStatus(User user);	
		

}
