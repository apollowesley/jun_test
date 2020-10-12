package com.foo.concurrency;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Suppose that doA() must be followed by the same instance's doB()
 */
public class ReentrantLockTest {

    final private static Logger logger = LoggerFactory.getLogger(ReentrantLockTest.class);

    private void increaseX(ReentrantLock reentrantLock) throws InterruptedException {
        reentrantLock.lock();
        doA();
        doB();
        reentrantLock.unlock();
    }

    private void doA() throws InterruptedException {
        logger.info("doing A.");
    }

    private void doB() throws InterruptedException {
        logger.info("doing B.");
    }


    @Test
    public void myReentrantLock() throws ExecutionException, InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        List<Callable<Integer>> tasks = ImmutableList.of(getMyCallable(lock), getMyCallable(lock));

        List<Future> futures = Lists.newArrayList();
        futures.addAll(executorService.invokeAll(tasks));

        for (Future futureTask : futures) {
            logger.info("{}", futureTask.get());
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ReentrantLock lock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        List<Callable<Integer>> tasks = ImmutableList.of(getMyCallable(lock), getMyCallable(lock));

        List<Future> futures = Lists.newArrayList();
        futures.addAll(executorService.invokeAll(tasks));

        for (Future futureTask : futures) {
            logger.info("{}", futureTask.get());
        }
    }

    private static Callable<Integer> getMyCallable(final ReentrantLock lock) {
        return () -> {
            ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
            for (int i = 0; i < 5; i++) {
                reentrantLockTest.increaseX(lock);
                TimeUnit.SECONDS.sleep(1);
            }
            return 0;
        };
    }


}
