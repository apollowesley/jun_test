package com.rann.offer.Problem44;

/**
 * Created by lemonjing on 2016/8/1.
 */
public class Test {
    public static void main(String[] args) {
        int[] a = new int[] {1, 2, 3, 5, 0};
        int[] b = new int[] {1, 2, 4, 0, 0};
        System.out.println(new SortPoker().isContinuous(a));
        System.out.println(new SortPoker().isContinuous(b));
    }
}
