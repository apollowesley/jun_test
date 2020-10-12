package com.scala.offer.Problem8

/**
 * Created by Lemonjing on 2018/5/13.
 * Github: Lemonjing
 * 求旋转数组的最小元素
 */
object Problem8 {

  def find(arr: Array[Int]): Int = {
    if (null == arr || arr.length < 1) {
      throw new Exception("array is null or empty.")
    }
    var low = 0
    var high = arr.length - 1
    while (arr(low) >= arr(high)) {
      if (high - low == 1) {
        return arr(high)
      }
      val mid = (low + high) / 2
      if (arr(low) == arr(mid) && arr(mid) == arr(high)) {
        return findInOrder(arr, low, high)
      }
      if (arr(mid) >= arr(low)) {
        low = mid
      } else {
        high = mid
      }
    }
    arr(high)
  }

  private def findInOrder(arr: Array[Int], low: Int, high: Int): Int = {
    var res = arr(low)
    for (i <- (low + 1) until high + 1) {
      if (arr(i) < res) {
        res = arr(i)
      }
    }
    res
  }
}
