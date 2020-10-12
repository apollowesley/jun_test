package com.rann.offer.problem3;

/**
 * Problem3
 * 二维数组查找指定数
 *
 * @author lemonjing
 */
public class Find {
    public boolean find(int[][] a, int number) {
        if (null == a || a.length <= 0) {
            return false;
        }
        int row = 0;
        int column = a[0].length - 1;
        while (row < a.length && column >= 0) {
            if (number == a[row][column]) {
                return true;
            } else if (a[row][column] > number) {
                column--;
            } else {
                row++;
            }
        }
        return false;
    }
}
