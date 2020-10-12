package com.scala.offer.Problem33

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 */
class Problem33Test extends FunSuite {

  test("testArrToMinNumber") {
    val a = Array(3, 32, 321)
    assert(Problem33.arrToMinNumber(a) === "321323")
    val b = Array(1)
    assert(Problem33.arrToMinNumber(b) === "1")
  }

}
