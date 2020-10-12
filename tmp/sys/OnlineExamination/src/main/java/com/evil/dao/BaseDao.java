package com.evil.dao;

import java.util.List;

/**
 * DAO �������෽���ӿ�
 * 
 * @�ļ�����: BaseDao.java
 * @��·��: common.core
 */
public interface BaseDao<T> {

	// д����
	/**
	 * ����ʵ�����
	 */
	public String saveEntity(T entity);

	/**
	 * ����ʵ�����
	 */
	public void updateEntity(T entity);

	/**
	 * ɾ��ʵ�����
	 * 
	 * @param entity
	 */
	public void deleteEntity(T entity);

	/**
	 * ����ʵ���Զ�ѡ����»򱣴�
	 * 
	 * @param entity
	 */
	public void saveOrUpdateEntity(T entity);

	/**
	 * ����HQLִ��д����
	 */
	public void batchEntityByHQL(String hql, Object... objects);

	/**
	 * ִ��ԭ����sql���
	 */
	public void executeSQL(String sql, Object... objects);

	// ������
	public T loadEntity(String id);

	public T getEntity(String id);

	/**
	 * ���ݲ�������ʵ����󼯺�
	 */
	public List<T> findEntityByHQL(String hql, Object... objects);

	/**
	 * ��ҳ��ѯ
	 */
	List<T> findEntityByPage(String hql, int page, int rows, Object... params);


	/**
	 *��ֵ������ȷ����ѯ�Ľ����Ψһ��
	 */
	public Object uniqueResult(String hql, Object... objects);

	/**
	 * ִ��ԭ����SQL�Ĳ�ѯ���
	 */
	@SuppressWarnings("rawtypes")
	public List executeSQLQuery(Class clazz, String sql, Object... objects);

}
