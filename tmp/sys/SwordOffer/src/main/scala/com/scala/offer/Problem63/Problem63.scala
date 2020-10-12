package com.scala.offer.Problem63

import com.scala.offer.Node.BTreeNode

/**
 * Created by Lemonjing on 2018/6/17.
 * Github: Lemonjing
 * 二叉搜索树的第k大结点 如k=3 => 4
 *
 * 思路：中序遍历
 *
 * *        5
 * *      /   \
 * *     3     7
 * *    / \   / \
 * *   2  4  6  8
 */
object Problem63 {

  def getKthNode(root: BTreeNode, k: Int): BTreeNode = {
    if (root == null || k <= 0) return null
    getKthNodeCore(root, Array(k))
  }

  private def getKthNodeCore(root: BTreeNode, k: Array[Int]): BTreeNode = {
    var target: BTreeNode = null
    if (root.leftChild != null) {
      target = getKthNodeCore(root.leftChild, k)
    }
    if (target == null) {
      if (k(0) == 1) {
        return root
      } else {
        k(0) -= 1
      }
    }
    if (target == null && root.rightChild != null) {
      target = getKthNodeCore(root.rightChild, k)
    }
    target
  }
}
