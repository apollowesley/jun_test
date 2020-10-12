package com.rann.offer.problem30;

/**
 * Problem 30
 * 基于partition的O(n)解法
 * 需要修改数组
 *
 * @author lemonjing
 */
public class GetLeastNumbersWithPartition {

    public void getLeastNumbers(int[] a, int k) {
        if (null == a || a.length <= 0 || k <= 0 || k > a.length) {
            return;
        }

        int low = 0;
        int high = a.length - 1;
        int index = partition(a, low, high, k);

        while (index != k - 1) {
            if (index > k - 1) {
                high = index - 1;
                index = partition(a, low, high, k);
            } else {
                low = index + 1;
                index = partition(a, low, high, k);
            }
        }

        for (int i = 0; i < k; i++) {
            System.out.println(a[i]);
        }
    }

    private int partition(int[] a, int low, int high, int k) {

        int temp = a[k - 1];
        a[k - 1] = a[low];
        a[low] = temp;

        int pivot = a[low];

        while (low < high) {
            while (low < high && a[high] >= pivot) {
                high--;
            }
            a[low] = a[high];
            while (low < high && a[low] <= pivot) {
                low++;
            }
            a[high] = a[low];
        }
        a[low] = pivot;

        return low;
    }
}
