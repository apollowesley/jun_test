package problems.daily1;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: shiyao.wei
 * @Date: 2019/6/25 9:26
 * @Version: 1.0
 * @Desc: 两数之和
 */
public class TwoSum {
    /**
     * 方法一：暴力法
     * @param nums
     * @param target
     * @return
     */
    public int[] solution1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - nums[i]) {
                    return new int[] { i, j };
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**
     * 方法二：两遍哈希表
     * @param nums
     * @param target
     * @return
     */
    public int[] solution2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i ++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i ++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[] { i, map.get(complement) };
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**
     * 方法三：一遍哈希表
     * @param nums
     * @param target
     * @return
     */
    public int[] solution3(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i ++) {
            int complement = target - nums[i];

            if (map.containsKey(nums[i])) {
                return new int[] { i, map.get(complement) };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
