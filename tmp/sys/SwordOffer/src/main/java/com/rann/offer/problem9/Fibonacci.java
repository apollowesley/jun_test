package com.rann.offer.problem9;

/**
 * Problem9
 * <p>
 * 斐波那契数列
 */
public class Fibonacci {

    public int fibRecursively(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("the param is less than 0");
        }
        if (0 == n) {
            return 0;
        }
        if (1 == n) {
            return 1;
        }
        return fibRecursively(n - 1) + fibRecursively(n - 2);
    }

    public int fib(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("the param is less than 0");
        }

        int res[] = {0, 1};
        if (n < 2) {
            return res[n];
        }

        int fib1 = 0;
        int fib2 = 1;
        int fibN = 0;

        for (int i = 2; i <= n; i++) {
            fibN = fib1 + fib2;
            fib1 = fib2;
            fib2 = fibN;
        }

        return fibN;
    }
}
