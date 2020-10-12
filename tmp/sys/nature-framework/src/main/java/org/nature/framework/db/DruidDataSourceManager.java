package org.nature.framework.db;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.nature.framework.helper.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidDataSourceManager{
	private static Logger LOGGER = LoggerFactory.getLogger(DruidDataSourceManager.class);
	private static DruidDataSource dataSource;
	static{
		dataSource=new DruidDataSource();
		dataSource.setDriverClassName(ConfigHelper.getJdbcDriver());
		dataSource.setUrl(ConfigHelper.getJdbcUrl());
		dataSource.setUsername(ConfigHelper.getJdbcUserName());
		dataSource.setPassword(ConfigHelper.getJdbcPassWord());
		dataSource.setInitialSize(ConfigHelper.getJdbcInitialSize());
		dataSource.setMinIdle(ConfigHelper.getJdbcMinidle());
		dataSource.setMaxActive(ConfigHelper.getJdbcMaxAction());
		dataSource.setMaxWait(ConfigHelper.getJdbcMaxWait());
		dataSource.setTimeBetweenEvictionRunsMillis(ConfigHelper.getJdbcTimeBetweenEvictionRunsMillis());
		dataSource.setPoolPreparedStatements(ConfigHelper.getJdbcPoolPreparedStatements());
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(ConfigHelper.getJdbcMaxPoolPreparedStatementPerConnectionSize());
		try {//TODO 进一步优化 
			dataSource.setFilters(ConfigHelper.getJdbcFilters());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
	
	public static DataSource getDataSource() {
		return dataSource;
	}

	
}
