package com.slavic.vesna.leet.leet;

import java.util.HashMap;
import java.util.Map;

public class Leet0127 {

    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> kv = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (kv.containsKey(numbers[i])) return new int[]{kv.get(numbers[i]) + 1, i + 1};
            kv.put(target - numbers[i], i);
        }
        return null;
    }

    public int[] twoSum2(int[] numbers, int target) {

        for (int i = 0, j = numbers.length - 1; i < j; ) {
            int sum = numbers[i] + numbers[j];
            if (sum == target) return new int[]{i + 1, j + 1};
            else if (sum > target) j--;
            else i++;
        }

        return null;
    }

}
