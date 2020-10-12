package org.xliang.elasticsearch;

import java.util.LinkedHashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest.OpType;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

public class ElasticSearchTest {
	private static final String index = "store";
	private static final String type = "book";

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Client client = null;
		try {
			Settings settings = ImmutableSettings.settingsBuilder()
					.put("client.transport.ignore_cluster_name", true)
					.put("client.transport.sniff", true).build();
			client = new TransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
			
//			recreateIndex(client);
//			doIndex(client);
			
			searchAll(client);
			
			searchRange(client);
			
			searchKeyWord(client);
			
			searchHightlight(client);
			
			searchOrdered(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void recreateIndex(Client client) {
		if (client.admin().indices().prepareExists(index).execute().actionGet()
				.isExists()) {
			DeleteIndexResponse deleteIndexResponse = client.admin().indices()
					.delete(new DeleteIndexRequest(index)).actionGet();
			System.out.println("delete index :" + deleteIndexResponse);
		}

		CreateIndexResponse createIndexResponse = client.admin().indices()
				.prepareCreate(index).execute().actionGet();
		System.out.println("create index :" + createIndexResponse);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void doIndex(final Client client) {
		Map s11 = new LinkedHashMap();
		s11.put("title", "Think in java");
		s11.put("origin", "美国");
		s11.put("description", "初级java开发人员必读的书");
		s11.put("author", "Bruce Eckel");
		s11.put("price", 108);

		Map s12 = new LinkedHashMap();
		s12.put("title", "Head First Java");
		s12.put("origin", "英国");
		s12.put("description", "java入门教材");
		s12.put("author", "Kathy Sierra");
		s12.put("price", 54);

		Map s21 = new LinkedHashMap();
		s21.put("title", "Design Pattern");
		s21.put("origin", "法国");
		s21.put("description", "程序员不得不读的设计模式");
		s21.put("author", "Kathy Sierra");
		s21.put("price", 89);

		Map s22 = new LinkedHashMap();
		s22.put("title", "黑客与画家");
		s22.put("origin", "法国");
		s22.put("description", "读完之后脑洞大开");
		s22.put("author", "Paul Graham");
		s22.put("price", 35);

		BulkResponse bulkResponse = client
				.prepareBulk()
				.add(client.prepareIndex(index, type).setId("11").setSource(s11).setOpType(OpType.INDEX).request())
				.add(client.prepareIndex(index, type).setId("12").setSource(s12).setOpType(OpType.INDEX).request())
				.add(client.prepareIndex(index, type).setId("21").setSource(s21).setOpType(OpType.INDEX).request())
				.add(client.prepareIndex(index, type).setId("22").setSource(s22).setOpType(OpType.INDEX).request())
				.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			System.err.println("index docs ERROR:" + bulkResponse.buildFailureMessage());
		} else {
			System.out.println("index docs SUCCESS:" + bulkResponse);
		}
	}
	
	/**
	 * 查询所有
	 */
	private static void searchAll(Client client) {
		SearchResponse response = client.prepareSearch(index)
				.setQuery(QueryBuilders.matchAllQuery())
				.setExplain(true).execute().actionGet();
		System.out.println("searchAll : " + response);
	}
	
	/**
	 * 关键词查询
	 * @param client
	 */
	private static void searchKeyWord(Client client) {
		SearchResponse response = client.prepareSearch(index)
				.setQuery(QueryBuilders.matchQuery("_all", "java"))
				.execute().actionGet();
		System.out.println("searchKeyWord : " + response);
	}
	
	/**
	 * 数值范围过滤
	 * @param client
	 */
	private static void searchRange(Client client) {
		SearchResponse response = client.prepareSearch(index).
				setQuery(QueryBuilders.filteredQuery(
						QueryBuilders.matchAllQuery(), 
						FilterBuilders.rangeFilter("price").gt(80)))
				.execute().actionGet();
		System.out.println("searchRange : " + response);
	}
	
	/**
	 * 排序
	 * @param client
	 */
	private static void searchOrdered(Client client) {
		SearchResponse response = client.prepareSearch(index)
				.setQuery(QueryBuilders.matchAllQuery())
				.addSort(SortBuilders.fieldSort("price")
				.order(SortOrder.DESC)).execute().actionGet();
		System.out.println("searchOrdered : " + response);
	}
	
	private static void searchHightlight(Client client) {
		SearchResponse response = client.prepareSearch(index)
				.setQuery(QueryBuilders.matchQuery("_all", "java"))
				.addHighlightedField("author")
				.addHighlightedField("price")
				.execute()
				.actionGet();
		System.out.println("searchHightlight : " + response);
	}
}
