package com.mkfree.datastructure;

/**
 *
 * 等差数列
 *
 */
public class EqualDifferenceSeries {

    public static void main(String[] args) {
        // 等差数列求和 3位数加起来的和

        // 1 硬加 时间复杂度 O(n)
        int sum = 0;
        for (int i = 100; i < 1000; i++) {
            sum += i;
        }
        System.out.println(sum);

        // 1 使用求和公式 时间复杂度O(1)
        sum = (100 + 999) * 900 / 2;
        System.out.println(sum);


    }
}
