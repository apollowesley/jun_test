package com.antdsp.dao.jpa;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.antdsp.common.enums.SortType;
import com.antdsp.common.pagination.PaginationData;

/**
 * 
 * <p>title:AntdspBaseRepositoryImpl.java</p>
 * <p>Description: 统一数据操作接口实现类</p>  
 * <p>Copyright: Copyright (c) 2019</p> 
 * 
 * @author jt-lee
 * @date 2019年6月10日
 * @email a496401006@qq.com
 *
 * @param <T>
 * @param <ID>
 */
public class AntdspBaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements AntdspBaseRepository<T , ID>{
	
	private final EntityManager em;
	
	public AntdspBaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.em = em;
	}
	
	@Override
	public int executeSQL(String sqlString , Map<String , Object> params) {
		Query query = em.createNativeQuery(sqlString);
		this.querySetParameter(query, params);
		return query.executeUpdate();
	}
	
	private void querySetParameter(Query query , Map<String , Object> params) {
		if(params == null) {
			return ;
		}
		Iterator<String> keys = params.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			query.setParameter(key, params.get(key));
		}
	}

	@Override
	public PaginationData<T> list(Specification<T> specification, int page , int count) {
		return this.list(specification, page, count, SortType.DESC, "created");
	}

	@Override
	public PaginationData<T> list(Specification<T> specification, int page, int count, SortType sort, String orderField) {

		page = (page - 1) ;
		Pageable pageable ;
		if(SortType.DESC.equals(sort)) {
			pageable  = PageRequest.of(page, count , Sort.by(Order.desc(orderField)));
		}else {
			pageable  = PageRequest.of(page, count , Sort.by(Order.asc(orderField)));
		}
		Page<T> userList = this.findAll(specification, pageable);
		return new PaginationData<>(userList.getContent() , page , userList.getTotalElements());
	}

	@Override
	public void detach(T obj) {
		em.detach(obj);
	}
	
	
}
