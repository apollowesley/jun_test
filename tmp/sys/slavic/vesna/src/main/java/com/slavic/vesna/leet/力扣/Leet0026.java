package com.slavic.vesna.leet.力扣;

public class Leet0026 {

    // 双指针法
    public int removeDuplicates(int[] nums) {

        for (int i = 0,j = 0; i < nums.length - 1;) {

            if (nums[i] == nums[j]) {
                j++;
            } else {
                nums[i] = nums[j];
            }

        }

        return 0;
    }

}
