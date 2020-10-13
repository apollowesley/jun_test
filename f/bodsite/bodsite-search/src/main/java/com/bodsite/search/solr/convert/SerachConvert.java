package com.bodsite.search.solr.convert;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.bodsite.common.utils.VerificationUtil;
import com.bodsite.search.vo.SolrPage;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;

public class SerachConvert {
	
	public static Map<String,String> simplifyHlMap(Map hlmap, String property){
		if(hlmap==null|| property==null){
			return null;
		}
		Map<String,String> result = new HashMap<String,String>();
		Set<Entry<String, Object>> entrySet = hlmap.entrySet();
		for(Entry<String, Object> hlentry:entrySet){
			String id = hlentry.getKey();
			Map rowHlMap = (Map) hlentry.getValue();
			List<String> list = (List<String>) rowHlMap.get(property);
			if(list!=null && list.size()>0){
				String str = list.get(0);
				result.put(id, str);
			}
		}
		return result;
	}

	public static <T> void saveHigh(Page<T> page,String keyId, String property) {
		saveHigh(page,keyId,property);
	}
	
	public static <T> void saveHigh(Page<T> page,String keyId, String source,String desc) {
		if (!(page instanceof SolrPage)) {
			return;
		}
		SolrPage<T> solrPage = (SolrPage<T>) page;
		if (solrPage == null || solrPage.isEmpty() || solrPage.getHighlighting() == null) {
			return;
		}
		Map<String, String> map = simplifyHlMap(solrPage.getHighlighting(), source);
		if (!map.isEmpty()) {
			for (T vo : solrPage) {
				try {
					String val = BeanUtils.getProperty(vo, keyId);
					if (map.containsKey(val)) {
						BeanUtils.setProperty(vo, desc, map.get(val));
					}
				} catch (Exception e) {
					//....
				}
			}
		}
	}

	/**
	 * 结果集合转换Page
	 * 
	 * @author bod
	 */
	public static <T> List<T> solrToPage(SolrDocumentList docs, Class<T> clazz)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		List<T> list = Lists.newArrayList();
		for (SolrDocument doc : docs) {
			list.add(solrToBean(doc, clazz));
		}
		return list;
	}

	/**
	 * 单个结果转换对象
	 * 
	 * @author bod
	 */
	public static <T> T solrToBean(SolrDocument doc, Class<T> clazz)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		T bean = clazz.newInstance();
		BeanUtils.populate(bean, doc);
		return bean;
	}

	/**
	 * 
	 * 对象转换 SolrInputDocument
	 * 
	 * @author bod
	 */
	public static <T> SolrInputDocument beanToDocument(T t)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, Object> map = PropertyUtils.describe(t);
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			solrInputDocument.addField(entry.getKey(), entry.getValue());
		}
		return solrInputDocument;
	}

	public static <T> List<SolrInputDocument> listToDocumentList(List<T> list)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		VerificationUtil.notEmpty(list);
		List<SolrInputDocument> documentList = new ArrayList<>();
		for (T t : list) {
			documentList.add(beanToDocument(t));
		}
		return documentList;
	}
}
