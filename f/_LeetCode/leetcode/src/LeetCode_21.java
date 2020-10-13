/**
 * @创建人 luoxiang
 * @创建时间 2019/9/19  18:03
 * @描述 21. 合并两个有序链表        https://leetcode-cn.com/problems/merge-two-sorted-lists/
 */
public class LeetCode_21 {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        node1.next = new ListNode(2);
        node1.next.next = new ListNode(4);

        ListNode node2 = new ListNode(1);
        node2.next = new ListNode(3);
        node2.next.next = new ListNode(4);

        LeetCode_21 code21 = new LeetCode_21();
        ListNode node = code21.mergeTwoLists(node1, node2);
        while (node != null) {
            System.out.print(node.val + "->");
            node = node.next;
        }
    }

    /**
     * Created by LuoXiang on 2019/09/20 12:02
     * Desc: 思路 - 递归。 取当前链表的较小值，再往下一层
     * 复杂度：   时间复杂度：O(N+M)    ;   空间复杂度：  O(N+M)
     **/
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val <= l2.val) {
            l1.next = mergeTwoLists2(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists2(l1, l2.next);
            return l2;
        }
    }

    /**
     * Method 1
     * Created by LuoXiang on 2019/09/20 11:55
     * Desc:   思路 : 迭代。 两个链表的值比较然后往下一层
     * 复杂度：   时间复杂度：O(N+M)    ;   空间复杂度：  O(1)
     **/
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode listNode = new ListNode(-1);
        ListNode current = listNode;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        current.next = l1 == null ? l2 : l1;
        return listNode.next;
    }
}
