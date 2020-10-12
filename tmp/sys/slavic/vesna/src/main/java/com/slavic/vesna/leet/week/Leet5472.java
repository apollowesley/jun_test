package com.slavic.vesna.leet.week;

import java.util.Arrays;

public class Leet5472 {

    public String restoreString(String s, int[] indices) {

        if (null == s || indices == null) return null;
        if (s.length() == 0 || indices.length == 0) return s;

        char[] arr = new char[s.length()];

        for (int i = 0; i < indices.length; i++) {
            arr[indices[i]] = s.charAt(i);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) sb.append(arr[i]);

        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println(new Leet5472().restoreString("codeleet", new int[]{4, 5, 6, 7, 0, 2, 1, 3}));
        System.out.println(new Leet5472().restoreString("abc", new int[]{0, 1, 2}));
        System.out.println(new Leet5472().restoreString("aiohn", new int[]{3, 1, 4, 2, 0}));
        System.out.println(new Leet5472().restoreString("aaiougrt", new int[]{4, 0, 2, 6, 7, 3, 1, 5}));
        System.out.println(new Leet5472().restoreString("art", new int[]{1, 0, 2}));

    }

}
