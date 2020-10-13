import java.util.Arrays;
import java.util.HashMap;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/24  16:59
 * @描述 169. 求众数     https://leetcode-cn.com/problems/majority-element/
 */
public class LeetCode_169 {
    public static void main(String[] args) {
        int[] nums1 = {3, 2, 3};
        int[] nums2 = {2, 2, 1, 1, 1, 2, 2};
        final LeetCode_169 code169 = new LeetCode_169();
        System.out.println(code169.majorityElement2(nums1));
        System.out.println(code169.majorityElement2(nums2));
    }

    /**
    *   Created by LuoXiang on 2019/09/24 17:35
    *   Desc:  LeetCode 上的 几个官方题解没看明白  。下次再学习学习，再战
     *   https://leetcode-cn.com/problems/majority-element/solution/qiu-zhong-shu-by-leetcode-2/
    **/

    /**
     * Method 2
     * Created by LuoXiang on 2019/09/24 17:27
     * Desc: 思路 —— 众数的数量一定会大于数组长度的一半，所以排序后一定会在 正中间的位置
     * 复杂度： 时间复杂度：O(nlogn)    ;   空间复杂度：O(1)
     **/
    public int majorityElement2(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * Method 1
     * Created by LuoXiang on 2019/09/24 17:18
     * Desc: 思路 —— 哈希表法。  用 哈希表 来快速统计每个元素出现的次数
     * 复杂度：   时间复杂度：O(N)  ;   空间复杂度：  O(N);
     **/
    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = nums.length / 2;
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.get(num) != null && map.get(num) > n) {
                return num;
            }
        }
        return 0;
    }
}
