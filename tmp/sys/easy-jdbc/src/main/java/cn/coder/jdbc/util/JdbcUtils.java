package cn.coder.jdbc.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.jdbc.core.BeanMapping;

public final class JdbcUtils {
	private static final Logger logger = LoggerFactory.getLogger(JdbcUtils.class);

	public static void bindArgs(PreparedStatement stmt, Object[] objs) throws SQLException {
		if (objs != null && objs.length > 0) {
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] instanceof Collection)
					throw new SQLException("Un support collection paremeter in args");
				stmt.setObject(i + 1, objs[i]);
			}
		}
		if (logger.isDebugEnabled())
			logger.debug(stmt.toString().replace("com.mysql.jdbc.JDBC4PreparedStatement@", ""));
	}

	public static <T> BeanMapping buildFiledMappings(Class<T> target, ResultSetMetaData metaData) throws SQLException {
		Set<Field> fields = FieldUtils.getDeclaredFields(target);
		BeanMapping mappings = new BeanMapping();
		if (fields == null || fields.isEmpty()) {
			return mappings;
		}
		try {
			String fieldName, label, column;
			for (Field field : fields) {
				fieldName = field.getName();
				for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
					label = metaData.getColumnLabel(i);
					column = metaData.getColumnName(i);
					if (fieldName.equals(label) || fieldName.equals(column)
							|| fieldName.equals(FieldUtils.convert(label))
							|| fieldName.equals(FieldUtils.convert(column))) {
						mappings.put(label, field);
						break;
					}
				}
			}
		} catch (SQLException e) {
			throw new SQLException("获取对象和数据库的映射失败", e);
		}
		return mappings;
	}

	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("Close statement faild", e);
			}
		}
	}

	public static void registerDriver(String driverClassName) {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			logger.error("Register driver faild", e);
		}
	}

	public static void deregisterDriver() {
		try {
			Class<?> clazz = Class.forName("com.mysql.jdbc.AbandonedConnectionCleanupThread");
			if (clazz != null) {
				clazz.getMethod("shutdown").invoke(clazz);
			}
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				Driver driver = (Driver) drivers.nextElement();
				DriverManager.deregisterDriver(driver);
			}
		} catch (Exception ex) {
			logger.error("Deregister driver faild", ex);
		}
	}

	public static boolean isValid(Connection conn) {
		if (conn != null) {
			try {
				return conn.isValid(0);
			} catch (SQLException e) {
				logger.error("Is valid error", e);
				return false;
			}
		}
		return false;
	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.clearWarnings();
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("Close connection faild", e);
		}
	}

	public static void clearWarnings(Connection con) {
		if (con != null) {
			try {
				con.clearWarnings();
			} catch (SQLException e) {
				logger.error("Clear warnings faild", e);
			}
		}
	}
}
