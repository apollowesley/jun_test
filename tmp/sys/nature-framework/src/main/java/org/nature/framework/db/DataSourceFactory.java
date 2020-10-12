package org.nature.framework.db;

import javax.sql.DataSource;

import org.nature.framework.helper.ConfigHelper;

public class DataSourceFactory {
	public static DataSource getDateSource(){
		if ("c3p0".equals(ConfigHelper.getJdbcDataSource().toLowerCase())) {
			return  C3p0DataSourceManager.getDataSource();
		}else if ("druid".equals(ConfigHelper.getJdbcDataSource().toLowerCase())) {
			return DruidDataSourceManager.getDataSource();
		}
		return  DruidDataSourceManager.getDataSource();
	}
	
}
