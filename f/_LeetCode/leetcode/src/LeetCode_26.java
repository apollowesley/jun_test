/**
 * @创建人 luoxiangs
 * @创建时间 2019/9/19  17:08
 * @描述  26. 删除排序数组中的重复项     https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/
 */
public class LeetCode_26 {
    public static void main(String[] args) {
        LeetCode_26 code26 = new LeetCode_26();
        int[] nums = {1, 1, 2};
        int[] nums2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println(code26.removeDuplicates(nums));
        System.out.println(code26.removeDuplicates(nums2));
    }

    /**
     * Created by LuoXiang on 2019/09/19 17:31
     * Desc:  快慢指针思路。慢指针只有不相等的时候才向前走一步，快指针每次都走一步
     * 复杂度： 时间复杂度：O(N) ;   空间复杂度： O(1)
     **/
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int slow = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[slow] != nums[i]) {
                nums[++slow] = nums[i];
            }
        }
        return slow + 1;
    }
}
