package com.rann.offer.problem14;

/**
 * Problem14
 * 调整数组使奇数位于偶数前面
 *
 * @author lemonjing
 */
public class Reorder {

    public void order(int[] a) {
        if (null == a || a.length <= 1) {
            return;
        }
        int low = 0;
        int high = a.length - 1;

        while (low < high) {
            while (low < high && (a[high] & 1) == 0) {
                high--;
            }
            while (low < high && (a[low] & 1) == 1) {
                low++;
            }
            if (low < high) {
                int temp = a[high];
                a[high] = a[low];
                a[low] = temp;
            }
        }
        return;
    }
}
