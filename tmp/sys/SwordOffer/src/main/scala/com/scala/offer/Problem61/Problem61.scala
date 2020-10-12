package com.scala.offer.Problem61

import com.scala.offer.Node.BTreeNode
import java.{util => ju}

/**
 * Created by Lemonjing on 2018/6/17.
 * Github: Lemonjing
 *
 * 之字形打印二叉树
 *
 * 思路：两个栈实现
 *
 * *        5
 * *      /   \
 * *     3     7
 * *    / \   / \
 * *   2  4  6  8
 */
object Problem61 {

  def zigzag(root: BTreeNode): Unit = {
    if (root == null) return

    val stacks = Array.fill(2)(new ju.Stack[BTreeNode])
    var current = 0
    var next = 1
    stacks(current).push(root)
    while (!stacks(0).isEmpty || !stacks(1).isEmpty) {
      val node = stacks(current).pop()
      print(node.data + " ")
      if (current == 0) {
        if (node.leftChild != null) stacks(next).push(node.leftChild)
        if (node.rightChild != null) stacks(next).push(node.rightChild)
      } else {
        if (node.rightChild != null) stacks(next).push(node.rightChild)
        if (node.leftChild != null) stacks(next).push(node.leftChild)
      }
      if (stacks(current).isEmpty) {
        println
        current = 1 - current
        next = 1 - next
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val root = new BTreeNode(5)
    val node1 = new BTreeNode(3)
    val node2 = new BTreeNode(7)
    val node3 = new BTreeNode(2)
    val node4 = new BTreeNode(4)
    val node5 = new BTreeNode(6)
    val node6 = new BTreeNode(8)
    root.leftChild = node1
    root.rightChild = node2
    node1.leftChild = node3
    node1.rightChild = node4
    node2.leftChild = node5
    node2.rightChild = node6
    zigzag(root)
  }
}
