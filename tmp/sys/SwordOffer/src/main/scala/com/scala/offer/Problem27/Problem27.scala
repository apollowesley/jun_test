package com.scala.offer.Problem27

import com.scala.offer.Node.BTreeNode

/**
 * Created by Lemonjing on 2018/6/1.
 * * Github: Lemonjing
 * * 二叉搜索树转为排序的双向链表
 * *         10
 * *       /   \
 * *      6    14
 * *     / \  / \
 * *    4  8 12 16
 * * 4 - 6 - 8 -10 - 12 - 14 -16
 */
object Problem27 {

  private var preNode:BTreeNode = _

  def convert(root: BTreeNode): BTreeNode = {
    if (root == null) return null
    convertNode(root)
    if (preNode == null) return null
    while (preNode.leftChild != null) {
      preNode = preNode.leftChild
    }
    preNode
  }

  private def convertNode(current: BTreeNode):Unit = {
    if (current == null) return
    if (current.leftChild != null) {
      convertNode(current.leftChild)
    }
    current.leftChild = preNode
    if (preNode != null) {
      preNode.rightChild = current
    }
    preNode = current
    if (current.rightChild != null) {
      convertNode(current.rightChild)
    }
  }
}
