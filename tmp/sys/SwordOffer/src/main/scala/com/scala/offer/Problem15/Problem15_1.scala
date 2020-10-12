package com.scala.offer.Problem15

import com.scala.offer.Node.ListNode

/**
 * Created by Lemonjing on 2017/4/30.
 * Github: Lemonjing
 * 扩展1：找出单链表中间元素
 * 双指针法，p每次走两步，q走一步，p走到尾的时候得q
 */
object Problem15_1 {

  def findMidInList(head: ListNode): ListNode = {
    if (head == null || head.next == null) {
      return head
    }
    var p = head
    var q = head
    while (p.next != null) {
      p = p.next
      if (p.next  == null) {
        return q
      } else {
        p = p.next
        q = q.next
      }
    }
    q
  }

  def main(args: Array[String]): Unit = {
    val list1 = new ListNode(1)
    val a = new ListNode(2)
    val b = new ListNode(3)
    val c = new ListNode(4)
    list1.next = a
    a.next = b
    b.next = c
    c.next = null
    val q = findMidInList(list1)
    println(q.data)
  }
}
