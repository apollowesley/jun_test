package com.scala.offer.Problem13

import com.scala.offer.Node.ListNode
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/5/22.
 * Github: Lemonjing
 */
class Problem13Test extends FunSuite with BeforeAndAfterEach {

  var head = new ListNode(1)
  val a = new ListNode(2)
  val b = new ListNode(3)
  val c = new ListNode(4)
  head.next = a
  a.next = b
  b.next = c
  c.next = null

  override def beforeEach() {

  }

  override def afterEach() {

  }

  test("testDeleteNode") {
    var res = Problem13.deleteNode(head, c)
    val buffer = new ListBuffer[Int]
    while (res != null) {
      buffer.append(res.data)
      res = res.next
    }
    assert(buffer.toList === List(1, 2, 3))
  }

}
