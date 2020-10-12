package com.kld.common.framework.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.kld.common.framework.dto.GridQueryPara;
import com.kld.common.framework.dto.GridResult;
import com.kld.common.framework.dto.QueryPageDto;
import com.kld.common.framework.dto.QueryParaDto;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;




/**
 * SimpleDao<T>
 * DAO层的公共父类，所有的DAO类都要继承此类
 * @author lihaizhou
 * @date 2015-12-03
 */
public abstract class BaseDaoImpl<T>
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

  public SqlSession getSqlSessionTemplate()
  {
    return this.sqlSessionTemplate;
  }

  public String getClassName()
  {
    return this.className;
  }

  public String getMapperMethod(String methodName) {
		return new StringBuilder(className).append(".").append(methodName).toString();
  }
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public BaseDaoImpl()
  {
    Type genType = getClass().getGenericSuperclass();
    Type[] params = ((ParameterizedType)genType).getActualTypeArguments();

    this.entityClass = ((Class)params[0]);
	//this.className = entityClass.getSimpleName();
    this.className = entityClass.getName();
  }

  public int insert(T entity) {
    return insert(INSERT,entity);
  }

  public int insert(String mapperMethod,T entity) {
    return getSqlSessionTemplate().insert(getMapperMethod(mapperMethod), entity);
    //return getSqlSessionTemplate().insert(mapperMethod, entity);
  }

  public int update(T entity) {
    return update(UPDATE,entity);
  }

  public int update(String mapperMethod,T entity) {
    return getSqlSessionTemplate().update(getMapperMethod(mapperMethod), entity);
  }

  public <I> int delete(I id) {
    return delete(DELETE,id);
  }

  public <I> int delete(String mapperMethod,I params) {
    return getSqlSessionTemplate().delete(getMapperMethod(mapperMethod), params);
  }

  /**
   * 按ids批量删除对象
   * @param haveFK  是否物理删除
   * @param ids  主键数组
   * @return
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
    //logger.debug("delete entity {}, batch: {}, ids is {}", className, haveFK, ids);
    return count;
  }

  public <I> T get(I id) {
    return get(GET,id);
  }

  public <I> T get(String mapperMethod,I id) {
    return getSqlSessionTemplate().selectOne(getMapperMethod(mapperMethod), id);
  }

  public <I> List<T> find(I params)
  {
    return find(FIND,params);
  }

  public <I> List<T> find(String mapperMethod,I params) {
    return getSqlSessionTemplate().selectList(getMapperMethod(mapperMethod), params);
  }

  public List<T> find() {
    return getSqlSessionTemplate().selectList(getMapperMethod(FIND));
  }

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
		Map<String,Object> m = new ConcurrentHashMap<String, Object>();
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
	
	public <I> GridResult<I> getPageList(GridQueryPara page){
		return getPageList(PAGE, page);
	}
	public <I> GridResult<I> getPageList( GridQueryPara page, QueryParaDto query){
		return getPageList(PAGE, page,null);
	}
	
}