package com.mini.jdbc;

/**
 * BaseEntity接口
 * @author sxjun
 * 2016-2-1
 */
public interface Model {
	/**
	 * 获取表名
	 * @return
	 */
	String getTableName();
	
	/**
	 * 获取主键名
	 * @return
	 */
	String[] getPrimaryKeys();
	
	/**
	 * 获取是否持久态数据
	 * @return
	 */
	boolean isPersistent();

	/**
	 * 设置是否持久态数据
	 * @param isPersistent
	 */
	void setPersistent(boolean isPersistent);
}
