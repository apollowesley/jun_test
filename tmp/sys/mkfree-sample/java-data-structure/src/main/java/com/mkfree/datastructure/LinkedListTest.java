package com.mkfree.datastructure;

import java.util.LinkedList;

/**
 * 单向链表
 */
public class LinkedListTest {

    public static void main(String[] args) {

        ListNode a1 = new ListNode(1);
        ListNode a2 = new ListNode(2);
        ListNode a3 = new ListNode(3);
        ListNode a4 = new ListNode(4);
        ListNode a5 = new ListNode(5);

        a1.next = a2;
        a2.next = a3;
        a3.next = a4;
        a4.next = a5;

        traversing(reverseList(a1));
    }

    /**
     * 遍历单向链表
     *
     * @param head
     * @return
     */
    private static ListNode traversing(ListNode head) {
        while (true) {
            System.out.println(head.val);
            if (head.next == null) {
                break;
            }
            head = head.next;
        }
        return null;
    }

    /**
     * 1 -> 2 -> 3 -> 4 -> 5
     * 5 -> 4 -> 3 -> 2 -> 1
     *
     * @param head
     * @return
     */
    private static ListNode reverseList(ListNode head) {
        ListNode current = head;
        ListNode prev = null;
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        return prev;
    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
