package org.beetl.json.test.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.json.Format;
import org.beetl.json.JsonTool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * include and order
 * @author joelli
 *
 */
public class FormatActionTest {


	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
	}
	
	@Test
	public void fomrat1() {
		Item item = new Item();
		String json1 = tool.serialize(item, "~d:f/yyyy-MM-dd/","~n:f/#.###/");
//		System.out.println(json1);
		String expected1 ="{'id':1,'sar':3.157,'date':'2014-05-18'}";
		Assert.assertEquals(expected1, json1);
	}
	
	@Test
	public void fomrat2() {
		Item item = new Item();
		List list = new ArrayList();
		list.add(item.getDate());
		list.add(item.getSar());
		String json1 = tool.serialize(list, "[*].~d:f/yyyy-MM-dd/,[*].~n:f/#.###/");
//		System.out.println(json1);
		String expected1 ="['2014-05-18',3.157]";
		Assert.assertEquals(expected1, json1);
	}
//	
//	
	@Test
	public void fomrat3() {
		tool.addLocationAction("~d", "f/yyyy-MM-dd HH:mm:ss/");
		Date date = null;
		try {
			date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-05-18 12:33:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List list = new ArrayList();
		list.add(date);
		String json1 = tool.serialize(list);
//		System.out.println(json1);
		String expected1 ="['2014-05-18 12:33:00']";
		Assert.assertEquals(expected1, json1);
	}
	
	@Test
	public void userDefind() {
	
		tool.addFormat(java.util.Date.class, new MyItemFomrat());
		Item item = new Item();		
		String json1 = tool.serialize(item, "date:f");
//		System.out.println(json1);
		String expected1 ="{'id':1,'sar':3.15674,'date':'simple'}";
		Assert.assertEquals(expected1, json1);
	}
	
	static public class MyItemFomrat implements Format{

		@Override
		public Object format(Object o, String pattern) {
			return "simple";
		}

		
		
	}
		

}
