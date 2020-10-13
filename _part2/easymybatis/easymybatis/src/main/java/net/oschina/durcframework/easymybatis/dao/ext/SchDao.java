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

package net.oschina.durcframework.easymybatis.dao.ext;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.util.StringUtils;

import net.oschina.durcframework.easymybatis.ext.code.util.ReflectionUtils;
import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Queryable;
import net.oschina.durcframework.easymybatis.query.expression.ValueExpression;

public class SchDao<Entity> extends BaseDao<Entity>
		implements net.oschina.durcframework.easymybatis.dao.SchDao<Entity> {

	private String pkName;
	private Class<?> entityClass;

	public SchDao() {
		super();
		this.pkName = this.buildPkName();
	}

	protected String getPkName() {
		return this.pkName;
	}

	private String buildPkName() {
		List<Field> fields = ReflectionUtils.getDeclaredFields(this.getEntityClass());

		for (Field field : fields) {
			if (isPK(field)) {
				return getColumnName(field);
			}
		}

		throw new RuntimeException(entityClass.getName() + "未找到主键，是否同时设置了@Id,@Column注解");
	}

	// 根据java字段获取数据库字段名
	private static String getColumnName(Field field) {
		Column columnAnno = field.getAnnotation(Column.class);
		// 存在注解
		if (columnAnno != null) {
			String columnName = columnAnno.name();
			if (StringUtils.isEmpty(columnName)) {
				throw new IllegalArgumentException(field.getName() + "注解@Column(name=\"\")name属性不能为空");
			}
			return columnName;
		}

		String javaFieldName = field.getName();
		// 如果开启了驼峰转下划线形式
		return javaFieldName;
	}

	private static boolean isPK(Field field) {
		return field.getAnnotation(Id.class) != null;
	}

	@Override
	public Entity get(Object id) {
		return this.getByProperty(this.getPkName(), id);
	}

	@Override
	public Entity getByExpression(Queryable queryable) {
		return this.getSqlSessionTemplate().selectOne(this.getStatement("getByExpression"), queryable);
	}

	@Override
	public Entity getByProperty(String column, Object value) {
		Query query = new Query();
		query.eq(column, value);
		return this.getByExpression(query);
	}

	@Override
	public List<Entity> listByProperty(String column, Object value) {
		Query query = new Query();
		query.eq(column, value);
		return this.find(query);
	}

	@Override
	public List<Entity> listByProperty(String column, Object value, Queryable queryable) {
		queryable.addExpression(new ValueExpression(column, value));
		return this.find(queryable);
	}

	@Override
	public List<Entity> find(Queryable queryable) {
		return this.getSqlSessionTemplate().selectList(this.getStatement("find"), queryable);
	}
	
	@Override
	public List<Entity> findAll(Queryable queryable) {
	    return this.getSqlSessionTemplate().selectList(this.getStatement("findAll"), queryable);
	}

	@Override
	public long countTotal(Queryable queryable) {
		Long total = this.getSqlSessionTemplate().selectOne(this.getStatement("countTotal"), queryable);
		return total == null ? 0L : total;
	}

	@Override
	public List<Map<String, Object>> findProjection(Queryable queryable) {
		return this.getSqlSessionTemplate().selectList(this.getStatement("findProjection"), queryable);
	}

}
