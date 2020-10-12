package org.durcframework.core.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.durcframework.core.Sch;
import org.durcframework.core.dao.BaseDao;
import org.durcframework.core.expression.Expression;
import org.durcframework.core.expression.ExpressionQuery;
import org.durcframework.core.expression.QBC;
import org.durcframework.core.expression.projection.ProjectionQuery;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 负责查询的Service
 * 
 * @author tanghc
 * 
 * @param <Entity>
 * @param <Dao>
 */
public abstract class SearchService<Entity, Dao extends BaseDao<Entity>> implements Sch<Entity>{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private Dao dao;

	public Dao getDao() {
		return dao;
	}

	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}

	/**
	 * 通过对象获取记录,可以传主键值,也可以传整个对象
	 * @param id 如:get(21)或get(student)
	 * @return 返回实体对象
	 */
	@Override
	public Entity get(Object id) {
		return dao.get(id);
	}

	/**
	 * 带入条件查询,返回结果集
	 * @param query
	 * @return 返回结果一定不为null,如果没有数据则返回一个空List
	 */
	@Override
	public List<Entity> find(ExpressionQuery query) {
		List<Entity> list = dao.find(query);
		if(list == null) {
			list = Collections.emptyList();
		}
		return list;
	}

	/**
	 * 带入条件查询总数
	 * @param query
	 * @return 返回的结果一定大于等于0
	 */
	@Override
	public int findTotalCount(ExpressionQuery query) {
		Integer total = dao.findTotalCount(query);
		return total == null ? 0 : total;
	}

	/**
	 * 根据条件查找单条记录
	 */
	@Override
	public Entity getByExpression(ExpressionQuery query) {
		return dao.getByExpression(query);
	}
	
	/**
	 * 根据字段查询一条记录
	 * @param column 数据库字段名
	 * @param value 字段值
	 * @return
	 */
	public Entity getByProperty(String column,Object value) {
		QBC<Entity> qbc = QBC.create(dao);
		return qbc.eq(column, value).listOne();
	}
	
	/**
	 * 根据字段查询集合
	 * @param column 数据库字段名
	 * @return
	 */
	public List<Entity> listByProperty(String column) {
		return this.listByProperty(column, null);
	}
	
	/**
	 * 根据字段查询集合
	 * @param column
	 * @param expressions
	 * @return
	 */
	public List<Entity> listByProperty(String column,List<Expression> expressions) {
		QBC<Entity> qbc = QBC.create(dao);
		qbc.addExpressions(expressions);
		return qbc.listAll();
	}

	@Override
	public List<Map<String,Object>> findProjection(ProjectionQuery query) {
		return dao.findProjection(query);
	}

	public Logger getLogger() {
		return logger;
	}
	
}
