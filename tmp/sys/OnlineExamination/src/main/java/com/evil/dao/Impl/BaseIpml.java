package com.evil.dao.Impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.evil.dao.BaseDao;

/**
 * �����BaseDao��ר�������̳�
 */
@SuppressWarnings("unchecked")
@Repository("baseDao")
public abstract class BaseIpml<T> implements BaseDao<T> {
	// ע��sessionFactory
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	// ��ü̳и��������ClaSS����
	protected Class<T> entityClass;

	public BaseIpml() {
		try {
			Object genericClz = getClass().getGenericSuperclass();
			if (genericClz instanceof ParameterizedType) {
				entityClass = (Class<T>) ((ParameterizedType) genericClz)
						.getActualTypeArguments()[0];
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String saveEntity(T entity) {
		return (String) getSession().save(entity);
	}

	@Override
	public void updateEntity(T entity) {
		getSession().update(entity);
	}

	@Override
	public void deleteEntity(T entity) {
		getSession().delete(entity);
	}

	@Override
	public void saveOrUpdateEntity(T entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public void batchEntityByHQL(String hql, Object... objects) {
		Query q = getSession().createQuery(hql);
		setParams(q, objects).executeUpdate();
	}

	/**
	 * ִ��ԭ����sql���
	 */
	public void executeSQL(String sql, Object... objects) {
		SQLQuery sq = getSession().createSQLQuery(sql);
		setParams(sq, objects).executeUpdate();
	}

	@Override
	public T loadEntity(String id) {
		return (T) getSession().load(entityClass, id);
	}

	@Override
	public T getEntity(String id) {
		return (T) getSession().get(entityClass, id);
	}

	@Override
	public List<T> findEntityByHQL(String hql, Object... objects) {
		Query q = getSession().createQuery(hql);
		return setParams(q, objects).list();
	}

	@Override
	public List<T> findEntityByPage(String sqlid, int page, int rows,
			Object... params) {
		if (page < 1) {
			page = 1;
		}
		if (rows < 1) {
			rows = 10;
		}
		Query q = getSession().createQuery(sqlid);
		setParams(q, params);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Object uniqueResult(String hql, Object... objects) {
		Query q = getSession().createQuery(hql);
		return setParams(q, objects).uniqueResult();
	}

	/**
	 * ִ��ԭ����SQL�Ĳ�ѯ���
	 */
	@SuppressWarnings("rawtypes")
	public List executeSQLQuery(Class clazz, String sql, Object... objects) {
		SQLQuery sq = getSession().createSQLQuery(sql);
		if (clazz != null) {
			sq.addEntity(clazz);
		}
		return setParams(sq, objects).list();
	}

	/**
	 * ΪHQL����ڵĵĲ�����ֵ
	 */
	public Query setParams(Query q, Object... params) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		return q;
	}
}
