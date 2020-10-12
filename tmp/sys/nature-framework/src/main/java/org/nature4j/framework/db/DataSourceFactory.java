package org.nature4j.framework.db;

import java.util.Map;

import javax.sql.DataSource;

import org.nature4j.framework.helper.ConfigHelper;

public class DataSourceFactory {
	public static Map<String,DataSource>  getDataSourceMap(){
		if ("c3p0".equals(ConfigHelper.getJdbcDataSource().toLowerCase())) {
			return  C3p0DataSourceManager.getDataSourceMap();
		}else if ("druid".equals(ConfigHelper.getJdbcDataSource().toLowerCase())) {
			return DruidDataSourceManager.getDataSourceMap();
		}
		return  DruidDataSourceManager.getDataSourceMap();
	}
	
}
