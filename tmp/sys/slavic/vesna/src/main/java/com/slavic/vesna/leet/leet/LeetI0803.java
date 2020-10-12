package com.slavic.vesna.leet.leet;

public class LeetI0803 {

    public int findMagicIndex(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            if (i == nums[i]) return i;
        }

        return -1;
    }

}
