package com.jeasy.base.dao;

import java.util.List;
import java.util.Map;

import com.jeasy.base.model.BaseModel;

/**
 * BaseDAO
 * @param <E>
 *     Model
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public interface BaseDAO<E extends BaseModel> {

	/**
	 * 插入
	 */
	public int insert(E entity);

	/**
	 * 批量插入
	 */
	public int insertBatch(List<E> entityList);

	/**
	 * 选择插入
	 */
	public int insertSelective(E entity);

	/**
	 * 按主键ID, 查询
	 */
	public E selectByPrimaryKey(long id);

	/**
	 * 按主键ID, 批量查询
	 */
	public List<E> selectBatchByPrimaryKey(List<Long> ids);

	/**
	 * 按主键ID更新
	 */
	public int updateByPrimaryKey(E entity);

	/**
	 * 按主键ID选择更新
	 */
	public int updateByPrimaryKeySelective(E entity);

	/**
	 * 按参数查询
	 */
	public List<E> selectByParams(Map<String, Object> params);

	/**
	 * 按参数查询数量
	 */
    public int countByParams(Map<String, Object> params);

	/**
	 * 按参数查询第一个
	 */
	public E selectFirstByParams(Map<String, Object> params);

	/**
	 * 按主键ID删除
	 */
	public int deleteByPrimaryKey(long id);

	/**
	 * 按主键ID批量删除
	 */
	public int deleteBatchByPrimaryKey(List<Long> ids);
}