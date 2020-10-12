package com.scala.offer.Problem3

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/7.
 * Github: Lemonjing
 */
class Problem3Test extends FunSuite with BeforeAndAfterEach {

  var a: Array[Array[Int]] = Array(Array(1, 2, 8, 9), Array(2, 4, 9, 12), Array(4, 7, 10, 13), Array(6, 8, 11, 15))

  override def beforeEach() {
  }

  override def afterEach() {

  }

  test("testFind") {
    assert(Problem3.find(a, -1) === false)
    assert(Problem3.find(a, 7) === true)
  }
}
