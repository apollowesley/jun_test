package com.bodsite.search.solr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bodsite.search.service.SearchService;
import com.bodsite.search.solr.convert.SerachConvert;
import com.bodsite.search.solr.dao.SolrDao;
import com.bodsite.search.vo.DemoResultVo;
import com.bodsite.search.vo.DemoSearchVo;
import com.bodsite.search.vo.SolrPage;

@Service("searchService")
public class SearchServiceImpl implements SearchService {
	private final static String DEMO_CLLECTION = "uliaow_product";
	@Autowired
	private SolrDao solrDao;
	@Override
	public SolrPage<DemoResultVo> findDemoSolr(DemoSearchVo searchVo) {
		List<String> hls = new ArrayList<String>();
		hls.add("hl");
		SolrPage<DemoResultVo> solrPage = SolrPage.toSolrPage(searchVo.getPage());
		solrDao.query(DEMO_CLLECTION, searchVo.queryMap(), hls , solrPage, DemoResultVo.class);
		SerachConvert.saveHigh(solrPage, "skuId" , "hl" , "title");
		return solrPage;
	}

}
