package org.durcframework.core.service;

import org.durcframework.core.DurcException;
import org.durcframework.core.Edit;
import org.durcframework.core.dao.BaseDao;
import org.durcframework.core.expression.ExpressionQuery;

/**
 * 负责增删改查的Service
 * 
 * @author tanghc
 * 
 * @param <Entity>
 * @param <Dao>
 */
public abstract class CrudService<Entity, Dao extends BaseDao<Entity>> extends
		SearchService<Entity, Dao> implements Edit<Entity> {

	/**
	 * 保存数据
	 * 
	 * @param entity
	 *            实体对象
	 * @return 受到影响的行数
	 */
	@Override
	public int save(Entity entity) {
		checkNull(entity);

		return this.getDao().save(entity);
	}

	/**
	 * 根据判断来保存数据
	 * 
	 * @param entity
	 * @param saveHandler
	 *            实现该接口用来判断能否保存,返回true则调用save(entity)方法,返回false不保存
	 * @return 受到影响的行数
	 */
	public int save(Entity entity, SaveHandler<Entity> saveHandler) {
		if (saveHandler.canSave(entity)) {
			return this.save(entity);
		}
		return 0;
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 *            实体对象
	 * @return 受到影响的行数
	 */
	@Override
	public int update(Entity entity) {
		checkNull(entity);

		return this.getDao().update(entity);
	}

	/**
	 * 删除
	 * 
	 * @param entity
	 *            实体对象
	 * @return 受到影响的行数
	 */
	@Override
	public int del(Entity entity) {
		checkNull(entity);

		return this.getDao().del(entity);
	}

	/**
	 * 对象是否存在
	 * 
	 * @param entity
	 *            对象
	 * @return 存在返回true
	 */
	public boolean exist(Entity entity) {
		return this.get(entity) != null;
	}
	
	private void checkNull(Entity entity) {
		if (entity == null) {
			throw new DurcException("对象不能为null");
		}
	}

	/**
	 * 新增（忽略空数据）
	 * @param entity
	 *            实体对象
	 * @return 受到影响的行数
	 */
	@Override
	public int saveNotNull(Entity entity) {
		checkNull(entity);
		
		return this.getDao().saveNotNull(entity);
	}

	/**
	 * 根据条件更新所有字段
	 * @param entity
	 *            实体对象
	 * @param query 条件对象
	 * @return 受到影响的行数
	 */
	@Override
	public int updateByExpression(Entity entity, ExpressionQuery query) {
		checkNull(entity);
		
		return this.getDao().updateByExpression(entity, query);
	}

	/**
	 * 根据主键更新不为null的字段
	 * @param entity
	 *            实体对象
	 * @return 受到影响的行数
	 */
	@Override
	public int updateNotNull(Entity entity) {
		checkNull(entity);
		
		return this.getDao().updateNotNull(entity);
	}

	/**
	 * 根据条件更新不为null的字段
	 * @param entity
	 *            实体对象
	 * @param query 条件对象
	 * @return 受到影响的行数
	 */
	@Override
	public int updateNotNullByExpression(Entity entity, ExpressionQuery query) {
		checkNull(entity);
		
		return this.getDao().updateNotNullByExpression(entity, query);
	}

	/**
	 * 根据条件删除
	 * @param query 条件对象
	 * @return 受到影响的行数
	 */
	@Override
	public int delByExpression(ExpressionQuery query) {
		return this.getDao().delByExpression(query);
	}
	
	
}
