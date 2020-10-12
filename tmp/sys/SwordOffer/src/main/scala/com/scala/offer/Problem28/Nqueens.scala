package com.scala.offer.Problem28

/**
 * Created by Lemonjing on 2018/6/2.
 * Github: Lemonjing
 *
 * Problem28 相关 N皇后
 */
object Nqueens {

  var sum = 0
  var _n = 0
  var cols: Array[Int] = _

  def queens(n: Int): Unit = {
    _n = n
    cols = new Array[Int](_n)
    backtrack(0)
  }

  private def backtrack(k: Int): Unit = {
    if (k == _n) {
      sum += 1
      println(s"第${sum}种方法: ")
      for (i <- 0 until _n) {
        println(s"[$i,${cols(i)}]")
      }
    } else {
      for (i <- 0 until _n) {
        cols(k) = i
        if (place(k)) {
          backtrack(k + 1)
        }
      }
    }
  }

  private def place(k: Int): Boolean = {
    for (j <- 0 until k) {
      if (cols(j) == cols(k) || math.abs(cols(j) - cols(k)) == math.abs(j - k)) {
        return false
      }
    }
    true
  }
}
