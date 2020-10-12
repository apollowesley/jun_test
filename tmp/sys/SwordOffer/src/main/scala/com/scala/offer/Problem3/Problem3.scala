package com.scala.offer.Problem3

/**
 * Created by Lemonjing on 2018/5/7.
 * Github: Lemonjing
 * 二维数组查找指定数
 */
object Problem3 {

  def find(a: Array[Array[Int]], k: Int): Boolean = {
    if (a == null || a.length <= 0) return false
    a.foreach(k => if (k == null) return false)
    var row = 0
    var col = a(0).length - 1
    while (row < a.length && col >= 0) {
      if (a(row)(col) == k) {
        return true
      } else if (k < a(row)(col)) {
        col -= 1
      } else {
        row += 1
      }
    }
    false
  }
}
