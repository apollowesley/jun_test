package com.rann.offer.problem20;

/**
 * Problem20
 * 顺时针打印矩阵
 *
 * @author lemonjing
 */
public class PrintMatrix {
	public void printMatrixByClockWise(int[][] a) {
        if (null == a || a.length <= 0) {
            return;
        }
        int row = a.length - 1;
        int column = a[0].length - 1;

        for (int i = 0; 2 * i <= row && 2 * i <= column; i++) {
            printOneCircle(a, i, row - i, column - i);
        }
    }

    private void printOneCircle(int[][] a, int start, int row, int column) {

        // 从左到右打印一行
        for (int i = start; i <= column; i++) {
            System.out.println(a[start][i]);
        }

        // 从上向下打印一列
        if (start == row) {
            return;
        }
        for (int i = start + 1; i <= row; i++) {
            System.out.println(a[i][column]);
        }

        // 从右向左打印一行
        if (start == column) {
            return;
        }
        for (int i = column - 1; i >= start; i--) {
            System.out.println(a[row][i]);
        }

        // 从下往上打印一列
        for (int i = row - 1; i >= start + 1; i--) {
            System.out.println(a[i][start]);
        }
    }
}
