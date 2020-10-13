/**
 * @创建人 luoxiang
 * @创建时间 2019/9/25  14:15
 * @描述 876. 链表的中间结点        https://leetcode-cn.com/problems/middle-of-the-linked-list/
 */
public class LeetCode_876 {

    public static void main(String[] args) {
        LeetCode_876 code_876 = new LeetCode_876();
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        node.next.next.next = new ListNode(4);
        node.next.next.next.next = new ListNode(5);
        node.next.next.next.next.next = new ListNode(6);
        final ListNode node1 = code_876.middleNode2(node);
        System.out.println(node1.val);

    }

    /**
     * Created by LuoXiang on 2019/09/25 14:32
     * Desc:   思路 —— 利用数组来存储链表，每走一步把链表用数组进行存储。数组中间就是链表的中间节点
     * 复杂度：    时间复杂度：O(n);     空间复杂度：O(n);
     **/
    public ListNode middleNode2(ListNode head) {
        ListNode[] nodes = new ListNode[100];
        int i = 0;
        while (head != null) {
            nodes[i++] = head;
            head = head.next;
        }
        return nodes[i / 2];
    }

    /**
     * Created by LuoXiang on 2019/09/25 14:32
     * Desc:   思路 —— 快慢指针法。快指针走两步，慢指针走一步。快指针走完的时候正好慢指针走到中间
     * 复杂度：   时间复杂度：O(n)  ;   空间复杂度：O(1);
     **/
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
