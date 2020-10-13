package com.bodsite.common.datasource;

/**
 * @Description:本地线程数据源
 * @author bod
 * @date
 *
 */
public class DataSourceHandler {
	public enum DYNAMIC_DATA_SOURCE{
		MASTER,//主库(写)
		SLAVE;//从库(读)
	}
	
	private static final ThreadLocal<DYNAMIC_DATA_SOURCE> dataSourceThreadLocal = new ThreadLocal<DYNAMIC_DATA_SOURCE>();
	
	protected static void set(DYNAMIC_DATA_SOURCE dynamic_data_source){
		dataSourceThreadLocal.set(dynamic_data_source);
	}
	
	/**
	 * 设置为主库
	 * @author bod
	 */
	protected static void setMaster(){
		dataSourceThreadLocal.set(DYNAMIC_DATA_SOURCE.MASTER);
	}
	
	/**
	 * 设置为读库
	 * @author bod
	 */
	protected static void setSlave(){
		dataSourceThreadLocal.set(DYNAMIC_DATA_SOURCE.SLAVE);
	}

	/**
	 * 判断是否为主库
	 * @author bod
	 */
	protected static boolean isMaster(){
		return isThis(DYNAMIC_DATA_SOURCE.MASTER);
	}
	
	/**
	 * 判断是否为从
	 * @author bod
	 */
	protected static boolean isSlave(){
		return isThis(DYNAMIC_DATA_SOURCE.SLAVE);
	}

	protected static boolean isThis(DYNAMIC_DATA_SOURCE dynamic_data_source){
		if(dataSourceThreadLocal.get()==null){
			return false;
		}
		return dynamic_data_source == dataSourceThreadLocal.get();
	}
	
	protected static void DataSoruceClean(){
		dataSourceThreadLocal.remove();
	}
	
	
}
