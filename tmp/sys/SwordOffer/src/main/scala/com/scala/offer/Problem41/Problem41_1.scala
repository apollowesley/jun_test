package com.scala.offer.Problem41

import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/6/6.
 * Github: Lemonjing
 *
 * Problem41扩展 和为s的连续正数序列
 * 双指针法
 * 输入15，由于1+2+3+4+5=4+5+6=7+8=15；所以打印出三个连续序列1~5,4~6,7~8；
 */
object Problem41_1 {

  def get(s: Int): List[List[Int]] = {
    if (s < 3) return null

    val list = new ListBuffer[List[Int]]
    var low = 1
    var high = 2
    while (low < (s + 1 >> 1)) {
      var curSum = 0
      for (i <- low until high + 1) {
        curSum += i
      }
      if (curSum == s) {
        list.append(List(low, high))
        low += 1
      } else if (curSum > s) {
        low += 1
      } else {
        high += 1
      }
    }
    list.toList
  }

  def main(args: Array[String]): Unit = {
    println(get(15))
  }
}
