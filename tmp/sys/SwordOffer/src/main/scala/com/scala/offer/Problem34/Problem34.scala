package com.scala.offer.Problem34

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 求从小到大的第1500个丑数
 * 丑数:只包含因子2,3,5的数称为丑数
 */
object Problem34 {

  // 直接求 速度慢
  def getUgly1(n: Int): Int = {
    if (n <= 0) return 0

    var count = 0
    var num = 0
    while (count < n) {
      num += 1
      if (isUgly(num)) count += 1
    }

    num
  }

  // 保存已产生的丑数，速度快
  def getUgly2(n: Int): Int = {
    if (n <= 0) return 0

    var m2 = 0
    var m3 = 0
    var m5 = 0
    val uglyArr = new Array[Int](n)
    uglyArr(0) = 1
    for (i <- 1 until n) {
      val min = min_3(uglyArr(m2) * 2, uglyArr(m3) * 3, uglyArr(m5) * 5)
      uglyArr(i) = min
      while (uglyArr(m2) * 2 <= min) {
        m2 += 1
      }
      while (uglyArr(m3) * 3 <= min) {
        m3 += 1
      }
      while (uglyArr(m5) * 5 <= min) {
        m5 += 1
      }
    }
    uglyArr(n - 1)
  }

  private def isUgly(num: Int): Boolean = {
    var _num = num
    while (_num % 2 == 0) {
      _num /= 2
    }
    while (_num % 3 == 0) {
      _num /= 3
    }
    while (_num % 5 == 0) {
      _num /= 5
    }

    if (_num == 1) true else false
  }

  private def min_3(num1: Int, num2: Int, num3: Int): Int = {
    val min = if (num1 < num2) num1 else num2
    if (min < num3) min else num3
  }
}
