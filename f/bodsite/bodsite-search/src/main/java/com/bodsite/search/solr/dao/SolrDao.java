package com.bodsite.search.solr.dao;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bodsite.saerch.solr.entity.ResultEntity;
import com.bodsite.search.solr.common.Criteral;
import com.bodsite.search.solr.server.SolrServer;
import com.bodsite.search.vo.SolrPage;
import com.github.pagehelper.Page;

/**
 * 
* solr 查询
* @author bod
* @date 2017年1月3日 上午10:35:30 
*
 */
@Repository
public class SolrDao {

	@Autowired
	private SolrServer solrServer;
	
	
	public <T> Page<T> query(String cllection, Map<String, ?> queryMap, List<String> hls,
			Page<T> page, Class<T> clazz) {
		return query(cllection,queryMap,null,null,hls,page,clazz);
	}
	
	public <T> Page<T> queryFq(String cllection,Map<String, Object> fqMap, List<String> hls,
			Page<T> page, Class<T> clazz) {
		return query(cllection,null,fqMap,null,hls,page,clazz);
	}
	
	public <T> Page<T> query(String cllection, Map<String, ?> queryMap,Map<String, Object> fqMap, List<String> hls,
			Page<T> page, Class<T> clazz) {
		return query(cllection,queryMap,fqMap,null,hls,page,clazz);
	}
	
	public <T> Page<T> query(String collection, Map<String, ?> queryMap,Map<String,?> fqMap,List<String> fls,List<String> hls, Page<T> page, Class<T> clazz) {
		return query(collection,queryMap,fqMap,null,fls,hls,page,clazz);
	}
	
	public <T> Page<T> query(String collection, Map<String, ?> queryMap,Map<String,?> fqMap, Page<T> page, Class<T> clazz) {
		return query(collection,queryMap,fqMap,null,page,clazz);
	}
	/**
	 * 
	* @Title: query 
	* @param collection solr集合
	* @param queryMap 过滤条件
	* @param sortMap 排序条件
	* @param page 分页
	* @param clazz 类型
	* @author bod     
	 */
	public <T> Page<T> query(String collection, Map<String, ?> queryMap,Map<String,?> fqMap, Map<String, ORDER> sortMap, Page<T> page, Class<T> clazz) {
		return query(collection,queryMap,fqMap,sortMap,null,null,page,clazz);
	}
	
	/**
	 * 
	* @Title: query 
	* @param collection solr集合
	* @param queryMap 过滤条件
	* @param sortMap 排序条件
	* @param fls 返回字段
	* @param hls 高亮
	* @param page 分页
	* @param clazz 类型
	* @author bod     
	 */
	public <T> Page<T> query(String collection, Map<String, ?> queryMap,Map<String, ?> fqMap, Map<String, ORDER> sortMap,
			List<String> fls,List<String> hls, Page<T> page, Class<T> clazz) {
		SolrQuery solrQuery = createSolrQuery(queryMap,fqMap, sortMap, fls,hls, page, clazz);
		solrQuery(collection, solrQuery, page, clazz);
		return page;
	}

	/**
	 * 查询
	* @author bod
	 */
	private <T> void solrQuery(String collection, SolrQuery query, Page<T> page, Class<T> clazz) {
		ResultEntity<T> result = solrServer.query(collection, query, clazz);
		page.setTotal(result.getNumFound());
		page.addAll(result.getLists());
		if (page instanceof SolrPage) {
			SolrPage<T> pe = (SolrPage<T>) page;
			pe.setHighlighting(result.getHighlighting());
		}
	}

	/**
	 * 封装 查询类 SolrQuery 
	* @author bod     
	 */
	private <T> SolrQuery createSolrQuery(Map<String, ?> queryMap,Map<String, ?> fqMap, Map<String, ORDER> sortMap,
			List<String> fls,List<String> hls, Page<T> page, Class<T> clazz) {
		Criteral criteral = new Criteral();
		criteral.setQByMap(queryMap);
		criteral.setPage(page);
		criteral.setFl(fls);
		criteral.setFqByMap(fqMap);
		criteral.setSortMap(sortMap);
		criteral.setHls(hls);
		return criteral.toSolrQuery();
	}

}
