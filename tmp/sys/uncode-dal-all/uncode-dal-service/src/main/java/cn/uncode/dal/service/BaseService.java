package cn.uncode.dal.service;

import java.util.List;
import java.util.Map;

import cn.uncode.dal.core.BaseDAL;
import cn.uncode.dal.criteria.QueryCriteria;

public interface BaseService<T> {
	
	
    //-------------------------
  	// get
  	//-------------------------
	/**
	 * 根据id获得对象
	 */
	T getById(Object id);
	
	/**
	 * 根据id查询对象的一些字段
	 * @param id   对象的id
	 * @param fields  要查询的字段
	 */
	T getById(Object id,List<String> fields);
	
	/**
	 * 根据id查询对象的一些字段
	 * @param id   对象的id
	 * @param fields  要查询的字段
	 */
	T getById(Object id,String ... fields);
	
	/**
	 * 根据条件查询对象
	 * @param criteria
	 * @param fields
	 */
	T getByCriteria(Map<String, Object> criteria, String ... fields);
	
	T getByCriteria(Map<String, Object> criteria);
	
	T getByCriteria(QueryCriteria queryCriteria);
	
	T getByCriteria(QueryCriteria queryCriteria, String ... fields);
	
	/**
	 * 根据id查询对象的一些字段
	 * @param id   对象的id
	 * @param fields  要查询的字段
	 */
	Map<String,Object> getById4Map(Object id,List<String> fields);
	
	/** 根据条件查询对象
	 * @param criteria
	 * @param fields
	 */
	Map<String, Object> getByCriteria4Map(Map<String, Object> criteria, String ... fields);
	
	
	Map<String, Object> getByCriteria4Map(Map<String, Object> criteria);
	
	
	Map<String, Object> getByCriteria4Map(QueryCriteria criteria, List<String> fields);
	
	Map<String, Object> getByCriteria4Map(QueryCriteria criteria);
	
	
    //-------------------------
  	// select
  	//-------------------------
	/**
     * 根据一组id查询
     * @param ids
     * @param fields
     */
    public List<Map<String,Object>> selectByIds4Map(List<Long> ids, String...fields);

	/**
     * 列表查询
     * @param ids
     */
    public List<T> selectByIds(List<Long> ids);
    
    /**
	 * 更具条件查询
	 * @param criteria 查询条件
	 * @param fields 显示字段
	 */
	List<T> selectList(Map<String, Object> criteria, String ... fields);
	
	List<T> selectList(Map<String, Object> criteria);
	
	/**
	 * 根据条件查询 map 列表
	 * @param map
	 * @param fields
	 */
	List<T> selectList(Map<String, Object> criteria, int pageSize, String ... fields);
	/**
	 * 根据条件查询 map 列表
	 * @param map
	 * @param fields
	 */
	List<Map<String,Object>> selectList4Map(Map<String, Object> criteria, int limit, String ... fields);
	
	
	Map<String, Object> selectPage4Map(QueryCriteria queryCriteria, String ... fields);

	Map<String, Object> selectPage4Map(QueryCriteria queryCriteria);
	
	List<T> selectAll();
	
	List<Map<String,Object>> selectList4Map(QueryCriteria queryCriteria);
	
	List<T> selectList(QueryCriteria queryCriteria);
	
	List<T> selectList(QueryCriteria queryCriteria, List<String> fields);
	
	List<T> selectList(QueryCriteria queryCriteria, String... fields);
	
	List<Map<String,Object>> selectList4Map(QueryCriteria queryCriteria, List<String> fields);
	
	List<Map<String,Object>> selectList4Map(QueryCriteria queryCriteria, String... fields);

	/**
     * 主要功能:
     * @param inMap
     * @param andMap
     * @param fields
     * @param order 排序字段 类型
     */
	public List<Map<String,Object>> selectListMapByFieldsInByOrder(Map<String, Object> inMap, Map<String, Object> andMap,String order,String... fields);
	/**
     * 主要功能:
     * @param inMap
     * @param andMap
     * @param fields
     * @param order 排序字段 类型
     */
	public List<T> selectListByFieldsInByOrder(Map<String, Object> inMap, Map<String, Object> andMap,String order,String... fields);
	
	
	
	
	
	
	
	
    //-------------------------
  	// save
  	//-------------------------
	/**
	 * 保存对象
	 */
	Object save(T t);
	
    //-------------------------
  	// delete
  	//-------------------------
	/**
	 * 删除对象
	 * @return
	 */
	int deleteById(Object id);

    //-------------------------
  	// udpate
  	//-------------------------
	/**
	 * 修改
	 */
	int updateById(T t);
	
	/**
	 * 根据查询条件更新
	 * @param t  当前对象
	 * @param criteriaMap  查询条件
	 */
	void updateByCriteria(T t, Map<String, Object> criteria);
	
	/**
	 * 根据查询条件更新
	 * @param t  当前对象
	 * @param criteriaMap  查询条件
	 */
	int updateByCriteria(T t, QueryCriteria queryCriteria);
	

    //-------------------------
  	// other
  	//-------------------------
	public BaseDAL getBaseDAL();




	

}
