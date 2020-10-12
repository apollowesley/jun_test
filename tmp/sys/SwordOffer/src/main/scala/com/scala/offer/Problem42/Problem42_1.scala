package com.scala.offer.Problem42

/**
 * Created by Lemonjing on 2018/6/7.
 * Github: Lemonjing
 *
 * 左旋字符串 abcdefg,2 => cdefgab
 */
object Problem42_1 {

  def leftReverse(s: String, n: Int): String = {
    if (s == null || s.length <= 0 || n <= 0 || n >= s.length) return s

    val s1 = s.substring(0, n)
    val reverse1 = core(s1, 0, s1.length - 1)
    val s2 = s.substring(n)
    val reverse2 = core(s2, 0, s2.length - 1)
    val s3 = reverse1 + reverse2

    core(s3, 0, s3.length - 1)
  }

  private def core(s: String, low: Int, high: Int): String = {
    val charArr = s.toCharArray
    val mid = (low + high) >> 1
    var _high = high
    for (i <- low until mid + 1) {
      val temp = charArr(_high)
      charArr(_high) = charArr(i)
      charArr(i) = temp
      _high -= 1
    }
    String.valueOf(charArr)
  }

  def main(args: Array[String]): Unit = {
    val a = "abcdefg"
    println(leftReverse(a, 2))
  }
}
