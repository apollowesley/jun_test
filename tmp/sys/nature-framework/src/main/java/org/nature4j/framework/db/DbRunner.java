package org.nature4j.framework.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.nature4j.framework.core.NatureMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(DbRunner.class);
	private static final QueryRunner QUERY_RUNNER = new QueryRunner();

	public static int excute(Connection conn, String sql) {
		int rows = 0;
		try {
			rows = QUERY_RUNNER.update(conn, sql);
		} catch (SQLException e) {
			LOGGER.error("excute sql [" + sql + "] error", e);
			throw new RuntimeException(e);
		}
		return rows;
	}

	public static int excute(Connection conn, String sql, Object... params) {
		int rows = 0;
		try {
			rows = QUERY_RUNNER.update(conn, sql, params);
		} catch (SQLException e) {
			LOGGER.error("excute sql[" + sql + "] params["+params+"] error", e);
			throw new RuntimeException(e);
		}
		return rows;
	}
	
	public static Map<String, Object> excuteReturnKey(Connection conn, String sql) {
		Map<String, Object>  result = null;
		try {
			result = QUERY_RUNNER.insert(conn, sql, new MapHandler());
			if (result==null) {
				result = Collections.emptyMap();
			}
		} catch (SQLException e) {
			LOGGER.error("excuteReturnKey sql[" + sql + "] error", e);
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static int batch(Connection conn, String sql, Object[][] params) {
		int insertBatch = 0;
		try {
			insertBatch = QUERY_RUNNER.batch(conn , sql, params)[0];
		} catch (SQLException e) {
			LOGGER.error("excuteReturnKey sql[" + sql + "] error", e);
			throw new RuntimeException(e);
		}
		return insertBatch;
	}
	
	public static Map<String, Object> excuteReturnKey(Connection conn, String sql, Object... params) {
		Map<String, Object>  result = null;
		try {
			result = QUERY_RUNNER.insert(conn, sql, new MapHandler(),params);
			if (result==null) {
				result = Collections.emptyMap();
			}
		} catch (SQLException e) {
			LOGGER.error("excuteReturnKey sql[" + sql + "] params["+params+"] error", e);
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static List<NatureMap> query(Connection conn, String sql) {
		List<NatureMap> list;
		try {
			list = QUERY_RUNNER.query(conn, sql, new ArrayNatureMapHandler());
		} catch (SQLException e) {
			LOGGER.error("query sql[" + sql + "] error", e);
			throw new RuntimeException(e);
		}
		return list;
	}
	
	public static Object queryUniqueValue(Connection conn, String sql) {
		Object object = null;
		try {
			object = QUERY_RUNNER.query(conn,sql, new ScalarHandler<Object>());
		} catch (SQLException e) {
			LOGGER.error("queryUnique sql[" + sql + "] error", e);
			throw new RuntimeException(e);
		}
		return object;
	}
	
	public static Object queryUniqueValue(Connection conn, String sql,Object...params) {
		Object object = null;
		try {
			object = QUERY_RUNNER.query(conn,sql, new ScalarHandler<Object>(),params);
		} catch (SQLException e) {
			LOGGER.error("queryUnique sql[" + sql + "] error", e);
			throw new RuntimeException(e);
		}
		return object;
	}
	
	public static NatureMap queryUniqueRow(Connection conn, String sql) {
		NatureMap natureMap;
		try {
			natureMap = QUERY_RUNNER.query(conn,sql, new UniqueRowHandler());
		} catch (SQLException e) {
			LOGGER.error("queryUnique sql[" + sql + "] error", e);
			throw new RuntimeException(e);
		}
		return natureMap;
	}
	
	public static NatureMap queryUniqueRow(Connection conn, String sql,Object ...params) {
		NatureMap natureMap;
		try {
			natureMap = QUERY_RUNNER.query(conn,sql, new UniqueRowHandler(),params);
		} catch (SQLException e) {
			LOGGER.error("queryUnique sql[" + sql + "] error", e);
			throw new RuntimeException(e);
		}
		return natureMap;
	}
	
	static class UniqueRowHandler implements ResultSetHandler<NatureMap>{
		public NatureMap handle(ResultSet rs) throws SQLException {
			NatureMap natureMap = new NatureMap();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()){
				for (int i = 1; i <= columnCount; i++) {
					String columnLabel = rsmd.getColumnLabel(i);
					Object value = rs.getObject(columnLabel);
					if (value instanceof Clob) {
						Clob clob = (Clob) value;
						Reader reader = clob.getCharacterStream();
						BufferedReader br = new BufferedReader(reader); 
						StringBuffer sb = new StringBuffer();
						String x = null;
						try {
							while ((x=br.readLine())!=null) {
								sb.append(x);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						value = sb.toString();
					}
					natureMap.put(columnLabel, value);
				}
				break;
			}
			
			return natureMap;
		}
	}
	
	static class ArrayNatureMapHandler implements ResultSetHandler<List<NatureMap>>{
		List<NatureMap>  result = new ArrayList<NatureMap>();
		public List<NatureMap> handle(ResultSet rs) throws SQLException {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()){
				NatureMap natureMap = new NatureMap();
				for (int i = 1; i <= columnCount; i++) {
					String columnLabel = rsmd.getColumnLabel(i);
					Object value = rs.getObject(columnLabel);
					if (value instanceof Clob) {
						Clob clob = (Clob) value;
						Reader reader = clob.getCharacterStream();
						BufferedReader br = new BufferedReader(reader); 
						StringBuffer sb = new StringBuffer();
						String x = null;
						try {
							while ((x=br.readLine())!=null) {
								sb.append(x);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						value = sb.toString();
					}
					natureMap.put(columnLabel.toLowerCase(), value);
				}
				result.add(natureMap);
			}
			return result;
		}
		
	}
	
	public static List<NatureMap> query(Connection conn, String sql, Object... params) {
		List<NatureMap>  result ;
		try {
			result = QUERY_RUNNER.query(conn, sql, new ArrayNatureMapHandler(), params);
		} catch (SQLException e) {
			LOGGER.error("query sql[" + sql + "] params["+params+"] error", e);
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static Set<String> querySet(Connection conn, String sql) {
		Set<String>  result ;
		try {
			result = QUERY_RUNNER.query(conn, sql, new SetHandler());
		} catch (SQLException e) {
			LOGGER.error("query sql[" + sql + "] error", e);
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static Set<String> querySet(Connection conn, String sql, Object... params) {
		Set<String>  result ;
		try {
			result = QUERY_RUNNER.query(conn, sql, new SetHandler(),params);
		} catch (SQLException e) {
			LOGGER.error("query sql[" + sql + "] error", e);
			throw new RuntimeException(e);
		}
		return result;
	}
	
	static class SetHandler implements ResultSetHandler<Set<String>>{
		Set<String> set = new HashSet<String>();
		public Set<String> handle(ResultSet rs) throws SQLException {
			while(rs.next()){
				String value = rs.getString(1);
				set.add(value);
			}
			return set;
		}
	}
	
	
}