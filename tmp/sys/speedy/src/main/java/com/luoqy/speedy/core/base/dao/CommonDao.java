package com.luoqy.speedy.core.base.dao;

import java.util.List;
import java.util.Map;

import com.luoqy.speedy.core.base.model.Common;

/**
 * @author qf
 * 数据加载，数据增加，数据删除，数据更新，数据查询
 */
public abstract interface CommonDao<T extends Common> {
	/**
	 * @param entity
	 * @return 加载所有数据
	 */
	public List<T> load(T entity);
	/**
	 * @param entity
	 * @return 保存数据
	 */
	public int save(T entity);
	/**
	 * @param map
	 *  彻底删除删除
	 */
	public void delete(Map<String,Object> map);
	/**
	 * @param entity
	 *  更新数据
	 */
	public void update(T entity);
	/**
	 * @param map
	 * @return 分页查询数据
	 */
	public List<Map<String, String>> search(Map<String,Object> map);
	/**
	 * @param map
	 * @return 分页数据
	 */
	public List<T>  loadPage(Map<String, Object> map);
	
}
