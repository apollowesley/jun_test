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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数据库表定义,从这里可以获取表名,字段信息
 */
public class TableDefinition {
	private String schema;
	private String tableName; // 表名
	private String comment; // 注释
	private List<ColumnDefinition> columnDefinitions = Collections.emptyList(); // 字段定义
	private ColumnDefinition pkColumn;
	private ColumnDefinition versionColumn; // 乐观锁字段
	private ColumnDefinition logicDeleteColumn; // 逻辑删除字段

	public TableDefinition() {
	}

	public TableDefinition(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * 返回表字段
	 * @return
	 */
	public List<ColumnDefinition> getTableColumns() {
		List<ColumnDefinition> columns = this.getColumnDefinitions();
		
		List<ColumnDefinition> ret = new ArrayList<>();
		
		for (ColumnDefinition columnDefinition : columns) {
			if(!columnDefinition.isTransient()) {
				ret.add(columnDefinition);
			}
		}
		
		return ret;
	}
	
	/**
	 * 返回所有定义的字段
	 * @return
	 */
	public List<ColumnDefinition> getAllColumns() {
		return this.getColumnDefinitions();
	}

	/**
	 * 是否含有时间字段
	 * @return
	 */
	public boolean getHasDateField() {
		List<ColumnDefinition> columns = getColumnDefinitions();
		for (ColumnDefinition definition : columns) {
			if("Date".equals(definition.getJavaType())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否含有BigDecimal字段
	 * @return
	 */
	public boolean getHasBigDecimalField() {
		List<ColumnDefinition> columns = getColumnDefinitions();
		for (ColumnDefinition definition : columns) {
			if("BigDecimal".equals(definition.getJavaType())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean getHasVersionColumn() {
		return this.versionColumn != null;
	}
	
	public boolean getHasLogicDeleteColumn() {
		return this.logicDeleteColumn != null;
	}
	

	public ColumnDefinition getPkColumn() {
		return this.pkColumn;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<ColumnDefinition> getColumnDefinitions() {
		return columnDefinitions;
	}

	public void setColumnDefinitions(List<ColumnDefinition> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
		
		this.initPKColumn(columnDefinitions);
		this.initVersionJavaColumn(columnDefinitions);
		this.initDeleteJavaColumn(columnDefinitions);
	}
	
	private void initPKColumn(List<ColumnDefinition> columnDefinitions) {
		for (ColumnDefinition column : columnDefinitions) {
			if (column.getIsPk()) {
				this.setPkColumn(column);
				break;
			}
		}
	}
	
	private void initVersionJavaColumn(List<ColumnDefinition> columnDefinitions) {
		for (ColumnDefinition column : columnDefinitions) {
			if (column.getIsVersion()) {
				this.setVersionColumn(column);
				break;
			}
		}
	}
	
	private void initDeleteJavaColumn(List<ColumnDefinition> columnDefinitions) {
		int count = 0;
		for (ColumnDefinition column : columnDefinitions) {
			if(column.getIsLogicDelete()) {
				if(count == 1) {
					throw new RuntimeException(column.getJavaFieldName() + "字段重复定义@LogicDelete.确保实体类中只有一个@LogicDelete注解");
				}
				this.setLogicDeleteColumn(column);
				count++;
			}
		}
	}
	
	
	public ColumnDefinition getVersionColumn() {
		return versionColumn;
	}

	public void setVersionColumn(ColumnDefinition versionColumn) {
		this.versionColumn = versionColumn;
	}

	public void setPkColumn(ColumnDefinition pkColumn) {
		this.pkColumn = pkColumn;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public ColumnDefinition getLogicDeleteColumn() {
		return logicDeleteColumn;
	}

	public void setLogicDeleteColumn(ColumnDefinition logicDeleteColumn) {
		this.logicDeleteColumn = logicDeleteColumn;
	}

}
