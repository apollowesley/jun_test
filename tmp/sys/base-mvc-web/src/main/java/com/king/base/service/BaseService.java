package com.king.base.service;

import java.util.List;
import java.util.Map;

import com.king.base.model.BaseModel;

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
	public List<E> query(Map<String, Object> params);

	/**
     * ID查询
     */
	public E findById(long id);

	/**
	 * ID批量查询
	 */
	public List<E> queryByIds(List<Long> ids);

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
	public E findFirst(Map<String, Object> params);

	/**
	 * 保存
	 */
    public int save(E ldOrderDetail);

	/**
	 * 批量保存
	 */
	public int batchSave(List<E> ldOrderDetailList);

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
	public int batchRemove(List<Long> ids);
}
