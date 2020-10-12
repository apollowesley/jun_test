/**
 * Project Name:java-core
 * Package Name:com.kind.core.thread.sync
 * Created on:2016年9月19日下午3:34:11
 * Copyright (c) 2016, http://www.mcake.com All Rights Reserved.
 */

package com.kind.samples.core.threads.sync;

/**
 * Function: TODO ADD FUNCTION. <br/>
 *
 * @author weiguo.liu
 * @see
 * @since JDK 1.7
 */
public class InvokedObject {

    public synchronized void sync1() {
        System.out.println("Invoked sync1 !");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sync2() {
        System.out.println("Invoked sync2 !");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void generalMethod() {
        System.out.println("Invoked generalMethod ...");
    }

}
