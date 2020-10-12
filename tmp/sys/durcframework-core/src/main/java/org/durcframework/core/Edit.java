package org.durcframework.core;

import org.apache.ibatis.annotations.Param;
import org.durcframework.core.expression.ExpressionQuery;

public interface Edit<Entity> extends Sch<Entity> {
	/**
	 * 新增,新增所有字段
	 * 
	 * @param entity
	 * @return 受到影响的行数
	 */
	int save(Entity entity);
	
	/**
	 * 新增（忽略空数据）
	 * @param entity
	 * @return 受到影响的行数
	 */
	int saveNotNull(Entity entity);

	/**
	 * 修改,修改所有字段
	 * 
	 * @param entity
	 * @return 受到影响的行数
	 */
	int update(Entity entity);
	
	/**
	 * 根据条件更新所有字段
	 * @param entity
	 * @param query
	 * @return 受到影响的行数
	 */
	int updateByExpression(@Param("entity")Entity entity,@Param("query")ExpressionQuery query);
	
	/**
	 * 根据主键更新不为null的字段
	 * @param entity
	 * @return 受到影响的行数
	 */
	int updateNotNull(Entity entity);
	
	/**
	 * 根据条件更新不为null的字段
	 * @param entity
	 * @param query
	 * @return 受到影响的行数
	 */
	int updateNotNullByExpression(@Param("entity")Entity entity,@Param("query")ExpressionQuery query);

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return 受到影响的行数
	 */
	int del(Entity entity);
	
	/**
	 * 根据条件删除
	 * @param entity
	 * @param query
	 * @return 受到影响的行数
	 */
	int delByExpression(ExpressionQuery query);
}
