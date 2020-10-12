package com.scala.offer.Problem45

import java.{util => ju}

/**
 * Created by Lemonjing on 2018/6/9.
 * Github: Lemonjing
 * 约瑟夫环
 *
 * 0,1,2,3,4 m=3 => 2,0,4,1 => 3
 */
object Problem45 {

  def josephLoop1(n: Int, m: Int): Int = {
    if (n < 1 || m < 1) return -1

    val loop = new ju.ArrayList[Int]()
    for (i <- 0 until n) {
      loop.add(i)
    }
    var index = 0
    while (loop.size > 1) {
      index = (index + m - 1) % loop.size()
      loop.remove(index)
    }

    loop.get(0)
  }

  def josephLoop2(n: Int, m: Int): Int = {
    if (n < 1 || m < 1) return -1

    var pre = 0
    for (i <- 2 until n + 1) {
      pre = (pre + m) % i
    }

    pre
  }

  def main(args: Array[String]): Unit = {
    println(josephLoop1(5, 3))
    println(josephLoop2(5, 3))
  }
}
