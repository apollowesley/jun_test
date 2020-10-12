package com.scala.offer.Problem59

import com.scala.offer.Node.BTreeNode

/**
 * Created by Lemonjing on 2018/6/17.
 * Github: Lemonjing
 *
 * 判断是否是对称二叉树
 *
 * 思路：前序遍历和对称前序遍历(先右孩子再左)一样
 *
 */
object Problem59 {

  def isSymmetric(root: BTreeNode): Boolean = {
    isSymmetric(root, root)
  }

  private def isSymmetric(root1: BTreeNode, root2: BTreeNode): Boolean = {
    if (root1 == null && root2 == null) return true
    if (root1 == null || root2 == null) return false
    if (root1.data != root2.data) return false

    isSymmetric(root1.leftChild, root2.rightChild) && isSymmetric(root1.rightChild) && isSymmetric(root2.leftChild)
  }
}
