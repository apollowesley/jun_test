package com.scala.offer.Problem38

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 排序数组查找指定数字的次数
 *
 * Array(1, 2, 3, 3, 3, 3, 4, 5) => 4
 */
object Problem38 {

  def getTimes(a: Array[Int], k: Int): Int = {
    if (a == null || a.length <= 0) return 0

    var low = binarySearch(a, 0, a.length - 1, k)
    if (low == -1) return 0

    var high = low
    while (low > 0 && a(low - 1) == k) {
      low = binarySearch(a, 0, low - 1, k)
    }
    while (high < a.length - 1 && a(high + 1) == k) {
      high = binarySearch(a, high + 1, a.length - 1, k)
    }

    high - low + 1
  }

  private def binarySearch(a: Array[Int], low: Int, high: Int, num: Int): Int = {
    if (a == null || a.length <= 0) return -1

    var _low = low
    var _high = high
    while (_low <= _high) {
      val mid = (_low + _high) >> 1
      if (a(mid) > num) {
        _high = mid - 1
      } else if (a(mid) < num) {
        _low = mid + 1
      } else {
        return mid
      }
    }
    -1
  }
}
