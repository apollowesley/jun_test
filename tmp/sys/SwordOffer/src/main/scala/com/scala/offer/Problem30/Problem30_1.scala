package com.scala.offer.Problem30

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 *
 * 最小的k个数字
 *
 * 类快排的partition法
 * 4, 5, 1, 6, 2, 7, 3, 8 => 1,2,3,4
 */

import java.{util => ju}

object Problem30_1 {

  def getLeastKNumbers(a: Array[Int], k: Int): Array[Int] = {
    if (a == null || a.length <= 0 || k < 0 || k > a.length) {
      return null
    }

    var low = 0
    var high = a.length - 1
    var index = partition(a, low, high, k)
    while (index != k - 1) {
      if (index > k - 1) {
        high = index - 1
        index = partition(a, low, high, k)
      } else {
        low = index + 1
        index = partition(a, low, high, k)
      }
    }
    val kArr = ju.Arrays.copyOfRange(a, 0, k)
    kArr
  }

  private def partition(a: Array[Int], low: Int, high: Int, k: Int): Int = {
    // make a(k-1) as pivot
    val temp = a(low)
    a(low) = a(k - 1)
    a(k - 1) = temp

    val pivot = a(low)
    var _low = low
    var _high = high
    while (_low < _high) {
      while (_low < _high && a(_high) >= pivot) {
        _high -= 1
      }
      a(_low) = a(_high)
      while (_low < _high && a(_low) <= pivot) {
        _low += 1
      }
      a(_high) = a(_low)
    }
    a(_low) = pivot
    _low
  }
}
