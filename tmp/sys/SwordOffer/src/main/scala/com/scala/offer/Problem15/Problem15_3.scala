package com.scala.offer.Problem15

import com.scala.offer.Node.ListNode

/**
 * Created by Lemonjing on 2017/5/1.
 * Github: Lemonjing
 * 扩展3：判断单链表环的长度
 * 找到相交点，从相交点走一圈
 */
object Problem15_3 {

  def findCircleLen(head: ListNode): Int = {
    if (head == null) {
      return 0
    }
    var p = head
    var q = head
    while (p.next != null) {
      p = p.next
      if (p.next == null) {
        return 0
      } else {
        p = p.next
        q = q.next
      }
      if (p == q) {
        var len:Int = 0
        var k = q
        while (k.next != q) {
          k = k.next
          len += 1
        }
        return len + 1
      }
    }
    0
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
    println(findCircleLen(list1))
  }
}
