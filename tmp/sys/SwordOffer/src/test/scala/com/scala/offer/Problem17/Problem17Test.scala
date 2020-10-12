package com.scala.offer.Problem17

import com.scala.offer.Node.ListNode
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/5/24.
 * Github: Lemonjing
 */
class Problem17Test extends FunSuite with BeforeAndAfterEach {

  var list1: ListNode = _
  var list2: ListNode = _

  override def beforeEach() {
    list1 = new ListNode(1)
    val a = new ListNode(3)
    val b = new ListNode(5)
    val c = new ListNode(7)
    list1.next = a
    a.next = b
    b.next = c
    c.next = null

    list2 = new ListNode(2)
    val d = new ListNode(4)
    val e = new ListNode(6)
    val f = new ListNode(8)
    list2.next = d
    d.next = e
    e.next = f
    f.next = null
  }

  override def afterEach() {

  }

  test("testMergeList") {
    var res = Problem17.mergeList(list1, list2)
    val buffer = new ListBuffer[Int]
    while (res != null) {
      buffer.append(res.data)
      res = res.next
    }
    assert(buffer.toList === List(1, 2, 3, 4, 5, 6, 7, 8))
  }

  test("testMergeListNull1") {
    list1 = null
    var res = Problem17.mergeList(list1, list2)
    val buffer = new ListBuffer[Int]
    while (res != null) {
      buffer.append(res.data)
      res = res.next
    }
    assert(buffer.toList === List(2, 4, 6, 8))
  }

  test("testMergeListNull2") {
    list2 = null
    var res = Problem17.mergeList(list1, list2)
    val buffer = new ListBuffer[Int]
    while (res != null) {
      buffer.append(res.data)
      res = res.next
    }
    assert(buffer.toList === List(1, 3, 5, 7))
  }

}
