package com.scala.offer.Problem13

import com.scala.offer.Node.ListNode

/**
 * Created by Lemonjing on 2018/5/21.
 * Github: Lemonjing
 *
 * 给定单向链表的头指针和一个节点，定义一个函数在O(1)时间删除该节点
 */
object Problem13 {

  def deleteNode(head: ListNode, node: ListNode): ListNode = {
    var _head = head
    var _node = node
    if (_head == null || _node == null) {
      return _head
    }
    if (_head == _node) {
      val next = _head.next
      _head = null
      return next
    }
    if (_node.next == null) {
      while (_head.next != _node) {
        _head = _head.next
      }
      _node = null
      _head.next = null
      head
    } else {
      _node.data = _node.next.data
      _node.next = _node.next.next
      head
    }
  }
}
