package com.kind.samples.core.exception;

public class ExceptionTest {
    public void testFinally() {
        try {
            System.out.println("abc");
            return;
        } catch (Exception e) {
            System.out.println("bbc");
            return;
        } finally {
            System.out.println("cbc");
        }
    }

    public static void main(String[] args) {
        ExceptionTest test = new ExceptionTest();
        test.testFinally();
    }

}
