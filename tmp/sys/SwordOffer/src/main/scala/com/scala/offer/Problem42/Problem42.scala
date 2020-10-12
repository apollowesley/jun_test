package com.scala.offer.Problem42

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 翻转单词 hello world => world hello
 */
object Problem42 {

  def reverseWord(s: String): String = {
    if (s == null || s.length <= 0) return s

    val wholeReverse = core(s, 0, s.length - 1)
    val reverseArr = wholeReverse.split(" ")
    val sb = new StringBuilder
    reverseArr.foreach {
      word => sb.append(core(word, 0, word.length -1)).append(" ")
    }

    sb.toString.trim
  }

  private def core(s: String, low:Int, high:Int):String = {
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
    val a = "hello world hadoop."
    val res = reverseWord(a);
    println(res)
  }
}
