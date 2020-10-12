package com.scala.offer.Problem17

import com.scala.offer.Node.ListNode

/**
 * Created by Lemonjing on 2018/5/4.
 * Github: Lemonjing
 * 合并两个已排序的链表
 */
object Problem17 {

  def mergeList(list1: ListNode, list2: ListNode): ListNode = {
    if (list1 == null) {
      return list2
    } else if (list2 == null) {
      return list1
    }
    var p = list1
    var q = list2
    val newHead:ListNode = new ListNode(-1)
    var r = newHead

    while (p != null && q != null) {
      if (p.data < q.data) {
        r.next = p
        p = p.next
      } else {
        r.next = q
        q = q.next
      }
      r = r.next
    }
    if (p != null) {
      r.next = p
    }
    if (q != null) {
      r.next = q
    }
    newHead.next
  }
}
