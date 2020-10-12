package com.scala.offer.Problem52

/**
 * Created by Lemonjing on 2018/6/10.
 * Github: Lemonjing
 *
 * 构建乘积数组
 */
object Problem52 {

  def multiply(A: Array[Int], C: Array[Int]): Unit = {
    if (A == null || A.length <= 0 || C == null || C.length <= 0) return
    if (A.length != C.length) return

    C(0) = 1
    for (i <- 1 until A.length) {
      C(i) = C(i - 1) * A(i - 1)
    }
    var temp = 1
    for (i <- 0 until A.length - 1 reverse) {
      temp *= A(i + 1)
      C(i) *= temp
    }
  }

  def main(args: Array[String]): Unit = {
    val a = Array(1, 2, 3, 4, 5)
    val b = new Array[Int](5)
    multiply(a, b)
    println(b.mkString(","))
  }
}
