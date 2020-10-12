package com.scala.offer.Problem14

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/24.
 * Github: Lemonjing
 */
class Problem14Test extends FunSuite with BeforeAndAfterEach {

  var a: Array[Int] = _

  override def beforeEach() {
    a = Array(2, 4, 6, 1, 3, 5)
  }

  override def afterEach() {

  }

  test("testReorder") {
    Problem14.reorder(a)
    assert(a sameElements Array(5, 3, 1, 6, 4, 2))
  }

}
