/**
 * @创建人 luoxiang
 * @创建时间 2019/9/25  15:55
 * @描述 234. 回文链表       https://leetcode-cn.com/problems/palindrome-linked-list/
 */
public class LeetCode_234 {

    public static void main(String[] args) {
        LeetCode_234 code234 = new LeetCode_234();
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        node.next.next.next = new ListNode(3);
        node.next.next.next.next = new ListNode(2);
//        node.next.next.next.next.next = new ListNode(1);
        System.out.println(code234.isPalindrome(node));

    }

    /** Method 1
     * Created by LuoXiang on 2019/09/25 16:18
     * Desc: 思路 ——
     * 1、通过快慢指针找中间节点
     * 2、同时 慢指针遍历的部分进行链表反转
     * 3、奇数 时特殊处理一下
     * 4、反转的链表 和 慢指针后续链表进行一一比较
     * 复杂度： 时间复杂度：O(n)  ;   空间复杂度：O(1);
     **/
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode pre = null;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            ListNode nextTemp = slow.next;
            slow.next = pre;
            pre = slow;
            slow = nextTemp;
        }
        if (fast != null) { // 奇数
            slow = slow.next;
        }
        while (slow != null) {
            if (slow.val != pre.val) {
                return false;
            }
            slow = slow.next;
            pre = pre.next;
        }
        return true;
    }
}
