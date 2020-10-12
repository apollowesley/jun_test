package com.slavic.vesna.leet.week;

public class Leet5464 {

    public static void main(String[] args) {
        System.out.println(new Leet5464().numWaterBottles2(9, 3));
        System.out.println(new Leet5464().numWaterBottles2(15, 4));
        System.out.println(new Leet5464().numWaterBottles2(5, 5));
        System.out.println(new Leet5464().numWaterBottles2(2, 3));
    }

    // 递归
    public int numWaterBottles(int numBottles, int numExchange) {
        return numWaterBottles(numBottles, numExchange, 0);
    }
    public int numWaterBottles(int n, int e, int k) {
        if (k + n < e) return n;
        return n + numWaterBottles((n + k) / e, e, (n + k) % e);
    }

    //循环
    public int numWaterBottles2(int numBottles, int numExchange) {

        int sum = numBottles;
        for (int e = numBottles; e >= numExchange;) {
            int i = (sum += e / numExchange) & (e = e % numExchange + e / numExchange);
        }

        return sum;
    }
}
