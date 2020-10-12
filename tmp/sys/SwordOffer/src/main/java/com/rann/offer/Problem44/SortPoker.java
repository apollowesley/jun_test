package com.rann.offer.Problem44;

import java.util.Arrays;

/**
 * Problem44
 * 扑克牌的顺子
 * 0可以充当任意数
 *
 * 如 0 1 3 4 5 => true
 * @author lemonjing
 */
public class SortPoker {
    public boolean isContinuous(int[] a) {
        if (null == a || a.length != 5) {
            return false;
        }

        Arrays.sort(a);
        int numberOfZero = 0;
        int numberOfGap = 0;

        // 统计数组中0的个数
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 0) {
                numberOfZero++;
            } else {
                break;
            }
        }

        // 统计数组中的间隔数目
        int small = numberOfZero;
        int big = small + 1;
        while (big < a.length) {
            if (a[small] == a[big]) {
                return false;
            }
            numberOfGap += (a[big] - a[small] - 1);
            small = big;
            big++;
        }

        return (numberOfGap > numberOfZero) ? false : true;
    }
}
