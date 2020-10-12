package com.scala.offer.Problem15

import com.scala.offer.Node.ListNode
import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/24.
 * Github: Lemonjing
 */
class Problem15Test extends FunSuite with BeforeAndAfterEach {

  var list1:ListNode = _

  override def beforeEach() {
    list1 = new ListNode(1)
    val a = new ListNode(2)
    val b = new ListNode(3)
    val c = new ListNode(4)
    list1.next = a
    a.next = b
    b.next = c
    c.next = null
  }

  override def afterEach() {

  }

  test("testFindKthToTail") {
    val node1 = Problem15.findKthToTail(list1, 2)
    assert(node1.data === 3)
    val node2 = Problem15.findKthToTail(list1, 5)
    assert(node2 === null)
    val node3 = Problem15.findKthToTail(list1, -1)
    assert(node2 === null)
  }

}
