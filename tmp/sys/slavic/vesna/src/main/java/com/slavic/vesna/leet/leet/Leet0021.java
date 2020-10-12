package com.slavic.vesna.leet.leet;

import com.slavic.vesna.leet.base.ListNode;

public class Leet0021 {
    // 方法1 循环
    // 建立结果链，保留引用
    // 判断l1 l2 大小，结果链追加小的，小的下移一位， 直到其中一个或两个为空
    // 对于l1与l2相等的值，结果链追加两个，各自下移一位，剪枝
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode ref = new ListNode();
        ListNode res = ref;
        while (null != l1 && null != l2) {
            if (l1.val < l2.val) {
                ref.next = l1;
                l1 = l1.next;
            } else if (l1.val > l2.val) {
                ref.next = l2;
                l2 = l2.next;
            } else {
                ref.next = l1;
                l1 = l1.next;
                ref = ref.next;
                ref.next = l2;
                l2 = l2.next;
            }
            ref = ref.next;
        }
        ref.next = l1 == null ? l2 : l1;
        return res.next;
    }

    // 递归
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (null == l1 || null == l2) return l1 == null ? l2 : l1;
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists2(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists2(l1, l2.next);
            return l2;
        }
    }

}
