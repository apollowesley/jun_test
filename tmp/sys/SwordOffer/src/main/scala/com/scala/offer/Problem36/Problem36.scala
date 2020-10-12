package com.scala.offer.Problem36

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 如数组{7,5,6,4}，逆序对总共有5对，{7,5}，{7,6}，{7,4}，{5,4}，{6,4}
 */
object Problem36 {

  // nlgn解法
  def inversePairs1(data: Array[Int]): Int = {
    if (data == null || data.length < 2) return 0

    val temp = new Array[Int](data.length)
    core(data, temp, 0, data.length - 1)
  }

  // n^2解法
  def inversePairs2(data: Array[Int]): Int = {
    if (data == null || data.length < 2) return 0

    var count = 0
    for (i <- 0 until data.length) {
      for (j <- i + 1 until data.length) {
        if (data(i) > data(j)) count += 1
      }
    }
    count
  }

  private def core(data: Array[Int], temp: Array[Int], start: Int, end: Int): Int = {
    if (start == end) {
      temp(start) = data(start)
      return 0
    }

    val mid = (start + end) >> 1
    val left = core(data, temp, start, mid)
    val right = core(data, temp, mid + 1, end)

    var i = mid
    var j = end
    var k = end
    var count = 0
    while (i >= start && j >= mid + 1) {
      if (data(i) > data(j)) {
        temp(k) = data(i)
        i -= 1
        k -= 1
        count += (j - mid)
      } else {
        temp(k) = data(j)
        j -= 1
        k -= 1
      }
    }
    while (i >= start) {
      temp(k) = data(i)
      i -= 1
      k -= 1
    }
    while (j >= mid + 1) {
      temp(k) = data(j)
      j -= 1
      k -= 1
    }
    for (i <- start until end + 1) {
      data(i) = temp(i)
    }

    left + right + count
  }
}
