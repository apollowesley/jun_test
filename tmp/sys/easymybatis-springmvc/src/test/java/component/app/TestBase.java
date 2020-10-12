package component.app;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestBase extends AbstractTransactionalJUnit4SpringContextTests {
	public void print(Object o) {
		System.out.println("=================");
		System.out.println(o);
		System.out.println("=================");
	}
	
	public void printJson(Object o) {
		System.out.println("=================");
		System.out.println(JSON.toJSONString(o));
		System.out.println("=================");
	}
}