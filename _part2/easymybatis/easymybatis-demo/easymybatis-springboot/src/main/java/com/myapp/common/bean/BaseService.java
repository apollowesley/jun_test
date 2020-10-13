package com.myapp.common.bean;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myapp.common.msg.Errors;
import com.myapp.common.util.ValidateUtil;

import net.oschina.durcframework.easymybatis.dao.CrudDao;
import net.oschina.durcframework.easymybatis.query.expression.Expressional;
import net.oschina.durcframework.easymybatis.support.CrudService;
import net.oschina.durcframework.easymybatis.support.Updater;
import net.oschina.durcframework.easymybatis.util.MyBeanUtil;

public class BaseService<Dao extends CrudDao<Entity>, Entity> 
extends CrudService<Dao, Entity> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 根据uid查找
	 * @param uid
	 * @return
	 */
	public Entity getByUid(String uid) {
		return this.getByProperty("uid", uid);
	}
	
	/**
	 * 属性拷贝,第一个参数中的属性值拷贝到第二个参数中<br>
		注意:当第一个参数中的属性有null值时,不会拷贝进去
	 * @param source
	 * @param target
	 */
	public void copyProperties(Object source,Entity target) {
		MyBeanUtil.copyProperties(source, target);
	}
	
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
				ValidateHolder validateHolder = validate(entity);
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
		ValidateHolder validateHolder = this.validate(entity);

		if (validateHolder.isSuccess()) {
			return super.save(entity);
		} else {
			throw validateHolder.getException();
		}
	}
	
	@Override
	public int saveIgnoreNull(Entity entity) {
		ValidateHolder validateHolder = this.validate(entity);

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
			validateHolder = this.validate(entity);
			if (!validateHolder.isSuccess()) {
				throw validateHolder.getException();
			}
		}
	}
	
	public ValidateHolder validate(Object entity) {
		return ValidateUtil.validate(entity);
	}
	
	public void check(Object entity) {
		ValidateHolder ret = this.validate(entity);
		if(!ret.isSuccess()) {
			throw ret.getException();
		}
	}

}
