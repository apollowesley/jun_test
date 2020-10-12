package com.scala.offer.Problem47

/**
 * Created by Lemonjing on 2018/6/10.
 * Github: Lemonjing
 *
 * 不使用加减乘除做加法
 * 三步走 先异或再与求进位第三步相加
 */
object Problem47 {

  def add(num1: Int, num2:Int): Int = {
    var sum = 0
    var carry = 0
    var _num1 = num1
    var _num2 = num2

    do {
      sum = _num1 ^ _num2
      carry = (_num1 & _num2) << 1
      _num1 = sum
      _num2 = carry
    } while (_num2 != 0)

    _num1
  }
}
