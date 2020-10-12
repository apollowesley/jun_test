package com.scala.offer.Problem57

import com.scala.offer.Node.ListNode

/**
 * Created by Lemonjing on 2018/5/4.
 * Github: Lemonjing
 * 删除链表中重复结点
 * 1->2->3->3->4->4->5 处理后为 1->2->5
 */
object Problem57 {

  def dedupNode(head: ListNode): ListNode = {
    var _head = head
    if (_head == null) {
      return null
    }
    var pre: ListNode = null
    var cur = _head
    while (cur != null) {
      if (cur.next != null && cur.data == cur.next.data) {
        while (cur.next != null && cur.data == cur.next.data) {
          cur = cur.next
        }
        // 头结点开始就重复
        if (pre == null) {
          _head = cur.next
          cur = cur.next
        } else {
          pre.next = cur.next
          cur = cur.next
        }
      } else {
        pre = cur
        cur = cur.next
      }
    }
    _head
  }
}
