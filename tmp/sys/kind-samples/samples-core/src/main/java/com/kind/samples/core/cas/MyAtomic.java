package com.kind.samples.core.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Atomic原子性测试。
 *
 * @author cary
 * @version 1.0.0
 */
public class MyAtomic {
    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger(0);
        int i1 = ai.get();
        print(i1);
        int i2 = ai.getAndSet(5);
        print(i2);
        int i3 = ai.get();
        print(i3);
        int i4 = ai.getAndIncrement();
        print(i4);
        print(ai.get());
    }

    static void print(int i) {
        System.out.println("i : " + i);
    }
}
