package org.durcframework.service;

import org.durcframework.dao.BaseDao;

/**
 * 负责增删改查的Service
 * @author hc.tang
 *
 * @param <Entity>
 * @param <Dao>
 */
public abstract class CrudService<Entity, Dao extends BaseDao<Entity>>
		extends SearchService<Entity, Dao> {

	public Entity save(Entity entity) {
		if(entity == null) {
			throw new RuntimeException("保存数据对象不能为null");
		}
		this.getDao().save(entity);
		
		return entity;
	}

	public Entity update(Entity entity) {
		if(entity == null) {
			throw new RuntimeException("修改数据对象不能为null");
		}
		this.getDao().update(entity);
		
		return entity;
	}

	public void del(Entity entity) {
		if(entity == null) {
			throw new RuntimeException("删除数据对象不能为null");
		}
		this.getDao().del(entity);
	}
}
