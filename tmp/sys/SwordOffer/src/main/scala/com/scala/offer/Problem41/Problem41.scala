package com.scala.offer.Problem41

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * TwoSum双指针法
 * 排序数组中指定和的两个数
 */
object Problem41 {

  def twoSum(a: Array[Int], sum: Int): Array[Int] = {
    if (a == null || a.length < 2) return null

    var low = 0
    var high = a.length - 1
    while (low < high) {
      val curSum = a(low) + a(high)
      if (curSum == sum) {
        return Array(a(low), a(high))
      } else if (curSum > sum) {
        high -= 1
      } else {
        low += 1
      }
    }
    null
  }
}
