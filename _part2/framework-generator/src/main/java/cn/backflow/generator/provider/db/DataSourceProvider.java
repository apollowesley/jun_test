package cn.backflow.generator.provider.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import cn.backflow.generator.GeneratorProperties;

/**
 * 用于提供生成器的数据源
 * @author badqiu
 */
public class DataSourceProvider {
	private static Connection connection;
	private static DataSource dataSource;

	public synchronized static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = getDataSource().getConnection();
			}
			return connection;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void setDataSource(DataSource dataSource) {
		DataSourceProvider.dataSource = dataSource;
	}

	public synchronized static DataSource getDataSource() {
		if (dataSource == null) {
			dataSource = new DriverManagerDataSource(GeneratorProperties.getRequiredProperty("jdbc.url"),
					GeneratorProperties.getRequiredProperty("jdbc.username"),
					GeneratorProperties.getProperty("jdbc.password"),
					GeneratorProperties.getRequiredProperty("jdbc.driver"));
		}
		return dataSource;
	}

	public static class DriverManagerDataSource implements DataSource {
		private final String url;
		private final String username;
		private final String password;
		private final String driverClass;

		private static void loadJdbcDriver(String driver) {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("not found jdbc driver class:[" + driver + "]", e);
			}
		}

		public DriverManagerDataSource(String url, String username, String password, String driverClass) {
			this.url = url;
			this.username = username;
			this.password = password;
			this.driverClass = driverClass;
			loadJdbcDriver(driverClass);
		}

		@Override
		public Connection getConnection() throws SQLException {
			return DriverManager.getConnection(url, username, password);
		}

		@Override
		public Connection getConnection(String username, String password) throws SQLException {
			return DriverManager.getConnection(url, username, password);
		}

		@Override
		public PrintWriter getLogWriter() throws SQLException {
			throw new UnsupportedOperationException("getLogWriter");
		}

		@Override
		public int getLoginTimeout() throws SQLException {
			throw new UnsupportedOperationException("getLoginTimeout");
		}

		@Override
		public void setLogWriter(PrintWriter out) throws SQLException {
			throw new UnsupportedOperationException("setLogWriter");
		}

		@Override
		public void setLoginTimeout(int seconds) throws SQLException {
			throw new UnsupportedOperationException("setLoginTimeout");
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			if (iface == null)
				throw new IllegalArgumentException("Interface argument must not be null");
			if (!DataSource.class.equals(iface)) {
				throw new SQLException("DataSource of type [" + getClass().getName()
						+ "] can only be unwrapped as [javax.sql.DataSource], not as [" + iface.getName());
			}
			return (T) this;
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return DataSource.class.equals(iface);
		}

		@Override
		public Logger getParentLogger() throws SQLFeatureNotSupportedException {
			throw new SQLFeatureNotSupportedException("getParentLogger");
		}
	}
}