package org.nature.framework.db;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.nature.framework.helper.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0DataSourceManager{
	private static Logger LOGGER = LoggerFactory.getLogger(C3p0DataSourceManager.class);
	private static ComboPooledDataSource dataSource=new ComboPooledDataSource();
	
	public static DataSource getDataSource() {
		try {
			dataSource.setDriverClass(ConfigHelper.getJdbcDriver());
			dataSource.setJdbcUrl(ConfigHelper.getJdbcUrl());
			dataSource.setUser(ConfigHelper.getJdbcUserName());
			dataSource.setPassword(ConfigHelper.getJdbcPassWord());
			dataSource.setInitialPoolSize(ConfigHelper.getJdbcInitialSize());
			dataSource.setMinPoolSize(ConfigHelper.getJdbcMinidle());
			dataSource.setMaxPoolSize(ConfigHelper.getJdbcMaxAction());
			dataSource.setMaxStatementsPerConnection(ConfigHelper.getJdbcMaxPoolPreparedStatementPerConnectionSize());
		} catch (PropertyVetoException e) {
			LOGGER.error("jdbc config error",e);
			throw new RuntimeException(e);
		}
		return dataSource;
	}

	
}
