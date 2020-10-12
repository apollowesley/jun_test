package com.rann.offer.problem36;

/**
 * Problem36
 * 数组中的逆序对
 * <p>
 * way1 一一比较O(n^2)
 * way2 -- 归并排序O(nlgn)
 *
 * @author lemonjing
 */
public class InversePairs {

    public int inversePairs(int[] data) {
        if (data == null || data.length <= 0) return 0;
        int[] temp = new int[data.length];
        return core(data, temp, 0, data.length - 1);
    }

    private int core(int[] data, int[] temp, int start, int end) {
        if (start == end) {
            temp[start] = data[start];
            return 0;
        }

        int mid = (start + end) >> 1;
        int left = core(data, temp, start, mid);
        int right = core(data, temp, mid + 1, end);

        int i = mid;
        int j = end;
        int k = end;
        int count = 0;
        while (i >= start && j >= mid + 1) {
            if (data[i] > data[j]) {
                temp[k--] = data[i--];
                count += (j - mid);
            } else {
                temp[k--] = data[j--];
            }
        }
        while (i >= start) {
            temp[k--] = data[i--];
        }
        while (j >= mid + 1) {
            temp[k--] = data[j--];
        }
        for (int idx = start; idx <= end; idx++) {
            data[idx] = temp[idx];
        }
        return left + right + count;
    }

    public static void main(String[] args) {
        int[] a = {7, 5, 6, 4};
        System.out.println(new InversePairs().inversePairs(a));
    }
}
