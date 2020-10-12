package com.slavic.vesna.leet.leet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Leet0169 {

    public int majorityElement(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            int j = 0;
            for (int i1 = i; i1 < nums.length; i1++) {
                if (nums[i] == nums[i1]) j++;
            }
            if (j > nums.length / 2) return j;
        }

        return 0;
    }

    public int majorityElement2(int[] nums) {

        Map<Integer, Integer> kv = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            kv.merge(nums[i], 1, Integer::sum);
            if (kv.get(nums[i]) > nums.length / 2) return nums[i];
        }

        return 0;
    }

    public int majorityElement3(int[] nums) {

        Arrays.sort(nums);
        return nums[nums.length/2];
    }

}
