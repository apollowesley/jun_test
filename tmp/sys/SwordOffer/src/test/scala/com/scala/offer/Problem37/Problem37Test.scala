package com.scala.offer.Problem37

import com.scala.offer.Node.ListNode
import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/5.
 * Github: Lemonjing
 */
class Problem37Test extends FunSuite {

  test("testGetFirstCommonNode") {
    val a1 = new ListNode(1)
    val a2 = new ListNode(2)
    val a3 = new ListNode(3)
    val a4 = new ListNode(6)
    val a5 = new ListNode(7)
    a1.next = a2
    a2.next = a3
    a3.next = a4
    a4.next = a5

    val b1 = new ListNode(4)
    val b2 = new ListNode(5)
    b1.next = b2
    b2.next = a4

    assert(Problem37.getFirstCommonNode(a1, b1) === a4)
  }

}
