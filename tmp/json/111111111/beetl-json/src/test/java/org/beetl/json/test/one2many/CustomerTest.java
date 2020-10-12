package org.beetl.json.test.one2many;

import org.beetl.json.JsonTool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerTest {
	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
	}

	

	@Test
	public void testOne2Many() {
		Customer c = new Customer();
		String json = tool.serialize(c);
		String expected = "{'id':1,'name':'joelli','list':[{'code':'01','total':12},{'code':'01','total':12}]}";
//		System.out.println(json);
		Assert.assertEquals(expected, json);
	}
	
	

}
