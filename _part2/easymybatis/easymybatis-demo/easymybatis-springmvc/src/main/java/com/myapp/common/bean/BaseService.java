package com.myapp.common.bean;

import com.myapp.common.msg.Errors;

import net.oschina.durcframework.easymybatis.dao.CrudDao;
import net.oschina.durcframework.easymybatis.support.CrudService;

public class BaseService<Dao extends CrudDao<Entity>, Entity> 
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


}
