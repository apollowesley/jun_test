package net.hinky.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AppTest {

	CacheManagerFactory cacheManagerFactory;
	CacheManager cacheManager;

	@Before
	public void beforeClass() {
		cacheManagerFactory = CacheManagerFactory.getInstance();
	}

	@Test
	public void test() {

		cacheManager = cacheManagerFactory.getCacheManager();
		System.out.println(cacheManager.toString());

		Cache cache = cacheManager.getCache("session");
		Assert.assertNull(cache.get("AAA"));
		cache.put("AAA", "AAA_VALUE");
		Assert.assertEquals("值不正确", "AAA_VALUE", cache.get("AAA"));

		cacheManager = cacheManagerFactory.getCacheManager();
		System.out.println(cacheManager.toString());
		Assert.assertEquals("值不正确", "AAA_VALUE", cache.get("AAA"));

		CacheManager cacheManager_1 = cacheManagerFactory
				.getCacheManager("name_1");
		System.out.println(cacheManager_1.toString());
		Assert.assertEquals("值不正确", "AAA_VALUE",
				cacheManager_1.getCache("session").get("AAA"));

	}

}
