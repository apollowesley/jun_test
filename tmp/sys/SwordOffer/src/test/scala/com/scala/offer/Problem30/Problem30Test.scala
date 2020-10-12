package com.scala.offer.Problem30

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 */
class Problem30Test extends FunSuite {

  test("testGetLeastKNumbers") {
    val a = Array(4, 5, 1, 6, 2, 7, 3, 8)
    val kArr = Problem30.getLeastKNumbers(a, 4)
    assert(kArr sameElements Array(4, 3, 1, 2))
  }

}
