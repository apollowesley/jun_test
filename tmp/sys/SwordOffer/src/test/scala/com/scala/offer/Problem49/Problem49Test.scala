package com.scala.offer.Problem49

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/10.
 * Github: Lemonjing
 */
class Problem49Test extends FunSuite {

  test("testAtoI") {
    assert(Problem49.atoI("+15677") === 15677)
    assert(Problem49.atoI("-15677") === -15677)
    assert(Problem49.atoI("15677") === 15677)
    assert(Problem49.atoI("   15677") === 15677)
  }

}
