package com.scala.offer.Problem37

import com.scala.offer.Node.ListNode

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 链表的第一个结点
 */
object Problem37 {

  def getFirstCommonNode(root1: ListNode, root2: ListNode): ListNode = {
    if (root1 == null || root2 == null) return null

    var dif = 0
    val len1 = getLen(root1)
    val len2 = getLen(root2)
    var p: ListNode = null
    var q: ListNode = null
    if (len1 > len2) {
      p = root1
      q = root2
      dif = len1 - len2
    } else {
      p = root2
      q = root1
      dif = len2 - len1
    }

    for (i <- 0 until dif) p = p.next
    while (p != null && q != null && p != q) {
      p = p.next
      q = q.next
    }
    p
  }

  private def getLen(root: ListNode): Int = {
    if (root == null) return 0
    var p = root
    var len = 0
    while (p != null) {
      len += 1
      p = p.next
    }
    len
  }
}
