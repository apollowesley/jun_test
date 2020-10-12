package com.scala.offer.Problem28

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/2.
 * Github: Lemonjing
 */
class Problem28Test extends FunSuite {

  test("testPermOfString") {
    assert(Problem28.permOfString("abc") === List("abc", "acb", "bac", "bca", "cba", "cab"))
    assert(Problem28.permOfString("aa") === List("aa", "aa"))
  }
}
