package com.rann.offer.problem47;

/**
 * Problem47
 *
 * 不使用加减乘除做加法
 */
public class Add {
    public int add (int num1, int num2) {
        int sum = 0;
        int carry = 0;

        do {
            sum = num1 ^ num2;
            carry = (num1 & num2) << 1;

            num1 = sum;
            num2 = carry;

        } while (num2 != 0);

        return num1;
    }

}



