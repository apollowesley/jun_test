package com.scala.offer.Problem11

/**
 * Created by Lemonjing on 2018/5/13.
 * Github: Lemonjing
 * power函数
 */
object Problem11 {
  def power(base: Double, exponent: Int): Double = {
    if (doubleEqual(base, 0) && exponent <= 0) {
      throw new Exception("error, 0的负数次幂或0的0次方无意义")
    } else if (doubleEqual(base, 0) && exponent > 0) {
      return 0
    }
    if (exponent == 0) {
      return 1
    }
    var res = 1.0
    if (exponent > 0) {
      var _exponent = exponent
      while (_exponent >= 1) {
        res = res * base
        _exponent -= 1
      }
      res
    } else {
      var _exponent = -exponent
      while (_exponent >= 1) {
        res = res * base
        _exponent -= 1
      }
      res = 1.0 / res
      res
    }
  }

  private def doubleEqual(num1:Double, num2:Double):Boolean = {
    if (Math.abs(num1 - num2) < 0.0000001) {
      true
    } else {
      false
    }
  }
}
