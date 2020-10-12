package com.scala.offer.Problem65

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/18.
 * Github: Lemonjing
 */
class Problem65Test extends FunSuite {

  test("testMaxInWindows") {
    val a = Array(2, 3, 4, 2, 6, 2, 5, 1)
    assert(Problem65.maxInWindows(a, 3) === List(4, 4, 6, 6, 6, 5))
  }

}
