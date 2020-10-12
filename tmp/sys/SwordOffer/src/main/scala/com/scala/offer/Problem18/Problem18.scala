package com.scala.offer.Problem18

import com.scala.offer.Node.BTreeNode

/**
 * Created by Lemonjing on 2018/5/24.
 * Github: Lemonjing
 * 树的子结构
 */
object Problem18 {

  /**
   * @param root1 主树
   * @param root2 目标树
   * @return
   */
  def hasSubTree(root1: BTreeNode, root2: BTreeNode): Boolean = {
    var flag = false

    if (root1 != null && root2 != null) {
      flag = isSubTree(root1, root2)
      if (!flag) {
        flag = hasSubTree(root1.leftChild, root2) || hasSubTree(root1.rightChild, root2)
      }
    }

    flag
  }

  private def isSubTree(root1: BTreeNode, root2: BTreeNode): Boolean = {
    if (root2 == null) {
      return true
    }
    if (root1 == null) {
      return false
    }
    if (root1.data != root2.data) {
      return false
    } else {
      return isSubTree(root1.leftChild, root2.leftChild) && isSubTree(root1.rightChild, root2.rightChild)
    }
  }
}
