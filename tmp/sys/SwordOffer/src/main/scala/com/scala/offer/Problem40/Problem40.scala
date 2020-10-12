package com.scala.offer.Problem40

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 数组中两个数字出现一次，其他都出现两次，找出这两个数字
 * 2,4,3,6,3,2,5,5 => 4,6
 */
object Problem40 {

  def get(a: Array[Int]): Array[Int] = {
    if (a == null || a.length < 2) return null

    var number = 0
    for (i <- 0 until a.length) {
      number ^= a(i)
    }
    val index = firstBitis1(number)
    var num1 = 0
    var num2 = 0
    for (i <- 0 until a.length) {
      if (isBit1(a(i), index)) {
        num1 ^= a(i)
      } else num2 ^= a(i)
    }
    Array(num1, num2)
  }

  // 右起的第一个1的bit的位置
  private def firstBitis1(num: Int): Int = {
    var index = 0
    var _num = num
    while ((_num & 1) == 0) {
      _num >>= 1
      index += 1
    }
    index
  }

  // 根据上一步的bit位分组
  private def isBit1(num: Int, index: Int): Boolean = {
    var _num = num
    for (i <- 0 until index) {
      _num >>= index
    }
    (_num & 1) == 1
  }
}
