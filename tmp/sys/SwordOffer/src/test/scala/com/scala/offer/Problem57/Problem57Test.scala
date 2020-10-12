package com.scala.offer.Problem57

import com.scala.offer.Node.ListNode
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/5/5.
 * Github: Lemonjing
 */
class Problem57Test extends FunSuite with BeforeAndAfterEach {

  var list1 = new ListNode(1)

  override def beforeEach() {
    val a = new ListNode(1)
    val b = new ListNode(3)
    val c = new ListNode(4)
    val d = new ListNode(4)
    val e = new ListNode(4)
    val f = new ListNode(5)
    list1.next = a
    a.next = b
    b.next = c
    c.next = d
    d.next = e
    e.next = f
    f.next = null
  }

  override def afterEach() {
    list1 = null
  }

  test("testDedupNode") {
    var newList = Problem57.dedupNode(list1)
    val elements = new ListBuffer[Int]()
    while (newList != null) {
      elements.append(newList.data)
      newList = newList.next
    }
    assert(elements.toList === List(3,5))
  }

}
