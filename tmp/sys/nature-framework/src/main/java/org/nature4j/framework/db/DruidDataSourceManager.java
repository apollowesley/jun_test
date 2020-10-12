package org.nature4j.framework.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.nature4j.framework.helper.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidDataSourceManager{
	private static Logger LOGGER = LoggerFactory.getLogger(DruidDataSourceManager.class);
	private static String[] dbs = ConfigHelper.getDb();
	private static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
	static{
		for (int i = 0; i < dbs.length; i++) {
			DruidDataSource dataSource=new DruidDataSource();
			dataSource.setDriverClassName(ConfigHelper.getJdbcDriver(dbs[i]));
			dataSource.setUrl(ConfigHelper.getJdbcUrl(dbs[i]));
			dataSource.setUsername(ConfigHelper.getJdbcUserName(dbs[i]));
			dataSource.setPassword(ConfigHelper.getJdbcPassWord(dbs[i]));
			dataSource.setInitialSize(ConfigHelper.getJdbcInitialSize(dbs[i]));
			dataSource.setMinIdle(ConfigHelper.getJdbcMinidle(dbs[i])); 
			dataSource.setMaxActive(ConfigHelper.getJdbcMaxAction(dbs[i]));
			dataSource.setMaxWait(ConfigHelper.getJdbcMaxWait(dbs[i]));
			dataSource.setTimeBetweenEvictionRunsMillis(ConfigHelper.getJdbcTimeBetweenEvictionRunsMillis(dbs[i]));
			dataSource.setTestWhileIdle(false);
			if (!DbIdentifyer.isSqlite(ConfigHelper.getJdbcDriver(dbs[i]))) {
				dataSource.setPoolPreparedStatements(ConfigHelper.getJdbcPoolPreparedStatements(dbs[i]));
				dataSource.setMaxPoolPreparedStatementPerConnectionSize(ConfigHelper.getJdbcMaxPoolPreparedStatementPerConnectionSize(dbs[i]));
			}
			try {
				dataSource.setFilters(ConfigHelper.getJdbcFilters(dbs[i]));
			} catch (SQLException e) {
				LOGGER.error("druid filter error ",e);
			}
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
