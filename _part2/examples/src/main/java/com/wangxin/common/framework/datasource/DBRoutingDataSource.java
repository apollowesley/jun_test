package com.wangxin.common.framework.datasource;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DBRoutingDataSource extends AbstractRoutingDataSource {
    
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getCurrentDBName();
    }
 
    @Override
    public void setTargetDataSources(Map targetDataSources) {
        super.setTargetDataSources(targetDataSources);
    }
     
    @Override
    public Object unwrap(Class iface) throws SQLException {
        return null;
    }
 
    @Override
    public boolean isWrapperFor(Class iface) throws SQLException {
        return false;
    }
}