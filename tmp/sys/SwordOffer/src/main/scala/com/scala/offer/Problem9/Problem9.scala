package com.scala.offer.Problem9

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 斐波那契数
 */
object Problem9 {

  // 递归
  def recursiveFibonacci(n: Int): Int = {
    if (n < 0) {
      throw new Exception("n is less than 0")
    }
    if (n == 0) {
      return 0
    }
    if (n == 1) {
      return 1
    }
    recursiveFibonacci(n - 1) + recursiveFibonacci(n - 2)
  }

  def fibonacci(n: Int): Int = {
    if (n < 0) {
      throw new Exception("n is less than 0")
    }
    if (n == 0) {
      return 0
    }
    if (n == 1) {
      return 1
    }
    var fib1 = 0
    var fib2 = 1
    var fibN = 0
    for (i <- 2 until n + 1) {
      fibN = fib1 + fib2
      fib1 = fib2
      fib2 = fibN
    }
    fibN
  }
}
