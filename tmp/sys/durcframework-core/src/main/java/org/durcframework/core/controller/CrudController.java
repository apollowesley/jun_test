package org.durcframework.core.controller;

import org.durcframework.core.DurcException;
import org.durcframework.core.Edit;
import org.durcframework.core.MessageResult;
import org.durcframework.core.ValidateHolder;
import org.durcframework.core.util.MyBeanUtil;
import org.durcframework.core.util.ValidateUtil;

/**
 * 增删改查的Controller
 * 
 * @author tanghc 2013年11月14日
 * 
 * @param <Entity>
 *            实体类
 * @param <Service>
 *            增删改查的Service
 */
public abstract class CrudController<Entity, Service extends Edit<Entity>>
		extends SearchController<Entity, Service> {

	/**
	 * 新增记录
	 * 
	 * @param entity
	 * @return
	 */
	public MessageResult save(Entity entity) {
		ValidateHolder validateHolder = ValidateUtil.validate(entity);
		
		if (validateHolder.isSuccess()) {
			this.getService().save(entity);
			return success();
		}
		
		return error("添加失败", validateHolder.buildValidateErrors());
	}

	/**
	 * 修改记录
	 * 
	 * @param entity
	 * @return
	 */
	public MessageResult update(Entity entity) {
		Entity e = this.get(entity);
		if (e == null) {
			throw new DurcException("修改失败-该记录不存在");
		}

		ValidateHolder validateHolder = ValidateUtil.validate(entity);
		
		if (validateHolder.isSuccess()) {
			MyBeanUtil.copyProperties(entity, e);
			getService().update(e);
			return success();
		}
		
		return error("修改失败", validateHolder.buildValidateErrors());
	}

	/**
	 * 删除记录
	 * 
	 * @param entity
	 * @return
	 */
	public MessageResult delete(Entity entity) {
		getService().del(entity);
		return success();
	}	

	
}
