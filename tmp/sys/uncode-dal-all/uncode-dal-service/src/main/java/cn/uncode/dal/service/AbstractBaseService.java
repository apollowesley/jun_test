package cn.uncode.dal.service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import cn.uncode.dal.core.BaseDAL;
import cn.uncode.dal.core.BaseDTO;
import cn.uncode.dal.criteria.QueryCriteria;
import cn.uncode.dal.descriptor.QueryResult;
import cn.uncode.dal.utils.IDGenerateUtils;

public  class AbstractBaseService<T> implements BaseService<T> {//abstract
	
	public static final int PAGE_SIZE = 15;

    @Autowired
    protected BaseDAL baseDAL;
    
    private Class<T> clazz;

    @SuppressWarnings("unchecked")
	private Class<T> getClazz() {
        if (clazz == null) {
        	clazz = (Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
        }
        return clazz;
    }
    
    
    /**
     * 根据id获得对象
     *
     * @param id
     */
    @Override
    public T getById(Object id) {
        if (null == id) return null;
        QueryResult result = this.baseDAL.selectByPrimaryKey(this.getClazz(), id);
        if (null == result) return null;
        return result.as(this.getClazz());
    }

    /**
     * 保存对象
     *
     * @param t
     */
	public Object save(T t) {
		Assert.notNull(t, "Entity is required");
		try {
			if (t instanceof BaseDTO) {
				Long id = 0l;
				if (((BaseDTO) t).getId() == null || ((BaseDTO) t).getId().equals(0l)) {
					id = IDGenerateUtils.getId();
				} else {
					id = ((BaseDTO) t).getId();
				}
//				Method method = t.getClass().getMethod("init", new Class[] { Long.class });
//				method.invoke(t, new Object[] { id });
				this.baseDAL.insert(t);
				return id;
			} else {
				return this.baseDAL.insert(t);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    /**
     * 删除对象
     *
     * @param id
     */
    @Override
    public int deleteById(Object id) {
        return this.baseDAL.deleteByPrimaryKey(this.getClazz(), id);
    }

    @Override
    public int updateById(T t) {
        return this.baseDAL.updateByPrimaryKey(t);

    }


    /**
     * 根据条件查询
     *
     * @param map
     * @param fields
     */
    @Override
    public List<T> selectList(Map<String, Object> map, int limit, String... fields) {
        QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable(getClazz());
        queryCriteria.setLimit(limit);
        if (map != null) {
            //增加排序功能
            Object orderByClause=map.get("orderByClause");
            if(null!=orderByClause){
            	queryCriteria.setOrderByClause(String.valueOf(orderByClause));
            	map.remove("orderByClause");
            }
            //搜索条件
            QueryCriteria.Criteria criteria = queryCriteria.createCriteria();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                criteria.andColumnEqualTo(entry.getKey(), entry.getValue());
            }
        }
        QueryResult result = null;
        if (fields == null || fields.length == 0)
            result = this.baseDAL.selectByCriteria(queryCriteria);
        else
            result = this.baseDAL.selectByCriteria(fields, queryCriteria);
        return result.asList(getClazz());
    }

    public List<T> selectList(Map<String, Object> map, String... fields) {
        return this.selectList(map, PAGE_SIZE, fields);
    }
    
    public List<T> selectList(Map<String, Object> criteriaMap){
    	QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable(getClazz());
        queryCriteria.setLimit(PAGE_SIZE);
        if (criteriaMap != null) {
            //增加排序功能
            Object orderByClause=criteriaMap.get("orderByClause");
            if(null!=orderByClause){
            	queryCriteria.setOrderByClause(String.valueOf(orderByClause));
            	criteriaMap.remove("orderByClause");
            }
            //搜索条件
            QueryCriteria.Criteria criteria = queryCriteria.createCriteria();
            for (Map.Entry<String, Object> entry : criteriaMap.entrySet()) {
                criteria.andColumnEqualTo(entry.getKey(), entry.getValue());
            }
        }
        QueryResult result = this.baseDAL.selectByCriteria(queryCriteria);
        return result.asList(getClazz());
    }
    
    @Override
	public List<T> selectList(QueryCriteria queryCriteria, List<String> fields) {
    	queryCriteria.setTable(getClazz());
    	QueryResult result = null;
        if (fields == null || fields.size() == 0)
            result = this.baseDAL.selectByCriteria(queryCriteria);
        else
            result = this.baseDAL.selectByCriteria(fields, queryCriteria);
		return result.asList(this.getClazz());
	}
    
    @Override
	public List<T> selectList(QueryCriteria queryCriteria, String... fields) {
    	queryCriteria.setTable(getClazz());
    	QueryResult result = null;
    	if(fields != null && fields.length > 0){
    		result = this.baseDAL.selectByCriteria(fields, queryCriteria);
    	}else{
            result = this.baseDAL.selectByCriteria(fields, queryCriteria);
    	}
    	return result.asList(getClazz());
	}
    

	@Override
	public List<T> selectList(QueryCriteria queryCriteria) {
		queryCriteria.setTable(getClazz());
    	QueryResult result = this.baseDAL.selectByCriteria(queryCriteria);
		return result.asList(this.getClazz());
	}
    

	@Override
	public List<Map<String, Object>> selectList4Map(QueryCriteria queryCriteria) {
		queryCriteria.setTable(getClazz());
		QueryResult result = this.baseDAL.selectByCriteria(queryCriteria);
		return result.getList();
	}

	@Override
	public List<Map<String, Object>> selectList4Map(QueryCriteria queryCriteria, List<String> fields) {
		queryCriteria.setTable(getClazz());
    	QueryResult result = null;
        if (fields == null || fields.size() == 0)
            result = this.baseDAL.selectByCriteria(queryCriteria);
        else
            result = this.baseDAL.selectByCriteria(fields, queryCriteria);
		return result.getList();
	}


	@Override
	public List<Map<String, Object>> selectList4Map(QueryCriteria queryCriteria, String... fields) {
		queryCriteria.setTable(getClazz());
    	QueryResult result = null;
        if (fields != null && fields.length > 0)
            result = this.baseDAL.selectByCriteria(queryCriteria);
        else
            result = this.baseDAL.selectByCriteria(fields, queryCriteria);
		return result.getList();
	}


    public List<T> selectAll() {
        QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable(getClazz());
        queryCriteria.setLimit(1000);
        queryCriteria.setPageSize(1000);
        QueryResult result = null;
        result = this.baseDAL.selectByCriteria(queryCriteria);
        return result.asList(getClazz());
    }
    
	@Override
	public Map<String, Object> selectPage4Map(QueryCriteria queryCriteria, String... fields) {
		Map<String, Object> result = new HashMap<String, Object>();
		queryCriteria.setTable(getClazz());
		QueryResult queryResult = null;
		if(null == fields || fields.length == 0){
			queryResult = this.baseDAL.selectPageByCriteria(queryCriteria);
		}else{
			queryResult = this.baseDAL.selectPageByCriteria(fields, queryCriteria);
		}
		if(queryResult != null){
			result.put("recordTotal", queryResult.getPage().get("recordTotal"));
			result.put("page",  getPageInfo(queryResult, queryCriteria.getPageIndex()));
			result.put("list", queryResult.getList());
		}
		return result;
	}
	

	@Override
	public Map<String, Object> selectPage4Map(QueryCriteria queryCriteria) {
		Map<String, Object> result = new HashMap<String, Object>();
		queryCriteria.setTable(getClazz());
		QueryResult	queryResult = this.baseDAL.selectPageByCriteria(queryCriteria);
		if(queryResult != null){
			result.put("recordTotal", queryResult.getPage().get("recordTotal"));
			result.put("page",  getPageInfo(queryResult, queryCriteria.getPageIndex()));
			result.put("list", queryResult.getList());
		}
		return result;
	}
	
	/**
	 * @Title: getPage
	 * @Description: TODO(获得分页信息)
	 * @return Map    返回类型
	 * @param queryResult：查询结果，pageNum：页码
	 * @author run
	 * @date 2015年4 月9日
	 */
	public static Map<String,Object> getPageInfo(QueryResult queryResult,int pageNum){
		if(null == queryResult){
			return null;
		}
		Map<String,Object> info = new HashMap<String, Object>();
		//获得分页信息
		//{recordTotal=13, pageCount=2, pageSize=10, pageIndex=1}
		Map<String, Object>  page = queryResult.getPage();
		if(MapUtils.isEmpty(page))
			return null;
		//总条数
		int recordTotal = (int) page.get("recordTotal");
		//总页数
		int pageCount = (int) page.get("pageCount");
		//是否有上一页
		Boolean hasPreviousPage = null;
		//是否有下一页
		Boolean hasNextPage = null;
		if(pageNum==1){
			hasPreviousPage = false;
		}
		else{
			hasPreviousPage = true;
			info.put("prePage", pageNum-1);
		}
		if(pageNum+1>pageCount){
			hasNextPage = false;
		}
		else{
			hasNextPage = true;
			info.put("nextPage", pageNum+1);
		}
		int begin = 1;
		int end = pageCount;
		if(pageNum-4>0){
			begin = pageNum-4;
		}
		if(pageCount<8){
			end = pageCount;
		}
		else if(pageNum+3<8){
			end = 8;
		}
		else if(pageNum+3<pageCount){
			end = pageNum+3;
		}
		//当前显示的页码
		List<Integer> navigatepageNums = new ArrayList<Integer>();
		for (int i = begin; i <= end; i++) {
			navigatepageNums.add(i);
		}
		info.put("total", recordTotal);
		info.put("pageNum", pageNum);
		info.put("hasPreviousPage", hasPreviousPage);
		info.put("hasNextPage", hasNextPage);
		info.put("navigatepageNums", navigatepageNums);
		info.put("pages", pageCount);
		return info;
	}

    /**
     * 根据条件查询对象
     *
     * @param fields
     */
    @Override
    public T getByCriteria(Map<String, Object> criteriaMap, String... fields) {
        QueryCriteria queryCriteria = new QueryCriteria();
        if (criteriaMap != null) {
            QueryCriteria.Criteria criteria = queryCriteria.createCriteria();
            for (Map.Entry<String, Object> entry : criteriaMap.entrySet()) {
                criteria.andColumnEqualTo(entry.getKey(), entry.getValue());
            }
        }
        queryCriteria.setSelectOne(true);
        queryCriteria.setTable(getClazz());
        queryCriteria.setOrderByClause("id desc");
        QueryResult result = null;
        if (fields == null || fields.length == 0)
            result = this.baseDAL.selectByCriteria(queryCriteria);
        else
            result = this.baseDAL.selectByCriteria(fields, queryCriteria);
        return result.as(getClazz());
    }
    
	@Override
	public T getByCriteria(Map<String, Object> criteriaMap) {
		QueryCriteria queryCriteria = new QueryCriteria();
        if (criteriaMap != null) {
            QueryCriteria.Criteria criteria = queryCriteria.createCriteria();
            for (Map.Entry<String, Object> entry : criteriaMap.entrySet()) {
                criteria.andColumnEqualTo(entry.getKey(), entry.getValue());
            }
        }
        queryCriteria.setSelectOne(true);
        queryCriteria.setTable(getClazz());
        queryCriteria.setOrderByClause("id desc");
        QueryResult result = this.baseDAL.selectByCriteria(queryCriteria);
        return result.as(getClazz());
	}
	
	
	/**
     * 根据条件查询对象
     *
     * @param fields
     */
    @Override
    public T getByCriteria(QueryCriteria queryCriteria, String... fields) {
        queryCriteria.setSelectOne(true);
        queryCriteria.setTable(getClazz());
        QueryResult result = null;
        if (fields == null || fields.length == 0)
            result = this.baseDAL.selectByCriteria(queryCriteria);
        else
            result = this.baseDAL.selectByCriteria(fields, queryCriteria);
        return result.as(getClazz());
    }
    
	@Override
	public T getByCriteria(QueryCriteria queryCriteria) {
        queryCriteria.setSelectOne(true);
        queryCriteria.setTable(getClazz());
        QueryResult result = this.baseDAL.selectByCriteria(queryCriteria);
        return result.as(getClazz());
	}

    
    
    @Override
    public Map<String, Object> getByCriteria4Map(Map<String, Object> criteriaMap, String... fields) {
    	QueryCriteria queryCriteria = new QueryCriteria();
    	if (criteriaMap != null) {
    		QueryCriteria.Criteria criteria = queryCriteria.createCriteria();
    		for (Map.Entry<String, Object> entry : criteriaMap.entrySet()) {
    			criteria.andColumnEqualTo(entry.getKey(), entry.getValue());
    		}
    	}
    	queryCriteria.setSelectOne(true);
    	queryCriteria.setTable(getClazz());
    	queryCriteria.setOrderByClause("id desc");
    	QueryResult result = null;
    	if (fields == null || fields.length == 0)
    		result = this.baseDAL.selectByCriteria(queryCriteria);
    	else
    		result = this.baseDAL.selectByCriteria(fields, queryCriteria);
    	return result.get();
    }
    
    @Override
	public Map<String, Object> getByCriteria4Map(QueryCriteria queryCriteria, List<String> fields) {
    	queryCriteria.setSelectOne(true);
    	queryCriteria.setTable(getClazz());
    	queryCriteria.setOrderByClause("id desc");
    	QueryResult result = null;
    	if (fields == null || fields.size() == 0)
    		result = this.baseDAL.selectByCriteria(queryCriteria);
    	else
    		result = this.baseDAL.selectByCriteria(fields, queryCriteria);
    	return result.get();
	}


	@Override
	public Map<String, Object> getByCriteria4Map(QueryCriteria criteria) {
		return getByCriteria4Map(criteria, null);
	}
    

	@Override
	public Map<String, Object> getByCriteria4Map(Map<String, Object> criteriaMap) {
		QueryCriteria queryCriteria = new QueryCriteria();
    	if (criteriaMap != null) {
    		QueryCriteria.Criteria criteria = queryCriteria.createCriteria();
    		for (Map.Entry<String, Object> entry : criteriaMap.entrySet()) {
    			criteria.andColumnEqualTo(entry.getKey(), entry.getValue());
    		}
    	}
    	queryCriteria.setSelectOne(true);
    	queryCriteria.setTable(getClazz());
    	queryCriteria.setOrderByClause("id desc");
    	QueryResult	result = this.baseDAL.selectByCriteria(queryCriteria);
    	return result.get();
	}
    
    
    /**
     * 根据id查询对象的一些字段
     *
     * @param id     对象的id
     * @param fields 要查询的字段
     */
    @Override
    public T getById(Object id, List<String> fields) {
        QueryResult result = this.baseDAL.selectByPrimaryKey(fields, this.getClazz(), id);
        if (null == result) return null;
        return result.as(this.getClazz());
    }

    /**
     * 根据id查询对象的一些字段
     *
     * @param id     对象的id
     * @param fields 要查询的字段
     */
    @Override
    public Map<String, Object> getById4Map(Object id, List<String> fields) {
        QueryResult result = this.baseDAL.selectByPrimaryKey(fields, this.getClazz(), id);
        if (null == result) return null;
        return result.get();
    }

    /**
     * 根据条件查询 map 列表
     *
     * @param fields
     */
    @Override
    public List<Map<String, Object>> selectList4Map(Map<String, Object> map, int pageSize, String... fields) {
        QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable(getClazz());
        queryCriteria.setOrderByClause("id");
        queryCriteria.setPageSize(pageSize);
        if (map != null) {
            QueryCriteria.Criteria criteria = queryCriteria.createCriteria();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                criteria.andColumnEqualTo(entry.getKey(), entry.getValue());
            }
        }
        QueryResult result = null;
        if (fields == null || fields.length == 0)
            result = this.baseDAL.selectByCriteria(queryCriteria);
        else
            result = this.baseDAL.selectByCriteria(fields, queryCriteria);
        return result.getList();
    }


    /**
     * 主要功能:
     *
     * @param inMap
     * @param andMap
     * @param fields
     * @param order 排序字段 类型
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<Map<String,Object>> selectListMapByFieldsInByOrder(Map<String, Object> inMap, Map<String, Object> andMap, String order,String... fields){
        QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable(this.getClazz());
        queryCriteria.setPageSize(10000);
        queryCriteria.setOrderByClause(order);
        QueryCriteria.Criteria criteria = queryCriteria.createCriteria();
        if (inMap != null && !inMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : inMap.entrySet()) {
                criteria.andColumnIn(entry.getKey(), (List<Object>) entry.getValue());
            }
        }
        if (andMap != null && !andMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : andMap.entrySet()) {
                criteria.andColumnEqualTo(entry.getKey(), entry.getValue());
            }
        }
        QueryResult result = null;
        if (fields == null || fields.length == 0)
            result = this.baseDAL.selectByCriteria(queryCriteria);
        else
            result = this.baseDAL.selectByCriteria(fields, queryCriteria);
        return result.getList();
    }
    /**
     * 主要功能:
     *
     * @param inMap
     * @param andMap
     * @param fields
     * @param order 排序字段 类型
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<T> selectListByFieldsInByOrder(Map<String, Object> inMap, Map<String, Object> andMap, String order,String... fields){
        QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable(this.getClazz());
        queryCriteria.setPageSize(10000);
        queryCriteria.setOrderByClause(order);
        QueryCriteria.Criteria criteria = queryCriteria.createCriteria();
        if (inMap != null && !inMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : inMap.entrySet()) {
                criteria.andColumnIn(entry.getKey(), (List<Object>) entry.getValue());
            }
        }
        if (andMap != null && !andMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : andMap.entrySet()) {
                criteria.andColumnEqualTo(entry.getKey(), entry.getValue());
            }
        }
        QueryResult result = null;
        if (fields == null || fields.length == 0)
            result = this.baseDAL.selectByCriteria(queryCriteria);
        else
            result = this.baseDAL.selectByCriteria(fields, queryCriteria);
        return result.asList(this.getClazz());
    }

    @Override
    public T getById(Object id, String... fields) {
        Map<String, Object> map = new HashMap<String, Object>(1);
        map.put("id", id);
        return (T)this.getByCriteria(map, fields);

    }


    /**
     * 根据查询条件更新
     *
     * @param t        当前对象
     */
    @Override
    public void updateByCriteria(T t, Map<String, Object> criteriaMap) {
        QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable(this.getClazz());
        if (criteriaMap != null) {
            QueryCriteria.Criteria criteria = queryCriteria.createCriteria();
            for (Map.Entry<String, Object> entry : criteriaMap.entrySet()) {
                criteria.andColumnEqualTo(entry.getKey(), entry.getValue());
            }
            if(criteriaMap.containsKey("version")){
            	queryCriteria.setVersion(criteriaMap.get("version"));
            }
        }
        this.baseDAL.updateByCriteria(t, queryCriteria);
    }
    
    @Override
    public int updateByCriteria(T t, QueryCriteria queryCriteria) {
    	queryCriteria.setTable(this.getClazz());
        return this.baseDAL.updateByCriteria(t, queryCriteria);
    }

    public BaseDAL getBaseDAL() {
        return this.baseDAL;
    }

    /**
     * 根据一组id查询
     * @param ids
     * @param fields
     * @return
     * @author Tony
     * @date 2016年4月7日下午2:00:03
     */
    @Override
    public List<Map<String ,Object>> selectByIds4Map(List<Long> ids,String...fields){
    	QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable(this.getClazz());
        queryCriteria.setPageSize(10000);
        queryCriteria.setOrderByClause("id");
        QueryCriteria.Criteria criteria = queryCriteria.createCriteria();
        criteria.andColumnIn("id", ids);
        QueryResult result = null;
        if (fields == null || fields.length == 0)
            result = this.baseDAL.selectByCriteria(queryCriteria);
        else
            result = this.baseDAL.selectByCriteria(fields, queryCriteria);
        return result.getList();
    }

    @Override
    public List<T> selectByIds(List<Long> ids){
    	if(null == ids) return Collections.emptyList();
    	QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setTable(this.getClazz());
        queryCriteria.setPageSize(10000);
        queryCriteria.setOrderByClause("id");
        QueryCriteria.Criteria criteria = queryCriteria.createCriteria();
        criteria.andColumnIn("id", ids);
        QueryResult result = this.baseDAL.selectByCriteria(queryCriteria);
        return result.asList(clazz);
    }







	











	

}
