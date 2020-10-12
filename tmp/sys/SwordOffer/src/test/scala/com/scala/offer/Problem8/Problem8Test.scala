package com.scala.offer.Problem8

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/13.
 * Github: Lemonjing
 */
class Problem8Test extends FunSuite with BeforeAndAfterEach {

  var arr0:Array[Int] = _
  var arr1:Array[Int] = _
  var arr2:Array[Int] = _
  var arr3:Array[Int] = _

  override def beforeEach() {
    arr0 = Array()
    arr1 = Array(3,4,5,1,2)
    arr2 = Array(1,0,1,1,1)
    arr3 = Array(1,1,1,0,1)
  }

  override def afterEach() {
    arr0 = null
    arr1 = null
    arr2 = null
    arr3 = null
  }

  test("testFind") {
    assert(Problem8.find(arr1) === 1)
    assert(Problem8.find(arr2) === 0)
    assert(Problem8.find(arr3) === 0)
    assertThrows[Exception](Problem8.find(arr0))
  }

}
