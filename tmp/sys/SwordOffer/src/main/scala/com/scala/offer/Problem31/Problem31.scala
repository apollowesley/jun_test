package com.scala.offer.Problem31

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 * 连续子数组的最大和
 * DP法
 * [1,-2,3,10,-4,7,2,-5] => [3,10,-4,7,2]=18
 */
object Problem31 {

  def getContinuosGreatestSum(a: Array[Int]): Int = {
    if (a == null || a.length <= 0) {
      throw new Exception("arrays is null or empty.")
    }
    var res = a(0)
    var sum = a(0)
    for (i <- 1 until a.length) {
      sum = if (sum < 0) a(i) else sum + a(i)
      if (sum > res) {
        res = sum
      }
    }
    res
  }
}
