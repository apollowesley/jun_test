package org.beetl.json.test.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.beetl.json.JsonTool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JavaTypeTest {
	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
	}
	@Test
	public void testList() {
		tool.singleQuotes = true;
		List list = new ArrayList();
		list.add(1);
		list.add("age");
		list.add(3.132);
		list.add(true);
		String json = tool.serialize(list);
		String expected = "[1,'age',3.132,true]";		
		Assert.assertEquals(expected, json);
	}
	@Test
	public void testArray(){
		tool.singleQuotes = true;
		List list = new ArrayList();
		list.add(1);
		list.add("age");
		list.add(3.132);
		list.add(true);
		Object[] array = list.toArray();
		String json = tool.serialize(array);
		String expected = "[1,'age',3.132,true]";		
		Assert.assertEquals(expected, json);
	}
	
	@Test
	public void testMap(){
		tool.singleQuotes = true;
		Map map = new TreeMap();
		map.put("a","name");
		map.put("b","1");
		map.put("c","true");
		String json = tool.serialize(map);
//		System.out.println(json);
		String expected = "{'a':'name','b':'1','c':'true'}";		
		Assert.assertEquals(expected, json);
	}

}
