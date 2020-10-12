package com.scala.offer.Problem5

import java.{util => ju}
import com.scala.offer.Node.ListNode
import scala.collection.JavaConverters._

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 从尾到头打印链表
 */
object Problem5 {

  // 栈
  def reversePrintNode(head: ListNode): ju.List[ListNode] = {
    if (head == null) {
      return null
    }
    val list = new ju.ArrayList[ListNode]()
    val stack = new ju.Stack[ListNode]()
    var _head = head
    while (_head != null) {
      stack.push(_head)
      _head = _head.next
    }
    while (!stack.isEmpty) {
      list.add(stack.pop())
    }
    list
  }

  // 递归
  def recursivePrintNode(head: ListNode): ju.List[ListNode] = {
    if (head == null) {
      return null
    }
    val list = new ju.ArrayList[ListNode]()
    recursive(head, list)
    list
  }

  private def recursive(head: ListNode, list: ju.List[ListNode]): Unit = {
    if (head.next != null) {
      recursive(head.next, list)
    }
    list.add(head)
  }
}
