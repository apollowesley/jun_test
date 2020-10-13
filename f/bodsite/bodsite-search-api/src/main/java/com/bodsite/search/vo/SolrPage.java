package com.bodsite.search.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bodsite.common.search.Search;
import com.bodsite.common.utils.BeanUtil;
import com.github.pagehelper.Page;

public class SolrPage<E> extends Page<E> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Map<String, List<String>>> highlighting;
	public Map<String, Map<String, List<String>>> getHighlighting() {
		return highlighting;
	}
	public void setHighlighting(Map<String, Map<String, List<String>>> highlighting) {
		this.highlighting = highlighting;
	}
	public SolrPage(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }
	
	public SolrPage(){
		super(Search.PAGE_NUM,Search.PAGE_SIZE);
	}
	
	public static <E> SolrPage<E> toSolrPage(Page<E> page){
		SolrPage<E> solrPage = new SolrPage<>();
		BeanUtil.copy(page, solrPage);
		return solrPage;
	}
	
}
