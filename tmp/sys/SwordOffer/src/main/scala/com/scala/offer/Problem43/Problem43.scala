package com.scala.offer.Problem43

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * n个骰子的点数和s 求s的所有可能值出现的概率
 */
object Problem43 {

  def getWithRecursive(n: Int): Unit = {
    if (n < 1) return

    // 存储点数和s出现的次数，和为s出现的次数位于位置s-n
    val probs = new Array[Int](6 * n - n + 1)
    probability(n, probs)

    val wholeTimes = math.pow(6, n)
    for (i <- n until 6 * n + 1) {
      val p = probs(i - n) / wholeTimes
      print(s"$i:$p, ")
    }
  }

  def getWithArray(n: Int): Unit = {
    if (n < 1) return

    val probs = Array.ofDim[Int](2, 6 * n + 1)

    var flag = 0
    // 第一个骰子
    for (i <- 1 until 7) {
      probs(flag)(i) = 1
    }
    // 第一个for为第二个骰子开始
    for (i <- 2 until n + 1) {
      // 清空第二个数组
      for (j <- 1 until 6 * n + 1) {
        probs(1 - flag)(j) = 0
      }
      // 第二个for为从最小点数到最大点数
      for (j <- i until 6 * n + 1) {
        for (k <- 1 until 7 if k < j) {
          probs(1 - flag)(j) += probs(flag)(j - k)
        }
      }
      flag = 1 - flag
    }

    val wholeTimes = math.pow(6, n)
    for (i <- n until 6 * n + 1) {
      val p = probs(flag)(i) / wholeTimes
      print(s"$i:$p, ")
    }
  }

  private def probability(n: Int, probs: Array[Int]): Unit = {
    for (i <- 1 until 7) {
      probability(n, n, i, probs)
    }
  }

  private def probability(originalN: Int, currentN: Int, sum: Int, probs: Array[Int]): Unit = {
    if (currentN == 1) {
      probs(sum - originalN) += 1
    } else {
      for (i <- 1 until 7) {
        probability(originalN, currentN - 1, sum + i, probs)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    getWithRecursive(3)
    println()
    getWithArray(3)
  }
}
