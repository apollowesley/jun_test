package com.rann.offer.problem28;

public class NQueens {

    private int[] cols;
    private int sum;

    public void queens(int n) {
        cols = new int[n];
        recursiveBacktrack(0, n);
        System.out.println("放置方式共：" + sum + "种");
    }

    private void recursiveBacktrack(int k, int n) {
        if (k == n) {
            sum++;
            System.out.println("第" + sum + "种方法");
            for (int i = 0; i < n; i++) {
                System.out.print(i + "行" + cols[i] + "列" + ",");
            }
            System.out.println();

        } else {
            for (int i = 0; i < n; i++) {
                cols[k] = i;
                if (place(k)) {
                    recursiveBacktrack(k + 1, n);
                }
            }
        }
    }

    private boolean place(int i) {
        for (int j = 0; j < i; j++) {
            if (cols[i] == cols[j] || Math.abs(cols[i] - cols[j]) == Math.abs(i - j)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new NQueens().queens(8);
    }
}
