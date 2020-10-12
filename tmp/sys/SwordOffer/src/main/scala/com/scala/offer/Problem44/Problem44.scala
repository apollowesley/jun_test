package com.scala.offer.Problem44

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 扑克牌的顺子
 *
 * 0 1 3 4 5 => true
 */
object Problem44 {

  def sortPoker(a: Array[Int]): Boolean = {
    if (a == null || a.length != 5) return false

    val b = a.sortWith(_ < _)
    var numOfZeros = 0
    for (i <- 0 until b.length) {
      if (b(i) == 0) numOfZeros += 1
    }

    val low = numOfZeros
    var gap = 0
    for (i <- low until b.length) {
      if (i == 0) {
        gap += b(i) - 1
      } else {
        if (b(i) == b(i - 1)) return false
        else gap += b(i) - b(i - 1) - 1
      }
    }

    if (numOfZeros >= gap) true else false
  }
}
