package com.kld.common.framework.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kld.common.framework.dto.QueryPageDto;
import com.kld.common.framework.dao.ISimpleDao;
import com.kld.common.framework.dto.GridQueryPara;
import com.kld.common.framework.dto.GridResult;
import com.kld.common.framework.dto.QueryParaDto;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;



/**
 * SimpleDao<T>
 * DAO层的公共父类，所有的DAO类都要继承此类
 * @author anpushang
 * @date 2016-03-12
 */
public abstract class SimpleDaoImpl<T> implements ISimpleDao<T>
{
  protected Logger logger = LoggerFactory.getLogger(getClass());
  protected static final String GET = "get";
  protected static final String FIND = "find";
  protected static final String PAGE = "page";
  protected static final String INSERT = "insert";
  protected static final String UPDATE = "update";
  protected static final String DELETE = "delete";
  protected static final String BATCH_DELETE = "batch_delete";

  @Resource(name="dynamicSqlSessionTemplate")
  private SqlSession sqlSessionTemplate;
  protected Class<T> entityClass;
  protected String className;

  /* (non-Javadoc)
 * @
 */

public SqlSession getSqlSessionTemplate()
  {
    return this.sqlSessionTemplate;
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#getClassName()
 */

public String getClassName()
  {
    return this.className;
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#getMapperMethod(java.lang.String)
 */

public String getMapperMethod(String methodName) {
		return new StringBuilder(className).append(".").append(methodName).toString();
  }
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public SimpleDaoImpl()
  {
    Type genType = getClass().getGenericSuperclass();
    Type[] params = ((ParameterizedType)genType).getActualTypeArguments();

    this.entityClass = ((Class)params[0]);
	//this.className = entityClass.getSimpleName();
    this.className = entityClass.getName();
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#insert(T)
 */

public int insert(T entity) {
    return insert(INSERT,entity);
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#insert(java.lang.String, T)
 */

public int insert(String mapperMethod,T entity) {
    return getSqlSessionTemplate().insert(getMapperMethod(mapperMethod), entity);
    //return getSqlSessionTemplate().insert(mapperMethod, entity);
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#update(T)
 */

public int update(T entity) {
    return update(UPDATE,entity);
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#update(java.lang.String, T)
 */

public int update(String mapperMethod,T entity) {
    return getSqlSessionTemplate().update(getMapperMethod(mapperMethod), entity);
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#delete(I)
 */

public <I> int delete(I id) {
    return delete(DELETE,id);
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#delete(java.lang.String, I)
 */

public <I> int delete(String mapperMethod,I params) {
    return getSqlSessionTemplate().delete(getMapperMethod(mapperMethod), params);
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#deleteMulti(boolean, I)
 */
  
public <I> int deleteMulti(boolean haveFK, final I... ids)
  {
    Assert.notEmpty(ids, "ids不能为空");
    int count = 0;
    if (haveFK) {
      for (I id : ids)
        try {
          count += delete(id);
        } catch (Exception e) {
          this.logger.error("删除出现异常!", e);
          throw new RuntimeException("删除出现异常!");
        }
    }
    else {
      count = getSqlSessionTemplate().update(getMapperMethod(BATCH_DELETE), ids);
    }
    logger.debug("delete entity {}, batch: {}, ids is {}", className, haveFK, ids);
    return count;
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#get(I)
 */

public <I> I get(Object id) {
    return get(GET,id);
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#get(java.lang.String, I)
 */
public <I> I get(String mapperMethod,Object id) {
    return getSqlSessionTemplate().selectOne(getMapperMethod(mapperMethod), id);
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#find(I)
 */

public <I> List<I> find(Object params)
  {
    return find(FIND,params);
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#find(java.lang.String, I)
 */

public <I> List<I> find(String mapperMethod,Object params) {
    return getSqlSessionTemplate().selectList(getMapperMethod(mapperMethod), params);
  }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#find()
 */

public <I> List<I> find() {
    return getSqlSessionTemplate().selectList(getMapperMethod(FIND));
  }


/**
public GridResult<T> getPageList(String sqlId,GridQueryPara page,QueryParaDto query){
	QueryPageDto qpd = new QueryPageDto();
	if(null != page.getCurrentPage()){
		qpd.setCurrentPage(Integer.parseInt(page.getCurrentPage()));
		qpd.setMaxRows(Integer.parseInt(page.getMaxRows()));
	}
	Map<String,Object> m = new HashMap<String,Object>();
	m.put("page", qpd);
	if(null != query) m.putAll(query.getMap());
	List<T> list = find(sqlId, m);
	GridResult<T> gr = new GridResult<T>();
	gr.setRows(list);
	gr.setPage(qpd.getCurrentPage());
	gr.setRecords(qpd.getRecords());
	gr.setTotal(qpd.getTotal());
	return gr;
}

public GridResult<T> getPageList(String sqlId,GridQueryPara page){
	return getPageList(sqlId, page,null);
}*/

/**
 * for list page
 * 
 * @param sqlId
 * @param page
 * @param query
 * @return
 */
public <I> GridResult<I> getPageList(String sqlId, GridQueryPara page, QueryParaDto query){
	QueryPageDto qpd = new QueryPageDto();
	if(null != page.getCurrentPage()){
		qpd.setCurrentPage(Integer.parseInt(page.getCurrentPage()));
		qpd.setMaxRows(Integer.parseInt(page.getMaxRows()));
	}
	Map<String,Object> m = new HashMap<String,Object>();
	m.put("page", qpd);
	if(null != query) m.putAll(query.getMap());
	List<I> list = getSqlSessionTemplate().selectList(getMapperMethod(sqlId), m);
	GridResult<I> gr = new GridResult<I>();
	gr.setRows(list);
	gr.setPage(qpd.getCurrentPage());
	gr.setRecords(qpd.getRecords());
	gr.setTotal(qpd.getTotal());
	return gr;
}

public <I> GridResult<I> getPageList(String sqlId,GridQueryPara page){
	return getPageList(sqlId, page,null);
}


}
