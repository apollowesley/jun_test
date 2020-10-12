package com.kind.samples.patterns.singleton;

import com.kind.samples.patterns.singleton.impl.SingletonFactory;
import com.kind.samples.patterns.singleton.impl.SingletonTest;

public class Client {

    public void test() {
        Factory factory = SingletonFactory.getInstance();
        System.out.println("count: " + factory.getCount());
    }

    public void testSingleton() {
        SingletonTest test = SingletonTest.getInstance();
        test.printCount();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Client client = new Client();
        for (int i = 0; i < 10; i++) {
            client.test();
        }
        client.testSingleton();
    }

}
