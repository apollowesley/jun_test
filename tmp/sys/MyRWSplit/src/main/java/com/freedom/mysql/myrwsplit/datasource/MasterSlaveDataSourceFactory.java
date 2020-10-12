package com.freedom.mysql.myrwsplit.datasource;

import java.util.Properties;
import javax.sql.DataSource;

import com.freedom.mysql.myrwsplit.helper.LoggerHelper;

import org.apache.ibatis.datasource.DataSourceFactory;

public class MasterSlaveDataSourceFactory implements DataSourceFactory {
	@SuppressWarnings("unused")
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(MasterSlaveDataSourceFactory.class);
	protected Properties properties;

	@Override
	public void setProperties(Properties props) {
		this.properties = props;
	}

	@Override
	public DataSource getDataSource() {
		try {
			return new MasterSlaveDataSource(properties);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("init data source error", e);
		}
	}

	

}
