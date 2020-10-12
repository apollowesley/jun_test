package com.scala.offer.Problem55

import com.scala.offer.Node.ListNode

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * Problem15的扩展，先求出环长，p先走环长的步数，然后一起走，相交点为入口结点
 */
object Problem55 {

  private def findCircleLen(head: ListNode): Int = {
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
        var k = q
        var len: Int = 0
        while (k.next != q) {
          k = k.next
          len += 1
        }
        return len + 1
      }
    }
    0
  }

  def findCircleEnterNode(head: ListNode): ListNode = {
    val len = findCircleLen(head)
    if (0 == len) {
      return null
    }
    var p = head
    var q = head
    for (i <- 0 until len) {
      p = p.next
    }
    while (p != q) {
      p = p.next
      q = q.next
    }
    p
  }

  def main(args: Array[String]): Unit = {
    val list1 = new ListNode(1)
    val a = new ListNode(2)
    val b = new ListNode(3)
    val c = new ListNode(4)
    list1.next = a
    a.next = b
    b.next = c
    c.next = a
    println(findCircleEnterNode(list1).data)
  }
}
