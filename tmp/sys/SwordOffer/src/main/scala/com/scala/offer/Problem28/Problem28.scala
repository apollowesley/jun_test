package com.scala.offer.Problem28

import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/6/2.
 * Github: Lemonjing
 *
 * 字符串的全排列
 * 如abc => abc,acb,bac,bca,cab,cba
 */
object Problem28 {

  def permOfString(input: String): List[String] = {
    if (input == null || input.isEmpty) return null
    val low = 0
    val high = input.length - 1
    val list = new ListBuffer[String]
    permCore(input.toCharArray, low, high, list)

    list.toList
  }

  private def permCore(input: Array[Char], low: Int, high: Int, list: ListBuffer[String]):Unit = {
    if (low == high) {
      // 允许重复 如aa=>aa,aa
      list.append(String.valueOf(input))
    } else {
      for (i <- low to high) {
        swap(input, i, low)
        permCore(input, low + 1, high, list)
        swap(input, i, low)
      }
    }
  }

  private def swap(a:Array[Char], low:Int, high:Int) = {
    val temp = a(low)
    a(low) = a(high)
    a(high) = temp
  }
}
