package com.scala.offer.Problem15

import com.scala.offer.Node.ListNode

/**
 * Problem15
 * 输入一个链表，输出该链表的倒数第K个结点
 * 双指针法 p指针先走k-1步，然后一起走p到尾得q
 * 查看该题的扩展1，2，3
 * 查找环的入口结点Problem55
 * @author lemonjing
 *
 */
object Problem15 {

  def findKthToTail(head: ListNode, k: Int): ListNode = {
    if (head == null || k <= 0) return null
    var p = head
    for (i <- 0 until k - 1) {
      if (p.next != null) p = p.next else return null
    }
    var q = head
    while (p.next != null) {
      p = p.next
      q = q.next
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
    val q = findKthToTail(list1, 5)
    println(q.data)
  }
}
