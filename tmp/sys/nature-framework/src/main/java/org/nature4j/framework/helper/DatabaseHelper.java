package org.nature4j.framework.helper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.nature4j.framework.bean.Page;
import org.nature4j.framework.bean.TableBean;
import org.nature4j.framework.cache.CacheManager;
import org.nature4j.framework.cache.NatureCacheManager;
import org.nature4j.framework.core.NatureMap;
import org.nature4j.framework.db.*;
import org.nature4j.framework.util.CastUtil;
import org.nature4j.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
	public String dbName;
	public String jdbcDriver;
	static NatureCacheManager natureCacheManager= CacheManager.getCacheManager();
	static Map<String, DatabaseHelper> dataBaseHelperMap = new HashMap<String, DatabaseHelper>();
	
	public static DatabaseHelper use(String dbName) {
		if (StringUtil.isNotBank(dbName)&&!dbName.endsWith(".")) {dbName+=".";}
		DatabaseHelper databaseHelper = dataBaseHelperMap.get(dbName);
		if (databaseHelper==null) {
			databaseHelper = new DatabaseHelper();
			dataBaseHelperMap.put(dbName, databaseHelper);
			String driver = ConfigHelper.getJdbcDriver(dbName);
			if (StringUtil.isBank(driver)) {
				LOGGER.error("db named ["+dbName+"] is not exist");
				throw new RuntimeException("db named ["+dbName+"] is not exist");
			}
			databaseHelper.jdbcDriver = driver;
			databaseHelper.dbName = dbName;
		}
		return databaseHelper;
	}
	public static DatabaseHelper use(){
		return use(ConfigHelper.getDb()[0]);
	}
	
	public static DatabaseHelper use(Db db){
		if (StringUtil.isBank(db.name())) {
			return use();
		}
		return use(db.name());
	}
	
	@SuppressWarnings("unchecked")
	public <T>T byId(T t){
		Connection conn = ConnectionManager.getConn(dbName);
		NatureMap natureMap = (NatureMap) t;
		String[] strs = DialectFactory.getInstance(jdbcDriver).tranformByIdSqlWithParam(natureMap);
		LOGGER.info("执行语句："+strs[0]);
		LOGGER.info("执行参数："+strs[1]);
		natureMap.putAll(DbRunner.queryUniqueRow(conn, strs[0], strs[1]));
		return (T) natureMap;
	}
	
	@SuppressWarnings("unchecked")
	public <T>T byId(T t,String cacheName){
		String[] strs = DialectFactory.getInstance(jdbcDriver).tranformByIdSqlWithParam((NatureMap)t);
		Object object = natureCacheManager.get(cacheName, dbName+strs[0]+strs[1]);   
		T tResult = null;
		if (object!=null) {
			tResult=(T) object;
		}else {
			tResult = byId(t);
			natureCacheManager.put(cacheName, dbName+strs[0]+strs[1],tResult);
		}
		return tResult;
	}
	
	public NatureMap unique(String sql){
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		return DbRunner.queryUniqueRow(conn, sql);
	}
	public NatureMap unique(String sql,String cacheName){
		Object object = natureCacheManager.get(cacheName, dbName+sql);
		NatureMap natureMap;
		if (object!=null) {
			natureMap = (NatureMap) object;
		}else {
			natureMap= unique(sql);
			natureCacheManager.put(cacheName, dbName+sql,natureMap);
		}
		return natureMap;
	}
	
	public NatureMap unique(String sql,Object...params){
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		return DbRunner.queryUniqueRow(conn, sql,params);
	}
	
	public NatureMap unique(String sql,String cacheName,Object...params){
		StringBuffer sb = new StringBuffer();
		if (params!=null)
			for (Object object : params) {
				sb.append(object.toString());
			}
		sb.append(sql);
		Object object = natureCacheManager.get(cacheName, dbName+sb.toString());
		NatureMap natureMap;
		if (object!=null) {
			natureMap = (NatureMap) object;
		}else {
			natureMap= unique(sql,params);
			natureCacheManager.put(cacheName, dbName+sb.toString(),natureMap);
		}
		return natureMap;
	}
	
	public NatureMap unique(SecSqlIntferface secSql){
		LOGGER.info("执行语句："+secSql.getSql());
		LOGGER.info("执行参数："+secSql.getParamsStr());
		Connection conn = ConnectionManager.getConn(dbName);
		return DbRunner.queryUniqueRow(conn, secSql.getSql(),secSql.getParams());
	}
	
	public NatureMap unique(SecSqlIntferface secSql,String cacheName){
		StringBuffer sb = new StringBuffer();
		if (secSql.getParams()!=null)
			for (Object object : secSql.getParams()) {
				sb.append(object.toString());
			}
		sb.append(secSql.getSql());
		Object object = natureCacheManager.get(cacheName, dbName+sb.toString());
		NatureMap natureMap;
		if (object!=null) {
			natureMap = (NatureMap) object;
		}else {
			natureMap= unique(secSql);
			natureCacheManager.put(cacheName, dbName+sb.toString(),natureMap);
		}
		return natureMap;
	}
	
	public Object uniqueValue(String sql){
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		return DbRunner.queryUniqueValue(conn, sql);
	}
	
	public Object uniqueValue(String sql,String cacheName){
		Object object = natureCacheManager.get(cacheName, dbName+sql);
		if (object==null) {
			object = uniqueValue(sql);
			natureCacheManager.put(cacheName, dbName+sql, object);
		}
		return object;
	}
	
	public Object uniqueValue(String sql,String cacheName,Object...params){
		StringBuffer sb = new StringBuffer();
		if (params!=null)
			for (Object object : params) {
				sb.append(object.toString());
			}
		sb.append(sql);
		Object object = natureCacheManager.get(cacheName, dbName+sb.toString());
		if (object==null) {
			object = uniqueValue(sql,params);
			natureCacheManager.put(cacheName, dbName+sb.toString(), object);
		}
		return object;
	}
	
	public Object uniqueValue(String sql,Object...params){
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		return DbRunner.queryUniqueValue(conn, sql,params);
	}
	
	public Object uniqueValue(SecSqlIntferface secSql){
		LOGGER.info("执行语句："+secSql.getSql());
		LOGGER.info("执行语句："+secSql.getParamsStr());
		Connection conn = ConnectionManager.getConn(dbName);
		return DbRunner.queryUniqueValue(conn, secSql.getSql(),secSql.getParams());
	}
	
	public Object uniqueValue(SecSqlIntferface secSql,String cacheName){
		Object object = natureCacheManager.get(cacheName, dbName+secSql.getSql());
		if (object==null) {
			object = uniqueValue(secSql);
			natureCacheManager.put(cacheName, dbName+secSql.getSql(), object);
		}
		return object;
	}
	
	public Set<String> querySet(String sql){
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		Set<String> result = DbRunner.querySet(conn, sql);
		return result;
	}	
	
	@SuppressWarnings("unchecked")
	public Set<String> querySet(String sql,String cacheName){
		Object object = natureCacheManager.get(cacheName, dbName+sql);
		Set<String> result;
		if (object!=null) {
			result = (Set<String>) object;
		}else {
			result = querySet(sql);
			natureCacheManager.put(cacheName, dbName+sql, result);
		}
		return result;
	}
	
	public Set<String> querySet(String sql,Object...params){
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		Set<String> result = DbRunner.querySet(conn, sql,params);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> querySet(String sql,String cacheName,Object...params){
		StringBuffer sb = new StringBuffer();
		if (params!=null)
			for (Object object : params) {
				sb.append(object.toString());
			}
		sb.append(sql);
		Object object = natureCacheManager.get(cacheName,dbName+sb.toString());
		Set<String> result;
		if (object!=null) {
			result = (Set<String>) object;
		}else {
			result = Collections.EMPTY_SET;
			result = querySet(sql, params);
			natureCacheManager.put(cacheName, dbName+sb.toString(), result);
		}
		return result;
	}
	
	public Set<String> querySet(SecSqlIntferface secSql){
		LOGGER.info("执行语句："+secSql.getSql());
		LOGGER.info("执行参数："+secSql.getParamsStr());
		Connection conn = ConnectionManager.getConn(dbName);
		Set<String> result = DbRunner.querySet(conn, secSql.getSql(),secSql.getParams());
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> querySet(SecSqlIntferface secSql,String cacheName){
		Object[] params = secSql.getParams();
		StringBuffer sb = new StringBuffer();
		if (params!=null)
			for (Object object : params) {
				sb.append(object.toString());
			}
		sb.append(secSql.getSql());
		Object object = natureCacheManager.get(cacheName,dbName+sb.toString());
		Set<String> result;
		if (object!=null) {
			result = (Set<String>) object;
		}else {
			result = Collections.EMPTY_SET;
			result = querySet(secSql);
			natureCacheManager.put(cacheName, dbName+sb.toString(), result);
		}
		return result;
	}
	
	public Page query(Page page,String sql) {
		String pageSql = DialectFactory.getInstance(jdbcDriver).tranformPageSql(page,sql);
		LOGGER.info("执行语句："+pageSql);
		Connection conn = ConnectionManager.getConn(dbName);
		List<NatureMap> list = DbRunner.query(conn, pageSql);
		page.setDataList(list);
		String cntSql = DialectFactory.getInstance(jdbcDriver).tranformCntSql(sql);
		long rowCnt = queryCnt(cntSql);
		page.setRowCnt((int) rowCnt);
		page.pagination();
		return page;
	}
	
	public Page query(Page page,String sql,String cacheName){
		String pageSql = DialectFactory.getInstance(jdbcDriver).tranformPageSql(page,sql);
		Object object = natureCacheManager.get(cacheName, dbName+page.getPageName()+pageSql);
		Page resultPage;
		if (object!=null) {
			resultPage = (Page) object;
		}else {
			resultPage = query(page, sql);
			natureCacheManager.put(cacheName, dbName+pageSql, resultPage);
		}
		return resultPage;
	}
	
	public Page query(Page page,SecSqlIntferface secSql) {
		String pageSql = DialectFactory.getInstance(jdbcDriver).tranformPageSql(page,secSql.getSql());
		LOGGER.info("执行语句："+pageSql);
		LOGGER.info("执行参数："+secSql.getParamsStr());
		Connection conn = ConnectionManager.getConn(dbName);
		List<NatureMap> list = DbRunner.query(conn, pageSql,secSql.getParams());
		page.setDataList(list);
		String cntSql = DialectFactory.getInstance(jdbcDriver).tranformCntSql(secSql.getSql());
		long rowCnt = queryCnt(cntSql,secSql.getParams());
		page.setRowCnt((int) rowCnt);
		page.pagination();
		return page;
	}
	
	public Page query(Page page,SecSqlIntferface secSql,String cacheName){
		String pageSql = DialectFactory.getInstance(jdbcDriver).tranformPageSql(page,secSql.getSql());
		Object object = natureCacheManager.get(cacheName, dbName+page.getPageName()+pageSql);
		Page resultPage;
		if (object!=null) {
			resultPage = (Page) object;
		}else {
			resultPage = query(page,secSql);
			natureCacheManager.put(cacheName, dbName+pageSql, resultPage);
		}
		return resultPage;
	}
	
	public long queryCnt(String sql){
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		Object cnt = DbRunner.queryUniqueValue(conn, sql);
		return CastUtil.castLong(cnt);
	}
	
	public long queryCnt(String sql,Object ...params){
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		Object cnt = DbRunner.queryUniqueValue(conn, sql,params);
		return CastUtil.castLong(cnt);
	}
	
	public long queryCnt(String sql,String cacheName){
		Object object = natureCacheManager.get(cacheName, dbName+sql);
		long cnt = 0;
		if (object!=null) {
			cnt = CastUtil.castLong(object);
		}else {
			cnt = queryCnt(sql);
			natureCacheManager.put(cacheName, dbName+sql, cnt);
		}
		return cnt;
	}
	
	public long queryCnt(SecSqlIntferface secSql){
		LOGGER.info("执行语句："+secSql.getSql());
		LOGGER.info("执行参数："+secSql.getParamsStr());
		Connection conn = ConnectionManager.getConn(dbName);
		Object cnt = DbRunner.queryUniqueValue(conn, secSql.getSql(),secSql.getParams());
		return CastUtil.castLong(cnt);
	}
	
	public long queryCnt(SecSqlIntferface secSql,String cacheName){
		Object object = natureCacheManager.get(cacheName, dbName+secSql.getSql());
		long cnt = 0;
		if (object!=null) {
			cnt = CastUtil.castLong(object);
		}else {
			cnt = queryCnt(secSql);
			natureCacheManager.put(cacheName, dbName+secSql.getSql(), cnt);
		}
		return cnt;
	}
	
	public int excute( String sql) {
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		int row = DbRunner.excute(conn, sql);
		return row;
	}
	
	public int excute( String sql,String cacheName) {
		natureCacheManager.flush(cacheName);
		return excute(sql);
	}

	public int excute( String sql, Object... params) {
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		int row = DbRunner.excute(conn, sql, params);
		return row;
	}
	
	public int excute( String sql,String cacheName, Object... params) {
		natureCacheManager.flush(cacheName);
		return excute(sql,params);
	}
	

	public Map<String, Object> excuteReturnKey( String sql) {
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		Map<String, Object> returnKey = DbRunner.excuteReturnKey(conn, sql);
		return returnKey;
	}
	
	public Map<String, Object> excuteReturnKey( String sql,String cacheName) {
		Map<String, Object> returnKey = excuteReturnKey(sql);
		natureCacheManager.flush(cacheName);
		return returnKey;
	}

	public Map<String, Object> excuteReturnKey(String sql, Object... params) {
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		Map<String, Object> returnKey = DbRunner.excuteReturnKey(conn, sql, params);
		return returnKey;
	}
	
	
	public Map<String, Object> excuteReturnKey(String sql,String cacheName ,Object... params) {
		Map<String, Object> returnKey = excuteReturnKey(sql, params);
		natureCacheManager.flush(cacheName);
		return returnKey;
	}
	
	public Map<String, Object> excuteReturnKey(SecSqlIntferface secSql) {
		LOGGER.info("执行语句："+secSql.getSql());
		LOGGER.info("执行参数："+secSql.getParamsStr());
		Connection conn = ConnectionManager.getConn(dbName);
		Map<String, Object> returnKey = DbRunner.excuteReturnKey(conn, secSql.getSql(), secSql.getParams());
		return returnKey;
	}
	
	public Map<String, Object> excuteReturnKey(SecSqlIntferface secSql,String cacheName) {
		Map<String, Object> returnKey = excuteReturnKey(secSql);
		natureCacheManager.flush(cacheName);
		return returnKey;
	}

	public List<NatureMap> query(String sql) {
		Connection conn = ConnectionManager.getConn(dbName);
		LOGGER.info("执行语句："+sql);
		return  DbRunner.query(conn, sql);
	}
	
	@SuppressWarnings("unchecked")
	public List<NatureMap> query(String sql,String cacheName) {
		Object object = natureCacheManager.get(cacheName, dbName+sql);
		List<NatureMap> list;
		if (object!=null) {
			list = (List<NatureMap>) object;
		}else {
			list = query(sql);
			natureCacheManager.put(cacheName, dbName+sql, list);
		}
		return list;
	}
	
	public List<NatureMap> query(String sql, Object... params) {
		Connection conn = ConnectionManager.getConn(dbName);
		LOGGER.info("执行语句："+sql);
		List<NatureMap> list = DbRunner.query(conn, sql, params);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<NatureMap> query(String sql,String cacheName, Object... params) {
		StringBuffer sb = new StringBuffer();
		if (params!=null)
			for (Object object : params) {
				sb.append(object.toString());
			}
		sb.append(sql);
		Object object = natureCacheManager.get(cacheName, dbName+sb.toString());
		List<NatureMap> list;
		if (object!=null) {
			list = (List<NatureMap>) object;
		}else {
			list = query(sql,params);
			natureCacheManager.put(cacheName, dbName+sb.toString(), list);
		}
		return list;
	}
	
	public List<NatureMap> query(SecSqlIntferface secSql) {
		Connection conn = ConnectionManager.getConn(dbName);
		LOGGER.info("执行语句："+secSql.getSql());
		LOGGER.info("执行参数："+secSql.getParamsStr());
		List<NatureMap> list = DbRunner.query(conn, secSql.getSql(), secSql.getParams());
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<NatureMap> query(SecSqlIntferface secSql,String cacheName) {
		Object[] params = secSql.getParams();
		StringBuffer sb = new StringBuffer();
		if (params!=null)
			for (Object object : params) {
				sb.append(object.toString());
			}
		sb.append(secSql.getSql());
		Object object = natureCacheManager.get(cacheName, dbName+sb.toString());
		List<NatureMap> list;
		if (object!=null) {
			list = (List<NatureMap>) object;
		}else {
			list = query(secSql.getSql(),params);
			natureCacheManager.put(cacheName, dbName+sb.toString(), list);
		}
		return list;
	}
	
	public void callPro(String callName,Object...params){
		Connection conn = ConnectionManager.getConn(dbName);
		try {
			CallableStatement prepareCall = conn.prepareCall(callName);
			for (int i = 0; i < params.length; i++) {
					prepareCall.setObject(i+1, params[i]);
			}
			prepareCall.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public int insert(NatureMap natureMap){
		Class<? extends NatureMap> cls = natureMap.getClass();
		TableBean tableBean = TableBeanHelper.getTableBean(cls);
		Object[] objs = DialectFactory.getInstance(jdbcDriver).tranformInsertSqlWithParams(natureMap,cls,tableBean);
		String sql = CastUtil.castString(objs[0]);
		Object[] params = (Object[]) objs[1];
		LOGGER.info("执行语句："+sql);
		LOGGER.info("执行参数："+natureMap.toString());
		Connection conn = ConnectionManager.getConn(dbName);
		int row = 0;
		if (StringUtil.isNotBank(sql)) {
			Map<String, Object> returnMap = DbRunner.excuteReturnKey(conn, sql,params);
			row = returnMap.values()!=null?returnMap.values().size():0;
			if (row>0) {
				String primaryKey = tableBean.getPrimaryKey();
				Object object = returnMap.get("GENERATED_KEY");
				if (object!=null) {
					natureMap.put(primaryKey, returnMap.get("GENERATED_KEY"));
				}
			}
		}
		return row;
	}
	public int insert(NatureMap natureMap,String cacheName){
		int row = insert(natureMap);
		natureCacheManager.flush(cacheName);
		return row;
	}
	
	public int insertBatch(List<? extends NatureMap> natureMaps){
		Class<? extends NatureMap> cls = natureMaps.get(0).getClass();
		TableBean tableBean = TableBeanHelper.getTableBean(cls);
		Object[] objs = DialectFactory.getInstance(jdbcDriver).tranformInsertBatchSqlWithParams(natureMaps,cls,tableBean);
		String sql = CastUtil.castString(objs[0]);
		Object[][] params = (Object[][]) objs[1];
		LOGGER.info("执行语句："+sql);
		LOGGER.info("执行参数："+natureMaps.toString());
		Connection conn = ConnectionManager.getConn(dbName);
		return DbRunner.batch(conn, sql, params);
	}
	
	public int insertBatch(List<? extends NatureMap> natureMaps,String cacheName){
		natureCacheManager.flush(cacheName);
		return insertBatch(natureMaps);
	}
	
	public int update(NatureMap natureMap){
		Object[] objs = DialectFactory.getInstance(jdbcDriver).tranformUpdateSqlWithParams(natureMap);
		String sql = CastUtil.castString(objs[0]);
		Object[] params = (Object[]) objs[1];
		LOGGER.info("执行语句："+sql);
		LOGGER.info("执行参数："+natureMap.toString());
		Connection conn = ConnectionManager.getConn(dbName);
		int row = 0;
		if (StringUtil.isNotBank(sql)) {
			row = DbRunner.excute(conn, sql,params);
		}
		return row;
	}
	
	public int update(NatureMap natureMap,String cacheName){
		natureCacheManager.flush(cacheName);
		return update(natureMap);
	}
	
	public int delete(NatureMap natureMap){
		String sql = DialectFactory.getInstance(jdbcDriver).tranformDeleteSql(natureMap);
		LOGGER.info("执行语句："+sql);
		Connection conn = ConnectionManager.getConn(dbName);
		int row = 0;
		if (StringUtil.isNotBank(sql)) {
			row = DbRunner.excute(conn, sql);
		}
		return row;
	}
	
	public int delete(NatureMap natureMap,String cacheName){
		natureCacheManager.flush(cacheName);
		return delete(natureMap);
	}
}
