package com.scala.offer.Problem9

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/7.
 * Github: Lemonjing
 */
class Problem9Test extends FunSuite with BeforeAndAfterEach {

  override def beforeEach() {

  }

  override def afterEach() {

  }

  test("testFibonacci") {
    assertThrows[Exception]{Problem9.fibonacci(-1)}
    assert(Problem9.fibonacci(0) === 0)
    assert(Problem9.fibonacci(1) === 1)
    assert(Problem9.fibonacci(2) === 1)
    assert(Problem9.fibonacci(3) === 2)
  }

  test("testRecursiveFibonacci") {
    assertThrows[Exception]{Problem9.fibonacci(-1)}
    assert(Problem9.fibonacci(0) === 0)
    assert(Problem9.fibonacci(1) === 1)
    assert(Problem9.fibonacci(2) === 1)
    assert(Problem9.fibonacci(3) === 2)
  }
}
