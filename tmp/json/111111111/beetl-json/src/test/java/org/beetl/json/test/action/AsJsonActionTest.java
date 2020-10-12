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
public class AsJsonActionTest {


	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
	}
	

	@Test
	public void asJsonTest() {
		String fun = "function(){alert(\"run json\")}";
		Map data = new HashMap();
		data.put("success", fun);
		data.put("info","server error,try again!");
		String json1 = tool.serialize(data, "[success]:asJson");
//		System.out.println(json1);
		String expected1 ="{'success':function(){alert(\"run json\")},'info':'server error,try again!'}";
		Assert.assertEquals(expected1, json1);
	}
	
	



	

}
