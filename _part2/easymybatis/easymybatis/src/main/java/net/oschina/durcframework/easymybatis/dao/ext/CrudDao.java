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

import net.oschina.durcframework.easymybatis.query.Queryable;

public class CrudDao<Entity> extends SchDao<Entity> {

	public int save(Entity entity) {
		return this.getSqlSessionTemplate().insert(this.getStatement("save"), entity);
	}

	public int update(Entity entity) {
		return this.getSqlSessionTemplate().update(this.getStatement("update"), entity);
	}

	public int del(Entity entity) {
		return this.getSqlSessionTemplate().delete(this.getStatement("del"), entity);
	}

	public int delByExpression(Queryable query) {
		return this.getSqlSessionTemplate().delete(this.getStatement("delByExpression"), query);
	}

}
