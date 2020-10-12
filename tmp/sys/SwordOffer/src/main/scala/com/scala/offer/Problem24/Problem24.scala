package com.scala.offer.Problem24

import java.{util => ju}

/**
 * Created by Lemonjing on 2018/5/31.
 * Github: Lemonjing
 *
 * 二叉搜索树的后序遍历系列
 * 输入一整型数组，判断是不是某二叉搜索树的后序遍历结果
 * 如：5，7，6，9，11，10，8 true
 * 7，4，6，5 false
 */
object Problem24 {

  def verifySequenceOfBST(a: Array[Int]): Boolean = {
    if (a == null || a.length <= 0) {
      return false
    }

    val root = a(a.length - 1)
    var i = 0
    var flag = true
    while (i < a.length - 1 && flag) {
      if (a(i) > root)
        flag = false
      else i += 1
    }

    var j = i
    while (j < a.length - 1) {
      if (a(j) < root) return false
      else j += 1
    }

    var left = true
    var right = true
    if (i > 0) {
      left = verifySequenceOfBST(ju.Arrays.copyOfRange(a, 0, i))
    }
    if (i < a.length - 1) {
      right = verifySequenceOfBST(ju.Arrays.copyOfRange(a, i, a.length - 1))
    }

    left && right
  }
}
