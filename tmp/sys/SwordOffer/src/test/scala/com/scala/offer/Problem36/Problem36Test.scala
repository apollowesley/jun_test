package com.scala.offer.Problem36

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 */
class Problem36Test extends FunSuite {

  test("testInversePairs1") {
    assert(Problem36.inversePairs1(Array(7,5,6,4)) === 5)
  }

  test("testInversePairs2") {
    assert(Problem36.inversePairs2(Array(7,5,6,4)) === 5)
  }

}
