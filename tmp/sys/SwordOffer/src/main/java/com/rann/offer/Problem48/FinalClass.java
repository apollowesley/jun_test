package com.rann.offer.Problem48;

/**
 * Problem48
 * 不能被继承的类
 *
 * @author lemonjing
 */
public class FinalClass {

    public static FinalClass getInstance() {
        return new FinalClass();
    }

    private FinalClass() {
    }
}
