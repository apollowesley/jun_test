package com.scala.offer.Problem33

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 把数组[3,32,321]排成最小的数 -> 321323
 */
object Problem33 {

  def arrToMinNumber(a: Array[Int]): String = {
    if (a == null || a.length <= 0) {
      return null
    }
    val b = a.sortWith((o1, o2) => {
      val s1 = String.valueOf(o1) + String.valueOf(o2)
      val s2 = String.valueOf(o2) + String.valueOf(o1)
      s1 < s2
    })
    b.mkString("")
  }
}
