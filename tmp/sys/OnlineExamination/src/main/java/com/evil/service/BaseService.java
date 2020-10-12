package com.evil.service;

import java.util.List;

import com.evil.util.Page;
import com.evil.util.PageResult;

public interface BaseService<T> {

	// д����
	/**
	 * ����ʵ�����
	 */
	public void saveEntity(T entity);

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
	 * ��ҳ��ѯ
	 * @param page  ��ҳ��Ϣ
	 * @param conditions   �����޶��������� id=? and name=?��
	 * @return
	 */
	public PageResult findEntityByPage(Page page, String conditions) ;
	
	/**
	 * ��ȡ����ʵ��Ķ���
	 */
	public List<T> findAllEntities();

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
