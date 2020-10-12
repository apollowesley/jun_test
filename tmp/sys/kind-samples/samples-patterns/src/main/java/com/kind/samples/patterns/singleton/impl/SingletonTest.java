package com.kind.samples.patterns.singleton.impl;

public class SingletonTest {
    // public static SingletonTest testClient = new SingletonTest();
    private static int count1;
    private static int count2 = 0;
    public static SingletonTest test = new SingletonTest();
    /* different position will cause different output */

    private SingletonTest() {
        count1++;
        System.out.println("count1 in constructor: " + count1);
        count2++;
        System.out.println("count2 in constructor: " + count2);
    }

    public static SingletonTest getInstance() {
        return test;
    }

    public void printCount() {
        System.out.println("count1 in printCount: " + count1);
        System.out.println("count2 in printCount: " + count2);
    }
}
