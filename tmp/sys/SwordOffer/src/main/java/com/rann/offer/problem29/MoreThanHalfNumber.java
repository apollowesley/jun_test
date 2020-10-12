package com.rann.offer.problem29;

/**
 * Problem 29
 * 找数组中出现超过一半的数字
 *
 * @author lemonjing
 */
public class MoreThanHalfNumber {
    public int findNum(int[] a) {
        int noAnswer = 0x7fffffff;

        if (null == a || a.length <= 0) {
            return noAnswer;
        }

        int res = a[0];
        int count = 1;

        for (int i = 1; i < a.length; i++) {
            if (count == 0) {
                res = a[i];
                count = 1;
            } else if (a[i] == res) {
                count++;
            } else {
                count--;
            }
        }
        if (checkMoreThanHalf(a, res)) {
            return res;
        } else {
            return noAnswer;
        }
    }

    private boolean checkMoreThanHalf(int[] a, int num) {
        int times = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == num) {
                times++;
            }
        }
        if (2 * times <= a.length) {
            return false;
        } else {
            return true;
        }
    }
}
