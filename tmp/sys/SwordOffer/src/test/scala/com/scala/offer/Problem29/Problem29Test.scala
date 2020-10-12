package com.scala.offer.Problem29

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 */
class Problem29Test extends FunSuite {

  test("testMoreThanHalf") {
    val a = Array(1,2,3,2,2,2,5,4,2)
    assert(Problem29.moreThanHalf(a) === 2)
  }

}
