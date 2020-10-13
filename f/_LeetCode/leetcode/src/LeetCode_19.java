/**
 * @创建人 luoxiang
 * @创建时间 2019/9/25  14:59
 * @描述 19. 删除链表的倒数第N个节点        https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 */
public class LeetCode_19 {
    public static void main(String[] args) {
        LeetCode_19 code19 = new LeetCode_19();
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        node.next.next.next = new ListNode(4);
        node.next.next.next.next = new ListNode(5);
        ListNode node1 = code19.removeNthFromEnd2(node, 2);
        while (node1 != null) {
            System.out.print(node1.val + " ->");
            node1 = node1.next;
        }
    }

    /** Method 2  (优于 Method 1)
     * Created by LuoXiang on 2019/09/25 15:47
     * Desc:   思路 —— 快慢指针。 快指针先走 n+1 位，再同时走，中间被 n 个节点分开。快指针结束的时候，慢指针删除一位即可
     * 复杂度：   时间复杂度：O(n)  ;   空间复杂度：O(1)
     **/
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode node = new ListNode(0);
        node.next = head;
        ListNode first = node;
        ListNode second = node;
        for (int i = 0; i <= n; i++) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return node.next;
    }


    /** Method 1
     * Created by LuoXiang on 2019/09/25 15:26
     * Desc:   思路 —— 两层遍历法。第一次遍历记住长度和要删除的位置，第二次遍历进行删除。
     * 复杂度：    时间复杂度:O(n)  ;   空间复杂度：O(1);
     **/
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int length = 0;
        ListNode listNode = new ListNode(-1);
        listNode.next = head;
        ListNode curr = head;

        while (curr != null) {
            length++;
            curr = curr.next;
        }
        curr = listNode;
        length -= n;
        for (int i = 0; i < length; i++) {
            curr = curr.next;
        }
        curr.next = curr.next.next;
        return listNode.next;
    }
}
