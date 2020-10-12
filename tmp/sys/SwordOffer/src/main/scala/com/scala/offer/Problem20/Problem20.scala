package com.scala.offer.Problem20

import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/5/28.
 * Github: Lemonjing
 */
object Problem20 {

  def printMatrixByClockWise(a: Array[Array[Int]]): List[Int] = {
    if (a == null || a.length <= 0) return null
    val res = new ListBuffer[Int]
    var start = 0
    val rows = a.length
    val cols = a(0).length
    while (2 * start < rows && 2 * start < cols) {
      printOneCircle(a, start, res)
      start += 1
    }
    res.toList
  }

  private def printOneCircle(a: Array[Array[Int]], start: Int, res: ListBuffer[Int]): Unit = {
    val row = a.length - start
    val col = a(0).length - start

    // 从左到右一行
    for (i <- start until col) {
      res.append(a(start)(i))
    }
    // 从上到下一列
    if (start < row - 1) {
      for (i <- start + 1 until row) {
        res.append(a(i)(col - 1))
      }
    }
    // 从右到左一行
    if (start < row - 1 && start < col - 1) {
      for (i <- start until col - 1 reverse) {
        res.append(a(row - 1)(i))
      }
    }
    // 从下到上一列
    if (start < row - 2 && start < col - 1) {
      for (i <- start + 1 until row - 1 reverse) {
        res.append(a(i)(start))
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val a1 = Array(Array(1, 2, 3, 4))
    printMatrixByClockWise(a1)
  }
}
