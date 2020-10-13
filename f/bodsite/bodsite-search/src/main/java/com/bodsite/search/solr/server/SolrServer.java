package com.bodsite.search.solr.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;
import com.bodsite.common.utils.StringUtils;
import com.bodsite.saerch.solr.entity.ResultEntity;
import com.bodsite.search.exception.SearchException;
import com.bodsite.search.solr.convert.SerachConvert;

/**
 * 
 * solr操作类
 * 
 * @author bod
 * @date 2016年12月29日 下午5:29:09
 *
 */
public class SolrServer {

	private static final Logger logger = LoggerFactory.getLogger(SolrServer.class);

	private String connectAddress;

	private SolrClient solrClient;

	private SolrClient solrClient() {
		if (solrClient == null) {
			synchronized (SolrServer.class) {
				if (solrClient == null) {
					if (connectAddress.startsWith("zk://")) {
						solrClient = new CloudSolrClient(connectAddress);
					} else {
						solrClient = new HttpSolrClient(connectAddress);
					}
				}
			}
		}
		return solrClient;
	}

	/**
	 * 查询带高亮、总数量等信息
	* @author bod     
	 */
	public <T> ResultEntity<T> query(String collection, SolrQuery query, Class<T> clazz) {
		try {
			QueryResponse response= queryPost(collection,query);
			SolrDocumentList docs= response.getResults();
			ResultEntity<T> result = sorlToResult(docs,clazz);
			result.setHighlighting(response.getHighlighting());
			return result;
		} catch (Exception e) {
			throw new SearchException(SearchException.SEARCH_EXPECTION.SOLR_ADD_ERROR, e);
		}
	}

	private <T> ResultEntity<T> sorlToResult(SolrDocumentList docs, Class<T> clazz)
			throws InstantiationException, IllegalAccessException, InvocationTargetException{
		ResultEntity<T> result = new ResultEntity<T>();
		result.setLists(SerachConvert.solrToPage(docs,clazz));
		result.setMaxScore(docs.getMaxScore());
		result.setNumFound(docs.getNumFound());
		result.setStart(docs.getStart());
		return result;
	}
	
	/**
	 * 
	* 查询
	* @return list 
	* @author bod     
	 */
	public <T> List<T> queryNoH(String collection, SolrQuery query, Class<T> clazz) {
		try {
			SolrDocumentList docs = querySolrDocumentList(collection, query);
			return SerachConvert.solrToPage(docs, clazz);
		} catch (Exception e) {
			throw new SearchException(SearchException.SEARCH_EXPECTION.SOLR_ADD_ERROR, e);
		}
	}



	/**
	 * 
	* 查询
	* @return 文档集合结果集
	* @author bod     
	 */
	private SolrDocumentList querySolrDocumentList(String collection, SolrQuery query)
			throws SolrServerException, IOException {
		QueryResponse queryResponse = queryPost(collection, query);
		return queryResponse.getResults();
	}

	/**
	 * 
	 * 查询 - post
	 * 
	 * @author bod
	 */
	public QueryResponse queryPost(String collection, SolrQuery query) throws SolrServerException, IOException {
		return query(collection, query, METHOD.POST);
	}

	/**
	 * 
	 * 查询
	 * 
	 * @author bod
	 */
	private QueryResponse query(String collection, SolrQuery query, METHOD method)
			throws SolrServerException, IOException {
		return this.solrClient().query(collection, query, method);
	}

	/**
	 * 
	 * 新增
	 * 
	 * @author bod
	 */
	protected <T> void save(String collection, T t) {
		try {
			SolrInputDocument solrInputDocument = SerachConvert.beanToDocument(t);
			this.solrClient().add(collection, solrInputDocument);
			this.solrClient().commit(collection);
		} catch (Exception e) {
			throw new SearchException(SearchException.SEARCH_EXPECTION.SOLR_ADD_ERROR, e);
		}
	}

	/**
	 * 
	 * 新增 - list
	 * 
	 * @author bod
	 */
	protected <T> void save(String collection, List<T> list) {
		try {
			List<SolrInputDocument> documentList = SerachConvert.listToDocumentList(list);
			this.solrClient().add(collection, documentList);
			this.solrClient().commit(collection);
		} catch (Exception e) {
			throw new SearchException(SearchException.SEARCH_EXPECTION.SOLR_ADD_ERROR, e);
		}
	}

	/**
	 * 删除 - id
	 * 
	 * @author bod
	 */
	protected void del(String collection, String id) {
		try {
			this.solrClient().deleteById(collection, id);
			this.solrClient().commit(collection);
		} catch (Exception e) {
			throw new SearchException(SearchException.SEARCH_EXPECTION.SOLR_DEL_ERROR, e);
		}
	}

	/**
	 * 删除 - ids
	 * 
	 * @author bod
	 */
	protected void del(String collection, List<String> ids) {
		try {
			this.solrClient().deleteById(collection, ids);
			this.solrClient().commit(collection);
		} catch (Exception e) {
			throw new SearchException(SearchException.SEARCH_EXPECTION.SOLR_DEL_ERROR, e);
		}
	}

	/**
	 * 删除 - 根据条件
	 * 
	 * @author bod
	 */
	protected void delByQuery(String collection, String query) {
		try {
			this.solrClient().deleteByQuery(collection, query);
			this.solrClient().commit();
		} catch (Exception e) {
			throw new SearchException(SearchException.SEARCH_EXPECTION.SOLR_DEL_ERROR, e);
		}
	}

	/**
	 * 删除 - 全部内容
	 * 
	 * @author bod
	 */
	protected void delAll(String collection) {
		try {
			this.solrClient().deleteByQuery(collection, "*");
			this.solrClient().commit();
		} catch (Exception e) {
			throw new SearchException(SearchException.SEARCH_EXPECTION.SOLR_DEL_ERROR, e);
		}
	}

	/**
	 * 优化
	 * 
	 * @author bod
	 */
	public void optimize(String collection) {
		try {
			this.solrClient().optimize(collection);
		} catch (Exception e) {
			throw new SearchException(SearchException.SEARCH_EXPECTION.SOLR_OPTIMIZE_ERROR, e);
		}
	}

	public void close(){
		if(solrClient!=null){
			try {
				solrClient.close();
				solrClient = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getConnectAddress() {
		return connectAddress;
	}

	public void setConnectAddress(String connectAddress) {
		if (StringUtils.isBlank(connectAddress)) {
			throw new SearchException(SearchException.SEARCH_EXPECTION.SOLR_CONNECT_ADDRESS_ERROR);
		}
		this.connectAddress = connectAddress;
	}
}
