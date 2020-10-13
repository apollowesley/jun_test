import java.util.HashSet;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/25  11:37
 * @描述 141. 环形链表       https://leetcode-cn.com/problems/linked-list-cycle/
 */
public class LeetCode_141 {
    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(1);
        final LeetCode_141 code141 = new LeetCode_141();
        System.out.println(code141.hasCycle(node));
    }


    /**
     * Method 2
     * Created by LuoXiang on 2019/09/25 11:53
     * Desc: 思路 —— 快慢指针。慢指针一次走一个，快指针一次走两个。
     * 无环的话，快指针会先走完；有环的话，慢指针 和 快指针 终会相遇
     * 复杂度：   时间复杂度：O(n)  ;   空间复杂度：O(1)
     **/
    public boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;

    }


    /**
     * Method 1
     * Created by LuoXiang on 2019/09/25 11:43
     * Desc: 思路 - 哈希表法 。 记录 当前的链表，有重复的即为有环
     * 复杂度：       时间复杂度：O(n)  ;   空间复杂度：O(n)
     **/
    public boolean hasCycle(ListNode head) {
        HashSet<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }
}
