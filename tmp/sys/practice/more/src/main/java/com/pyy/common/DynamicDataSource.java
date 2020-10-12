package com.pyy.common;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	private String defaultKey;
	
    @Override  
    protected Object determineCurrentLookupKey() {  
    	String dbType = DataContextHolder.getDbType();
    	if(StringUtils.isBlank(dbType)) {
    		logger.info("current data source key is {}", dbType);
    		dbType = this.defaultKey;
    	}
        return dbType;  
    }
    
    @Override
	protected DataSource determineTargetDataSource() {
		return super.determineTargetDataSource();
	}

	public String getDefaultKey() {
		return defaultKey;
	}

	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
	}
    
    
}
