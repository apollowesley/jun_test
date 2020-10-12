package com.rann.offer.problem38;

/**
 * 排序数组中指定数字出现的次数
 * 二分 firstK & lastK O(lgn)
 */
public class TimesOfDigitInSortedArray {

    public int GetNumberOfK(int[] a, int k) {
        if (a == null || a.length <= 0) return 0;
        int left = binarySearch(a, 0, a.length - 1, k);
        if (left == -1) return 0;
        int right = left;
        while (left > 0 && a[left - 1] == k) {
            left = binarySearch(a, 0, left - 1, k);
        }
        while (right < a.length - 1 && a[right + 1] == k) {
            right = binarySearch(a, right + 1, a.length - 1, k);
        }
        return right - left + 1;
    }

    private int binarySearch(int[] a, int low, int high, int num) {
        if (a == null || a.length <= 0) return -1;
        while (low <= high) {
            int mid = (low + high) >> 1;
            if (a[mid] > num) {
                high = mid - 1;
            } else if (a[mid] < num) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
