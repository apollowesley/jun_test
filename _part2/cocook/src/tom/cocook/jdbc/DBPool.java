package tom.cocook.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import tom.cocook.log.Logger;
import tom.cocook.log.LoggerFactory;
import tom.kit.Encrypt;
import tom.kit.clazz.ReflectUtil;

public final class DBPool {
	private DataSource dataSource;
	public String driverClass;
	private String username;
	private String password;
	private final static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
	private boolean show_sql = false;

	private File dbFile;
	private int flag = 0;
	private static Logger logger = LoggerFactory.getLog(DBPool.class);

	public DBPool(File dbFile) {
		this.dbFile = dbFile;
		init();
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void init(Properties prop) {
		this.flag += 1;
		logger.info("init connection " + this.flag + " times");
		try {
			this.driverClass = prop.getProperty("driverClassName");
			this.show_sql = Boolean.parseBoolean(prop.getProperty("show_sql"));
			String encrypt = prop.getProperty("encrypt");
			prop.remove("show_sql");
			prop.remove("encrypt");

			this.username = prop.getProperty("username");
			this.password = prop.getProperty("password");
			if (encrypt != null && !encrypt.isEmpty()) { // 如果是加密状态需要解密
				prop.setProperty("username", Encrypt.decryptString(2, getUsername()));
				prop.setProperty("password", Encrypt.decryptString(2, password));
			}
			/* 使用class加载器 代替forName(forName会初始化一次) */
			this.dataSource = (DataSource) Thread.currentThread().getContextClassLoader().loadClass(prop.getProperty("dataSource")).newInstance();
			// this.dataSource = (DataSource)
			// Class.forName(prop.getProperty("dataSource")).newInstance();
			prop.remove("dataSource");

			/* dataSource set方法注入 */
			ReflectUtil.populate(dataSource, prop);
			Connection conn = getConnection();
			DatabaseMetaData mdm = conn.getMetaData();

			logger.info("connect database success: " + mdm.getDatabaseProductName() + mdm.getDatabaseProductVersion());
			closeConnection(conn);
		} catch (Exception e) {
			logger.info("init connection failed in " + this.flag + " times" + e.toString());
		}
	}

	public void init() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(dbFile));
		} catch (IOException e) {
			logger.info("read dbconfig error", e);
		}
		init(prop);

	}

	/**
	 * 断开连接池
	 */
	public final void closeDataSource() {
		try {
			dataSource.getClass().getMethod("close").invoke(dataSource);
		} catch (Exception e) {
		}
	}

	private final Connection proxyConn(Connection conn) {
		return (show_sql && !Proxy.isProxyClass(conn.getClass())) ? new _DebugConnection().bindConnection(conn) : conn;
	}

	/**
	 * 默认初始化两次
	 */
	public Connection getConnection() throws SQLException {
		try {
			return proxyConn(dataSource.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("init connection failed, init again..." + e.toString());
			init();
			try {
				return proxyConn(dataSource.getConnection());
			} catch (SQLException ee) {
				logger.info("init connection faild, check DBconfig!" + ee.toString());
				throw ee;
			}
		}
	}

	/**
	 * servlet 都会使用线程池, 线程池中线程重复利用 
	 * request对应的ThreadLocaL可能获取同一个Connetion,此时connection 可能已经被关闭
	 * 解决方案: 每次获取时都新建connetion, 关闭时从 ThreadLocal 中获取关闭,保证了connetion的有效性
	 * @return
	 */
	@Deprecated
	public final Connection getThreadConnection() {
		Connection conn = conns.get();
		try {
			if (conn == null || conn.isClosed()) {
				conn = getConnection();
			}
		} catch (SQLException e) {
		}
		conns.set(conn);
		return proxyConn(conn);
	}

	/**
	 * 关闭连接线程局部变量连接
	 */
	@Deprecated
	public final void closeThreadConnection() {
		Connection conn = conns.get();
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
		}
		conns.remove();
	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 */
	public final void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
		}
	}

	/**
	 * 用于跟踪执行的SQL语句
	 * 
	 * @author Winter Lau
	 */
	static class _DebugConnection implements InvocationHandler {
		private static Logger logger = LoggerFactory.getLog(DBPool.class);
		private Connection conn = null;

		/**
		 * Returns the conn.
		 * 
		 * @return Connection
		 */
		public Connection bindConnection(Connection conn) {
			this.conn = conn;
			return (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces(), this);
		}

		@Override
		public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
			String methodName = m.getName();
			if ("prepareStatement".equals(methodName) || "prepareCall".equals(methodName)) {
				logger.info("[SQL]>> " + args[0]);
			}
			try {
				return m.invoke(conn, args);
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}
	}
}
