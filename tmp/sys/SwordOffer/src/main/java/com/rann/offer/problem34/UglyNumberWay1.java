package com.rann.offer.problem34;

/**
 * Problem34
 * 求从小到大的第1500个丑数
 * 丑数:只包含因子2,3,5的数称为丑数
 * 该方法复杂度过大
 * @author lemonjing
 */
public class UglyNumberWay1 {
    public int getUglyNumber(int index) {
        if (index <= 0) {
            return 0;
        }
        int number = 0;
        int count = 0;
        while (count < index) {
            number++;
            if (isUgly(number)) {
                count++;
            }
        }
        return number;
    }

    private boolean isUgly(int number) {
        while (number % 2 == 0) {
            number /= 2;
        }
        while (number % 3 == 0) {
            number /= 3;
        }
        while (number % 5 == 0) {
            number /= 5;
        }

        return 1 == number;
    }
}
