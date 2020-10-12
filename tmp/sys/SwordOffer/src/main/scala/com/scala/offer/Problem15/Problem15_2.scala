package com.scala.offer.Problem15

import com.scala.offer.Node.ListNode

/**
 * Created by Lemonjing on 2018/4/30.
 * 扩展2：判断单链表是否有环
 * 双指针法，p每次走两步，q走一步，有环的话会相交
 */
object Problem15_2 {

  def judge(head:ListNode):Boolean = {
    if (head == null) {
      return false
    }
    var p = head
    var q = head
    while (p.next != null) {
      p = p.next
      if (p.next == null) {
        return false
      } else {
        p = p.next
        q = q.next
      }
      if (p == q) {
        return true
      }
    }
    false
  }

  def main(args: Array[String]): Unit = {
    val list1 = new ListNode(1)
    list1.next = list1
    println(judge(list1))
  }
}
