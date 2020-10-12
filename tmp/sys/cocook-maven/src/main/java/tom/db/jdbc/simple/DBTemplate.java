package tom.db.jdbc.simple;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tom.cocook.jdbc.DBPool;
import tom.db.jdbc.DBOperations;
import tom.kit.clazz.ReflectUtil;

public class DBTemplate extends DBOperations {
	
	/**
	 * get(1) 单行单列查询方法
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	private Object getObject(Connection conn,String sql, Object... params) throws SQLException {
		try{
			return DBUtil.getObject(DBUtil.query(conn, sql, params));
		}finally{
			DBUtil.close(conn);
		}
	} 

	@Override
	public <T> T queryForObject(String sql, Class<T> clazz, Object... params) throws SQLException {
		Object obj = getObject(DBUtil.getConnection(), sql, params);
		return obj == null ? clazz.cast(null) : clazz.cast(ReflectUtil.covert(clazz, obj));
	}
	
	@Override
	public <T> T queryForObject(Connection conn, String sql, Class<T> clazz, Object... params) throws SQLException {
		Object obj = DBUtil.getObject(DBUtil.query(conn, sql, params));
		return obj == null ? clazz.cast(null) : clazz.cast(ReflectUtil.covert(clazz, obj));
	}

	@Override
	public Long queryForLong(String sql, Object... params) throws SQLException {
		return queryForObject(sql, Long.class, params);
	}

	@Override
	public Integer queryForInt(String sql, Object... params) throws SQLException {
		return queryForObject(sql, Integer.class, params);
	}

	@Override
	public String queryForString(String sql, Object... params) throws SQLException {
		return queryForObject(sql, String.class, params);
	}
	
	/**
	 * 单列 多行 数据集合
	 * @param <T>
	 * @param _conn
	 * @param sql
	 * @param clazz
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	private static <T> List<T> getListT(Connection conn, String sql, Class<T> clazz, Object... params) throws SQLException{
		try{
			return DBUtil.getListT(DBUtil.query(conn, sql, params), clazz) ;
		}finally{
			DBUtil.close(conn);
		}
	}
	
	@Override
	public <T> List<T> queryForList(Connection conn, String sql, Class<T> clazz, Object... params) throws SQLException {
		return DBUtil.getListT(DBUtil.query(conn, sql, params), clazz);
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> clazz, Object... params) throws SQLException {
		return getListT(DBUtil.getConnection(), sql, clazz, params);
	}

	/**
	 * 多行多列 key -> map 数据集合
	 * @param conn
	 * @param sql
	 * @param clazz
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String, Object>> getList(Connection conn, String sql, @SuppressWarnings("rawtypes") Class<? extends Map> clazz, Object... params) throws SQLException{
		try{
			return DBUtil.getList(DBUtil.query(conn, sql, params), clazz) ;
		}finally{
			DBUtil.close(conn);
		}
	}
	
	
	@Override
	public List<Map<String, Object>> queryForList(Connection conn, String sql, Object... params) throws SQLException {
		return DBUtil.getList(DBUtil.query(conn, sql, params), HashMap.class) ;
	}
	
	@Override
	public List<Map<String, Object>> queryForList(String sql, Object... params) throws SQLException {
		return getList(DBUtil.getConnection(), sql,  HashMap.class, params);
	}
	
	@Override
	public List<Map<String, Object>> queryForLinkedMapList(String sql, Object... params) throws SQLException {
		return getList(DBUtil.getConnection(), sql, LinkedHashMap.class, params); 
	};

	@Override
	public Map<String, Object> queryForMap(Connection conn, String sql, Object... params) throws SQLException {
		List<Map<String, Object>> list = queryForList(conn, sql, params);
		if (list.size() == 0) {
			return Collections.emptyMap();
		}
		return list.get(0);
	}
	@Override
	public Map<String, Object> queryForMap(String sql, Object... params) throws SQLException {
		List<Map<String, Object>> list = queryForList(sql, params);
		if (list.size() == 0) {
			return Collections.emptyMap();
		}
		return list.get(0);
	}

	@Override
	public Map<String, Object> queryForLinkedMap(String sql) throws SQLException {
		return queryForLinkedMap(sql, new Object[] {});
	}

	@Override
	public Map<String, Object> queryForLinkedMap(String sql, Object... params) throws SQLException {
		List<Map<String, Object>> list = queryForLinkedMapList(sql, params);
		if (list.size() == 0) {
			return Collections.emptyMap();
		}
		return list.get(0);
	}

	@Override
	public int update(String sql, Object... params) throws SQLException {
		Connection conn = DBUtil.getConnection();
		try{
			return update(conn, sql, params);
		}finally{
			DBUtil.close(conn);
		}
	}
	
	@Override
	public int update(Connection conn, String sql, Object... params) throws SQLException {
		if(DBPool.show_sql) DBPool.logger.info(sql +" "+ Arrays.asList(params));
		PreparedStatement pst = conn.prepareStatement(sql);
		DBUtil.bindInputStatement(pst,params);
		return pst.executeUpdate();
	}
	
	@Override
	public int insert(String sql, Object... params) throws SQLException {
		Connection conn = DBUtil.getConnection();
		try{
			return insert(conn, sql, params);
		}finally{
			DBUtil.close(conn);
		}
	}
	
	@Override
	public int insert(Connection conn, String sql, Object... params) throws SQLException {
		if(DBPool.show_sql) DBPool.logger.info(sql +" "+ Arrays.asList(params));
		PreparedStatement pst = conn.prepareStatement(sql, 1);
		DBUtil.bindInputStatement(pst,params);
		int r = pst.executeUpdate();
		try{
			ResultSet rs = pst.getGeneratedKeys();
			rs.next();
			r = rs.getInt(1);
			return r;
		}catch(SQLException e){
			DBPool.logger.error("insert:", e);
			return r;
		}finally{
			DBUtil.close(pst);
		}
	}
	
	@Override
	public int execute(String sql, Object... params) throws SQLException {
		return update(sql, params);
	}
	
	@Override
	public int[] batchUpdate(String sql, List<Object[]> list) throws SQLException {
		if(DBPool.show_sql) DBPool.logger.info(sql +" "+ list);
		Connection conn = DBUtil.getConnection();
		PreparedStatement pst = null;
		try{
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sql);
			for(Object[] obj: list){
				DBUtil.bindInputStatement(pst, obj);
				pst.addBatch();
			}
			int i[] = pst.executeBatch();
			conn.commit();
			return i;
		}finally{
			DBUtil.close(pst);
			DBUtil.close(conn);
		}
	}

	@Override
	public int[] batchUpdate(String sql, Object... obj) throws SQLException {
		if(DBPool.show_sql) DBPool.logger.info(sql +" "+ Arrays.asList(obj));
		Connection conn = DBUtil.getConnection();
		PreparedStatement pst = null;
		try{
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sql);
			for(Object o: obj){
				DBUtil.bindInputStatement(pst, o);
				pst.addBatch();
			}
			int i[] = pst.executeBatch();
			conn.commit();
			return i;
		}finally{
			DBUtil.close(pst);
			DBUtil.close(conn);
		}
	}

	@Override
	public int[] batchUpdate(List<String> list) throws SQLException {
		Connection conn = DBUtil.getConnection();
		Statement stat  = null;
		try{
			conn.setAutoCommit(false);
			stat = conn.createStatement();
			for(String sql: list){
				stat.addBatch(sql);
			}
			int i[] = stat.executeBatch();
			conn.commit();
			return i;
		}finally{
			DBUtil.close(stat);
			DBUtil.close(conn);
		}
	}
	
}
