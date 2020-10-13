package com.bodsite.search.privoder;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.response.QueryResponse;

import com.bodsite.search.solr.common.Criteral;
import com.bodsite.search.solr.server.SolrServer;
import com.github.pagehelper.Page;

public class SearchTest {
	public static void main(String[] args) throws Exception {
		String connectAddress = "http://127.0.0.1:8983/solr/";
		String collection = "demo";
		SolrServer client = new SolrServer();
		client.setConnectAddress(connectAddress);
		Criteral criteral = new Criteral();
		//criteral.setQf("title^10 memo^5 xx^2");
		criteral.setDefType("edismax");
		//criteral.addQ("memo", "丝");
		criteral.addQ("title", "天");

		List<String> hls = new ArrayList<>();
		hls.add("title");
		criteral.setHls(hls);
		Page page = new Page();
		page.setPageSize(11);
		criteral.setPage(page);
		System.out.println(criteral.toSolrQuery().toQueryString());
		QueryResponse list = client.queryPost(collection, criteral.toSolrQuery());
		System.out.println(list);
	}

}
