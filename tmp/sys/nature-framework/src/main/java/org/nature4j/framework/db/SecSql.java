package org.nature4j.framework.db;

import java.util.ArrayList;
import java.util.List;

import org.nature4j.framework.util.CastUtil;
import org.nature4j.framework.util.StringUtil;

public class SecSql  implements SecSqlIntferface{
	
	private List<Object> params = new ArrayList<Object>();
	private StringBuffer sb = new StringBuffer();
	
	public static SecSql use(){
		return new SecSql();
	}
	
	/**
	 * append 直接拼接。不进行任何判断
	 * @param value
	 * @return
	 */
	public SecSql a(String sqlfrag) {
		sb.append(' ').append(sqlfrag);
		return this;
	}
	
	/**
	 * append 直接拼接。不进行任何判断
	 * @param condition
	 * @param value
	 * @return
	 */
	public SecSql a(String condition, Object value) {
		params.add(value);
		sb.append(' ').append ( condition);
		return this;
	}
	
	/**
	 * notBank 非空和非空格+ //(最常用)
	 * @param condition
	 * @param value
	 * @return
	 */
	public SecSql nb(String condition,Object value){
		if(StringUtil.isNotBank(CastUtil.castString(value))){
			params.add(value);
			sb.append(' ').append ( condition);
		}
		return this;
	}
	
	/**
	 * notNull 非空
	 * @param condition
	 * @param value
	 * @return
	 */
	public SecSql nn(String condition,Object value){
		if(null != value){
			params.add(value);
			sb.append(' ').append ( condition);
		}
		return this;
	}
	/**
	 * notEmpty 非空并且非''
	 * @param condition
	 * @param value
	 * @return
	 */
	public SecSql ne(String condition,Object value){
		if(StringUtil.isNotEmpty(CastUtil.castString(value))){
			params.add(value);
		    sb.append(' ').append ( condition);
		}
		return this;
	}

	public Object[] getParams() {
		return params.toArray();
	}
	
	public String getSql(){
		return sb.toString();
	}

	public String getParamsStr() {
		return params.toString();
	}
	
	
}
