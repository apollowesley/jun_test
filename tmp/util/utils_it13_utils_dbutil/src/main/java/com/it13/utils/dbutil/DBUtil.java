package com.it13.utils.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

/**
 * apache DbUtils封装类
 * @author yzChen  cyzshenzhen@163.com
 * @date 2013-9-8 上午10:43:37 
 *
 */
public class DBUtil {
	private static final QueryRunner runner = new QueryRunner();

	/**
	 * 打开数据库连接
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @return    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-8 上午10:57:22
	 */
	public static Connection openConn(String driver, String url, String username, String password) {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库连接
	 * @param conn    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-8 上午10:50:55
	 */
	public static void closeConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询（返回ListMap结果）
	 * @param conn
	 * @param sql
	 * @param params
	 * @return    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-8 上午10:51:33
	 */
	public static List<Map<String, Object>> queryListMap(Connection conn, String sql, Object... params) {
		List<Map<String, Object>> result = null;
		try {
			result = runner.query(conn, sql, new MapListHandler(), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询（返回Bean结果）
	 * @param cls
	 * @param map
	 * @param conn
	 * @param sql
	 * @param params
	 * @return    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-8 上午10:51:39
	 */
	public static <T> T queryBean(Class<T> cls, Map<String, String> map, Connection conn, String sql, Object... params) {
		T result = null;
		try {
			if (MapUtils.isNotEmpty(map)) {
				result = runner.query(conn, sql,
						new BeanHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
			} else {
				result = runner.query(conn, sql, new BeanHandler<T>(cls), params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询（返回ListBean结果）
	 * @param cls
	 * @param map
	 * @param conn
	 * @param sql
	 * @param params
	 * @return    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-8 上午10:51:45
	 */
	public static <T> List<T> queryListBean(Class<T> cls, Map<String, String> map, Connection conn, String sql,
			Object... params) {
		List<T> result = null;
		try {
			if (MapUtils.isNotEmpty(map)) {
				result = runner.query(conn, sql, new BeanListHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(
						map))), params);
			} else {
				result = runner.query(conn, sql, new BeanListHandler<T>(cls), params);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询指定列名的值（多条数据）
	 * @param column
	 * @param conn
	 * @param sql
	 * @param params
	 * @return    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-8 上午10:52:05
	 */
	public static <T> List<T> queryListColumn(String column, Connection conn, String sql, Object... params) {
		List<T> result = null;
		try {
			result = runner.query(conn, sql, new ColumnListHandler<T>(column), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询指定列名对应的记录映射
	 * @param column
	 * @param conn
	 * @param sql
	 * @param params
	 * @return    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-8 上午10:52:11
	 */
	public static <T> Map<T, Map<String, Object>> queryMapKey(String column, Connection conn, String sql,
			Object... params) {
		Map<T, Map<String, Object>> result = null;
		try {
			result = runner.query(conn, sql, new KeyedHandler<T>(column), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更新（包括UPDATE、INSERT、DELETE，返回受影响的行数）
	 * @param conn
	 * @param sql
	 * @param params
	 * @return    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-8 上午10:52:16
	 */
	public static int update(Connection conn, String sql, Object... params) {
		int result = 0;
		try {
			result = runner.update(conn, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
