package org.beetl.json.test.location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.json.JsonTool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * include and order
 * @author joelli
 *
 */
public class TypeLocationTest {

	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
		tool.addAlias("loc", "org.beetl.json.test.location");
	}


	@Test
	public void loc1() {
		Car item = new Car();
		String[] policy = new String[]{
				"~L/#loc.Car/:u/name/"
		};
		String json1 = tool.serialize(item, policy);
//		System.out.println(json1);
		String expected1 ="{'name':'307'}";
		Assert.assertEquals(expected1, json1);
	}
	
	@Test
	public void loc2() {
		Car item = new Car();
		String[] policy = new String[]{
				"~L/#loc.Car/:u/name,carType/",
				"~L/#loc.CarType/:u/typeName/",
				
		};
		String json1 = tool.serialize(item, policy);
//		System.out.println(json1);
		String expected1 ="{'name':'307','carType':{'typeName':'new'}}";
		Assert.assertEquals(expected1, json1);
	}
	
	
	@Test
	public void loc3() {
		Car item = new Car();
		String[] policy = new String[]{
				"~L/#loc.Car/:u/name,carType/",
				"~L/#loc.CarType/:u/typeName/",
				
		};
		List list = new ArrayList();
		list.add(item);
		Map map = new HashMap();
		map.put("info", list);
		String json1 = tool.serialize(map, policy);
//		System.out.println(json1);
		String expected1 ="{'info':[{'name':'307','carType':{'typeName':'new'}}]}";
		Assert.assertEquals(expected1, json1);
	}
	
	

}
