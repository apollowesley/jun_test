package org.beetl.json.test.error;

import java.lang.reflect.InvocationTargetException;

import org.beetl.json.AttribiuteErrorHandler;
import org.beetl.json.AttribiuteErrorIgnoreHandler;
import org.beetl.json.JsonTool;
import org.beetl.json.JsonWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * include and order
 * @author joelli
 *
 */
public class ErrorTest {

	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
	}

	@Test
	public void errorIngore() {
		ErrorModel item = new ErrorModel();
		tool.attributeErrorHander = new AttribiuteErrorIgnoreHandler();
		String json1 = tool.serialize(item);
//		System.out.println(json1);
		String expected1 ="{'id':1}";
		Assert.assertEquals(expected1, json1);
	}
	
	
	@Test
	public void errorLog() {
		ErrorModel item = new ErrorModel();
		tool.attributeErrorHander = new MyLogAttribiuteErrorHandler();
		String json1 = tool.serialize(item);
		System.out.println(json1);
		String expected1 ="{'id':1}";
		Assert.assertEquals(expected1, json1);
	}
	
	static class MyLogAttribiuteErrorHandler extends AttribiuteErrorHandler{
		public void process(Object obj,String key,JsonWriter w,Throwable ex){
			if(ex instanceof  InvocationTargetException){
				System.out.println("error:"+key);
			}
			else{
				throw new RuntimeException(ex);
			}
			
		}
	}

	

}
