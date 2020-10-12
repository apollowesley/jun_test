package org.beetl.json.test.location;

import java.util.ArrayList;
import java.util.List;

import org.beetl.json.JsonTool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * include and order
 * @author joelli
 *
 */
public class AttributeLocationTest {

	
	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
		tool.addAlias("loc", "org.beetl.json.test.location");
	}


	@Test
	public void loc1() {
		Menu obj = new Menu();
		String[] policy = new String[]{
				"item.id:i"
		};
		String json1 = tool.serialize(obj, policy);
//		System.out.println(json1);
		String expected1 ="{'id':1,'item':{'name':'菜单项'},'list':[{'id':1,'name':'菜单项'}]}";
		Assert.assertEquals(expected1, json1);
	}
	
	
//	@Test
	public void loc2() {
		List list = new ArrayList();
		Menu obj = new Menu();
		list.add(obj);
		String[] policy = new String[]{
				"[*].item.id:i"
		};
		String json1 = tool.serialize(list, policy);
//		System.out.println(json1);
		String expected1 ="[{'id':1,'item':{'name':'菜单项'},'list':[{'id':1,'name':'菜单项'}]}]";
		Assert.assertEquals(expected1, json1);
	}
	
	
//	@Test
	public void loc3() {
		List list = new ArrayList();
		Menu obj = new Menu();
		Menu obj2 = new Menu();		
		list.add(obj);
		list.add(obj2);
		
		String[] policy = new String[]{
				"[0].~L/#loc.MenuItem/:u/id/"
		};
		String json1 = tool.serialize(list, policy);
//		System.out.println(json1);
		String expected1 ="[{'id':1,'item':{'id':1},'list':[{'id':1}]},{'id':1,'item':{'id':1,'name':'菜单项'},'list':[{'id':1,'name':'菜单项'}]}]";
		Assert.assertEquals(expected1, json1);
	}
	
	
	@Test
	public void reg1() {
		
		Menu obj = new Menu();
		
		String[] policy = new String[]{
				"/.*i.*/:i"
		};
		String json1 = tool.serialize(obj, policy);
//		System.out.println(json1);
		String expected1 ="{}";
		Assert.assertEquals(expected1, json1);
	}
	
	@Test
	public void reg2() {
		
		Menu obj = new Menu();
		
		String[] policy = new String[]{
				"item./id/:i,list[*]./i.*/:i"
		};
		String json1 = tool.serialize(obj, policy);
//		System.out.println(json1);
		String expected1 ="{'id':1,'item':{'name':'菜单项'},'list':[{'name':'菜单项'}]}";
		Assert.assertEquals(expected1, json1);
	}
	
	@Test
	public void reg3() {
		
		Menu obj = new Menu();
		
		String[] policy = new String[]{
				"*./id/:i"
		};
		String json1 = tool.serialize(obj, policy);
//		System.out.println(json1);
		String expected1 ="{'item':{'name':'菜单项'},'list':[{'name':'菜单项'}]}";
		Assert.assertEquals(expected1, json1);
	}
	

}
