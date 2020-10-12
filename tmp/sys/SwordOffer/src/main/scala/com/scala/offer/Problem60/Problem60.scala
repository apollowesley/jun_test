package com.scala.offer.Problem60

import com.scala.offer.Node.BTreeNode
import java.{util => ju}

/**
 * Created by Lemonjing on 2018/6/17.
 * Github: Lemonjing
 *
 * 二叉树打印成多行
 *
 * 思路：lineNodes代表当前行结点数 nextLineNodes下一行结点数
 *
 * *        5
 * *      /   \
 * *     3     7
 * *    / \   / \
 * *   2  4  6  8
 */
object Problem60 {

  def levelPrint(root: BTreeNode): Unit = {
    if (root == null) return

    val queue = new ju.LinkedList[BTreeNode]()
    queue.offer(root)
    var lineNodes = 1
    var nextLineNodes = 0
    while (!queue.isEmpty) {
      val node = queue.poll()
      print(node.data + " ")
      lineNodes -= 1
      if (node.leftChild != null) {
        queue.offer(node.leftChild)
        nextLineNodes += 1
      }
      if (node.rightChild != null) {
        queue.offer(node.rightChild)
        nextLineNodes += 1
      }
      if (lineNodes == 0) {
        println()
        lineNodes = nextLineNodes
        nextLineNodes = 0
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
    levelPrint(root)
  }
}
