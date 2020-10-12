package com.scala.offer.Problem62

import com.scala.offer.Node.BTreeNode

/**
 * Created by Lemonjing on 2018/6/17.
 * Github: Lemonjing
 *
 * 序列化二叉树
 * *        5
 * *      /   \
 * *     3     7
 * *    / \   / \
 * *   2  4  6  8
 */
object Problem62 {

  var index = -1

  def serialize(root: BTreeNode): String = {
    if (root == null) return ""
    val sb = new StringBuilder
    serialize(root, sb)
    sb.toString
  }

  private def serialize(root: BTreeNode, sb: StringBuilder): Unit = {
    if (root == null) {
      sb.append("$,")
      return
    }
    sb.append(root.data).append(",")
    serialize(root.leftChild, sb)
    serialize(root.rightChild, sb)
  }

  def deserialize(str: String): BTreeNode = {
    if (str == null || str.length <= 0) return null
    val strs = str.split(",")
    deserialize(strs)
  }

  private def deserialize(strs: Array[String]): BTreeNode = {
    index += 1
    if (!(strs(index) == "$")) {
      val root = new BTreeNode(strs(index).toInt)
      root.leftChild = deserialize(strs)
      root.rightChild = deserialize(strs)
      return root
    }
    null
  }
}
