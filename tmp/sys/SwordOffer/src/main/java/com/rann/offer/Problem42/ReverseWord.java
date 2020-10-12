package com.rann.offer.Problem42;

/**
 * Created by lemonjing on 2016/8/1.
 * 翻转单词
 */
public class ReverseWord {
    public String reverse(String s) {
        if (null == s || s.length() <= 0) {
            return null;
        }
        //防止多个空格符
        if (s.trim().equals("")) return s;

        String wholeReverse = core(s);
        String[] wholeReverseArr = wholeReverse.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String val : wholeReverseArr) {
            sb.append(core(val)).append(" ");
        }

        return sb.toString().trim();
    }

    private String core(String s) {
        char[] array = s.toCharArray();
        for (int i = 0; i < array.length / 2; i++) {
            char temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }

        return String.valueOf(array);
    }
}
