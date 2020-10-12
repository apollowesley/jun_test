package com.slavic.vesna.leet.leet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Leet0001 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Leet0001().twoSum(new int[]{}, 10)));
    }

    // 1 暴力法  两遍循环 N^2 复杂度
    public int[] twoSum(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++)
            for (int i1 = i + 1; i1 < nums.length; i1++)
                if (nums[i] != nums[i1] && nums[i] + nums[i1] == target) return new int[]{i, i1};

        return null;
    }

    // 2 hash表方法 循环的时候，put target - nums[i] -> hash表 如果hash表nums[i]存在，说明 nums[i] + hash key-》 value == target
    // 复杂度 add kv 这里数据少，不存在hash冲突的情况 所以是O(1) 整体O(N)
    public int[] twoSum2(int[] nums, int target) {

        Map<Integer, Integer> kv = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (kv.containsKey(nums[i])) return new int[]{kv.get(nums[i]), i};
            kv.put(target - nums[i], i);
        }
        return null;
    }

}
