package com.scala.offer.Problem21

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/31.
 * Github: Lemonjing
 */
class Problem21Test extends FunSuite with BeforeAndAfterEach {

  var stack:Problem21 = _

  override def beforeEach() {
    stack = new Problem21
    stack.push(2)
    stack.push(3)
  }

  override def afterEach() {
    stack = null
  }

  test("testMin") {
    assert(stack.min === 2)
  }

}
