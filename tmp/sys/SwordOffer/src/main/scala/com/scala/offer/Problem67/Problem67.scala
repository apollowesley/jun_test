package com.scala.offer.Problem67

/**
 * Created by Lemonjing on 2018/6/18.
 * Github: Lemonjing
 *
 * 机器人的运动范围
 * 10, 10, k=5时 => 21
 * 20, 20, 15 => 359
 */
object Problem67 {

  def movingCount(rows: Int, cols: Int, k: Int): Int = {
    if (rows < 0 || cols < 0 || k < 0) return 0

    val visited = new Array[Boolean](rows * cols)
    val count = movingCountCore(rows, cols, k, 0, 0, visited)
    count
  }

  private def movingCountCore(rows: Int, cols: Int, k: Int, row: Int, col: Int, visited: Array[Boolean]): Int = {
    var count = 0
    if (check(rows, cols, k, row, col, visited)) {
      visited(row * cols + col) = true
      count = 1 +
        movingCountCore(rows, cols, k, row + 1, col, visited) +
        movingCountCore(rows, cols, k, row - 1, col, visited) +
        movingCountCore(rows, cols, k, row, col + 1, visited) +
        movingCountCore(rows, cols, k, row, col - 1, visited)
    }
    count
  }

  private def check(rows: Int, cols: Int, k: Int, row: Int, col: Int, visited: Array[Boolean]): Boolean = {
    if (row >= 0 && row < rows && col >= 0 && col < cols && getDigitSum(row) + getDigitSum(col) <= k &&
      !visited(row * cols + col)) {
      return true
    }
    false
  }

  private def getDigitSum(num: Int): Int = {
    var sum = 0
    var _num = num
    while (_num > 0) {
      sum += _num % 10
      _num /= 10
    }
    sum
  }

  def main(args: Array[String]): Unit = {
    println(movingCount(10, 10, 5))
    println(movingCount(20, 20, 15))
  }
}
