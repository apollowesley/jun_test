package com.jeasy.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeasy.base.dao.BaseDAO;
import com.jeasy.base.model.BaseModel;
import com.jeasy.base.service.BaseService;

import lombok.extern.log4j.Log4j;

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
@Log4j
public class BaseServiceImpl<T extends BaseDAO<E>, E extends BaseModel> implements BaseService<E> {

	@Autowired
	private T dao;

	@Override
	public List<E> find(Map<String, Object> params) {
		return dao.selectByParams(params);
	}

	@Override
	public E getById(long id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public List<E> findByIds(List<Long> ids) {
		return dao.selectBatchByPrimaryKey(ids);
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
	public E getFirst(Map<String, Object> params) {
		return dao.selectFirstByParams(params);
	}

	@Override
	public int save(E entity) {
		return dao.insert(entity);
	}

	@Override
	public int saveBatch(List<E> entityList) {
		return dao.insertBatch(entityList);
	}

    @Override
    public int saveSelective(E entity) {
    	return dao.insertSelective(entity);
    }

	@Override
	public int modify(E entity) {
		return dao.updateByPrimaryKey(entity);
	}

    @Override
    public int modifySelective(E entity) {
    	return dao.updateByPrimaryKeySelective(entity);
    }

	@Override
	public int remove(long id) {
		return dao.deleteByPrimaryKey(id);
	}

	@Override
	public int removeBatch(List<Long> ids) {
		return dao.deleteBatchByPrimaryKey(ids);
	}
}