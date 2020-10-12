package com.scala.offer.Problem7

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/13.
 * Github: Lemonjing
 */
class MyQueueTest extends FunSuite with BeforeAndAfterEach {

  override def beforeEach() {

  }

  override def afterEach() {

  }

  test("testPush") {
    MyQueue.push(1)
    MyQueue.push(2)
    MyQueue.push(3)
    assert(List(MyQueue.pop(), MyQueue.pop(), MyQueue.pop()) === List(1, 2, 3))
  }

  test("testPop") {
    assertThrows[Exception](MyQueue.pop())
  }

}
