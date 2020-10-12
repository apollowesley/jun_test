package org.beetl.json.test.cycle;

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
public class CycleRefActionTest {

	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
	}

	@Test
	public void cycle() {
		User user = new User(1);
		String json1 = tool.serialize(user);
//		System.out.println(json1);
		String expected1 ="{'id':1,'self':{'$ref':'$'}}";
		Assert.assertEquals(expected1, json1);

		
	}
	
	@Test
	public void cycle1() {
		User1 user = new User1(1);
		String json1 = tool.serialize(user);
//		System.out.println(json1);
		String expected1 ="{'id':1,'list':[{'$ref':'$'},{'$ref':'$'}]}";
		Assert.assertEquals(expected1, json1);

		
	}
	
	@Test
	public void cycle3() {
		List list = new ArrayList();
		
		User3 user = new User3();
		user.setId(1);
		User3 friend = new User3();
		friend.setId(2);
		user.setFriend(friend);
		friend.setFriend(user);
		list.add(user);
		list.add(friend);
		String json1 = tool.serialize(list);
//		System.out.println(json1);
		String expected1 ="[{'id':1,'friend':{'id':2,'friend':{'$ref':'$[0]'}}},{'id':2,'friend':{'id':1,'friend':{'$ref':'$[1]'}}}]";
		Assert.assertEquals(expected1, json1);

		
	}
	
	@Test
	public void cycle4() {
		User1 user = new User1(1);
		User1 user2 = new User1(2);
		user.list = new ArrayList();
		user.list.add(user2);
		Map map = new HashMap();
		map.put("user", user);
		map.put("user2", user2);
		
		
		String json1 = tool.serialize(map);
		System.out.println(json1);
		String expected1 ="{'user2':{'id':2,'list':[{'$ref':'$.user2'},{'$ref':'$.user2'}]},'user':{'id':1,'list':[{'id':2,'list':[{'$ref':'$.user.list[0]'},{'$ref':'$.user.list[0]'}]}]}}";
		Assert.assertEquals(expected1, json1);
//
//		
	}
	

	

}
