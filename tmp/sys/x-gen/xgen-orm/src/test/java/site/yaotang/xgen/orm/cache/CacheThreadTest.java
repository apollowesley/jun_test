package site.yaotang.xgen.orm.cache;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class CacheThreadTest {

	@Test
	public void test() throws Exception {

		new CacheThread().start();

		CacheFactory.put("a1", "a", 1);
		CacheFactory.put("a2", "a", 1);
		CacheFactory.put("a3", "a", 1);
		CacheFactory.put("a4", "a", 1);

		TimeUnit.SECONDS.sleep(8);
	}

}
