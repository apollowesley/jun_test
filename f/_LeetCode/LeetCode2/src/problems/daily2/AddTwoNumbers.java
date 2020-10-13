package problems.daily2;

/**
 * @Author: shiyao.wei
 * @Date: 2019/6/25 13:47
 * @Version: 1.0
 * @Desc: 两数相加
 */
public class AddTwoNumbers {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null && q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            // 当前位置数值
            carry = sum / 10;
            // 链表下一位node
            curr.next = new ListNode(sum % 10);
            // 移动向下一位
            curr = curr.next;
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

    public static void main(String[] args) {
        ListNode n1 = new ListNode(2);
        n1.next = new ListNode(4);
        n1.next.next = new ListNode(3);

        ListNode n2 = new ListNode(5);
        n2.next = new ListNode(6);
        n2.next.next = new ListNode(4);
        ListNode node = AddTwoNumbers.addTwoNumbers(n1, n2);
        System.out.println(node.val + "," + node.next.val + "," + node.next.next.val);
    }
}
