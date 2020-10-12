package com.scala.offer.Problem51

/**
 * Created by Lemonjing on 2018/6/10.
 * Github: Lemonjing
 *
 * {2,3,1,0,2,5,3} => 2
 */
object Problem51 {

  def dedup(a: Array[Int], dedup: Array[Int]): Boolean = {
    if (a == null || a.length <= 0) return false

    for (i <- 0 until a.length) {
      if (a(i) < 0 || a(i) > a.length - 1) {
        return false
      }
    }
    for (i <- 0 until a.length) {
      while (a(i) != i) {
        if (a(i) == a(a(i))) {
          dedup(0) = a(i)
          return true
        }
        //swap
        val temp = a(i)
        a(i) = a(temp)
        a(temp) = temp
      }
    }
    false
  }

  def main(args: Array[String]): Unit = {
    val a = Array(2, 3, 1, 0, 2, 5, 3)
    val res = new Array[Int](1)
    dedup(a, res)
    println(res.mkString(","))
  }
}
