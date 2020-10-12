package org.nature.framework.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.nature.framework.bean.Page;
import org.nature.framework.bean.TableBean;
import org.nature.framework.cache.CacheManagerFactory;
import org.nature.framework.cache.NatureCacheManager;
import org.nature.framework.core.NatureMap;
import org.nature.framework.db.DataSourceFactory;
import org.nature.framework.db.DbRunner;
import org.nature.framework.db.DialectFactory;
import org.nature.framework.util.CastUtil;
import org.nature.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
	private static ThreadLocal<Connection> currentConnMap = new ThreadLocal<Connection>();
	static DataSource dataSource=DataSourceFactory.getDateSource();
	static NatureCacheManager natureCacheManager= CacheManagerFactory.getCacheManager();
	@SuppressWarnings("unchecked")
	public static <T>T byId(T t){
		NatureMap natureMap = (NatureMap) t;
		String sql = DialectFactory.getInstance().tranformByIdSql(natureMap);
		Connection conn = getConn();
		natureMap.putAll(DbRunner.queryUniqueRow(conn, sql));
		return (T) natureMap;
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T byId(T t,String cacheName){
		String sql = DialectFactory.getInstance().tranformByIdSql((NatureMap)t);
		Object object = natureCacheManager.get(cacheName, sql);   
		T tResult = null;
		if (object!=null) {
			tResult=(T) object;
		}else {
			tResult = byId(t);
			natureCacheManager.put(cacheName, sql,tResult);
		}
		return tResult;
	}
	
	public static NatureMap unique(String sql){
		System.out.println(sql);
		Connection conn = getConn();
		return DbRunner.queryUniqueRow(conn, sql);
	}
	public static NatureMap unique(String sql,String cacheName){
		Object object = natureCacheManager.get(cacheName, sql);
		NatureMap natureMap;
		if (object!=null) {
			natureMap = (NatureMap) object;
		}else {
			natureMap= unique(sql);
			natureCacheManager.put(cacheName, sql,natureMap);
		}
		return natureMap;
	}
	
	public static NatureMap unique(String sql,Object...params){
		System.out.println(sql);
		Connection conn = getConn();
		return DbRunner.queryUniqueRow(conn, sql,params);
	}
	
	public static NatureMap unique(String sql,String cacheName,Object...params){
		StringBuffer sb = new StringBuffer();
		if (params!=null)
			for (Object object : params) {
				sb.append(object.toString());
			}
		sb.append(sql);
		Object object = natureCacheManager.get(cacheName, sb.toString());
		NatureMap natureMap;
		if (object!=null) {
			natureMap = (NatureMap) object;
		}else {
			natureMap= unique(sql,params);
			natureCacheManager.put(cacheName, sb.toString(),natureMap);
		}
		return natureMap;
	}
	
	public static Object uniqueValue(String sql){
		System.out.println(sql);
		Connection conn = getConn();
		return DbRunner.queryUniqueValue(conn, sql);
	}
	
	public static Object uniqueValue(String sql,String cacheName){
		Object object = natureCacheManager.get(cacheName, sql);
		if (object==null) {
			object = uniqueValue(sql);
			natureCacheManager.put(cacheName, sql, object);
		}
		return object;
	}
	
	public static Object uniqueValue(String sql,Object...params){
		System.out.println(sql);
		Connection conn = getConn();
		return DbRunner.queryUniqueValue(conn, sql,params);
	}
	
	public static Object uniqueValue(String sql,String cacheName,Object...params){
		StringBuffer sb = new StringBuffer();
		if (params!=null)
			for (Object object : params) {
				sb.append(object.toString());
			}
		sb.append(sql);
		Object object = natureCacheManager.get(cacheName, sb.toString());
		if (object==null) {
			object = uniqueValue(sql,params);
			natureCacheManager.put(cacheName, sb.toString(), object);
		}
		return object;
	}
	
	public static Set<String> querySet(String sql){
		System.out.println(sql);
		Connection conn = getConn();
		Set<String> result = DbRunner.querySet(conn, sql);
		return result;
	}	
	
	@SuppressWarnings("unchecked")
	public static Set<String> querySet(String sql,String cacheName){
		Object object = natureCacheManager.get(cacheName, sql);
		Set<String> result;
		if (object!=null) {
			result = (Set<String>) object;
		}else {
			result = querySet(sql);
			natureCacheManager.put(cacheName, sql, result);
		}
		return result;
	}
	
	public static Set<String> querySet(String sql,Object...params){
		System.out.println(sql);
		Connection conn = getConn();
		Set<String> result = DbRunner.querySet(conn, sql,params);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static Set<String> querySet(String sql,String cacheName,Object...params){
		StringBuffer sb = new StringBuffer();
		if (params!=null)
			for (Object object : params) {
				sb.append(object.toString());
			}
		sb.append(sql);
		Object object = natureCacheManager.get(cacheName,sb.toString());
		Set<String> result;
		if (object!=null) {
			result = (Set<String>) object;
		}else {
			result = Collections.EMPTY_SET;
			result = querySet(sql, params);
			natureCacheManager.put(cacheName, sb.toString(), result);
		}
		return result;
	}
	
	
	public static int insert(NatureMap natureMap){
		Class<? extends NatureMap> cls = natureMap.getClass();
		TableBean tableBean = TableBeanHelper.getTableBean(cls);
		Object[] objs = DialectFactory.getInstance().tranformInsertSqlWithParams(natureMap,cls,tableBean);
		String sql = CastUtil.castString(objs[0]);
		Object[] params = (Object[]) objs[1];
		System.out.println(sql);
		Connection conn = getConn();
		int row = 0;
		if (StringUtil.isNotBank(sql)) {
			Map<String, Object> returnMap = DbRunner.excuteReturnKey(conn, sql,params);
			row = returnMap.values().size();
			if (row>0) {
				String primaryKey = tableBean.getPrimaryKey();
				natureMap.put(primaryKey, returnMap.get("GENERATED_KEY"));
			}
		}
		return row;
	}
	public static int insert(NatureMap natureMap,String cacheName){
		int row = insert(natureMap);
		natureCacheManager.flush(cacheName);
		return row;
	}
	
	public static int update(NatureMap natureMap){
		Object[] objs = DialectFactory.getInstance().tranformUpdateSqlWithParams(natureMap);
		String sql = CastUtil.castString(objs[0]);
		Object[] params = (Object[]) objs[1];
		System.out.println(sql);
		Connection conn = getConn();
		int row = 0;
		if (StringUtil.isNotBank(sql)) {
			row = DbRunner.excute(conn, sql,params);
		}
		return row;
	}
	
	public static int update(NatureMap natureMap,String cacheName){
		int row = update(natureMap);
		natureCacheManager.flush(cacheName);
		return row;
	}
	
	public static int delete(NatureMap natureMap){
		String sql = DialectFactory.getInstance().tranformDeleteSql(natureMap);
		System.out.println(sql);
		Connection conn = getConn();
		int row = 0;
		if (StringUtil.isNotBank(sql)) {
			row = DbRunner.excute(conn, sql);
		}
		return row;
	}
	
	public static int delete(NatureMap natureMap,String cacheName){
		int row = delete(natureMap);
		natureCacheManager.flush(cacheName);
		return row;
	}
	
	public static Page query(Page page,String sql) {
		String pageSql = DialectFactory.getInstance().tranformPageSql(page,sql);
		System.out.println(pageSql);
		Connection conn = getConn();
		List<NatureMap> list = DbRunner.query(conn, pageSql);
		page.setDataList(list);
		String cntSql = DialectFactory.getInstance().tranformCntSql(sql);
		System.out.println(cntSql);
		long rowCnt = queryCnt(cntSql);
		page.setRowCnt((int) rowCnt);
		page.pagination();
		return page;
	}
	
	public static Page query(Page page,String sql,String cacheName){
		String pageSql = DialectFactory.getInstance().tranformPageSql(page,sql);
		Object object = natureCacheManager.get(cacheName, pageSql);
		Page resultPage;
		if (object!=null) {
			resultPage = (Page) object;
		}else {
			resultPage = query(page, sql);
			natureCacheManager.put(cacheName, pageSql, resultPage);
		}
		return resultPage;
	}
	
	public static long queryCnt(String sql){
		Connection conn = getConn();
		Object cnt = DbRunner.queryUniqueValue(conn, sql);
		return CastUtil.castLong(cnt);
	}
	
	public static long queryCnt(String sql,String cacheName){
		Object object = natureCacheManager.get(cacheName, sql);
		long cnt = 0;
		if (object!=null) {
			cnt = CastUtil.castLong(object);
		}else {
			cnt = queryCnt(sql);
			natureCacheManager.put(cacheName, sql, cnt);
		}
		return cnt;
	}
	
	public static int excute( String sql) {
		System.out.println(sql);
		Connection conn = getConn();
		int row = DbRunner.excute(conn, sql);
		return row;
	}
	
	public static int excute( String sql,String cacheName) {
		int row = excute(sql);
		natureCacheManager.flush(cacheName);
		return row;
	}

	public static int excute( String sql, Object... params) {
		Connection conn = getConn();
		int row = DbRunner.excute(conn, sql, params);
		return row;
	}
	
	public static int excute( String sql,String cacheName, Object... params) {
		int row = excute(sql,params);
		natureCacheManager.flush(cacheName);
		return row;
	}

	public static Map<String, Object> excuteReturnKey( String sql) {
		Connection conn = getConn();
		Map<String, Object> returnKey = DbRunner.excuteReturnKey(conn, sql);
		return returnKey;
	}
	
	public static Map<String, Object> excuteReturnKey( String sql,String cacheName) {
		Map<String, Object> returnKey = excuteReturnKey(sql);
		natureCacheManager.flush(cacheName);
		return returnKey;
	}

	public static Map<String, Object> excuteReturnKey(String sql, Object... params) {
		Connection conn = getConn();
		Map<String, Object> returnKey = DbRunner.excuteReturnKey(conn, sql, params);
		return returnKey;
	}
	
	public static Map<String, Object> excuteReturnKey(String sql,String cacheName ,Object... params) {
		Map<String, Object> returnKey = excuteReturnKey(sql, params);
		natureCacheManager.flush(cacheName);
		return returnKey;
	}

	public static List<NatureMap> query(String sql) {
		Connection conn = getConn();
		System.out.println(sql);
		return  DbRunner.query(conn, sql);
	}
	
	@SuppressWarnings("unchecked")
	public static List<NatureMap> query(String sql,String cacheName) {
		Object object = natureCacheManager.get(cacheName, sql);
		List<NatureMap> list;
		if (object!=null) {
			list = (List<NatureMap>) object;
		}else {
			list = query(sql);
			natureCacheManager.put(cacheName, sql, list);
		}
		return list;
	}
	
	public static List<NatureMap> query(String sql, Object... params) {
		Connection conn = getConn();
		System.out.println(sql);
		List<NatureMap> list = DbRunner.query(conn, sql, params);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static List<NatureMap> query(String sql,String cacheName, Object... params) {
		StringBuffer sb = new StringBuffer();
		if (params!=null)
			for (Object object : params) {
				sb.append(object.toString());
			}
		sb.append(sql);
		Object object = natureCacheManager.get(cacheName, sb.toString());
		List<NatureMap> list;
		if (object!=null) {
			list = (List<NatureMap>) object;
		}else {
			list = query(sql,params);
			natureCacheManager.put(cacheName, sb.toString(), list);
		}
		return list;
	}

	public static Connection getConn() {
		Connection conn = currentConnMap.get();
		if (conn == null) {
			try {
				conn = dataSource.getConnection();
				currentConnMap.set(conn);
			} catch (SQLException e) {
				LOGGER.error("get conn error ", e);
				throw new RuntimeException(e);
			}
		}
		return conn;
	}

	public static void closeConn(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			LOGGER.error("close conn error ", e);
			throw new RuntimeException(e);
		}
		currentConnMap.remove();
	}
	
}
