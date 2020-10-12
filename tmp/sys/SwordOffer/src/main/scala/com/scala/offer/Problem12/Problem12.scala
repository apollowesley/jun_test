package com.scala.offer.Problem12

/**
 * Created by Lemonjing on 2018/5/13.
 * Github: Lemonjing
 * 输入数字n, 按顺序打印出从1到最大的n位十进制数
 */
object Problem12 {

  def print1ToNDigits(n: Int): Unit = {
    if (n <= 0) return
    val number: Array[Char] = new Array[Char](n)
    for (i <- 0 until 10) {
      number(0) = (i + '0').toChar
      printRecursive(number, 0, number.length)
    }
  }

  private def printRecursive(number: Array[Char], index: Int, len: Int): Unit = {
    if (index == len - 1) {
      val s = new String(number)
      var break = false
      for (i <- 0 until s.length if !break) {
        if (s.charAt(i) != '0') {
          println(s.substring(i))
          break = true
        }
      }
      return
    }
    for (i <- 0 until 10) {
      number(index + 1) = (i + '0').toChar
      printRecursive(number, index + 1, len)
    }
  }

  def main(args: Array[String]): Unit = {
    print1ToNDigits(3)
  }
}
