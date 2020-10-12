package org.beetl.json.test.simple;

import org.beetl.json.JsonTool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PojoTest {
	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
	}
	
	@Test
	public void testPojo() {
	
		String json = tool.serialize(User.getTestUser());		
		String expected = "{'id':1,'name':'joelli','wife':{'id':2,'name':'lucy','wife':null}}";
//		System.out.println(json);
		Assert.assertEquals(expected, json);
	}
//	
	@Test
	public void testPojo2() {
		tool.singleQuotes = true;
		String json = tool.serialize(User.getTestUser(),"id:i","name:nn/newName/");		
		String expected = "{'newName':'joelli','wife':{'id':2,'name':'lucy','wife':null}}";
//		System.out.println(json);
		Assert.assertEquals(expected, json);
	}

	@Test
	public void testList() {
		tool.singleQuotes = true;
		String json = tool.serialize(User.getTestUsers());		
//		System.out.println(json);
		String expected = "[{'id':1,'name':'joelli','wife':null},{'id':2,'name':'lucy','wife':null},{'id':3,'name':'bear','wife':null}]";

		
		Assert.assertEquals(expected, json);
	}
	
	@Test
	public void testMap() {
		tool.singleQuotes = true;
		String json = tool.serialize(User.getTestUserMap());		
//		System.out.println(json);
		String expected = "{'bear':{'id':3,'name':'bear','wife':null},'joelli':{'id':1,'name':'joelli','wife':null},'lucy':{'id':2,'name':'lucy','wife':null}}";
		
		Assert.assertEquals(expected, json);
	}
	
	
	@Test
	public void testWifeAttribute() {
		String json = tool.serialize(User.getTestUser(),"wife.id:i,wife.name:nn/wifeName/");		
//		System.out.println(json);
		String expected = "{'id':1,'name':'joelli','wife':{'wifeName':'lucy','wife':null}}";
		
		Assert.assertEquals(expected, json);
	}
	
	@Test
	public void testWifeAttribute2() {
		String json = tool.serialize(User.getTestUser(),"wife.~*:u/id/");		
//		System.out.println(json);
		String expected = "{'id':1,'name':'joelli','wife':{'id':2}}";		
		Assert.assertEquals(expected, json);
	}
	
	

}
