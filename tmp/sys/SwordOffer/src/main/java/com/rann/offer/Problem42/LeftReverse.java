package com.rann.offer.Problem42;

/**
 * Created by lemonjing on 2016/8/1.
 * 左旋字符串 abcdefg,2 => cdefgab
 */
public class LeftReverse {

    public String leftReverse(String s, int n) {
        if (null == s || s.length() <= 0) {
            return null;
        }
        if (n < 0 || n > s.length()) {
            return null;
        }
        String reverse1 = core(s.substring(0, n));
        String reverse2 = core(s.substring(n, s.length()));

        System.out.println(reverse1);
        System.out.println(reverse2);

        String wholeReverse = reverse1 + reverse2;
        String res = core(wholeReverse);

        return res;
    }

    private String core(String s) {
        char[] array = s.toCharArray();
        for (int i = 0; i < array.length/2; i++) {
            char temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }

        return String.valueOf(array);
    }

    public String LeftRotateString(String str,int n) {
        if (str == null || str.length() <= 0 || n <=0 || n>str.length()) return str;
        String s1 = str.substring(0, n);
        String s2 = str.substring(n, str.length());
        String reverse1 = core(s1, 0, s1.length()-1);
        String reverse2 = core(s2, 0, s2.length()-1);
        String s3 = reverse1 + reverse2;
        return core(s3, 0, s3.length()-1);
    }

    private String core(String str, int low, int high) {
        char[] chArr = str.toCharArray();
        int mid = (low + high) >> 1;
        for (int i=low; i<=mid; i++) {
            char temp = chArr[i];
            chArr[i] = chArr[high];
            chArr[high] = temp;
            high--;
        }
        return String.valueOf(chArr);
    }
}
