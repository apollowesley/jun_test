package com.scala.offer.Problem44

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/9.
 * Github: Lemonjing
 */
class Problem44Test extends FunSuite {

  test("testSortPoker") {
    val a = Array(1, 4, 3, 5, 0)
    assert(Problem44.sortPoker(a) === true)
  }

}
