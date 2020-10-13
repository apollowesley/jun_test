/**
 * @创建人 luoxiang
 * @创建时间 2019/9/25  11:00
 * @描述 206. 反转链表       https://leetcode-cn.com/problems/reverse-linked-list/
 */
public class LeetCode_206 {
    public static void main(String[] args) {
        LeetCode_206 code206 = new LeetCode_206();
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        node.next.next.next = new ListNode(4);
        node.next.next.next.next = new ListNode(5);
        ListNode list = code206.reverseList(node);
        while (list != null) {
            System.out.print(list.val + " -> ");
            list = list.next;
        }
    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode tmp = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return tmp;
    }


    /**
     * Method 1
     * Created by LuoXiang on 2019/09/25 11:05
     * Desc: 思路 - 两条链表的反转操作
     * 复杂度：   时间复杂度：O(n)  ;   空间复杂度：O(1)
     **/
    public ListNode reverseList1(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = nextTemp;
        }
        return pre;
    }
}
