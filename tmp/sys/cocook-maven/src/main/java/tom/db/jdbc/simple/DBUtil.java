package tom.db.jdbc.simple;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import tom.cocook.jdbc.DBPool;
import tom.db.jdbc.DBException;
import tom.db.jdbc.DBOperations;
import tom.db.jdbc.Query;
import tom.kit.clazz.ReflectUtil;

public class DBUtil {

	public static final DBOperations TEMPLATE = new DBTemplate();
	private static DBPool dbPool ;

	public static void init(File dbFile) {
		dbPool = new DBPool(dbFile);
	}
	public static void init(Properties prop) {
		dbPool = new DBPool();
		dbPool.init(prop);
	}

	public static Connection getConnection() {
		try {
			return dbPool.getConnection();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static int exec(Connection _conn, String sql, Object... params) {
		try {
			return TEMPLATE.update(_conn, sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static int exec(String sql, Object... params) {
		try {
			return TEMPLATE.update(sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	
	public static int exec(Query query) {
		return exec(query.getSql(), query.getParams());
	}

	/**
	 * 返回自增ID,如果没有自增主键,请使用update
	 */
	public static int insert(String sql, Object... params){
		try {
			return TEMPLATE.insert(sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static int insert(Connection _conn, String sql, Object... params) {
		try {
			return TEMPLATE.insert(_conn, sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static int[] batchUpdate(List<String> sqls) {
		try {
			return TEMPLATE.batchUpdate(sqls);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	public static int[] batchUpdate(String sql, List<Object[]> list){
		try {
			return TEMPLATE.batchUpdate(sql, list);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static int[] batchUpdate(String sql, Object... obj) {
		try {
			return TEMPLATE.batchUpdate(sql, obj);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	

	@SuppressWarnings("unchecked")
	public static int insertDB(String tableName ,Object obj) {
		Map<String, Object> map = null;
		if (obj instanceof Map<?, ?>) {
			map = (Map<String, Object>) obj;
		} else {
			map = ReflectUtil.beanToMap(obj);
		}
		Query query = getInsertBody(map, tableName);
		return insert(query.getSql(), query.getParams());
	}

	@SuppressWarnings("unchecked")
	public static int updateDB(String tableName, Object obj,  String where) {
		Map<String, Object> map = null;
		if (obj instanceof Map<?, ?>) {
			map = (Map<String, Object>) obj;
		} else {
			map = ReflectUtil.beanToMap(obj);
		}
		Query query = getUpdateBody(map, tableName, where);
		return exec(query.getSql(), query.getParams());
	}

	/*--------------------- 以上为update方法--------------------------------------------------*/

	public static Integer getInt(String sql, Object... params) {
		try {
			Integer i =  TEMPLATE.queryForInt(sql, params);
			return i == null? 0 :i;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	
	public static Long getLong(String sql, Object... params) {
		try {
			Long i =  TEMPLATE.queryForLong(sql, params);
			return i == null? 0 :i;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static String getString(String sql, Object... params) {
		try {
			return TEMPLATE.queryForString(sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static <T> T getObject(String sql, Class<T> clazz, Object... params) {
		try {
			return TEMPLATE.queryForObject(sql, clazz, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static <T> T getObject(Connection conn, String sql, Class<T> clazz, Object... params) {
		try {
			return TEMPLATE.queryForObject(conn, sql, clazz, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	

	public static <T> List<T> getList(String sql, Class<T> clazz, Object... params) {
		try {
			return TEMPLATE.queryForList(sql, clazz, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static <T> List<T> getList(Connection conn, String sql, Class<T> clazz, Object... params) {
		try {
			return TEMPLATE.queryForList(conn, sql, clazz, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static List<Map<String, Object>> getList(Connection conn, String sql, Object... params) {
		try {
			return TEMPLATE.queryForList(conn, sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static List<Map<String, Object>> getList(String sql, Object... params) {
		try {
			return TEMPLATE.queryForList(sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static List<Map<String, Object>> getList(Query query) {
		return getList(query.getSql(), query.getParams());
	}

	public static List<Map<String, Object>> getLinkedMapList(String sql, Object... params) {
		try {
			return TEMPLATE.queryForLinkedMapList(sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static Map<String, Object> getMap(String sql, Object... params) {
		try {
			return TEMPLATE.queryForMap(sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static Map<String, Object> getLinkedMap(String sql, Object... params) {
		try {
			return TEMPLATE.queryForLinkedMap(sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public static <T> T getBean(String sql, Class<T> clazz, Object... params) {
		Map<String, Object> map = getMap(sql, params);
		if(map.isEmpty()) return null;
		return ReflectUtil.mapToBean(map, clazz);
	}

	/**
	 * sql输入参数绑定
	 */
	protected static void bindInputStatement(PreparedStatement statement, int index, Object obj) throws SQLException {
		/* 只需要setObject(), 在jdbc驱动中已经做了instanceof, 自己做常用的e二个判断 */
		if (obj instanceof String) {
			statement.setString(index, (String) obj);
		} else if (obj instanceof Integer) {
			statement.setInt(index, ((Integer) obj));
		} else if(obj instanceof Date){
			statement.setTimestamp(index, new java.sql.Timestamp(((Date)obj).getTime()));
		}else{
			statement.setObject(index, obj);
		}
	}

	public static void bindInputStatement(PreparedStatement statement, Object... obj) throws SQLException {
		if (obj != null) {
			for (int i = 0; i < obj.length; i++) {
				bindInputStatement(statement, i + 1, obj[i]);
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static List<Map<String, Object>> getList(ResultSet res, Class<? extends Map> clazz) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			ResultSetMetaData rsmd = res.getMetaData();
			while (res.next()) {
				Map<String, Object> map = clazz.newInstance();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					map.put(rsmd.getColumnLabel(i).toLowerCase(), res.getObject(i));
				}
				list.add(map);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			close(res);
		}
		return list;
	}

	protected static <T> List<T> getListT(ResultSet res, Class<T> clazz) throws SQLException {
		List<T> list = new ArrayList<T>();
		try {
			while (res.next()) {
				Object obj = res.getObject(1);
				list.add(obj == null ? clazz.cast(null) : clazz.cast(ReflectUtil.covert(clazz, obj)));
			}
		} finally {
			close(res);
		}
		return list;
	}

	protected static Object getObject(ResultSet res) throws SQLException {
		try {
			if (res.next()) {
				return res.getObject(1);
			}
		} finally {
			close(res);
		}
		return null;
	}
	
	
	protected static ResultSet query(Connection _conn, String sql, Object... params) throws SQLException {
		if(DBPool.show_sql) DBPool.logger.info(sql +" "+ Arrays.asList(params));
		PreparedStatement pst = _conn.prepareStatement(sql);
		bindInputStatement(pst, params);
		return pst.executeQuery();
	}

	public static void close(Connection _conn) {
		dbPool.closeConnection(_conn);
	}

	public static void close(ResultSet _rs) {
		if (_rs != null)
			try {
				_rs.close();
			} catch (SQLException e) {
			}
	}
	
	public static void close(Statement stat) {
		if (stat != null)
			try {
				stat.close();
			} catch (SQLException e) {
			}
	}
	
	public static void closeDataSource() {
		if(dbPool!=null){
			dbPool.closeDataSource();
		}
	}

	private static Query getInsertBody(Map<String, Object> map, String _tableName) {
		Query insert = new DBQuery(new StringBuffer("INSERT INTO " + _tableName + "("));
		String field = "";
		String value = "VALUES( ";
		for (String key: map.keySet()) {
			Object obj = map.get(key);
			if (field.length() > 0) {
				field += ", ";
				value += ", ";
			}
			field += key;
			value += "?";
			insert.setParams(obj);
		}
		field += ")";
		value += ")";
		insert.add(field + value);
		return insert;
	}

	private static Query getUpdateBody(Map<String, Object> _map, String _tableName, String _where) {
		Query update = new DBQuery(new StringBuffer("UPDATE " + _tableName + " SET "));
		String field = "";
		for (String key: _map.keySet()) {
			Object obj = _map.get(key);
			if (field.length() > 0) {
				field += ", ";
			}
			field += key + " =?";
			update.setParams(obj);
		}
		update.add(field + " WHERE " + _where);
		return update;
	}

}
