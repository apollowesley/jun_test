package cn.afterturn.dao.test.examples;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.afterturn.dao.test.examples.service.UserServer;

/**
 * 单元测试
 * MiniDao支持实体维护
 * 
 * @author yanping.shi
 * 
 */
public class UserDaoJunit extends SpringTxTestCase {

	@Autowired
	private UserServer userServer;
	
	String  id = "402880e74a2f3678014a2f367b790000";
	

	
	@Test
	public void testSql() {
		Integer age = new Integer(30);
		List<Map<String, Object>> list =  userServer.listUserByAge(30);
		System.out.println("------------------------------------------------------");
		System.out.println("小于30岁人的名字");
		for(Map m:list){
			System.out.println(m.get("name"));
		}
	
	}
	
	@Test
	public void testUpSql() {
		Integer age = new Integer(20);
		userServer.updateUserBirthday("小张", age, new Date());
	
	}
	
	
	//@Test
	public void testsle() {
		//String s = userServer.sle();
	
	}

}
