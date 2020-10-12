package com.antdsp.dao.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.antdsp.common.enums.SortType;
import com.antdsp.common.pagination.PaginationData;

/**
 * 
 * <p>title:AntdspBaseRepository.java</p>
 * <p>Description: 统一的数据操作父接口</p>  
 * <p>Copyright: Copyright (c) 2019</p> 
 * 
 * @author jt-lee
 * @date 2019年6月10日
 * @email a496401006@qq.com
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface AntdspBaseRepository<T , ID extends Serializable > extends JpaRepository<T, ID> , JpaSpecificationExecutor<T>{
	
	/**
	 * 执行SQL语句（update, insert, delete），表名、字段名和数据库对应
	 * @param sqlString		SQL语句
	 * @param params			参数
	 * @return
	 */
	int executeSQL(String sqlString , Map<String , Object> params);
	
	/**
	 * 通用分页, 默认排序方式（创建时间倒序查询）
	 * @param specification
	 * @return
	 */
	PaginationData<T> list(Specification<T> specification, int page , int count);
	
	/**
	 * 通用分页
	 * @param specification
	 * @param page
	 * @param count
	 * @param sort
	 * @param orderField
	 * @return
	 */
	PaginationData<T> list(Specification<T> specification, int page , int count, SortType sort, String orderField);
	
	/**
	 * 将一个托管状态的对象变为游离态
	 * 如果查询出一个对象后，要对对象字段set新值但不想保存到数据库，可以使用此方法
	 * @param obj
	 */
	void detach(T obj);
	
}
