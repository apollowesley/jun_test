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

import org.apache.ibatis.annotations.Param;

import net.oschina.durcframework.easymybatis.query.Column;

public interface SaveDao<Entity> extends Edit<Entity> {
	/**
	 * 新增,新增所有字段
	 * 
	 * @param entity
	 * @return 受到影响的行数
	 */
	int save(Entity entity);
	
	/**
	 * 新增（忽略null数据）
	 * @param entity
	 * @return 受到影响的行数
	 */
	int saveIgnoreNull(Entity entity);
	
	/**
	 * 批量添加,只支持mysql,sqlserver2008及以上数据库.<br>
	 * <strong>若要兼容其它版本数据库,请使用saveMulti()方法</strong>
	 * @param entitys
	 * @return
	 */
	int saveBatch(@Param("entitys")List<Entity> entitys);
	
	/**
	 * 批量添加指定字段,只支持mysql,sqlserver2008及以上数据库.<br>
	 * <strong>若要兼容其它版本数据库,请使用saveMulti()方法</strong>
	 * @param columns 指定字段
	 * @param entitys
	 * @return
	 */
	int saveBatchWithColumns(@Param("columns")List<Column> columns,@Param("entitys")List<Entity> entitys);
	
	/**
	 * 批量添加,兼容更多的数据库版本.<br>
	 * 此方式采用union all的方式批量insert,如果是mysql或sqlserver2008及以上推荐saveBatch()方法.
	 * @param entitys
	 * @return
	 */
	int saveMulti(@Param("entitys")List<Entity> entitys);
	
	/**
	 * 批量添加指定字段,兼容更多的数据库版本.<br>
	 * 此方式采用union all的方式批量insert,如果是mysql或sqlserver2008及以上推荐saveBatch()方法.
	 * @param columns 指定字段
	 * @param entitys
	 * @return
	 */
	int saveMultiWithColumns(@Param("columns")List<Column> columns,@Param("entitys")List<Entity> entitys);
}
