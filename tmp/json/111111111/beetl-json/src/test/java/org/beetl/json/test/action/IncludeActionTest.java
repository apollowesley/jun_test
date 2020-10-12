package org.beetl.json.test.action;

import org.beetl.json.JsonTool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * include and order
 * @author joelli
 *
 */
public class IncludeActionTest {

	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
	}
	

	@Test
	public void simpleAttrbiute() {
		Order order = new Order(1, "order");
		String json1 = tool.serialize(order, "~*:u/id,name/");
//		System.out.println(json1);
		String expected1 = "{'id':1,'name':'order'}";
		Assert.assertEquals(expected1, json1);

	}

	@Test
	public void classIgnore() {
		Order order = new Order(1, "order");
		OrderDetail detail = new OrderDetail();
		detail.setId(2);
		detail.setTotal(20);
		order.getDetails().add(detail);
		detail = new OrderDetail();
		detail.setId(3);
		detail.setTotal(30);
		order.getDetails().add(detail);
		String json1 = tool.serialize(order, "~*:u/name,details/");
		String expected1 = "{'name':'order','details':[{'id':2,'total':20},{'id':3,'total':30}]}";
//		 System.out.println(json1);
		Assert.assertEquals(expected1, json1);
		
		String json2 = tool.serialize(order, "details[0].~*:u/total/");
		String expected2 = "{'id':1,'name':'order','details':[{'total':20},{'id':3,'total':30}]}";
//		 System.out.println(json2);
		Assert.assertEquals(expected2, json2);
		
		String json3 = tool.serialize(order, "details[*].~*:u/total/");
		String expected3 = "{'id':1,'name':'order','details':[{'total':20},{'total':30}]}";
//		 System.out.println(json3);
		Assert.assertEquals(expected3, json3);

	}
	
	@Test
	public void classOrder() {
		Order order = new Order(1, "order");
		OrderDetail detail = new OrderDetail();
		detail.setId(2);
		detail.setTotal(20);
		order.getDetails().add(detail);
		String json1 = tool.serialize(order, "~*:o/id,details/");
		String expected1 = "{'id':1,'details':[{'id':2,'total':20}],'name':'order'}";
//		 System.out.println(json1);
		Assert.assertEquals(expected1, json1);

	}

}
