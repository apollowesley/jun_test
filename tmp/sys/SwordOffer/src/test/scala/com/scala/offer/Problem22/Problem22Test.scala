package com.scala.offer.Problem22

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/5/31.
 * Github: Lemonjing
 */
class Problem22Test extends FunSuite {

  test("testIsPopOrder") {
    val push = Array(1,2,3,4,5)
    val pop = Array(4,5,3,2,1)
    val pop2 = Array(4,5,3,1,2)
    assert(Problem22.isPopOrder(push, pop) === true)
    assert(Problem22.isPopOrder(push, pop2) === false)
  }

}
