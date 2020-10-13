package com.bodsite.search.service;

import com.bodsite.search.vo.DemoResultVo;
import com.bodsite.search.vo.DemoSearchVo;
import com.bodsite.search.vo.SolrPage;

public interface SearchService {
	
	public SolrPage<DemoResultVo> findDemoSolr(DemoSearchVo searchVo);
	

}
