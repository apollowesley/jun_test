package com.slavic.vesna.leet.leet;

public class Leet0011 {

    // 1暴力法   双重循环，穷举每两个柱子间的面积
    public int maxArea(int[] height) {

        int res = 0;
        for (int i = 0; i < height.length; i++)
            for (int i1 = i + 1; i1 < height.length; i1++)
                res = Math.max(res, Math.min(height[i], height[i1]) * (i1 - i));

        return res;
    }

    // 双指针， 移动较小的指针，直到两指针相遇
    public int maxArea2(int[] height) {
        int res = 0;
        for (int i = 0, j = height.length - 1; i <= j; ) {
            res = Math.max(res, Math.min(height[i], height[j]) * (j - i));
            if (height[i] < height[j]) {
                i++;
            } else if (height[i] == height[j]) {
                i++;
                j--;
            } else {
                j--;
            }
        }
        return res;
    }
}
