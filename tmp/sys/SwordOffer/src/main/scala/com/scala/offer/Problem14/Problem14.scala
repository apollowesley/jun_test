package com.scala.offer.Problem14

/**
 * Created by Lemonjing on 2018/5/23.
 * Github: Lemonjing
 * 调整数组使奇数位于偶数前面
 */
object Problem14 {

  def reorder(a:Array[Int]): Unit = {
    if (a == null || a.length < 1) {
      return
    }

    var low = 0
    var high = a.length - 1
    while (low < high) {
      while (low < high && (a(high) & 1) == 0) {
        high -= 1
      }
      while (low < high && (a(low) & 1) == 1) {
        low += 1
      }
      if (low < high) {
        val temp = a(low)
        a(low) = a(high)
        a(high) = temp
      }
    }
  }
}
