package org.beetl.json.test.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.beetl.json.JsonTool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * ->
 * @author joelli
 *
 */
public class ExpressActionTest {

	

	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
	}
	
	@Test
	public void fomrat1() {
		
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse("2015--5-21");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Map map = new HashMap();
		map.put("date", cal);
		String json1 = tool.serialize(map,"~L/#ju.Calendar*/:$.getTime->f/yyyy-MM-dd/" );
//				System.out.println(json1);
		String expected1 ="{'date':'2014-07-21'}"; //?
		Assert.assertEquals(expected1, json1);
	}
	
	@Test
	public void fomrat2() {		
		SampleData data = new SampleData();
		String json1 = tool.serialize(data,"~L/#ju.Calendar/:$.getTime->f/yyyy-MM-dd/,~d:f/yyyy-MM-dd/" );
//		System.out.println(json1);
		String expected1 ="{'sar':1.2378,'cal':'2014-07-21','date':'2014-07-21','book':['hello'],'users':null}";
		Assert.assertEquals(expected1, json1);
	}
	
	@Test
	public void fomrat3() {		
		SampleData data = new SampleData();
		data.setDate(null);
		data.setCal(null);
		String json1 = tool.serialize(data,"sar:->null,book:->null" );
//		System.out.println(json1);
		String expected1 ="{'sar':null,'cal':null,'date':null,'book':null,'users':null}";
		Assert.assertEquals(expected1, json1);
	}
	

	
	

}
