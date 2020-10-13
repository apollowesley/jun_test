/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oschina.durcframework.easymybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.oschina.durcframework.easymybatis.query.Queryable;

public interface SchDao<Entity> extends Sch<Entity> {
	
	String baseResultMap = "baseResultMap";

	/**
	 * 根据对象查询,可以传主键值,也可以传整个对象
	 * 
	 * @param id
	 * @return
	 */
	Entity get(Object id);
	
	/**
	 * 根据条件查找单条记录
	 * @param queryable 查询条件，默认实现类为Query
	 * @return
	 */
	Entity getByExpression(Queryable queryable);
	
	/**
	 * 根据字段查询一条记录
	 * @param column 数据库字段名
	 * @param value 字段值
	 * @return
	 */
	Entity getByProperty(@Param("column")String column,@Param("value")Object value);
	
	/**
	 * 根据字段查询集合
	 * @param column 数据库字段名
	 * @return
	 */
	List<Entity> listByProperty(@Param("column")String column,@Param("value")Object value);
	
	/**
	 * 根据字段查询集合
	 * @param column 字段名
	 * @param value 值
	 * @param queryable 条件
	 * @return
	 */
	List<Entity> listByProperty(@Param("column")String column,@Param("value")Object value,Queryable queryable);
	
	/**
	 * 条件查询
	 * 
	 * @param queryable 查询条件，默认实现类为Query
	 * @return
	 */
	List<Entity> find(Queryable queryable);
	
	/**
     * 查询全部
     * 
     * @param queryable 查询条件，默认实现类为Query
     * @return
     */
    List<Entity> findAll(Queryable queryable);

	/**
	 * 查询总记录数
	 * 
	 * @param queryable 查询条件，默认实现类为Query
	 * @return
	 */
	long countTotal(Queryable queryable);
	
	/**
	 * 聚合查询
	 * @param queryable 查询条件，默认实现类为Query
	 * @return
	 */
	List<Map<String,Object>> findProjection(Queryable queryable);

}
