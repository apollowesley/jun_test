package com.scala.offer.Problem32

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 */
class Problem32Test extends FunSuite {

  test("testGetNumberOf1Between1AndN") {
    assert(Problem32.getNumberOf1Between1AndN(12) === 5)
  }

}
