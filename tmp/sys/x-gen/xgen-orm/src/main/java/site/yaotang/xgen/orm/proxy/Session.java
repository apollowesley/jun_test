package site.yaotang.xgen.orm.proxy;

import java.io.Serializable;

import site.yaotang.xgen.orm.sqltools.DetachCriteria;

/**
 * 自定义Session查询单个实体
 * @author hyt
 * @date 2018年1月1日
 */
public interface Session {

	public Object load(Class<?> clazz, Serializable id);

	public Object load(Class<?> clazz, DetachCriteria dc);

	public Object load(Class<?> clazz, String condition);
}