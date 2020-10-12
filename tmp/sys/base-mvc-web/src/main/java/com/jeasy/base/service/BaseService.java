package com.jeasy.base.service;

import java.util.List;
import java.util.Map;

import com.jeasy.base.model.BaseModel;

/**
 * BaseService
 * @param <E>
 *     Model
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public interface BaseService<E extends BaseModel> {

	/**
	 * 查询
	 */
	public List<E> find(Map<String, Object> params);

	/**
     * ID查询
     */
	public E getById(long id);

	/**
	 * ID批量查询
	 */
	public List<E> findByIds(List<Long> ids);

	/**
	 * 参数分页查询
	 */
	public List<E> page(Map<String, Object> params, int offset, int size);

	/**
	 * 参数查询总数
	 */
	public int count(Map<String, Object> params);

	/**
	 * First查询
	 */
	public E getFirst(Map<String, Object> params);

	/**
	 * 保存
	 */
    public int save(E entity);

	/**
	 * 批量保存
	 */
	public int saveBatch(List<E> entityList);

    /**
     * 选择保存
     */
    public int saveSelective(E entity);

	/**
	 * 修改
	 */
	public int modify(E entity);

    /**
     * 选择修改
     */
    public int modifySelective(E entity);

	/**
	 * 删除
	 */
    public int remove(long id);

	/**
	 * 批量删除
	 */
	public int removeBatch(List<Long> ids);
}
