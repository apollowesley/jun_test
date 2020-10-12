package com.scala.offer.Problem35

import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 */
class Problem35Test extends FunSuite {

  test("testGetFirstNotRepeatWithMap") {
    assert(Problem35.getFirstNotRepeatWithMap("gogoup") === 'u')
  }

  test("testGetFirstNotRepeatWithArray") {
    assert(Problem35.getFirstNotRepeatWithArray("gogoup") === 'u')
  }

}
