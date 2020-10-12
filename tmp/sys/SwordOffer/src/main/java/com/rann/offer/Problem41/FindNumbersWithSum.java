package com.rann.offer.Problem41;

/**
 * Problem41 TwoSum
 * 查找排序数组中等于指定和的两个数
 * way1 固定1个数字 O(n^2)
 * way2 双指针两端向中间扫描O(n)
 * 如 1,2,4,7,11,15和15 => 4,11
 */
public class FindNumbersWithSum {

    public int[] find(int[] a, int n) {
        if (null == a || a.length <= 0) {
            return null;
        }

        int low = 0;
        int high = a.length - 1;

        while (low < high) {
            int curSum = a[low] + a[high];
            if (curSum == n) {
                return new int[] {a[low], a[high]};
            } else {
                if (curSum > n) {
                    high--;
                } else {
                    low ++;
                }
            }
        }

        return null;
    }

}
