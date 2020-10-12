package com.evil.service;

import java.util.List;

import com.evil.util.Page;
import com.evil.util.PageResult;

public interface BaseService<T> {

	// 写操作
	/**
	 * 创建实体对象
	 */
	public void saveEntity(T entity);

	/**
	 * 更新实体对象
	 */
	public void updateEntity(T entity);

	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 */
	public void deleteEntity(T entity);

	/**
	 * 根据实体自动选择更新或保存
	 * 
	 * @param entity
	 */
	public void saveOrUpdateEntity(T entity);

	/**
	 * 根据HQL执行写操作
	 */
	public void batchEntityByHQL(String hql, Object... objects);

	/**
	 * 执行原生的sql语句
	 */
	public void executeSQL(String sql, Object... objects);

	// 读操作
	public T loadEntity(String id);

	public T getEntity(String id);

	/**
	 * 根据参数返回实体对象集合
	 */
	public List<T> findEntityByHQL(String hql, Object... objects);

	/**
	 * 分页查询
	 */
	List<T> findEntityByPage(String hql, int page, int rows, Object... params);
	/**
	 * 分页查询
	 * @param page  分页信息
	 * @param conditions   语句的限定条件（如 id=? and name=?）
	 * @return
	 */
	public PageResult findEntityByPage(Page page, String conditions) ;
	
	/**
	 * 获取所有实体的对象
	 */
	public List<T> findAllEntities();

	/**
	 *单值检索，确保查询的结果是唯一的
	 */
	public Object uniqueResult(String hql, Object... objects);

	/**
	 * 执行原生的SQL的查询语句
	 */
	@SuppressWarnings("rawtypes")
	public List executeSQLQuery(Class clazz, String sql, Object... objects);

}
