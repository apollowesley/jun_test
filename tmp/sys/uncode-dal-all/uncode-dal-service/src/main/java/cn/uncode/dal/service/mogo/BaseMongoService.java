package cn.uncode.dal.service.mogo;

import cn.uncode.dal.criteria.QueryCriteria;

import java.util.List;

public interface BaseMongoService<T> {
	/**
	 * 保存对象
	 * @param t
	 */
  	String save(T t);
  	/**
  	 * 根据id获得对象
  	 * @param id  数据id
  	 */
  	T getById(String id);
  	/**
  	 * 根据id更新对象
  	 * @param t 对象
  	 */
  	void updateById(T t);
  	/**
  	 * 根据id删除
  	 * @param t 对象
  	 */
  	void deleteById(T t);
	/**
	 * 根据queryCriteria删除
	 * @param queryCriteria 自定义条件
	 */
	int deleteByCriteria(QueryCriteria queryCriteria);
  	/**
  	 * 批量保存
  	 * @param list  对象列表
  	 */
  	void saveMany(List<Object> list);
}
