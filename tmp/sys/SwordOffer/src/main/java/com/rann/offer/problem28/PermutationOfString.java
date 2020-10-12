package com.rann.offer.problem28;

import java.util.ArrayList;
import java.util.List;

/**
 * Problemn28
 * 字符串的全排列
 *
 * 涉及到2个问题，是否允许重复和字典序输出(可使用TreeSet)
 *
 * @author lemonjing
 */
public class PermutationOfString {
    public List<String> Permutation(String str) {
        if (null == str || str.length() <= 0) {
            return null;
        }

        List<String> list = new ArrayList<>();
        int low = 0;
        int high = str.length() - 1;
        permCore(str.toCharArray(), low, high, list);

        return list;
    }

    private void permCore(char[] a, int low, int high, List<String> list) {
        if (low == high) {
            // 1.是否允许重复 如aa => aa,aa
            // 2.字典序
            list.add(String.valueOf(a));
        } else {
            for (int i = low; i <= high; i++) {
                swap(a, i, low);
                permCore(a, low + 1, high, list);
                swap(a, i, low);
            }
        }
    }

    private void swap(char[] a, int i, int j) {
        char temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}


