/**
 * @创建人 luoxiang
 * @创建时间 2019/9/23  17:35
 * @描述 24. 两两交换链表中的节点       https://leetcode-cn.com/problems/swap-nodes-in-pairs/
 */
public class LeetCode_24 {

    public static void main(String[] args) {
        final ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        node.next.next.next = new ListNode(4);
        final LeetCode_24 code24 = new LeetCode_24();
        ListNode node1 = code24.swapPairs2(node);
        while (node1 != null) {
            System.out.print(node1.val + "—>");
            node1 = node1.next;
        }
    }

    /**
     * Created by LuoXiang on 2019/09/24 11:15
     * Desc: 思路 —— 递归。
     * 1、将 第一个 节点的next —> 第二个节点的 next
     * 2、第二个节点的next —> 第一个节点
     * 复杂度：   时间复杂度：O(N)  ;   空间复杂度：O(N)
     **/
    public ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }


    /**
     * Method 1
     * Created by LuoXiang on 2019/09/23 18:39
     * Desc: 思路 —— 迭代。三个链表作为一个环，
     * 1、将 第一个节点的 next —> 第三个
     * 2、将 第二个节点的 next —> 第三个的下一个
     * 3、将 第二个的节点 next —> 第二个
     * 循环一次到下一个
     * 复杂度：   时间复杂度：O(N)  ;   空间复杂度：O(1)
     **/
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = new ListNode(0);
        pre.next = head;
        ListNode tmp = pre;
        while (tmp.next != null && tmp.next.next != null) {
            ListNode start = tmp.next;
            ListNode end = tmp.next.next;
            tmp.next = end;
            start.next = end.next;
            end.next = start;
            tmp = start;
        }
        return pre.next;
    }
}
