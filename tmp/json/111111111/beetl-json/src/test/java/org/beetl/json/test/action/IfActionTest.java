package org.beetl.json.test.action;

import org.beetl.json.JsonTool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * if
 * @author joelli
 *
 */
public class IfActionTest {


	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
	}
	

	@Test
	public void emptyTest() {
		School s = new School();		
		String json1 = tool.serialize(s, "list:?empty->1");
//		System.out.println(json1);
		String expected1 ="{'name':'小学','master':{'id':1,'name':'joelli'},'list':1}";
		Assert.assertEquals(expected1, json1);
	}
	@Test
	public void emptyTest2() {
		School s = new School();
		s.list=null;
		String json1 = tool.serialize(s, "list:?empty->1");
//		System.out.println(json1);
		String expected1 ="{'name':'小学','master':{'id':1,'name':'joelli'},'list':1}";
		Assert.assertEquals(expected1, json1);
	}
//	
	@Test
	public void emptyTest3() {
		School s = new School();
		s.list=null;
		String json1 = tool.serialize(s, "list:?null->2");
//		System.out.println(json1);
		String expected1 ="{'name':'小学','master':{'id':1,'name':'joelli'},'list':2}";
		Assert.assertEquals(expected1, json1);
	}
//	
//	
	@Test
	public void iftest3() {
		School s = new School();
		Teacher t1 = new Teacher(2,"lucy");
		s.getList().add(t1);
		String json1 = tool.serialize(s, "list:?null->2");
//		System.out.println(json1);
		String expected1 ="{'name':'小学','master':{'id':1,'name':'joelli'},'list':[{'id':2,'name':'lucy'}]}";
		Assert.assertEquals(expected1, json1);
	}
//	
	@Test
	public void test4() {
		School s = new School();		
		String json1 = tool.serialize(s, "master.id:nn/code/");
//		System.out.println(json1);
		String expected1 ="{'name':'小学','master':{'code':1,'name':'joelli'},'list':[]}";
		Assert.assertEquals(expected1, json1);
	}
	
	
	
	

}
