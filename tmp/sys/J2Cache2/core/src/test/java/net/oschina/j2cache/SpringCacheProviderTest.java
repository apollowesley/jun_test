/**
 * 
 */
package net.oschina.j2cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-test.xml"})
public class SpringCacheProviderTest{

	@Test
	public void test(){
		CacheChannel cache = J2Cache.getChannel();
		
		cache.set("test", "hello", "world");
		
		CacheObject object = cache.get("test", "hello");
		System.out.println(object.getValue());
	}
	
}
