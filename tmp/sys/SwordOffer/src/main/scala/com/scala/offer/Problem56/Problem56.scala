package com.scala.offer.Problem56

/**
 * Created by Lemonjing on 2018/5/4.
 * Github: Lemonjing
 * 字符流中第一个不重复的字符
 */
object Problem56 {

  private val input = Array.fill(256)(-1)
  private var index = 0

  def insert(ch: Char) = {
    if (input(ch) == -1) {
      input(ch) = index
    } else if (input(ch) >= 0) {
      input(ch) = -2
    }
    index += 1
  }

  def firstAppearOnce(): Char = {
    var minIndex = Int.MaxValue
    var ch  = ' '
    for (i <- 0 until input.length if input(i) >= 0) {
      if (input(i) < minIndex) {
        minIndex = input(i)
        ch = i.toChar
      }
    }
    ch
  }

  def main(args: Array[String]): Unit = {
    insert('g')
    insert('o')
    insert('o')
    insert('g')
    insert('l')
    insert('e')
    println(firstAppearOnce())
  }
}
