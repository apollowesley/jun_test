package cn.coder.jdbc.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.jdbc.util.JdbcUtils;

/**
 * 连接池核心类
 * 
 * @author YYDF
 *
 */
public final class ConnectionPool {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
	
	private static final Object lockObj = new Object();
	private final LinkedBlockingQueue<Connection> idle;
	private final DataSourceConfig config;
	private final AtomicInteger waitNum = new AtomicInteger(0);// 统计当前等待连接数

	public ConnectionPool(DataSourceConfig db) throws SQLException {
		this.config = db;
		idle = new LinkedBlockingQueue<>(db.getInitialSize());
		init();
	}

	private void init() throws SQLException {
		JdbcUtils.registerDriver(config.getDriverClassName());
		Connection conn;
		for (int i = 0; i < config.getInitialSize(); i++) {
			conn = createConnection();
			idle.add(conn);
			logger.debug("[{}]Inited {}", config.getSourceName(), conn.hashCode());
		}
	}

	public Connection getConnection() throws SQLException {
		try {
			return borrowConnection();
		} catch (SQLException | InterruptedException e) {
			logger.error("Borrow connection faild", e);
			throw new SQLException("Borrow connection faild");
		}
	}

	/**
	 * 获取一个数据库连接<br>
	 * 如果链接一直没有归还，会造成死锁
	 * 
	 * @return 可用连接
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	private Connection borrowConnection() throws SQLException, InterruptedException {
		// 从队列获取，队列为空返回null
		Connection conn = idle.poll();
		if (conn != null) {
			if (JdbcUtils.isValid(conn)) {
				logger.debug("[{}]Borrowed {}", config.getSourceName(), conn.hashCode());
				return conn;
			}
			JdbcUtils.closeConnection(conn);
			return createConnection();
		}
		// 如果连接池为空，则等待
		while (idle.isEmpty()) {
			synchronized (lockObj) {
				waitNum.incrementAndGet();
				lockObj.wait();
				waitNum.decrementAndGet();
			}
		}
		return borrowConnection();
	}

	private synchronized Connection createConnection() throws SQLException {
		return DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
	}

	public void releaseConnection(Connection con) {
		if (idle.offer(con)) {
			JdbcUtils.clearWarnings(con);
			logger.debug("[{}]Released {}, Waiting {}", config.getSourceName(), con.hashCode(), waitNum.get());
			if (waitNum.get() > 0) {
				synchronized (lockObj) {
					lockObj.notify();
				}
			}
		} else {
			JdbcUtils.closeConnection(con);
			logger.warn("[{}]Released {} faild and force close", config.getSourceName(), con.hashCode());
		}
	}

	public void clear() {
		logger.debug("[{}]Pool start clear", config.getSourceName());
		for (Connection con : idle) {
			JdbcUtils.closeConnection(con);
		}
		idle.clear();
		JdbcUtils.deregisterDriver();
	}

}
