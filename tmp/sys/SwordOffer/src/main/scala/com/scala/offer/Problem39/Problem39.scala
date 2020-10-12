package com.scala.offer.Problem39

import com.scala.offer.Node.BTreeNode

/**
 * Created by Lemonjing on 2018/5/1.
 * Github: Lemonjing
 * 二叉树的深度 判平衡等
 */
object Problem39 {

  def getDepth(root: BTreeNode): Int = {
    if (root == null) return 0

    val left = getDepth(root.leftChild)
    val right = getDepth(root.rightChild)

    if (left > right) left + 1 else right + 1
  }

  def isBalanced(root: BTreeNode):Boolean = {
    if (root == null) return true

    val left = getDepth(root.leftChild)
    val right = getDepth(root.rightChild)
    val dif = left - right

    if (dif > 1 || dif < -1) return false

    isBalanced(root.leftChild) && isBalanced(root.rightChild)
  }
}
