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

package net.oschina.durcframework.easymybatis.support;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import net.oschina.durcframework.easymybatis.PageResult;
import net.oschina.durcframework.easymybatis.dao.CrudDao;
import net.oschina.durcframework.easymybatis.query.Column;
import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Queryable;
import net.oschina.durcframework.easymybatis.query.expression.Expressional;
import net.oschina.durcframework.easymybatis.support.lock.DefaultRecordLock;
import net.oschina.durcframework.easymybatis.support.lock.RecordLock;
import net.oschina.durcframework.easymybatis.util.MyBeanUtil;
import net.oschina.durcframework.easymybatis.util.QueryUtils;

/**
 * 通用service
 * 
 * @author tanghc
 *
 * @param <Dao>
 * @param <Entity>
 */
public class CrudService<Dao extends CrudDao<Entity>, Entity> {

	private Dao dao;

	private static RecordLock DEFAULT_RECORD_LOCK = new DefaultRecordLock();

	public Dao getDao() {
		return dao;
	}

	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}

	protected RecordLock getLock(Entity entity) {
		return DEFAULT_RECORD_LOCK;
	}

	public PageResult<Entity> query(Query query) {
		return QueryUtils.query(dao, query);
	}

	public <T extends PageResult<Entity>> T query(Query query, Class<T> resultClass) {
		return QueryUtils.query(dao, query, resultClass);
	}

	public int update(Entity entity) {
		return this.lockUpdate(new Updater<Entity>() {

			public int doUpdate(Entity entity) {
				return getDao().update(entity);
			}
		}, entity);
	}

	public int updateIgnoreNull(Entity entity) {
		return this.lockUpdate(new Updater<Entity>() {

			public int doUpdate(Entity entity) {
				return getDao().updateIgnoreNull(entity);
			}
		}, entity);
	}

	public int updateIgnoreNullByExpression(Entity entity, final Expressional expressional) {
		return this.lockUpdate(new Updater<Entity>() {

			public int doUpdate(Entity entity) {
				return getDao().updateIgnoreNullByExpression(entity, expressional);
			}
		}, entity);
	}

	protected int lockUpdate(Updater<Entity> updater, Entity entity) {
		// 单机部署情况下可以这样做
		// 集群的话要考虑分布式锁
		RecordLock recordLock = this.getLock(entity);

		recordLock.lock(entity);
		int i = 0;

		try {
			Entity obj = this.get(entity);
			copyProperties(entity, obj);
			i = updater.doUpdate(obj);
		} finally {
			recordLock.unlock(entity);
		}

		return i;
	}

	/**
	 * 属性拷贝,第一个参数中的属性值拷贝到第二个参数中<br>
	 * 注意:当第一个参数中的属性有null值时,不会拷贝进去
	 * 
	 * @param source
	 * @param target
	 */
	public void copyProperties(Object source, Entity target) {
		MyBeanUtil.copyProperties(source, target);
	}

	public Entity get(Object id) {
		return this.getDao().get(id);
	}

	public Entity getByExpression(Queryable Queryable) {
		return this.getDao().getByExpression(Queryable);
	}

	public Entity getByProperty(String column, Object value) {
		return this.getDao().getByProperty(column, value);
	}

	public List<Entity> listByProperty(String column, Object value) {
		return this.getDao().listByProperty(column, value);
	}

	public List<Entity> listByProperty(String column, Object value, Queryable Queryable) {
		return this.getDao().listByProperty(column, value, Queryable);
	}

	public List<Entity> find(Queryable Queryable) {
		return this.getDao().find(Queryable);
	}

	public long countTotal(Queryable Queryable) {
		return this.getDao().countTotal(Queryable);
	}

	public List<Map<String, Object>> findProjection(Queryable Queryable) {
		return this.getDao().findProjection(Queryable);
	}

	public int save(Entity entity) {
		return this.getDao().save(entity);
	}

	public int saveIgnoreNull(Entity entity) {
		return this.getDao().saveIgnoreNull(entity);
	}

	public int saveBatch(List<Entity> entitys) {
		return this.getDao().saveBatch(entitys);
	}

	public int saveBatchWithColumns(List<Column> columns, List<Entity> entitys) {
		return this.getDao().saveBatchWithColumns(columns, entitys);
	}

	public int saveMulti(List<Entity> entitys) {
		return this.getDao().saveMulti(entitys);
	}

	public int saveMultiWithColumns(List<Column> columns, List<Entity> entitys) {
		return this.getDao().saveMultiWithColumns(columns, entitys);
	}

	public int del(Entity entity) {
		return this.getDao().del(entity);
	}

	public int delByExpression(Queryable query) {
		return this.getDao().delByExpression(query);
	}

}
