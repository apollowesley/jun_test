package com.scala.offer.Problem26

import com.scala.offer.Node.ComplexNode

/**
 * Created by Lemonjing on 2018/6/1.
 * Github: Lemonjing
 * 复杂链表的复制
 */
object Problem26 {

  def copyList(head: ComplexNode): ComplexNode = {
    if (head == null) return null
    copy(head)
    processSibling(head)
    split(head)
  }

  private def copy(head: ComplexNode) = {
    var p = head
    while (p != null) {
      val next = p.next
      val cloneNode = new ComplexNode(p.data)
      p.next = cloneNode
      cloneNode.next = next
      p = next
    }
  }

  private def processSibling(head: ComplexNode) = {
    var p = head
    while (p != null) {
      val cloneNode = p.next
      if (p.sibling != null) {
        cloneNode.sibling = p.sibling.next
      }
      p = cloneNode.next
    }
  }

  private def split(head: ComplexNode): ComplexNode = {
    var p = head
    var cloneHead: ComplexNode = null
    var q: ComplexNode = null

    if (p != null) {
      cloneHead = p.next
      q = cloneHead
    }

    while (p != null) {
      p.next = q.next
      p = p.next
      if (p != null) {
        q.next = p.next
        q = q.next
      }
    }
    cloneHead
  }
}
