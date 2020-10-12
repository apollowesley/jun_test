package com.myapp.common.bean;

import java.util.List;

import com.myapp.common.msg.Errors;
import com.myapp.common.util.ValidateUtil;

import net.oschina.durcframework.easymybatis.HasPk;
import net.oschina.durcframework.easymybatis.dao.CrudDao;
import net.oschina.durcframework.easymybatis.query.expression.Expressional;
import net.oschina.durcframework.easymybatis.support.CrudService;
import net.oschina.durcframework.easymybatis.support.Updater;

public class BaseService<Dao extends CrudDao<Entity>, Entity extends HasPk<?>> 
extends CrudService<Dao, Entity> {
	
	/**
	 * 添加记录时,检查是否已经添加,存在返回true
	 * @param column 
	 * @param value
	 * @return
	 */
	public void checkAddExsit(String column,Object value) {
		if(this.exsit(column, value)) {
			throw Errors.RECORD_EXSIT.getException();
		}
	}
	
	/**
	 * 根据字段和值查询记录是否存在
	 * @param column 数据库字段名
	 * @param value 值
	 * @return
	 */
	public boolean exsit(String column,Object value) {
		return this.getDao().getByProperty(column, value) != null;
	}

	/**
	 * 修改，校验成功后修改
	 * 
	 * @param entity
	 * @return
	 */
	public int update(Entity entity) {
		return this.doValidateUpdate(new Updater<Entity>() {
			@Override
			public int doUpdate(Entity entity) {
				return getDao().update(entity);
			}
		}, entity);
	}
	
	@Override
	public int updateIgnoreNull(Entity entity) {
		return this.doValidateUpdate(new Updater<Entity>() {
			@Override
			public int doUpdate(Entity entity) {
				return getDao().updateIgnoreNull(entity);
			}
		}, entity);
	}
	
	@Override
	public int updateIgnoreNullByExpression(Entity entity, final Expressional expressional) {
		return this.doValidateUpdate(new Updater<Entity>() {
			@Override
			public int doUpdate(Entity entity) {
				return getDao().updateIgnoreNullByExpression(entity, expressional);
			}
		}, entity);
	}
	
	/**
	 * 先检查后更新
	 * @param updater
	 * @param entity
	 * @return
	 */
	protected int doValidateUpdate(final Updater<Entity> updater,Entity entity) {
		return this.lockUpdate(new Updater<Entity>() {
			@Override
			public int doUpdate(Entity entity) {
				Entity record = getDao().get(entity);
				if (record == null) {
					throw Errors.NO_RECORD.getException();
				}
				ValidateHolder validateHolder = ValidateUtil.validate(entity);
				if (validateHolder.isSuccess()) {
					copyProperties(entity, record);
					return updater.doUpdate(record);
				} else {
					throw validateHolder.getException();
				}
			}
		
		}, entity);
	}

	/**
	 * 新增记录
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public int save(Entity entity) {
		ValidateHolder validateHolder = ValidateUtil.validate(entity);

		if (validateHolder.isSuccess()) {
			return super.save(entity);
		} else {
			throw validateHolder.getException();
		}
	}
	
	@Override
	public int saveIgnoreNull(Entity entity) {
		ValidateHolder validateHolder = ValidateUtil.validate(entity);

		if (validateHolder.isSuccess()) {
			return super.saveIgnoreNull(entity);
		} else {
			throw validateHolder.getException();
		}
	}
	
	@Override
	public int saveBatch(List<Entity> entitys) {
		this.validateMutil(entitys);
		return super.saveBatch(entitys);
	}
	
	@Override
	public int saveMulti(List<Entity> entitys) {
		this.validateMutil(entitys);
		return super.saveMulti(entitys);
	}
	
	private void validateMutil(List<Entity> entitys) {
		ValidateHolder validateHolder = null;
		for (Entity entity : entitys) {
			validateHolder = ValidateUtil.validate(entity);
			if (!validateHolder.isSuccess()) {
				throw validateHolder.getException();
			}
		}
	}

}
