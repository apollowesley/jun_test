package org.nature.framework.core;

import java.util.List;
import java.util.Set;

import org.nature.framework.annotation.Service;
import org.nature.framework.bean.Page;
import org.nature.framework.helper.DatabaseHelper;
@Service
public class NatureService{

	
	public Set<String> querySet(String sql,Object ...params ){
		return DatabaseHelper.querySet(sql, params);
	}
	public Set<String> querySet(String sql,String cacheName,Object ...params ){
		return DatabaseHelper.querySet(sql, cacheName,params);
	}
	
	public int excute(String sql) {
		return DatabaseHelper.excute(sql);
	}
	
	public int insert(NatureMap natureMap) {
		return DatabaseHelper.insert(natureMap);
	}
	
	public int insert(NatureMap natureMap,String cacheName) {
		return DatabaseHelper.insert(natureMap,cacheName);
	}

	public int update(NatureMap natureMap) {
		return DatabaseHelper.update(natureMap);
	}
	
	public int update(NatureMap natureMap,String cacheName) {
		return DatabaseHelper.update(natureMap,cacheName);
	}
 
	public int delete(NatureMap natureMap) {
		return DatabaseHelper.delete(natureMap);
	}
	public int delete(NatureMap natureMap,String cacheName) {
		return DatabaseHelper.delete(natureMap,cacheName);
	}

	public <T> T byId(T t) {
		return DatabaseHelper.byId(t);
	}
	public <T> T byId(T t,String cacheName) {
		return DatabaseHelper.byId(t,cacheName);
	}
	
	public NatureMap unique(String sql) {
		return DatabaseHelper.unique(sql);
	}
	public NatureMap unique(String sql,String cacheName) {
		return DatabaseHelper.unique(sql,cacheName);
	}
	
	public NatureMap unique(String sql,Object...params) {
		return DatabaseHelper.unique(sql,params);
	}
	
	public NatureMap unique(String sql,String cacheName,Object...params) {
		return DatabaseHelper.unique(sql,cacheName,params);
	}

	public Object uniqueValue(String sql,Object...params) {
		return DatabaseHelper.uniqueValue(sql, params);
	}
	public Object uniqueValue(String sql, String cacheName,Object...params) {
		return DatabaseHelper.uniqueValue(sql,cacheName, params);
	}
	
	public List<NatureMap> list(String sql) {
		return DatabaseHelper.query(sql);
	}
	
	public List<NatureMap> list(String sql,String cacheName) {
		return DatabaseHelper.query(sql,cacheName);
	}
	
	public List<NatureMap> list(String sql,Object...params) {
		return DatabaseHelper.query(sql,params);
	}
	
	public List<NatureMap> list(String sql,String cacheName,Object...params) {
		return DatabaseHelper.query(sql,cacheName,params);
	}

	public Page page(Page page, String sql) {
		return DatabaseHelper.query(page, sql);
	}
	
	public void page(Page page, String sql,String cacheName) {
		DatabaseHelper.query(page, sql,cacheName);
	}
	
}
