package com.slavic.vesna.leet.leet;

public class Leet0153 {

    public int findMin(int[] nums) {

        int l = 0, h = nums.length - 1;
        while (l < h) {
            int p = l + (h - l) / 2;
            if (nums[p] < nums[h]) h = p;
            else if (nums[p] > nums[h]) l = p + 1;
            else h--;
        }

        return nums[l];
    }

}
