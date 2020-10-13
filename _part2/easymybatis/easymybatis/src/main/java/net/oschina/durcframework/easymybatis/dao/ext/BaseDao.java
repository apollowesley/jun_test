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

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import net.oschina.durcframework.easymybatis.util.ClassUtil;

public class BaseDao<Entity> {

	private Class<?> entityClass;
	private SqlSessionTemplate sqlSessionTemplate;
	
	public BaseDao() {
		super();
		entityClass = ClassUtil.getSuperClassGenricType(getClass(), 0);
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	protected String getStatement(String id) {
		return getNamespace() + "." + id;
	}

	protected String getNamespace() {
		return this.getClass().getName();
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}
	

}
