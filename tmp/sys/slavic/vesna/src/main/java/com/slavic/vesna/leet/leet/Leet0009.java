package com.slavic.vesna.leet.leet;

public class Leet0009 {

    // 负数，非回文， 正数，转字符串，双指针比较前后是否相等
    public boolean isPalindrome(int x) {
        if (x < 0) return false;
        String s = String.valueOf(x);
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
    }
}
