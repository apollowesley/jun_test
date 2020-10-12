package com.scala.offer.Problem19

import com.scala.offer.Node.BTreeNode
import java.{util => ju}

/**
 * Created by Lemonjing on 2018/5/28.
 * Github: Lemonjing
 * 二叉树的镜像
 */
object Problem19 {

  def mirror(root: BTreeNode): BTreeNode = {
    if (root == null || (root.leftChild == null && root.rightChild == null)) {
      return root
    }

    val stack = new ju.Stack[BTreeNode]
    stack.push(root)
    while (!stack.isEmpty) {
      val node = stack.pop()
      // exchange
      val temp = node.leftChild
      node.leftChild = node.rightChild
      node.rightChild = temp

      if (node.leftChild != null) {
        stack.push(node.leftChild)
      }
      if (node.rightChild != null) {
        stack.push(node.rightChild)
      }
    }
    root
  }

  def mirrorRecursive(root: BTreeNode): BTreeNode = {
    if (root == null || (root.leftChild == null && root.rightChild == null)) {
      return root
    }

    val temp = root.leftChild
    root.leftChild = root.rightChild
    root.rightChild = temp

    if (root.leftChild != null) {
      mirrorRecursive(root.leftChild)
    }

    if (root.rightChild != null) {
      mirrorRecursive(root.rightChild)
    }
    root
  }
}
