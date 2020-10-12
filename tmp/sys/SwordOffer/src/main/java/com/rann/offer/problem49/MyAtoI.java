package com.rann.offer.problem49;

/**
 * Created by lemonjing on 2015/8/2.
 */
public class MyAtoI {

    public int myAtoi(String s) {
        int index = 0, sign = 1, total = 0;

        // 1. handle invalid input
        if (null == s || s.length() <= 0) {
            throw new NumberFormatException("cast error");
        }

        // 2.handle white spaces
        while (s.charAt(index) == ' ' && index < s.length()) {
            index++;
            if (index == s.length()) {
                throw new NumberFormatException("cast error");
            }
        }

        // 3.handle signs and single +/-
        if (s.charAt(index) == '+' || s.charAt(index) == '-') {
            sign = s.charAt(index) == '+' ? 1 : -1;
            index++;
            if (index == s.length()) {
                throw new NumberFormatException("cast error");
            }
        }

        // 4.convert string and avoid overflow
        while (index < s.length()) {
            int digit = s.charAt(index) - '0';
            if (digit < 0 || digit > 9) {
                throw new NumberFormatException("cast error");
            }

            if (total > Integer.MAX_VALUE / 10 || (total == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            total = total * 10 + digit;
            index++;
        }

        return sign * total;
    }

}
