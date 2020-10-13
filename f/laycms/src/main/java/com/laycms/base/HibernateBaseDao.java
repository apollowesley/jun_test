package com.laycms.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.laycms.base.entity.BaseEntity;

@Transactional
public abstract class HibernateBaseDao<T extends BaseEntity, ID extends Serializable> {
	static final Log log = LogFactory.getLog(HibernateBaseDao.class);

	public abstract Class<T> getEntityClass();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	/**
	 * @functionDescription :分页查询
	 * @param ev 过滤条件
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public PageContext<T> queryUsePage(EntityView ev,Integer pageNum){
		return queryUsePage(ev, pageNum, 20);
	}
	/**
	 * @functionDescription :分页查询
	 * @param ev 过滤条件
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public PageContext<T> queryUsePage(EntityView ev,Integer pageNum,Integer pageSize){
		pageNum = pageNum==null?1:pageNum;
		pageSize = pageSize==null?20:pageSize;
		PageContext<T> pageCtx = new PageContext<T>();
		try {
			Criteria criteria = getCriteria(ev);
			criteria.setFirstResult((pageNum - 1) * pageSize);
			// 返回的最大结果集
			criteria.setMaxResults(pageSize);
			
			List<T> list = findPage(criteria, pageNum, pageSize);
			
			criteria.setFirstResult(0);
			int totalCount = Integer.valueOf(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
			if (totalCount == 0) {
				pageNum = 1;
			}
			pageCtx.setItemlList(list);
			PageBean pageBean = new PageBean(pageNum,totalCount);
			pageBean.setPageSize(pageSize);
			pageCtx.setPageBean(pageBean);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageCtx;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findPage(Criteria criteria, int pageNum, int pageSize) {
		criteria.setFirstResult((pageNum-1)*pageSize);
		criteria.setMaxResults(pageSize);
		
		List<T> list = new ArrayList<>();
		List<T> tmpList = criteria.list();
		if (tmpList != null && tmpList.size()>0) {
			list.addAll(tmpList);
		}
		return list;
	}
	public List<T> findLimit(EntityView ev, int limit) {
		Criteria criteria = getCriteria(ev);
		return findPage(criteria, 1, limit);		
	}
	
	/**	限制返回數量
	 * @param ev
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<T> findLimit(EntityView ev,Integer limit)
	{
		return queryUsePage(ev,1,limit).getItemList();
	}
	
	
	@SuppressWarnings("unchecked")
	public ID addNew(T editData){
		ID id = null;
		id = (ID) getSession().save(this.getEntityClass().getName(),editData);
		return id;
	}
	
	public boolean update(T editData){
		getSession().update(this.getEntityClass().getName(),editData);
		return true;
	}

	public boolean saveOrUpdate(T editData){
		getSession().saveOrUpdate(this.getEntityClass().getName(), editData);
		return true;
	}
	
	
	/**
	 * @functionDescription :根据sql更新，执行update，返回首次更新影响的记录条数
	 * @param sql
	 * @param paramValues
	 * @return
	 */
	public int update(final String sql, final Object[] paramValues) {
		
		return jdbcTemplate.update(sql, paramValues);
		
	}
	/**
	 * @functionDescription :根据sql更新，执行update，返回首次更新影响的记录条数
	 * @param sql
	 * @return
	 */
	

	public int update(final String sql){
		return update(sql,null);
	}
	

	public int[] batch(final String sql,List<Object[]> batchArgs){
		
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
	public int[] batch(final String sql,final Object[][] paramValues){
		List<Object[]> batchArgs = new ArrayList<>();
		for (int i = 0; i < paramValues.length; i++) {
			batchArgs.add(paramValues[i]);
		}
		
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
	public boolean delete(ID[] ids){
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < ids.length; i++) {
			T obj = this.findById(ids[i]);
			if (obj != null) {
				list.add(obj);
			}
		}
		this.deleteAll(list);
		return true;
	}

	

	public boolean deleteAll(Collection<T> list){
		Session session = getSession();
		try {
			for (Object entity : list) {
				session.delete(entity);
			}
			log.debug("delete successful!");
		} catch (RuntimeException re) {
			log.error(re);
			throw re;
		}
		return true;
	}
	
	/**
	 * @functionDescription :根据EntityView删除，删除满足EntityView条件的所有记录
	 * @param ev
	 * @return
	 */
	public boolean delete(EntityView ev){
		List<T> list = this.findByEntityView(ev);
		return this.deleteAll(list);
	}
	
	public boolean merge(T editData){
		
		getSession().merge(this.getEntityClass().toString(),editData);
		return true;
	}
	
	public T findById(ID id){
		T entity = null;
		try{
			entity = (T) getSession().get(this.getEntityClass(), id);
		}catch(RuntimeException re){
			log.error("findById failed!", re);
		}
		return entity;
	}
	/**
	 * @functionDescription :根据属性名称和属性值获取唯一对象
	 * @param propertyName
	 * @param propertyValue``````````````````
	 * @return
	 */

	public T findUniq(String propertyName, Object propertyValue) {
		EntityView ev = new EntityView();
		ev.add(Restrictions.eq(propertyName, propertyValue));
		return this.findUniq(ev);
	}
	/**
	 * @functionDescription :根据EntityView获取唯一对象
	 * @param ev
	 * @return
	 */
	public T findUniq(EntityView ev){
		List<T> list = this.findByEntityView(ev);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String, Object>> findList(String sql,Object... args){
		return jdbcTemplate.queryForList(sql, args);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findList(String sql,Class requiredType,Object... args){
		
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(requiredType),args); 
	}
	
	public T findUniq(String sql,Class<T> requiredType,Object... args){
		List<T> list = findList(sql, requiredType, args);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * @functionDescription :根据属性名和属性值查询
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public List<T> findByProperty(String propertyName,Object propertyValue){
		EntityView ev = new EntityView(this.getEntityClass());
		ev.add(Restrictions.eq(propertyName, propertyValue));
		return findByEntityView(ev);
	}
	
	/**
	 * @functionDescription :根据EntityView进行查询
	 * @param ev 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<T> findByEntityView(EntityView ev){
		Criteria criteria = getCriteria(ev);
		return criteria.list();
		
	}
	

	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		String hql = "from "+this.getEntityClass().getName();
		
		return getSession().createQuery(hql).list();
	}


	public boolean delete(T entity){
		try {
			getSession().delete(entity);
			
		} catch (RuntimeException e) {
			throw e;
		}		
		return true;
	}
	/**
	 * @functionDescription :根据id判断数据库中是否存在该实体
	 * @param id
	 * @return
	 */
	public boolean exist(ID id){
		return findById(id) != null;
		
	}
	
	/**
	 * @functionDescription :根据EntityView判断满足条件的记录是否存在
	 * @author 王渊博
	 * @date 2009-7-27 上午10:11:48
	 */
	public boolean exist(final EntityView ev){
		return this.count(ev)>0;
	}
	/**
	 * @functionDescription :根据EntityView查询满足条件的记录条数
	 * @param ev
	 * @return 返回根据EntityView查询满足的记录条数
	 */
	public int count(final EntityView ev) {
		Criteria criteria = getCriteria(ev);
		return Integer.valueOf(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
	}
	
	private Criteria getCriteria(EntityView ev){
		Criteria criteria = getSession().createCriteria(getEntityClass());
		if (ev.getCriterionList() != null) {
			for(Criterion c:ev.getCriterionList()){
				criteria.add(c);
			}
		}
		if (ev.getOrderList() != null) {
			for(Order order:ev.getOrderList()){
				criteria.addOrder(order);
			}
		}
		return criteria;
	}
	
}
