package com.king.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.king.base.dao.BaseDAO;
import com.king.base.model.BaseModel;
import com.king.base.service.BaseService;

/**
 * BaseServiceImpl
 * @param <T>
 *     DAO
 * @param <E>
 *     Model
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class BaseServiceImpl<T extends BaseDAO<E>, E extends BaseModel> implements BaseService<E> {

	@Autowired
	private T dao;

	@Override
	public List<E> query(Map<String, Object> params) {
		return dao.selectByParams(params);
	}

	@Override
	public E findById(long id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public List<E> queryByIds(List<Long> ids) {
		Map<String, Object> params = new HashMap<>();
		params.put("ids", ids);
		return dao.selectByParams(params);
	}

	@Override
	public List<E> page(Map<String, Object> params, int offset, int size) {
		params.put("offset", offset);
		params.put("size", size);
		return dao.selectByParams(params);
	}

    @Override
    public int count(Map<String, Object> params) {
		return dao.countByParams(params);
    }

	@Override
	public E findFirst(Map<String, Object> params) {
		return dao.selectByParams(params).get(0);
	}

	@Override
	public int save(E ldOrderDetail) {
		return dao.insert(ldOrderDetail);
	}

	@Override
	public int batchSave(List<E> ldOrderDetailList) {
		return dao.insertBatch(ldOrderDetailList);
	}

    @Override
    public int saveSelective(E ldOrderDetail) {
    	return dao.insertSelective(ldOrderDetail);
    }

	@Override
	public int modify(E ldOrderDetail) {
		return dao.updateByPrimaryKey(ldOrderDetail);
	}

    @Override
    public int modifySelective(E ldOrderDetail) {
    	return dao.updateByPrimaryKeySelective(ldOrderDetail);
    }

	@Override
	public int remove(long id) {
		return dao.deleteByPrimaryKey(id);
	}

	@Override
	public int batchRemove(List<Long> ids) {
		return dao.deleteBatchByPrimaryKey(ids);
	}
}