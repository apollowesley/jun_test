package cn.coder.jdbc.core;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JdbcDataSource implements javax.sql.DataSource {
	private static final Logger logger = LoggerFactory.getLogger(JdbcDataSource.class);
	
	private ConnectionPool pool;
	private PrintWriter printWriter;
	private DataSourceConfig config;

	public synchronized void createPool(DataSourceConfig dbConfig) throws SQLException {
		this.config = dbConfig;
		if (pool == null) {
			this.pool = new ConnectionPool(dbConfig);
		}
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return this.printWriter;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.printWriter = out;
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		config.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return config.getLoginTimeout();
	}

	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return pool.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getConnection();
	}

	public DataSourceConfig getDataSourceConfig() {
		return config;
	}

	public void releaseConnection(Connection con) {
		try {
			if (con != null && !con.isClosed()) {
				pool.releaseConnection(con);
			}
		} catch (SQLException e) {
			logger.error("Release connection faild", e);
		}
	}

	public void close() {
		pool.clear();
		pool = null;
	}

}
