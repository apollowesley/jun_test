package com.scala.offer.Problem30

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 *
 * 最小的k个数字
 *
 * 大顶堆法
 * 4, 5, 1, 6, 2, 7, 3, 8 => 1,2,3,4
 */

import java.{util => ju}

object Problem30 {

  def getLeastKNumbers(a: Array[Int], k: Int):Array[Int] = {
    if (a == null || a.length <= 0 || k < 0 || k > a.length) {
      return null
    }
    val kArr = ju.Arrays.copyOfRange(a, 0, k)
    buildMaxHeap(kArr)
    for (i <- k until a.length) {
      if (a(i) < kArr(0)) {
        kArr(0) = a(i)
        adjustHeap(kArr, 0)
      }
    }
    kArr
  }

  private def buildMaxHeap(a: Array[Int]) = {
    for (i <- 0 until a.length / 2 reverse) {
      adjustHeap(a, i)
    }
  }

  private def adjustHeap(a: Array[Int], parent: Int):Unit = {
    var largest = parent
    val left = 2 * parent + 1
    val right = 2 * parent + 2

    if (left < a.length && a(left) > a(largest)) {
      largest = left
    }
    if (right < a.length && a(right) > a(largest)) {
      largest = right
    }
    if (largest != parent) {
      val temp = a(largest)
      a(largest) = a(parent)
      a(parent) = temp
      adjustHeap(a, largest)
    }
  }
}
