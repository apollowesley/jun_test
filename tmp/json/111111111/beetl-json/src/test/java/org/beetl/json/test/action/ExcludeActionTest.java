package org.beetl.json.test.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.beetl.json.ActionReturn;
import org.beetl.json.JsonTool;
import org.beetl.json.JsonWriter;
import org.beetl.json.OutputNode;
import org.beetl.json.action.IKeyAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExcludeActionTest {

	
	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
	}
	

	@Test
	public void simpleAttrbiute() {
		Order order = new Order(1, "order");
		String json1 = tool.serialize(order, "id:i");
		// System.out.println(json1);
		String expected1 = "{'name':'order','details':[]}";
		Assert.assertEquals(expected1, json1);

	}

	@Test
	public void simpleAttrbiute2() {
		Order order = new Order(1, "order");
		String json1 = tool.serialize(order, "details:i");
		String expected1 = "{'id':1,'name':'order'}";
		// System.out.println(json1);
		Assert.assertEquals(expected1, json1);

	}

	@Test
	public void ignoreListItem() {
		Order order = new Order(1, "order");
		OrderDetail detail = new OrderDetail();
		detail.setId(1);
		detail.setTotal(10);
		order.getDetails().add(detail);

		detail = new OrderDetail();
		detail.setId(2);
		detail.setTotal(20);
		order.getDetails().add(detail);

		detail = new OrderDetail();
		detail.setId(3);
		detail.setTotal(30);
		order.getDetails().add(detail);

		String json1 = tool.serialize(order, "details[0]:i");
		String expected1 = "{'id':1,'name':'order','details':[{'id':2,'total':20},{'id':3,'total':30}]}";
		// System.out.println(json1);

		String json2 = tool.serialize(order, "details[2]:i");
		String expected2 = "{'id':1,'name':'order','details':[{'id':1,'total':10},{'id':2,'total':20}]}";
		// System.out.println(json2);
		Assert.assertEquals(expected2, json2);

	}

	@Test
	public void ignoreList() {
		List list = new ArrayList();
		list.add(1);
		List child = new ArrayList();
		child.add("hello");
		child.add("joelli");
		list.add(child);
		list.add(2);
		String json1 = tool.serialize(list, "[1]:i");
		String expected1 = "[1,2]";
		Assert.assertEquals(expected1, json1);
		// System.out.println(json1);
		json1 = tool.serialize(list, "[2]:i");
		expected1 = "[1,['hello','joelli']]";
		Assert.assertEquals(expected1, json1);
		// System.out.println(json1);
	}

	@Test
	public void ignoreMap() {
		Map map = new TreeMap();
		map.put("a", "1");
		map.put("b", "2");
		map.put("c", "3");
		String json1 = tool.serialize(map, "[a]:i");
		String expected1 = "{'b':'2','c':'3'}";
		// System.out.println(json1);
		Assert.assertEquals(expected1, json1);
		json1 = tool.serialize(map, "[c]:i");
		expected1 = "{'a':'1','b':'2'}";
		// System.out.println(json1);
		Assert.assertEquals(expected1, json1);

	}

	@Test
	public void classIgnore() {
		Order order = new Order(1, "order");
		OrderDetail detail = new OrderDetail();
		detail.setId(2);
		detail.setTotal(20);
		order.getDetails().add(detail);
		String json1 = tool.serialize(order, "~*:i/name,details/");
		String expected1 = "{'id':1}";
		// System.out.println(json1);
		Assert.assertEquals(expected1, json1);
		String json2 = tool.serialize(order, "details[0].~*:i/id/");
		String expected2 = "{'id':1,'name':'order','details':[{'total':20}]}";
		// System.out.println(json2);
		Assert.assertEquals(expected2, json2);

	}

	@Test
	public void classItemIgnore() {
		Order order = new Order(1, "order");
		OrderDetail detail = new OrderDetail();
		detail.setId(2);
		detail.setTotal(20);
		order.getDetails().add(detail);
		String json2 = tool.serialize(order, "details[*].~*:i/id/");
		String expected2 = "{'id':1,'name':'order','details':[{'total':20}]}";
		// System.out.println(json2);
		Assert.assertEquals(expected2, json2);

	}

	@Test
	public void ingnoreIds() {
		Order order = new Order(1, "order");
		OrderDetail detail = new OrderDetail();
		detail.setId(2);
		detail.setTotal(20);
		order.getDetails().add(detail);
		tool.addAction("ignoreIds", new IKeyAction() {

		
			@Override
			public ActionReturn doit(Object o, OutputNode thisNode, JsonWriter w) {
				if (o.equals("id")) {
					return new ActionReturn(null, ActionReturn.RETURN);
				} else {
					return new ActionReturn(o, ActionReturn.CONTINUE);
				}
			}
		});

		String json2 = tool.serialize(order, "*:!ignoreIds");
		String expected2 = "{'name':'order','details':[{'total':20}]}";
		 System.out.println(json2);
		Assert.assertEquals(expected2, json2);


	}

	// @Test
	// public void ingnoreIds2() {
	// Order order = new Order(1,"order");
	// OrderDetail detail = new OrderDetail();
	// detail.setId(2);
	// detail.setTotal(20);
	// order.getDetails().add(detail);
	//
	// String json2= tool.serialize(order,"*:i");
	// String expected2 = "{'id':1,'name':'order','details':[]}";
	// System.out.println(json2);
	// Assert.assertEquals(expected2, json2);
	//
	//
	// }

}
