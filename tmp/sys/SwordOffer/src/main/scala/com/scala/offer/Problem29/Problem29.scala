package com.scala.offer.Problem29

/**
 * Created by Lemonjing on 2018/6/2.
 * Github: Lemonjing
 * 找数组中出现超过一半的数字
 */
object Problem29 {

  def moreThanHalf(a: Array[Int]):Int = {
    if (a == null || a.length <= 0)
      throw new NoSuchElementException

    var res = a(0)
    var count = 1
    for (i <- 1 until a.length) {
      if (count == 0) {
        res = a(i)
        count = 1
      } else if (a(i) == res) {
        count += 1
      } else {
        count -= 1
      }
    }
    if(check(a, res)) {
      res
    } else {
      throw new NoSuchElementException
    }
  }

  private def check(a: Array[Int], num:Int):Boolean = {
    var times = 0
    for (i <- 0 until a.length) {
      if (a(i) == num) times += 1
    }
    if (2*times > a.length) true
    else false
  }
}
