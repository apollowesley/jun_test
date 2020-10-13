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

package net.oschina.durcframework.easymybatis.ext.code.generator;

import javax.persistence.Table;

import net.oschina.durcframework.easymybatis.EasymybatisConfig;
import net.oschina.durcframework.easymybatis.ext.code.util.FieldUtil;

public class TableSelector {

	private ColumnSelector columnSelector;
	private Class<?> entityClass;
	private EasymybatisConfig config;

	public TableSelector(Class<?> entityClass,EasymybatisConfig config) {
		if(config == null) {
			throw new IllegalArgumentException("EasymybatisConfig不能为null");
		}
		if(entityClass == null) {
			throw new IllegalArgumentException("entityClass不能为null");
		}
		this.entityClass = entityClass;
		this.config = config;
		this.columnSelector = new ColumnSelector(entityClass,config);
	}
	
	public TableDefinition getTableDefinition() {
		TableDefinition tableDefinition = new TableDefinition();
		Table tableAnno = entityClass.getAnnotation(Table.class);
		
		String schema = "";
		String tableName = entityClass.getSimpleName();
		
		if(tableAnno != null) {
			schema = tableAnno.schema();
			tableName = tableAnno.name();
			
		}else {
			String javaBeanName = entityClass.getSimpleName();
			if(config.isCamel2underline()) {
				tableName = FieldUtil.camelToUnderline(javaBeanName);
			}
		}
		
		tableDefinition.setSchema(schema);
		tableDefinition.setTableName(tableName);
		
		tableDefinition.setColumnDefinitions(columnSelector.getColumnDefinitions());
		
		return tableDefinition;
	}



}
