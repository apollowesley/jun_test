package com.scala.offer.Problem20

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/5/31.
 * Github: Lemonjing
 */
class Problem20Test extends FunSuite {

  test("testPrintMatrixByClockWise") {
    val a1 = Array(Array(1, 2, 3, 4))
    val a2 = Array(Array(1), Array(2), Array(3), Array(4))
    val a3 = Array(Array(1, 2, 3, 4), Array(5, 6, 7, 8), Array(9, 10, 11, 12), Array(13, 14, 15, 16))
    assert(Problem20.printMatrixByClockWise(a1) === List(1, 2, 3, 4))
    assert(Problem20.printMatrixByClockWise(a2) === List(1, 2, 3, 4))
    assert(Problem20.printMatrixByClockWise(a3) === List(1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10))
  }
}
