import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/24  11:42
 * @描述 15. 三数之和     https://leetcode-cn.com/problems/3sum/
 */
public class LeetCode_15 {

    public static void main(String[] args) {
         LeetCode_15 code15 = new LeetCode_15();
         int [] nums={-1, 0, 1, 2, -1, -4};
        final List<List<Integer>> lists = code15.threeSum(nums);
        for (List<Integer> list : lists) {
            for (Integer integer : list) {
                System.out.print(integer+" -- ");
            }
            System.out.println();
        }
    }
    /** Method 1
    *   Created by LuoXiang on 2019/09/24 11:55
    *   Desc: 思路 —— 先排序，两层循环使用双指针
     *   复杂度：   时间复杂度：O(N*N)    ;   空间复杂度：O(1)
    **/
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) break;
            if (i > 0 && nums[i] == nums[i-1]) continue;
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    while (left < right && nums[right] == nums[--right]) ;
                } else if (sum < 0) {
                    while (left < right && nums[left] == nums[++left]) ;
                } else {
                    list.add(new ArrayList<Integer>(Arrays.asList(nums[i], nums[left], nums[right])));
                    while (left < right && nums[right] == nums[--right]) ;
                    while (left < right && nums[left] == nums[++left]) ;
                }
            }
        }
        return list;

    }
}
