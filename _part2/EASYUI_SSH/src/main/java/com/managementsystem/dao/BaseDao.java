package com.managementsystem.dao;

import java.io.Serializable;
import java.util.List;

import com.managementsystem.query.QueryParms;

public interface BaseDao<PK extends Serializable, T> {
	/**
	 * 根据ID获取对象(对象不存在则返回null)
	 * @param id
	 * @return
	 */
	T getById(PK id);
	/**
	 * 根据ID获取对象(对象不存在则报异常)
	 * @param id
	 * @return
	 */
	T loadById(PK id);
	/**
	 * 获取所有对象
	 * @return
	 */
	List<T> getAll();
	/**
	 * 根据单个字段,字段值获取对应的对象(适用于 equeal 的情况)
	 * @param filed
	 * @param value
	 * @return
	 */
	List<T> getByFiled(String filed,Object value);

	/**
	 * 多个参数配合查询
	 * @param queryParams
	 * @param entityClass
	 * @return
	 */
	List<T> getByFileds(QueryParms queryParams);
	
	/**
	 * insert 保存一个对象
	 * @param entity
	 * @return
	 */
	int save(T entity); 
	/**
	 * delete 删除一个对象
	 * @param entity
	 */
	int delete(T entity);
}
