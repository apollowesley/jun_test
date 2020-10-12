package org.durcframework.core;

import java.util.List;
import java.util.Map;

import org.durcframework.core.expression.ExpressionQuery;
import org.durcframework.core.expression.projection.ProjectionQuery;

public interface Sch<Entity> {
	/**
	 * 根据对象查询,可以传主键值,也可以传整个对象
	 * 
	 * @param id
	 * @return
	 */
	Entity get(Object id);
	
	/**
	 * 根据条件查找单条记录
	 * @param id
	 * @param query
	 * @return
	 */
	Entity getByExpression(ExpressionQuery query);
	
	/**
	 * 条件查询
	 * 
	 * @param query
	 * @return
	 */
	List<Entity> find(ExpressionQuery query);

	/**
	 * 查询总记录数
	 * 
	 * @param query
	 * @return
	 */
	int findTotalCount(ExpressionQuery query);
	
	/**
	 * 聚合查询
	 * @param query
	 * @return
	 */
	List<Map<String,Object>> findProjection(ProjectionQuery query);
}
