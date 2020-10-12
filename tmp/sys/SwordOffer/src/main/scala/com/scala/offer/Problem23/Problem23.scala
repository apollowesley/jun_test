package com.scala.offer.Problem23

import com.scala.offer.Node.BTreeNode
import java.{util => ju}
import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/5/31.
 * Github: Lemonjing
 * 二叉树的层序遍历
 */
object Problem23 {

  def levelTraverse(root: BTreeNode):List[BTreeNode] = {
    if (root == null) return null
    val nodes = new ListBuffer[BTreeNode]
    val queue = new ju.LinkedList[BTreeNode]

    queue.offer(root)
    while (!queue.isEmpty) {
      val node = queue.poll()
      nodes.append(node)
      if (node.leftChild != null) {
        queue.add(node.leftChild)
      }
      if (node.rightChild != null) {
        queue.add(node.rightChild)
      }
    }
    nodes.toList
  }
}
