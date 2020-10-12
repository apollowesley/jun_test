package com.scala.offer.Problem49

/**
 * Created by Lemonjing on 2018/6/10.
 * Github: Lemonjing
 */
object Problem49 {

  def atoI(s: String): Int = {
    if (s == null || s.length <= 0) {
      throw new NumberFormatException("cast error.")
    }

    var sign = 1
    var total = 0
    var index = 0

    // handle spaces
    while (s.charAt(index) == ' ' && index < s.length) {
      index += 1
      if (index == s.length) {
        throw new NumberFormatException("cast error.")
      }
    }

    // handle sign
    if (s.charAt(index) == '+' || s.charAt(index) == '-') {
      sign = if (s.charAt(index) == '+') 1 else -1
      index += 1
      if (index == s.length) {
        throw new NumberFormatException("cast error.")
      }
    }

    // handle digit
    while (index < s.length) {
      val digit: Int = s.charAt(index) - '0'
      if (digit < 1 || digit > 9) {
        throw new NumberFormatException("cast error.")
      }
      //handle overflow
      if (total > Int.MaxValue / 10 || (total == Int.MaxValue && digit > Int.MaxValue % 10)) {
        if (sign == 1) return Int.MaxValue else Int.MinValue
      }
      total = 10 * total + digit
      index += 1
    }

    sign * total
  }
}
