package org.beetl.json.test.simple;

import java.util.ArrayList;
import java.util.List;

import org.beetl.json.JsonTool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IteratorTest {
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
		list.add(User.getTestUser());
		list.add(true);
		String json = tool.serialize(list);
		String expected = "[1,'age',3.132,{'id':1,'name':'joelli','wife':{'id':2,'name':'lucy','wife':null}},true]";		
//		System.out.println(json);
		Assert.assertEquals(expected, json);
	}
	
	@Test
	public void testIgnoreListItem() {
		List list = new ArrayList();
		list.add(1);
		list.add("age");
		list.add(3.132);
		list.add(User.getTestUser());
		list.add(true);
		String json = tool.serialize(list,"[0]:i,[3]:i");
		String expected = "['age',3.132,true]";		
//		System.out.println(json);
		Assert.assertEquals(expected, json);
	}
	
	@Test
	public void testIndexItem() {
		List list = new ArrayList();
		list.add(1);
		list.add("age");
		list.add(3.132);
		list.add(User.getTestUser());
		list.add(true);
		String json = tool.serialize(list,"[3].~*:u/id,name/");
		String expected = "[1,'age',3.132,{'id':1,'name':'joelli'},true]";		
//		System.out.println(json);
		Assert.assertEquals(expected, json);
	}
	
}
