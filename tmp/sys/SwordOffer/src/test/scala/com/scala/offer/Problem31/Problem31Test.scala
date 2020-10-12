package com.scala.offer.Problem31

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 */
class Problem31Test extends FunSuite {

  test("testGetContinuosGreatestSum") {
    val a = Array(1,-2,3,10,-4,7,2,-5)
    assert(Problem31.getContinuosGreatestSum(a) === 18)
  }

}
