package com.scala.offer.Problem35

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 字符串中第一个只出现一次的字符位置
 * 如gogoup => u
 */

import java.{util => ju}
import scala.collection.JavaConverters._

object Problem35 {

  // LinkedHashMap法
  def getFirstNotRepeatWithMap(s: String): Character = {
    if (s == null || s.length <= 0) return null

    val lmap = new ju.LinkedHashMap[Char, Int]()
    s.foreach {
      ch =>
        if (lmap.containsKey(ch)) {
          lmap.put(ch, lmap.get(ch) + 1)
        } else lmap.put(ch, 1)
    }

    lmap.asScala.foreach {
      kv => if (kv._2 == 1) return kv._1
    }
    null
  }

  // 数组法
  def getFirstNotRepeatWithArray(s: String): Character = {
    if (s == null || s.length <= 0) return null

    val num = new Array[Int](256)
    val index = new Array[Int](256)
    ju.Arrays.fill(index, -1)

    for (i <- 0 until s.length) {
      num(s.charAt(i)) += 1
      if (index(s.charAt(i)) == -1) index(s.charAt(i)) = i
    }

    var minIndex = Integer.MAX_VALUE
    for (i <- 0 until num.length) {
      if (num(i) == 1) {
        minIndex = if (index(i) < minIndex) index(i) else minIndex
      }
    }
    if (minIndex != Integer.MAX_VALUE) return s.charAt(minIndex)

    null
  }
}
