package com.rann.offer.problem34;

/**
 * Problem34
 * 丑数:只包含因子2,3,5的数称为丑数
 *
 * @author lemonjing
 */
public class UglyNumberWay2 {
    public int getUglyNumber(int n) {
        if (n <= 0) {
            return 0;
        }

        int[] uglyArr = new int[n];
        uglyArr[0] = 1;
        int m2 = 0, m3 = 0, m5 = 0;

        for (int i = 1; i < uglyArr.length; i++) {
            int min = getMin(uglyArr[m2] * 2, uglyArr[m3] * 3, uglyArr[m5] * 5);

            uglyArr[i] = min;

            while (uglyArr[m2] * 2 <= min) {
                m2++;
            }
            while (uglyArr[m3] * 3 <= min) {
                m3++;
            }
            while (uglyArr[m5] * 5 <= min) {
                m5++;
            }
        }

        return uglyArr[n - 1];
    }

    private int getMin(int number1, int number2, int number3) {
        int min = (number1 < number2) ? number1 : number2;
        return min < number3 ? min : number3;
    }

    public static void main(String[] args) {
        System.out.println(new UglyNumberWay2().getUglyNumber(11));
    }
}
