package com.kld.common.framework.dao;

import java.util.List;

import com.kld.common.framework.dto.GridQueryPara;
import com.kld.common.framework.dto.GridResult;
import com.kld.common.framework.dto.QueryParaDto;
import org.apache.ibatis.session.SqlSession;

public interface ISimpleDao<T> {

	SqlSession getSqlSessionTemplate();

	String getClassName();

	String getMapperMethod(String methodName);

	int insert(T entity);

	int insert(String mapperMethod, T entity);

	int update(T entity);

	int update(String mapperMethod, T entity);

	<I> int delete(I id);

	<I> int delete(String mapperMethod, I params);

	<I> int deleteMulti(boolean haveFK, I... ids);

	<I> I get(Object id);

	<I> I get(String mapperMethod, Object id);

	<I> List<I> find(Object params);

	<I> List<I> find(String mapperMethod, Object params);

	<I> List<I> find();
	
	public <I> GridResult<I> getPageList(String sqlId,GridQueryPara page,QueryParaDto query);
	
	public <I> GridResult<I> getPageList(String sqlId,GridQueryPara page);

}