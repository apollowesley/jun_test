package com.hibernate.voDao;

import java.util.List;

/**
 * A data access object (DAO) providing persistence and search support for T
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.hibernate.voDao.T
 * @author MyEclipse Persistence Tools
 */
public interface DAO<T> {

	public void save(T transientInstance);

	/** 物理删除，从数据库中彻底删除，管理员使用 */
	public void delete(T persistentInstance);

	public T findById(java.lang.Integer id);

	public List findByExample(T instance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

	public T merge(T detachedInstance);

	public void attachDirty(T instance);

	public void attachClean(T instance);
}