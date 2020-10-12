package com.foo.concurrency;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Test AtomicInteger
 *
 * @author Dean
 */
public class AtomicIntegerTest {
    final private static Logger logger = LoggerFactory.getLogger(AtomicIntegerTest.class);

    private final AtomicInteger one = new AtomicInteger();

    @Test
    public void test() {
        Assert.assertEquals(0, one.get());

        Assert.assertEquals(0, one.getAndIncrement());

        Assert.assertEquals(1, one.getAndIncrement());

        Assert.assertEquals(2, one.get());

        Assert.assertEquals(3, one.incrementAndGet());
        Assert.assertEquals(4, one.incrementAndGet());
        logger.info("{}", "123");

    }
}
