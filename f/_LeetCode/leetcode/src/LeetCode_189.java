/**
 * @创建人 luoxiang
 * @创建时间 2019/9/19  17:34
 * @描述 189. 旋转数组       https://leetcode-cn.com/problems/rotate-array/
 */
public class LeetCode_189 {

    public static void main(String[] args) {
        final LeetCode_189 code189 = new LeetCode_189();
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        int[] nums2 = {-1, -100, 3, 99};
        code189.rotate2(nums, 3);
        code189.rotate2(nums2, 2);
        for (int num : nums) {
            System.out.print(num + ",");
        }
        System.out.println();
        System.out.println("-----------------------");
        for (int i : nums2) {
            System.out.print(i + ",");
        }

    }

    /**
     * Method 2
    *   Created by LuoXiang on 2019/09/19 17:53
    *   Desc: 反转的思路。1、先全部反转一次。2、k 之前再反转一次。3、k之后在翻转一次
     *   复杂度：   时间复杂度：  O(N)    ;   空间复杂度：  O(1)
    **/
    public void rotate2(int[] nums, int k) {
        if (nums.length == 0) return;
        int n = nums.length;
        k %= n;
        reverse(nums,0,n-1);
        reverse(nums,0,k-1);
        reverse(nums,k,n-1);
    }

    private void reverse(int[] nums,int start,int end){
        while (start<end){
            int tmp = nums[start];
            nums[start]=nums[end];
            nums[end]=tmp;
            start++;
            end--;
        }
    }

    /**
     * Method 1
     * Created by LuoXiang on 2019/09/19 17:44
     * Desc: 先将 k 除 n 取模。 两次循环 一个个移
     * 复杂度： 时间复杂度：  O(k*N)    ;   空间复杂度：  O(1)
     **/
    public void rotate(int[] nums, int k) {
        if (nums.length == 0) return;
        int n = nums.length;
        k %= n;
        for (int i = 0; i < k; i++) {
            int tmp = nums[n - 1];
            for (int j = n - 1; j > 0; j--) {
                nums[j] = nums[j - 1];
            }
            nums[0] = tmp;
        }
    }
}
