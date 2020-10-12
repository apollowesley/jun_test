package com.scala.offer.Problem4

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/7.
 * Github: Lemonjing
 */
class Problem4Test extends FunSuite with BeforeAndAfterEach {

  var testStr: String = _

  override def beforeEach() {
    testStr = "We Are Happy"
  }

  override def afterEach() {
    testStr = null
  }

  test("testReplaceSpace") {
    val target = Problem4.replaceSpace(testStr)
    assert(target === "We%20Are%20Happy")
  }

}
