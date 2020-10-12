package com.scala.offer.Problem24

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/5/31.
 * Github: Lemonjing
 */
class Problem24Test extends FunSuite {

  test("testVerifySequenceOfBST") {
    val a = Array(5, 7, 6, 9, 11, 10, 8)
    val b = Array(7, 4, 6, 5)
    assert(Problem24.verifySequenceOfBST(a) === true)
    assert(Problem24.verifySequenceOfBST(b) === false)
  }
}
