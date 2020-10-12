package com.scala.offer.Problem10

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/21.
 * Github: Lemonjing
 */
class Problem10Test extends FunSuite with BeforeAndAfterEach {

  override def beforeEach() {

  }

  override def afterEach() {

  }

  test("testNumberOf1") {
    assert(Problem10.numberOf1(0) === 0)
    assert(Problem10.numberOf1(1) === 1)
    assert(Problem10.numberOf1(2) === 1)
    assert(Problem10.numberOf1(3) === 2)
  }

}
