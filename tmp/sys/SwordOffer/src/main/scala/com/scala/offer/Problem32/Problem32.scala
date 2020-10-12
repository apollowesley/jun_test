package com.scala.offer.Problem32

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 *
 * 1到n数字中1的个数，如12，一共5个数字有1
 */
object Problem32 {

  def getNumberOf1Between1AndN(n: Int): Int = {
    if (n <= 0) return 0

    var count = 0
    for (i <- 1 until n+1) {
      var res = i
      while (res != 0) {
        if (res % 10 == 1) count += 1
        res = res / 10
      }
    }
    count
  }
}
