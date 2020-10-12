package com.freedom.mysql.myrwsplit.datasource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.SqlCommandType;

import com.alibaba.druid.pool.DruidDataSource;
import com.freedom.mysql.myrwsplit.helper.LoggerHelper;
import com.freedom.mysql.myrwsplit.helper.ThreadLocalHelper;
import com.freedom.mysql.myrwsplit.helper.UrlHelper;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
//TODO : 这里考虑复制出一个properties出来,防止有覆盖情况
//看过Druid的方式，是不会有此问题,别的数据连接池未测试，所以复制出1个最保险！
@SuppressWarnings("serial")
public class MasterSlaveDataSource extends DruidDataSource {
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(MasterSlaveDataSource.class);
	private static AtomicInteger COUNTER = new AtomicInteger(0);
	private DataSource masterDataSource = null;
	private DataSource[] slaveDataSources = null;

	public MasterSlaveDataSource(Properties properties) {
		try {
			ArrayList<String> urls = UrlHelper.handle(properties.getProperty("url"));
			slaveDataSources = new DataSource[urls.size() - 1];
			int index = 0;
			for (String url : urls) {
				properties.setProperty("url", url);
				if (0 == index) {
					properties.put("defaultReadOnly", "false");
					masterDataSource = DruidDataSourceFactory.createDataSource(properties);
				} else {
					properties.put("defaultReadOnly", "true");
					slaveDataSources[index - 1] = DruidDataSourceFactory.createDataSource(properties);
				}
				index++;
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
			System.exit(-1);
		}

	}

	@Override
	public DruidPooledConnection getConnection() throws SQLException {
		long begin = System.currentTimeMillis();
		try {
			// 1)get all variables
			boolean autoCommit = ThreadLocalHelper.AutoCommitThreadLocal.get();
			String sql = ThreadLocalHelper.BoundSqlThreadLocal.get();
			SqlCommandType type = ThreadLocalHelper.SqlCommandTypeThreadLocal.get();
			// 2)use them
			if (false == autoCommit) {
				//
				//LOGGER.debug("get Connection by MasterSlaveDataSource--->use masterDataSource");
				return (DruidPooledConnection) masterDataSource.getConnection();
			} else {
				//
				if (SqlCommandType.SELECT != type) {
					//
					LOGGER.debug("get Connection by MasterSlaveDataSource--->use masterDataSource");
					return (DruidPooledConnection) masterDataSource.getConnection();
				} else {
					//
					if (-1 != sql.indexOf("/*FORCE_MASTER*/")) {
						LOGGER.debug("get Connection by MasterSlaveDataSource--->use masterDataSource");
						return (DruidPooledConnection) masterDataSource.getConnection();
					} else {
						LOGGER.debug("get Connection by MasterSlaveDataSource--->use slaveDataSources");
						//
						int slaveIndex = COUNTER.getAndIncrement();
						if (slaveIndex < 0) {
							slaveIndex = 0;
						}
						//
						slaveIndex = slaveIndex % slaveDataSources.length;
						// ok,let us fetch the connection
						for (int i = 0; i < slaveDataSources.length; i++) {
							DruidPooledConnection connection = (DruidPooledConnection) slaveDataSources[(slaveIndex++)
									% slaveDataSources.length].getConnection();
							if (null != connection) {
								return connection;
							}
						}
						throw new SQLException(
								"fail to get slave mysql connection,maybe all slave machines DOWN,check it now!");
					}
				}
			}
		} finally {
			long end = System.currentTimeMillis();
			long cost = end - begin;
			LOGGER.debug("get Druid Connection cost ---> " + cost + " ms");
			ThreadLocalHelper.FetchConnectionTimeThreadLocal.set(cost);
		}

	}

	// private static int random(int min, int max) {
	// Random random = new Random();
	// int selectedIndex = random.nextInt(max) % (max - min + 1) + min;
	// LOGGER.info("" + selectedIndex);
	// return selectedIndex;
	// }

	public static void main(String[] args) {
		for (int index = 0; index < 100; index++) {
			// LOGGER.info("" + random(0, 2));
		}
	}
}
