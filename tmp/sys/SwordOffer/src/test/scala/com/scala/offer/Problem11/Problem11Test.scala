package com.scala.offer.Problem11

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/13.
 * Github: Lemonjing
 */
class Problem11Test extends FunSuite with BeforeAndAfterEach {

  override def beforeEach() {

  }

  override def afterEach() {

  }

  test("testPower") {
    assertThrows[Exception](Problem11.power(0, 0))
    assertThrows[Exception](Problem11.power(0, -1))
    assert(Problem11.power(0, 1) === 0)
    assert(Problem11.power(2, 0) === 1)
    assert(Problem11.power(2, 2) === 4)
    assert(Problem11.power(2, -2) === 1.0 / 4)
  }

}
