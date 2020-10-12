package com.scala.offer.Problem6

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/7.
 * Github: Lemonjing
 */
class Problem6Test extends FunSuite with BeforeAndAfterEach {

  var pre: Array[Int] = _
  var in: Array[Int] = _

  override def beforeEach() {
    pre = Array(1, 2, 4, 7, 3, 5, 6, 8)
    in = Array(4, 7, 2, 1, 5, 3, 8, 6)
  }

  override def afterEach() {
    pre = null
    in = null
  }

  test("testConstructBTree") {
    assert(Problem6.constructBTree(pre, in).data === 1)
    assert(Problem6.constructBTree(pre, in).leftChild.data === 2)
    assert(Problem6.constructBTree(pre, in).rightChild.data === 3)
  }

}
