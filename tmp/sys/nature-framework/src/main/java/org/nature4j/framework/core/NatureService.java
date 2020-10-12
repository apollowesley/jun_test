package org.nature4j.framework.core;

import java.util.List;
import java.util.Set;

import org.nature4j.framework.bean.Page;
import org.nature4j.framework.db.Db;
import org.nature4j.framework.db.SecSqlIntferface;
import org.nature4j.framework.helper.DatabaseHelper;

public class NatureService{

	
	public Set<String> querySet(String sql,Object ...params ){
		return DatabaseHelper.use().querySet(sql, params);
	}
	
	public Set<String> querySet(Db db,String sql,Object ...params ){
		return DatabaseHelper.use(db).querySet(sql, params);
	}
	
	public Set<String> querySet(String sql,String cacheName,Object ...params ){
		return DatabaseHelper.use().querySet(sql, cacheName,params);
	}
	
	public Set<String> querySet(Db db,String sql,String cacheName,Object ...params ){
		return DatabaseHelper.use(db).querySet(sql, cacheName,params);
	}
	
	public Set<String> querySet(SecSqlIntferface secSql){
		return DatabaseHelper.use().querySet(secSql);
	}
	
	public Set<String> querySet(Db db,SecSqlIntferface secSql){
		return DatabaseHelper.use(db).querySet(secSql);
	}
	
	public Set<String> querySet(SecSqlIntferface secSql,String cacheName){
		return DatabaseHelper.use().querySet(secSql, cacheName);
	}
	
	public Set<String> querySet(Db db,SecSqlIntferface secSql,String cacheName){
		return DatabaseHelper.use(db).querySet(secSql, cacheName);
	}
	
	public int excute(String sql) {
		return DatabaseHelper.use().excute(sql);
	}
	
	public int excute(Db db,String sql) {
		return DatabaseHelper.use(db).excute(sql);
	}
	
	public int excute(String sql,String cacheName) {
		return DatabaseHelper.use().excute(sql,cacheName);
	}
	public int excute(Db db,String sql,String cacheName) {
		return DatabaseHelper.use(db).excute(sql,cacheName);
	}
	
	public int insert(NatureMap natureMap) {
		return DatabaseHelper.use().insert(natureMap);
	}
	public int insert(Db db,NatureMap natureMap) {
		return DatabaseHelper.use(db).insert(natureMap);
	}
	
	public int insert(NatureMap natureMap,String cacheName) {
		return DatabaseHelper.use().insert(natureMap,cacheName);
	}
	public int insert(Db db,NatureMap natureMap,String cacheName) {
		return DatabaseHelper.use(db).insert(natureMap,cacheName);
	}
	
	public int insertBatch(List<? extends NatureMap> natureMaps) {
		return DatabaseHelper.use().insertBatch(natureMaps);
	}
	public int insertBatch(Db db,List<? extends NatureMap> natureMaps) {
		return DatabaseHelper.use(db).insertBatch(natureMaps);
	}
	
	public int insertBatch(List<? extends NatureMap> natureMaps,String cacheName) {
		return DatabaseHelper.use().insertBatch(natureMaps,cacheName);
	}
	
	public int insertBatch(Db db,List<? extends NatureMap> natureMaps,String cacheName) {
		return DatabaseHelper.use(db).insertBatch(natureMaps,cacheName);
	}


	public int update(NatureMap natureMap) {
		return DatabaseHelper.use().update(natureMap);
	}
	public int update(Db db,NatureMap natureMap) {
		return DatabaseHelper.use(db).update(natureMap);
	}
	
	public int update(NatureMap natureMap,String cacheName) {
		return DatabaseHelper.use().update(natureMap,cacheName);
	}
	public int update(Db db,NatureMap natureMap,String cacheName) {
		return DatabaseHelper.use(db).update(natureMap,cacheName);
	}
 
	public int delete(NatureMap natureMap) {
		return DatabaseHelper.use().delete(natureMap);
	}
	
	public int delete(Db db,NatureMap natureMap) {
		return DatabaseHelper.use(db).delete(natureMap);
	}
	
	public int delete(NatureMap natureMap,String cacheName) {
		return DatabaseHelper.use().delete(natureMap,cacheName);
	}
	
	public int delete(Db db,NatureMap natureMap,String cacheName) {
		return DatabaseHelper.use(db).delete(natureMap,cacheName);
	}

	public <T> T byId(T t) {
		return DatabaseHelper.use().byId(t);
	}
	public <T> T byId(Db db,T t) {
		return DatabaseHelper.use(db).byId(t);
	}
	public <T> T byId(T t,String cacheName) {
		return DatabaseHelper.use().byId(t,cacheName);
	}
	public <T> T byId(Db db,T t,String cacheName) {
		return DatabaseHelper.use(db).byId(t,cacheName);
	}
	
	public NatureMap unique(String sql) {
		return DatabaseHelper.use().unique(sql);
	}
	public NatureMap unique(Db db,String sql) {
		return DatabaseHelper.use(db).unique(sql);
	}
	public NatureMap unique(String sql,String cacheName) {
		return DatabaseHelper.use().unique(sql,cacheName);
	}
	
	public NatureMap unique(Db db,String sql,String cacheName) {
		return DatabaseHelper.use(db).unique(sql,cacheName);
	}
	
	public NatureMap unique(String sql,Object...params) {
		return DatabaseHelper.use().unique(sql,params);
	}
	
	public NatureMap unique(Db db,String sql,Object...params) {
		return DatabaseHelper.use(db).unique(sql,params);
	}
	
	public NatureMap unique(String sql,String cacheName,Object...params) {
		return DatabaseHelper.use().unique(sql,cacheName,params);
	}
	
	public NatureMap unique(Db db,String sql,String cacheName,Object...params) {
		return DatabaseHelper.use(db).unique(sql,cacheName,params);
	}
	
	public NatureMap unique(SecSqlIntferface secSql) {
		return DatabaseHelper.use().unique(secSql);
	}
	
	public NatureMap unique(SecSqlIntferface secSql,String cacheName) {
		return DatabaseHelper.use().unique(secSql,cacheName);
	}
	
	public NatureMap unique(Db db,SecSqlIntferface secSql) {
		return DatabaseHelper.use(db).unique(secSql);
	}
	
	public NatureMap unique(Db db,SecSqlIntferface secSql,String cacheName) {
		return DatabaseHelper.use(db).unique(secSql,cacheName);
	}

	public Object uniqueValue(String sql,Object...params) {
		return DatabaseHelper.use().uniqueValue(sql, params);
	}
	
	public Object uniqueValue(Db db,String sql,Object...params) {
		return DatabaseHelper.use(db).uniqueValue(sql, params);
	}
	
	public Object uniqueValue(String sql, String cacheName,Object...params) {
		return DatabaseHelper.use().uniqueValue(sql,cacheName, params);
	}
	
	public Object uniqueValue(Db db,String sql, String cacheName,Object...params) {
		return DatabaseHelper.use(db).uniqueValue(sql,cacheName, params);
	}
	
	public Object uniqueValue(SecSqlIntferface secSql) {
		return DatabaseHelper.use().uniqueValue(secSql);
	}
	
	public Object uniqueValue(SecSqlIntferface secSql, String cacheName) {
		return DatabaseHelper.use().uniqueValue(secSql,cacheName);
	}
	
	public Object uniqueValue(Db db,SecSqlIntferface secSql) {
		return DatabaseHelper.use(db).uniqueValue(secSql);
	}
	
	public Object uniqueValue(Db db,SecSqlIntferface secSql, String cacheName) {
		return DatabaseHelper.use(db).uniqueValue(secSql,cacheName);
	}
	
	public List<NatureMap> list(String sql) {
		return DatabaseHelper.use().query(sql);
	}
	
	public List<NatureMap> list(Db db,String sql) {
		return DatabaseHelper.use(db).query(sql);
	}
	
	public List<NatureMap> list(String sql,String cacheName) {
		return DatabaseHelper.use().query(sql,cacheName);
	}
	
	public List<NatureMap> list(Db db,String sql,String cacheName) {
		return DatabaseHelper.use(db).query(sql,cacheName);
	}
	
	public List<NatureMap> list(String sql,Object...params) {
		return DatabaseHelper.use().query(sql,params);
	}
	
	public List<NatureMap> list(Db db,String sql,Object...params) {
		return DatabaseHelper.use(db).query(sql,params);
	}
	
	public List<NatureMap> list(String sql,String cacheName,Object...params) {
		return DatabaseHelper.use().query(sql,cacheName,params);
	}
	
	public List<NatureMap> list(Db db,String sql,String cacheName,Object...params) {
		return DatabaseHelper.use(db).query(sql,cacheName,params);
	}
	
	public List<NatureMap> list(SecSqlIntferface secSql) {
		return DatabaseHelper.use().query(secSql);
	}
	
	public List<NatureMap> list(SecSqlIntferface secSql,String cacheName) {
		return DatabaseHelper.use().query(secSql,cacheName);
	}
	
	public List<NatureMap> list(Db db,SecSqlIntferface secSql) {
		return DatabaseHelper.use(db).query(secSql);
	}
	
	public List<NatureMap> list(Db db,SecSqlIntferface secSql,String cacheName) {
		return DatabaseHelper.use(db).query(secSql,cacheName);
	}

	public Page page(Page page, String sql) {
		return DatabaseHelper.use().query(page, sql);
	}
	
	public Page page(Db db,Page page, String sql) {
		return DatabaseHelper.use(db).query(page, sql);
	}
	
	public void page(Page page, String sql,String cacheName) {
		DatabaseHelper.use().query(page, sql,cacheName);
	}
	
	public void page(Db db,Page page, String sql,String cacheName) {
		DatabaseHelper.use(db).query(page, sql,cacheName);
	}
	
	public Page page(Page page, SecSqlIntferface secSql) {
		return DatabaseHelper.use().query(page, secSql);
	}
	
	public Page page(Page page, SecSqlIntferface secSql,String cacheName) {
		return DatabaseHelper.use().query(page, secSql,cacheName);
	}
	
	public Page page(Db db,Page page, SecSqlIntferface secSql) {
		return DatabaseHelper.use().query(page, secSql);
	}
	
	public Page page(Db db,Page page, SecSqlIntferface secSql,String cacheName) {
		return DatabaseHelper.use().query(page, secSql,cacheName);
	}
	
}
