package com.scala.offer.Problem34

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 */
class Problem34Test extends FunSuite {

  test("testGetUgly1") {
    assert(Problem34.getUgly1(1500) === 859963392)
  }

  test("testGetUgly2") {
    assert(Problem34.getUgly2(1500) === 859963392)
  }

}
