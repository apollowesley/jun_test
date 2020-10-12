package com.foo.common.base.utils.laboratory;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class FooUtilsGuavaCache {
	public static void main(String[] args) throws InterruptedException {
		CacheLoader<String, String> loader = new CacheLoader<String, String>() {
			public String load(String key) {
				return key.toUpperCase();
			}
		};

		LoadingCache<String, String> cache = CacheBuilder.newBuilder()
				.maximumSize(2).expireAfterWrite(3, TimeUnit.SECONDS)
				.recordStats()
				.removalListener(new RemovalListener<String, String>() {
					@Override
					public void onRemoval(
							RemovalNotification<String, String> notification) {
						System.out.println("remove..."
								+ notification.getValue());
					}
				}).build(loader);

		System.out.println(cache.size());

		System.out.println(cache.getUnchecked("simple test"));

		cache.invalidate("simple test");

		System.out.println(cache.size());

		System.out.println(cache.getUnchecked("simple test"));

		Thread.sleep(5000);
		System.out.println(cache.getIfPresent("simple test"));

	}
}
