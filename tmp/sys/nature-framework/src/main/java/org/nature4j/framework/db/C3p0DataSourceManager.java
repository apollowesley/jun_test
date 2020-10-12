package org.nature4j.framework.db;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.nature4j.framework.helper.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0DataSourceManager{
	private static Logger LOGGER = LoggerFactory.getLogger(C3p0DataSourceManager.class);
	private static String[] dbs = ConfigHelper.getDb();
	private static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
	static{
		for (int i = 0; i < dbs.length; i++) {
			ComboPooledDataSource dataSource=new ComboPooledDataSource();
			try {
				dataSource.setDriverClass(ConfigHelper.getJdbcDriver(dbs[i]));
			} catch (PropertyVetoException e) {
				LOGGER.error("driver class set error ",e);
				throw new RuntimeException();
			}
			dataSource.setJdbcUrl(ConfigHelper.getJdbcUrl(dbs[i]));
			dataSource.setUser(ConfigHelper.getJdbcUserName(dbs[i]));
			dataSource.setPassword(ConfigHelper.getJdbcPassWord(dbs[i]));
			dataSource.setInitialPoolSize(ConfigHelper.getJdbcInitialSize(dbs[i]));
			dataSource.setMinPoolSize(ConfigHelper.getJdbcMinidle(dbs[i]));
			dataSource.setMaxPoolSize(ConfigHelper.getJdbcMaxAction(dbs[i]));
			dataSource.setMaxStatementsPerConnection(ConfigHelper.getJdbcMaxPoolPreparedStatementPerConnectionSize(dbs[i]));
			dataSourceMap.put(dbs[i], dataSource);
		}
	}
	public static Map<String, DataSource> getDataSourceMap() {
		return dataSourceMap;
	}
	public static DataSource getDataSource(String dbName) {
		return dataSourceMap.get(dbName);
	}
	
}
