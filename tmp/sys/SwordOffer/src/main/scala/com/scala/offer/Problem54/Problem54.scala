package com.scala.offer.Problem54

/**
 * Created by Lemonjing on 2018/6/11.
 * Github: Lemonjing
 *
 * 表示数值的字符串
 *
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是
 */
object Problem54 {

  def isNumberic(charArr: Array[Char]): Boolean = {
    if (charArr == null || charArr.length <= 0) return false

    var index = 0
    // 如果第一位是正数或者负数
    if (charArr(index) == '+' || charArr(index) == '-') {
      index += 1
    }
    // 如果此时达到数组的长度，证明此时字符数组为“+”，“-”
    if (index == charArr.length) return false

    var flag = true
    index = scanDigits(charArr, index)
    if (index < charArr.length) {
      // 下个字符为小数点
      if (charArr(index) == '.') {
        index += 1
        index = scanDigits(charArr, index)
        if (index < charArr.length) {
          // 小数后跟指数
          if (charArr(index) == 'e' || charArr(index) == 'E') {
            // 判断指数后的字符是否满足指数条件
            index += 1
            if (index == charArr.length) {
              // 到达尾部，形如123.46e
              flag = false
            } else {
              flag = isExponential(charArr, index)
            }
          } else {
            flag = false
          }
        } else {
          // 扫描到数组结尾，形如123.456
          flag = true
        }
      } else if (charArr(index) == 'e' || charArr(index) == 'E') {
        // 如果此时index指向字符为e或者E，则继续往后判断是否为指数位
        index += 1
        if (index == charArr.length) {
          // 到达尾部，形如123e
          flag = false
        } else {
          flag = isExponential(charArr, index)
        }
      } else {
        // 下个字符既不是小数点也不是指数
        flag = false
      }
    } else {
      // 已经扫描到字符串尾部，此时的字符串应为“234“
      flag = true
    }
    flag
  }

  private def scanDigits(charArr: Array[Char], index: Int): Int = {
    var _index = index
    while (_index < charArr.length && charArr(_index) >= '0' && charArr(_index) <= '9') {
      _index += 1
    }
    _index
  }

  private def isExponential(charArr: Array[Char], index: Int): Boolean = {
    var _index = index
    if (charArr(_index) == '+' || charArr(_index) == '-') {
      _index += 1
    }

    while (_index < charArr.length && charArr(_index) >= '0' && charArr(_index) < '9') {
      _index += 1
    }

    if (_index == charArr.length) true else false
  }

  def main(args: Array[String]): Unit = {
    val a = "+100"
    val b = "0123"
    val c = "12e+5.4"
    val d = "5e2"
    println(isNumberic(a.toCharArray))
    println(isNumberic(b.toCharArray))
    println(isNumberic(c.toCharArray))
    println(isNumberic(d.toCharArray))
  }
}
