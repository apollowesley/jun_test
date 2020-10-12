package com.rann.offer.problem30;

import java.util.Arrays;

/**
 * Problem30
 * 最小的k个数
 * 最大堆法O(nlgk)
 *
 * @author lemonjing
 */
public class GetLeastNumbersWithHeap {

    public void getLeastNumbers(int[] a, int k) {
        if (null == a || k <= 0 || k > a.length) {
            return;
        }
        //根据输入数组前k个数建立最大堆
        //从k+1个数开始与根节点比较
        //大于根节点，舍去
        //小于，取代根节点，重排最大堆
        int[] kArr = Arrays.copyOfRange(a, 0, k);
        buildMaxHeap(kArr);
        for (int i = k; i < a.length; i++) {
            if (a[i] < kArr[0]) {
                kArr[0] = a[i];
                adjustHeap(kArr, 0);
            }
        }
        for (int i = 0; i < kArr.length; i++) {
            System.out.println(kArr[i]);
        }
    }

    private void adjustHeap(int[] a, int parent) {
        int left = 2 * parent + 1;
        int right = 2 * parent + 2;
        int largest = parent;
        if (left < a.length && a[left] > a[largest]) {
            largest = left;
        }
        if (right < a.length && a[right] > a[largest]) {
            largest = right;
        }
        if (largest != parent) {
            int temp = a[parent];
            a[parent] = a[largest];
            a[largest] = temp;
            adjustHeap(a, largest);
        }
    }

    /**
     * 建堆
     *
     * @param a
     */
    private void buildMaxHeap(int[] a) {
        for (int i = a.length / 2; i >= 0; i--) {
            adjustHeap(a, i);
        }
    }
}
