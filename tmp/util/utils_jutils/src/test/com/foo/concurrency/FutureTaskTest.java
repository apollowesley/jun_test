package com.foo.concurrency;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * FutureTask可以用做闭锁，实现了Future语义，表示一种抽象的可生成结果的计算
 * <p>
 * 三个线程,主线程在一定时间后返回一个值,剩下2个线程一个测试:get(),另外一个测试:get(long timeout, TimeUnit unit)
 *
 * @author Dean, Steve
 */
public class FutureTaskTest {

    final private static Logger logger = LoggerFactory.getLogger(FutureTaskTest.class);

    @Test
    public void test() throws InterruptedException {

        final int maxWaitSeconds = 15;
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            int cash = new Random().nextInt(1000000);
            logger.info("wait for a while...Opening in:{} seconds.", maxWaitSeconds);
            TimeUnit.SECONDS.sleep(maxWaitSeconds);
            return cash;
        });
        new Thread(futureTask).start();//DO NOT use run,it'll block current thread.

        new Thread(() -> {

            while (!futureTask.isDone()) {
                try {
                    logger.info("i will wait for a another while.");
                    TimeUnit.SECONDS.sleep(maxWaitSeconds / 3);
                } catch (InterruptedException e) {
                    logger.error("{}", e);
                }
            }

            try {
                //this will block until get.
                int cash = futureTask.get();
                logger.info("aha,finally get money of:{}", cash);

            } catch (InterruptedException | ExecutionException e) {
                logger.error("{}", e);
            }
        }, "LazyMan").start();

        new Thread(() -> {
            int timeout = 2;
            logger.info("max wait for:{}", timeout);

            try {
                int cash = futureTask.get(timeout, TimeUnit.SECONDS);
                logger.info("aha,finally get money of:{}", cash);
            } catch (Exception e) {
                logger.info("Sorry i can not wait.88");
            }
        }, "ImpatienceMan").start();

        TimeUnit.SECONDS.sleep(maxWaitSeconds + 5);
        logger.info("Close now.");
    }

}
