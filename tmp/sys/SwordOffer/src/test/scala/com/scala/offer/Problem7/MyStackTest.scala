package com.scala.offer.Problem7

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/13.
 * Github: Lemonjing
 */
class MyStackTest extends FunSuite with BeforeAndAfterEach {

  override def beforeEach() {

  }

  override def afterEach() {

  }

  test("testPush") {
    MyStack.push(1)
    MyStack.push(2)
    MyStack.push(3)
    assert(List(MyStack.pop(), MyStack.pop(), MyStack.pop()) === List(3, 2, 1))
  }

  test("testPop") {
    assertThrows[Exception](MyStack.pop())
  }
}
