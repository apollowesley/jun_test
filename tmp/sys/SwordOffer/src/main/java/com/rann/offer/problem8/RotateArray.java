package com.rann.offer.problem8;

/**
 * Problem8
 * 求旋转数组的最小元素O(lgn)
 *
 * @author lemonjing
 */
public class RotateArray {
    public Integer min(int[] a) {
        if (null == a || a.length < 1) {
            return null;
        }
        int low = 0;
        int high = a.length - 1;
        int mid = low;
        while (a[low] >= a[high]) {
            if (high - low == 1) {
                mid = high;
                break;
            }
            mid = (low + high) / 2;
            if (a[low] == a[high] && a[low] == a[mid]) {
                return getMinInOrder(a, low, high);
            }
            if (a[mid] >= a[low]) {
                low = mid;
            } else if (a[mid] <= a[high]) {
                high = mid;
            }
        }

        return a[mid];
    }

    /**
     * 当low, mid, high相等时我们无法判断a[mid]属于哪个递增子数组时
     * 只能进行顺序查找
     *
     * @param a
     * @param low
     * @param high
     * @return
     */
    private int getMinInOrder(int[] a, int low, int high) {
        int res = a[low];
        for (int i = low + 1; i <= high; i++) {
            if (a[i] < res) {
                res = a[i];
            }
        }

        return res;
    }


}
