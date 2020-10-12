package com.foo.concurrency;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * url as:
 * http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Semaphore.html
 * <p>
 * Usecase:we have 3 printers,deisgn a system that manages N print tasks
 */

public class ScheduledExecutorsTest {
    final private static Logger logger = LoggerFactory.getLogger(ScheduledExecutorsTest.class);

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(3);

        ExecutorService executorService = Executors.newFixedThreadPool(20);


        List<Callable<Boolean>> callables = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            callables.add(new PrintTask(semaphore, i));
        }

        executorService.invokeAll(callables);

        executorService.shutdown();

    }

    private static class PrintTask implements Callable<Boolean> {
        private Semaphore semaphore;
        private int num;

        PrintTask(Semaphore semaphore, int num) {
            this.semaphore = semaphore;
            this.num = num;
        }

        @Override
        public Boolean call() throws Exception {
            try {
                semaphore.acquire();
                logger.info("task of:{} is printing.", num);
                Thread.sleep(3000);
                logger.info("task of:{} end printing.", num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
            return true;
        }
    }

}
