package com.pyy.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author PYY
 *
 */
public class DataContextHolder {
	
	private static Logger logger = LoggerFactory.getLogger(DataContextHolder.class);
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
	public static void setDbType(String dbType) {
		logger.info("set context type {}", dbType);
        contextHolder.set(dbType);  
	 }  
	
	 public static String getDbType() {
		 String dbType = contextHolder.get();
		 logger.info("getting database type is {}", dbType);
	     return  dbType;  
	 }  
	
	 public static void clearDbType() {  
		 logger.info("remove data from context holder");
	     contextHolder.remove();  
	 } 
}
