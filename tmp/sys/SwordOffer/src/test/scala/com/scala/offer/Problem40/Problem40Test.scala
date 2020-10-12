package com.scala.offer.Problem40

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/5.
 * Github: Lemonjing
 */
class Problem40Test extends FunSuite {

  test("testGet") {
    val a = Array(2, 4, 3, 6, 3, 2, 5, 5)
    assert(Problem40.get(a) sameElements Array(6, 4))
  }

}
