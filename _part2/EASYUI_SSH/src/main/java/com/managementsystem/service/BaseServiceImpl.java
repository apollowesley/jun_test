package com.managementsystem.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.managementsystem.dao.BaseDao;
import com.managementsystem.query.QueryParms;


/**
 * 泛型业务类,此时这里不能加@service 这个变量,因为controller层自动注入service对象时不需要生成泛型对象,
 * 又因为集成了此类,故此类下的对应Dao类也需要实现依赖注入,供controller层的实体serivices使用
 * @author cong.liu
 *
 * @param <PK>
 * @param <T>
 */
public class BaseServiceImpl<PK extends Serializable, T> implements BaseService<PK, T>{
	
	@Autowired
	private BaseDao<PK, T> baseDao;
	

//	public BaseDao<PK, T> getBaseDao() {
//		return baseDao;
//	}
//
//	public void setBaseDao(BaseDao<PK, T> baseDao) {
//		this.baseDao = baseDao;
//	}

	@Override
	public T getById(PK id) {
		return baseDao.getById(id);
	}

	@Override
	public T loadById(PK id) {
		return baseDao.loadById(id);
	}

	@Override
	public List<T> getAll() {
		return baseDao.getAll();
	}

	@Override
	public List<T> getByFiled(String filed, Object value) {
		// TODO Auto-generated method stub
		return baseDao.getByFiled(filed, value);
	}

	@Override
	public List<T> getByFileds(QueryParms queryParams) {
		// TODO Auto-generated method stub
		return baseDao.getByFileds(queryParams);
	}

	@Override
	public int save(T entity) {
		// TODO Auto-generated method stub
		return baseDao.save(entity);
	}

	@Override
	public int delete(T entity) {
		return baseDao.delete(entity);
	}

}
