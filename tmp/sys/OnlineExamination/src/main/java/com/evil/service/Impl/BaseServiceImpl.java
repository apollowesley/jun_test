package com.evil.service.Impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import com.evil.dao.BaseDao;
import com.evil.service.BaseService;
import com.evil.util.Page;
import com.evil.util.PageResult;
import com.evil.util.PageUtil;

/**
 * 抽象的BaseSerice的类，专门用来继承
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDao<T> baseDao;
	private Class<T> clazz;
	
	/**
	 * 在构造方法中利用反射，获得继承该类的Class对象
	 */
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		
		ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
		this.clazz=(Class<T>) type.getActualTypeArguments()[0];
	}

	// 注入basedao
	@Resource
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void saveEntity(T entity) {
		try {
			baseDao.saveEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateEntity(T entity) {
		baseDao.updateEntity(entity);
	}

	@Override
	public void deleteEntity(T entity) {
		baseDao.deleteEntity(entity);
	}

	@Override
	public void saveOrUpdateEntity(T entity) {
		baseDao.saveOrUpdateEntity(entity);
	}

	@Override
	public void batchEntityByHQL(String hql, Object... objects) {
		baseDao.batchEntityByHQL(hql, objects);
	}

	@Override
	public void executeSQL(String sql, Object... objects) {
		this.baseDao.executeSQL(sql, objects);
	}

	@Override
	public T loadEntity(String id) {
		return baseDao.loadEntity(id);
	}

	@Override
	public T getEntity(String id) {
		return baseDao.getEntity(id);
	}

	@Override
	public List<T> findEntityByHQL(String hql, Object... objects) {
		return baseDao.findEntityByHQL(hql, objects);
	}

	public List<T> findEntityByPage(String hql, int page, int rows,
			Object... params) {
		return baseDao.findEntityByPage(hql, page, rows, params);
	}
	@Override
	public PageResult findEntityByPage(Page page, String conditions) {
		String hql = "select count(*) from "+ clazz.getSimpleName()  +" where 1=1";
		hql +=conditions;
		Long count = null;
		count = (Long) this.uniqueResult(hql);
		count = count == null ? 0 : count;
		page = PageUtil.createPage(page.getNumPerPage(), count,
				page.getCurrentPage());// 根据总记录数创建分页信息
		hql = "from "+clazz.getSimpleName()+" where 1=1";
		hql += conditions;
		List<T> list = this.findEntityByPage(hql,
				page.getCurrentPage(), page.getNumPerPage());// 通过分页信息取得试题
		PageResult result = new PageResult(page, list);// 封装分页信息和记录信息，返回给调用处
		return result;
	}
	
	/**
	 * 获取所有实体的对象
	 */
	public List<T> findAllEntities() {
		String hql="from "+clazz.getSimpleName();
		return baseDao.findEntityByHQL(hql);
	}

	/**
	 * 单值检索，确保查询的结果是唯一的
	 */
	public Object uniqueResult(String hql, Object... objects) {
		return this.baseDao.uniqueResult(hql, objects);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List executeSQLQuery(Class clazz, String sql, Object... objects) {
		return this.baseDao.executeSQLQuery(clazz, sql, objects);
	}

	
}
