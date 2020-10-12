package com.scala.offer.Problem5

import com.scala.offer.Node.ListNode
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import scala.collection.JavaConverters._

/**
 * Created by Lemonjing on 2018/5/7.
 * Github: Lemonjing
 */
class Problem5Test extends FunSuite with BeforeAndAfterEach {
  var head = new ListNode(1)
  val a = new ListNode(2)
  val b = new ListNode(3)
  val c = new ListNode(4)
  head.next = a
  a.next = b
  b.next = c
  c.next = null

  test("testReversePrintNode") {
    val nodes = Problem5.recursivePrintNode(head)
    assert(nodes.asScala === List(c, b, a, head))
  }

  test("testRecursivePrintNode") {
    val nodes = Problem5.recursivePrintNode(head)
    assert(nodes.asScala === List(c, b, a, head))
  }
}
