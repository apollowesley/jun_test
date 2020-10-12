package site.yaotang.xgen.orm.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import site.yaotang.xgen.orm.tools.Constant;
import site.yaotang.xgen.orm.utils.LogUtil;
import site.yaotang.xgen.orm.utils.ReflectUtil;

/**
 * 数据库操作助手
 * @author hyt
 * @date 2017年12月31日
 */
@Slf4j
public class DBHelper {
	private static DataSource dataSource;
	private static final Map<String, String> DBMAP = Constant.DBMAP;

	public static Connection getConnection() {
		try {
			Connection connection = getDatasourceConnection();
			if (connection == null) {
				connection = getJdbcConnection();
			}
			return connection;
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException(e);
		}
	}

	private static Connection getJdbcConnection() {
		try {
			Class.forName(DBMAP.get("driver"));
			Connection connection = DriverManager.getConnection(DBMAP.get("url"), DBMAP.get("username"), DBMAP.get("password"));
			return connection;
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException(e);
		}
	}

	private static Connection getDatasourceConnection() {
		try {
			if (dataSource == null) {
				String className = DBMAP.get("dataSource");
				dataSource = (DataSource) ReflectUtil.newInstance(className);
				ReflectUtil.setValue(className, dataSource, "driverClassName", DBMAP.get("dataSource.driverClassName"));
				ReflectUtil.setValue(className, dataSource, "url", DBMAP.get("dataSource.url"));
				ReflectUtil.setValue(className, dataSource, "username", DBMAP.get("dataSource.username"));
				ReflectUtil.setValue(className, dataSource, "password", DBMAP.get("dataSource.password"));
			}
			return dataSource.getConnection();
		} catch (Exception e) {
			LogUtil.error(log, e);
			return null;
		}
	}

	public static void closeConnection(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				// connection.close();// XXX 如果关闭会造成ResultSet无法操作
			}
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException(e);
		}
	}

	public static void executeWrite(String sql) {
		LogUtil.debug(log, sql);
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			statement.execute(sql);
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException(e);
		} finally {
			closeConnection(connection);
		}
	}

	public static ResultSet executeRead(String sql) {
		LogUtil.debug(log, sql);
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			return statement.executeQuery(sql);
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException(e);
		} finally {
			closeConnection(connection);
		}
	}
}
