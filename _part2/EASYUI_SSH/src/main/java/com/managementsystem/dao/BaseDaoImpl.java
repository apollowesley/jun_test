package com.managementsystem.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.managementsystem.common.ConstantUtil;
import com.managementsystem.query.QueryParms;
import com.managementsystem.query.SingleQueryParam;
@Repository
public  class BaseDaoImpl<PK extends Serializable, T> implements BaseDao<PK, T> {
	private  Class<T> persistentClass;
	/**
	 * 映射方式找到传递进来类的本质类
	 */
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		
        this.persistentClass = null;  
        @SuppressWarnings("rawtypes")
		Class c = getClass();  
        Type t = c.getGenericSuperclass();  
        if (t instanceof ParameterizedType) {  
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();  
            this.persistentClass = (Class<T>) p[1];  
        }
	}
	
	@Resource
	private SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	protected Criteria createEntityCriteria(){
		return getSession().createCriteria(persistentClass);
	}
	@SuppressWarnings("unchecked")
	@Override
	public T getById(PK id) {
		return (T) getSession().get(persistentClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T loadById(PK id) {
		return (T) getSession().load(persistentClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		Criteria criteria=createEntityCriteria();
		return (List<T>)criteria.list();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByFiled(String propertyName, Object value) {
		Criteria criteria=createEntityCriteria();
		criteria.add(Restrictions.eq(propertyName, value));
		return (List<T>)criteria.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByFileds(QueryParms queryParams) {
		Criteria criteria=createEntityCriteria();
		handlerQueryParams(criteria,queryParams);
		return (List<T>)criteria.list();
	}

	@Override
	public int save(T entity) {
		int num=1;
        try {
//        	Transaction tx=getSession().getTransaction();
            getSession().save(entity);
//            tx.commit();
        } catch (HibernateException e) {
            num=0;
            e.printStackTrace();
        }
        return num;	   
	}
	
	private Criteria handlerQueryParams(Criteria criteria,QueryParms queryParams) {
		if(queryParams != null && StringUtils.isEmpty(queryParams.getSingleQueryParams())) {
			for(SingleQueryParam singleQueryparam:queryParams.getSingleQueryParams()) {
				if(ConstantUtil.getGt().equals(singleQueryparam.getOpCode())) {
					criteria.add(Restrictions.gt(singleQueryparam.getPropertity(), singleQueryparam.getPropertityValue()));
				}else if(ConstantUtil.getGe().equals(singleQueryparam.getOpCode())) {
					criteria.add(Restrictions.ge(singleQueryparam.getPropertity(), singleQueryparam.getPropertityValue()));
				}else if(ConstantUtil.getLt().equals(singleQueryparam.getOpCode())) {
					criteria.add(Restrictions.lt(singleQueryparam.getPropertity(), singleQueryparam.getPropertityValue()));
				}else if(ConstantUtil.getLe().equals(singleQueryparam.getOpCode())) {
					criteria.add(Restrictions.le(singleQueryparam.getPropertity(), singleQueryparam.getPropertityValue()));
				}else if(ConstantUtil.getIn().equals(singleQueryparam.getOpCode())) {
					//TODO 处理 in操作数据库的情况
//					criteria.add(Restrictions.in(singleQueryparam.getPropertity(), singleQueryparam.getPropertityValue()));
				}
			}
		}
		return criteria;
	}
	@Override
	public int delete(T entity) {
		int num=1;
        try {
            getSession().delete(entity);
        } catch (RuntimeException  e) {
            num=0;
            e.printStackTrace();
        }
        return num;
    }
	

}
