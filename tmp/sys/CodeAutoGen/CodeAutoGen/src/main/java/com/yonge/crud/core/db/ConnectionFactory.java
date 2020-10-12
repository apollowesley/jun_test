package com.yonge.crud.core.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ConnectionFactory {

	private static ConnectionFactory instance = new ConnectionFactory();

	private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

	public static ConnectionFactory getInstance() {
		return instance;
	}

	private ConnectionFactory() {
		super();
	}

	public Connection getConnection(String driverClass, String url,
			String username, String password) throws SQLException {
		Driver driver = getDriver(driverClass);

		Properties props = new Properties();

		props.setProperty("user", username);

		props.setProperty("password", password);

		// 只有oracle才需要这个参数
		if (StringUtils.equals(driverClass, ORACLE_DRIVER)) {
			props.setProperty("remarksReporting", "true");
		}

		Connection conn = driver.connect(url, props);

		if (conn == null) {
			throw new SQLException("连接获取失败");
		}

		return conn;
	}

	private Driver getDriver(String driverClass) {
		Driver driver = null;
		try {
			Class<?> clazz = Class.forName(driverClass);
			driver = (Driver) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("类加载器加载driver失败", e);
		}

		return driver;
	}
}
