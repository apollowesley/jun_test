package com.scala.offer.Problem16

import com.scala.offer.Node.ListNode

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 反转链表
 */
object Problem16 {

  def reverseList(head:ListNode):ListNode = {
    if (head == null) {
      return null
    }
    var pre:ListNode = null
    var cur = head
    while (cur != null) {
      val next = cur.next
      cur.next = pre
      pre = cur
      cur = next
    }
    pre
  }
}
