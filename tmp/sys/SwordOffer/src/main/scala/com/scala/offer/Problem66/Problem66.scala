package com.scala.offer.Problem66

/**
 * Created by Lemonjing on 2018/6/18.
 * Github: Lemonjing
 *
 * 矩阵中的路径
 * a b c e
 * s f c s
 * a d e e
 *
 * input bcced => true abcb => false
 */
object Problem66 {

  def hasPath(matrix: Array[Char], rows: Int, cols: Int, str: Array[Char]): Boolean = {
    if (matrix == null || rows <= 0 || cols <= 0 || str == null) return false
    val visited = new Array[Boolean](rows * cols)
    val pathLen = Array(0)
    for (row <- 0 until rows) {
      for (col <- 0 until cols) {
        if (hasPathCore(matrix, str, rows, cols, row, col, visited, pathLen)) {
          return true
        }
      }
    }
    false
  }

  private def hasPathCore(matrix: Array[Char], str: Array[Char], rows: Int, cols: Int, row: Int, col: Int,
                          visited: Array[Boolean], pathLen: Array[Int]): Boolean = {
    if (pathLen(0) == str.length) return true

    var hasPath = false
    if (row >= 0 && row < rows && col >= 0 && col < cols && matrix(row * cols + col) == str(pathLen(0))
      && !visited(row * cols + col)) {
      pathLen(0) += 1
      visited(row * cols + col) = true
      hasPath = hasPathCore(matrix, str, rows, cols, row - 1, col, visited, pathLen) ||
          hasPathCore(matrix, str, rows, cols, row + 1, col, visited, pathLen) ||
          hasPathCore(matrix, str, rows, cols, row, col - 1, visited, pathLen) ||
          hasPathCore(matrix, str, rows, cols, row, col + 1, visited, pathLen)
      if (!hasPath) {
        pathLen(0) -= 1
        visited(row * cols + col) = false
      }
    }
    hasPath
  }

  def main(args: Array[String]): Unit = {
    val matrix = Array('a', 'b', 'c', 'e', 's', 'f', 'c', 's', 'a', 'd', 'e', 'e')
    val str1 = Array('b', 'c', 'c', 'e', 'd')
    val str2 = Array('a', 'b', 'c', 'b')
    println(hasPath(matrix, 3, 4, str1))
    println(hasPath(matrix, 3, 4, str2))
  }
}
