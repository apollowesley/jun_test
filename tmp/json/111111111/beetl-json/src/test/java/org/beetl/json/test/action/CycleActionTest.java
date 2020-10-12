package org.beetl.json.test.action;

import org.beetl.json.JsonTool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * cycle include &  cycle exlcude
 * @author joelli
 *
 */
public class CycleActionTest {

	
	JsonTool tool = new JsonTool();
	@Before
	public void setUp() throws Exception {
		tool.singleQuotes = true;
		tool.orderAttribute = true;
		tool.addAlias("action", "org.beetl.json.test.action"); 
	}
	


	@Test
	public void excludeDept() {
		Department dept = getDept();
		String json1 = tool.serialize(dept, "~L/#action.Department/:ci/users/");
//		System.out.println(json1);
		String expected1 ="{'id':0,'users':[{'name':'user1','dept':{'$ref':'$','id':0}},{'name':'user2','dept':{'$ref':'$','id':0}}]}";
		Assert.assertEquals(expected1, json1);
	}
	
	//@Test
	public void excludeUser() {
		Department dept = getDept();
		String json1 = tool.serialize(dept, "~L/#action.SysUser/:ci/dept/,~L/#action.Department/:ci/users/");
//		System.out.println(json1);
		String expected1 ="{'id':0,'users':[{'name':'user1','dept':{'id':0}},{'name':'user2','dept':{'id':0}}]}";
		Assert.assertEquals(expected1, json1);
	}
//	
//	@Test
	public void excludeUser2() {
		SysUser user = getUser();
		String json1 = tool.serialize(user, "~L/#action.SysUser/:ci/dept/,~L/#action.Department/:ci/users/");
//		System.out.println(json1);
		String expected1 ="{'name':'user1','dept':{'id':0,'users':[{'$ref':'$','name':'user1'},{'name':'user2','dept':{'$ref':'$.dept','id':0}}]}}";
		Assert.assertEquals(expected1, json1);
	}
//	
//	@Test
	public void includeUser() {
		Department dept = getDept();
		String json1 = tool.serialize(dept, "~L/#action.SysUser/:cu/name/,~L/#action.Department/:cu/id/");
//		System.out.println(json1);
		String expected1 ="{'id':0,'users':[{'name':'user1','dept':{'$ref':'$','id':0}},{'name':'user2','dept':{'$ref':'$','id':0}}]}";
		Assert.assertEquals(expected1, json1);
	}
	
//	@Test
	public void includeUser2() {
		SysUser user = getUser();
		String json1 = tool.serialize(user, "~L/#action.SysUser/:cu/name/,~L/#action.Department/:cu/id/");
//		System.out.println(json1);
		String expected1 ="{'name':'user1','dept':{'id':0,'users':"
				+ "[{'$ref':'$','name':'user1'},{'name':'user2','dept':{'$ref':'$.dept','id':0}}]}}";
		Assert.assertEquals(expected1, json1);
	}
	

	public Department getDept(){
		Department dept = new Department();
		dept.id=0;
//		dept.name="部门";
//		
		SysUser user = new SysUser();
		user.setDept(dept);
//		user.setId(1);
		user.setName("user1");
		dept.users.add(user);
		
		user = new SysUser();
		user.setDept(dept);
//		user.setId(2);
		user.setName("user2");
		dept.users.add(user);
		return dept;
		
	}
	
	public SysUser getUser(){
		Department dept = new Department();
		dept.id=0;
//		dept.name="部门";
//		
		SysUser user = new SysUser();
		user.setDept(dept);
//		user.setId(1);
		user.setName("user1");
		dept.users.add(user);
		
		SysUser user1 = new SysUser();
		user1.setDept(dept);
//		user.setId(2);
		user1.setName("user2");
		dept.users.add(user1);
		return user;
		
	}
	

}
