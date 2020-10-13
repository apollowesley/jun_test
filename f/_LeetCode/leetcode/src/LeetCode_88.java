import java.util.Arrays;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/19  18:10
 * @描述 88. 合并两个有序数组     https://leetcode-cn.com/problems/merge-sorted-array/
 */
public class LeetCode_88 {
    public static void main(String[] args) {
        final LeetCode_88 code88 = new LeetCode_88();
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        code88.merge(nums1, 3, nums2, 3);
        for (int i : nums1) {
            System.out.print(i + ",");
        }
    }

    /**
     * Method 2:
     * Created by LuoXiang on 2019/09/20 10:11
     * Desc: 思路 - 在大数组里面直接添加小数组中的元素，再进行排序。
     * 复杂度:   时间复杂度：O(N+logN)  ；   空间复杂度：  O(1)
     **/
    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        Arrays.sort(nums1);
    }

    /**
     * Method 1
    *   Created by LuoXiang on 2019/09/20 10:21
    *   Desc:   思路 - 利用有序数组的前提条件。从后向前 进行处理。
     *   有一种条件需要特殊处理：小数组当中的值没有循环结束的时候，需要 copy 到大数组当中去
     *   杂度度
    **/
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int m2 = m - 1;
        int n2 = n - 1;
        int newIndex = m + n - 1;
        while (m2 >= 0 && n2 >= 0) {
            nums1[newIndex--] = nums1[m2] > nums2[n2] ? nums1[m2--] : nums2[n2--];
        }
        if(n2>=0){
            System.arraycopy(nums2,0, nums1,0,n2+1);
        }
    }

}
