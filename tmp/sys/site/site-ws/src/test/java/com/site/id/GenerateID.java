package com.site.id;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

@SuppressWarnings({ "static-access", "unused" })
public class GenerateID {

    private static final long ONE_STEP = 10;
    private static final long BASE = 1129703383453l;
    private static final Lock LOCK = new ReentrantLock();
    private static long lastTime = System.currentTimeMillis();
    private static short lastCount = 0;

    private final long time;

    private final short count;

    public GenerateID() {
        LOCK.lock();
        try {
            if (lastCount == ONE_STEP) {
                boolean done = false;
                while (!done) {
                    long now = System.currentTimeMillis();
                    if (now == lastTime) {
                        // pause for a second to wait for time to change
                        try {
                            Thread.currentThread().sleep(1);
                        } catch (java.lang.InterruptedException e) {
                        } // ignore exception
                        continue;
                    } else {
                        lastTime = now;
                        lastCount = 0;
                        done = true;
                    }
                }
            }
            time = lastTime;
            count = lastCount++;
        } finally {
            LOCK.unlock();
        }
    }

    @Test
    public void testGetId() {

        try {
            System.out.println(new GenerateID().clone());
            System.out.println(new GenerateID().clone());
            System.out.println(new GenerateID().clone());
            System.out.println(new GenerateID().clone());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
