package com.mkfree.datastructure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 从数组中找出两个数相加等于target，数组中的下标
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] nums = new int[]{3, 3};
        int target = 6;

        System.out.println(Arrays.toString(twoSum1(nums, target)));

    }

    private static int[] towSum(int[] nums, int target) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {

            int a = target - nums[i];
            if (map.containsKey(a) && map.get(a) != i) {
                return new int[]{map.get(a), i};
            }
        }
        return new int[]{};
    }

    private static int[] towSum2(int[] nums, int target) {

        int[] result = new int[2];

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int a = target - nums[i];
            if (map.containsKey(a)) {
                result[0] = map.get(a);
                result[1] = i;
                break;
            }
            map.put(nums[i], i);
        }
        return result;
    }

    private static int[] twoSum1(int[] nums, int target) {
        int t = 4096;
        int bitMode = t - 1;
        int[] temp = new int[t];
        int length = nums.length;
        int firstValue = nums[0];

        for (int i = 1; i < length; i++) {
            int different = target - nums[i];
            int current = nums[i];
            if (different == firstValue) {
                return new int[]{0, i};
            }
            int differentIndex = temp[different & bitMode];
            if (differentIndex != 0) {
                return new int[]{differentIndex, i};
            }

            int currentIndex = current & bitMode;
            temp[currentIndex] = i;
        }
        return null;
    }
}
