package com.rann.offer.problem33;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Problem33 把数组排成最小的数
 *
 * @author lemonjing
 */
public class PrintMinNumber {
    public String print(int[] array) {

        Integer[] targetArr = new Integer[array.length];

        for (int i = 0; i < array.length; i++) {
            targetArr[i] = array[i];
        }

        Arrays.sort(targetArr, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                String s1 = String.valueOf(o1) + String.valueOf(o2);
                String s2 = String.valueOf(o2) + String.valueOf(o1);

                return s1.compareTo(s2);
            }
        });

        StringBuilder sb = new StringBuilder();

        for (int val : targetArr) {
            sb.append(val);
        }
        return sb.toString();
    }
}
