package com.scala.offer.Problem6

import java.{util => ju}

import com.scala.offer.Node.BTreeNode

/**
 * Problem6
 * 根据二叉树前序和中序重建二叉树
 *
 * @author lemonjing
 */
object Problem6 {

  def constructBTree(pre: Array[Int], in: Array[Int]): BTreeNode = {
    if (pre == null || in == null || pre.length <= 0 || in.length <= 0) {
      return null
    }
    if (pre.length != in.length) {
      throw new Exception("error, array length is not equal.")
    }

    val root: BTreeNode = new BTreeNode(-1)
    var flag: Boolean = false
    for (i <- 0 until pre.length) {
      if (pre(0) == in(i)) {
        flag = true
        root.data = pre(0)
        root.leftChild = constructBTree(ju.Arrays.copyOfRange(pre, 1, i + 1), ju.Arrays.copyOfRange(in, 0, i))
        root.rightChild = constructBTree(ju.Arrays.copyOfRange(pre, i + 1, pre.length), ju.Arrays.copyOfRange(in, i + 1, in.length))
      }
    }

    if (!flag) {
      throw new Exception("error, no root node.")
    }

    root
  }
}
