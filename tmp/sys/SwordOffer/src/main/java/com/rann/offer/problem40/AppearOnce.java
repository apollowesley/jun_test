package com.rann.offer.problem40;

/**
 * Problem40 数组中两个数字出现一次，其他都出现两次，找出这两个数字
 *
 * @author lemonjing
 */
public class AppearOnce {
    public int[] find(int[] a) {
        if (null == a || a.length <= 1) {
            return null;
        }
        int number = 0;
        for (int i : a) {
            number ^= i;
        }
        int index = findFirstBitIs1(number);
        int number1 = 0, number2 = 0;

        // 划分两组
        for (int i : a) {
            if (isBit1(i, index)) {
                number1 ^= i;
            } else {
                number2 ^= i;
            }
        }

        return new int[]{number1, number2};
    }

    // 找出number的二进制中最右边是1的位
    private int findFirstBitIs1(int number) {
        int indexBit = 0;
        while ((number & 1) == 0) {
            number >>= 1;
            ++indexBit;
        }

        return indexBit;
    }

    // 判断number的二进制表示中从右边起的第index位是不是1
    private boolean isBit1(int number, int indexBit) {
        number >>= indexBit;

        return (number & 1) == 1;
    }
}
