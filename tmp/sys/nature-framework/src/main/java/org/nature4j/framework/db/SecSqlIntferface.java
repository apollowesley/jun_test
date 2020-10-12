package org.nature4j.framework.db;

public interface SecSqlIntferface {
	/**
	 * append 直接拼接。不进行任何判断
	 * @param condition
	 * @param value
	 * @return
	 */
	public SecSqlIntferface a(String condition, Object value);
	
	/**
	 * notBank 非空和非空格+ //(最常用)
	 * @param condition
	 * @param value
	 * @return
	 */
	public SecSqlIntferface nb(String condition,Object value);
	
	/**
	 * notNull 非空
	 * @param condition
	 * @param value
	 * @return
	 */
	public SecSqlIntferface nn(String condition,Object value);
	/**
	 * notEmpty 非空并且非''
	 * @param condition
	 * @param value
	 * @return
	 */
	public SecSqlIntferface ne(String condition,Object value);

	public Object[] getParams();
	public String getSql();
	public String getParamsStr();
}
