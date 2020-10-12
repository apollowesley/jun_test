package com.scala.offer.Problem16

import com.scala.offer.Node.ListNode
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/5/26.
 * Github: Lemonjing
 */
class Problem16Test extends FunSuite with BeforeAndAfterEach {

  var list1: ListNode = _

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
    list1 = null
  }

  test("testReverseList") {
    var res = Problem16.reverseList(list1)
    val buffer = new ListBuffer[Int]
    while (res != null) {
      buffer.append(res.data)
      res = res.next
    }
    assert(buffer.toList === List(4, 3, 2, 1))
  }

}
