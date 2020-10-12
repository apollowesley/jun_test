package org.chinasb.framework.application.modules.demo.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.chinasb.framework.core.base.test.BaseTestTemplate;
import org.junit.Assert;
import org.junit.Test;

public class MemcachedUtilsTest extends BaseTestTemplate {

    @Resource
    private MemcachedClient memcachedClient;

    @Test
    public void testAdd() throws TimeoutException, InterruptedException, MemcachedException {
        memcachedClient.add("test", 0, "hello test.");
    }

    @Test
    public void testGet() throws TimeoutException, InterruptedException, MemcachedException {
        String str = memcachedClient.get("test");
        Assert.assertEquals(str, "hello test.");
    }

    @Test
    public void testMemcached() {
        try {
            memcachedClient.set("hello", 0, "Hello,xmemcached");
            String value = memcachedClient.get("hello");
            System.out.println("hello=" + value);
            memcachedClient.delete("hello");
            value = memcachedClient.get("hello");
            System.out.println("hello=" + value);
        } catch (MemcachedException e) {
            System.err.println("MemcachedClient operation fail");
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.err.println("MemcachedClient operation timeout");
            e.printStackTrace();
        } catch (InterruptedException e) {
            // ignore
        }
        // close memcached
        try {
            memcachedClient.shutdown();
        } catch (IOException e) {
            System.err.println("Shutdown MemcachedClient fail");
            e.printStackTrace();
        }
    }
}
