package com.scala.offer.Problem38

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/5.
 * Github: Lemonjing
 */
class Problem38Test extends FunSuite {

  test("testGetTimes") {
    val a = Array(1, 2, 3, 3, 3, 3, 4, 5)
    assert(Problem38.getTimes(a, 3) === 4)
  }

}
