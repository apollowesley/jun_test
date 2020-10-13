import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/29  11:30
 * @描述 496. 下一个更大元素 I      https://leetcode-cn.com/problems/next-greater-element-i/
 * 给定两个没有重复元素的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。找到 nums1 中每个元素在 nums2 中的下一个比其大的值。
 * nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出-1。
 */
public class LeetCode_496 {
    public static void main(String[] args) {
        final int[] ints = new LeetCode_496().nextGreaterElement2(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2});
        final int[] ints2 = new LeetCode_496().nextGreaterElement2(new int[]{1, 3, 5, 2, 4}, new int[]{6, 5, 4, 3, 2, 1, 7});
        for (int anInt : ints) {
            System.out.print(anInt + "->");
        }
        System.out.println();
        for (int anInt : ints2) {
            System.out.print(anInt + "->");
        }
    }

    /**
     * Method 2
     * Created by LuoXiang on 2019/09/29 19:26
     * Desc:   思路  —— 利用栈和哈希表来求取大数组当中的每一个值后面的第一个大值。
     * 复杂度：    时间复杂度：O(n)  ;   空间复杂度：O(n)
     **/
    public int[] nextGreaterElement2(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        for (int num : nums2) {
            while (!stack.empty() && stack.peek() < num) {
                map.put(stack.pop(), num);
            }
            stack.push(num);
        }
        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = map.getOrDefault(nums1[i], -1);
        }
        return nums1;
    }


    /**
     * Method 1
     * Created by LuoXiang on 2019/09/29 11:52
     * Desc:   思路 —— 两层循环数组，第一次找到 当前值的下标，第二次找下一个更大元素
     * 复杂度：   时间复杂度：O(n*m)    ;   空间复杂度：O(n)
     **/
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] ints = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            int tmp = nums1[i];
            int tmpIndex = 0;
            for (int j = 0; j < nums2.length; j++) {
                if (tmp == nums2[j]) {
                    tmpIndex = j;
                    break;
                }
            }
            ints[i] = -1;
            for (int k = tmpIndex; k < nums2.length; k++) {
                if (tmp < nums2[k]) {
                    ints[i] = nums2[k];
                    break;
                }
            }
        }
        return ints;
    }
}
