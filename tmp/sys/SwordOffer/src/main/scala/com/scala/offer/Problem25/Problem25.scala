package com.scala.offer.Problem25

import com.scala.offer.Node.BTreeNode
import java.{util => ju}
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/6/1.
 * Github: Lemonjing
 * 输入一个二叉树和一个整数，打印出二叉树中节点值的和为输入整数的所有路径
 * 如
 *       10
 *      /  \
 *     5   12
 *    / \
 *   4  7
 * 和为22的路径[[10,5,7],[10,12]]
 */
object Problem25 {

  def findPath(root: BTreeNode, target:Int):List[List[Int]] = {
    if (root == null) return null
    val paths = new ListBuffer[List[Int]]
    val stack = new ju.Stack[Int]
    generate(root, target, stack, paths)
    paths.toList
  }

  private def generate(node: BTreeNode, target:Int, stack:ju.Stack[Int], paths: ListBuffer[List[Int]]):Unit = {
    if (node == null) return
    if (node.leftChild == null && node.rightChild == null) {
      if (node.data == target) {
        val path = ListBuffer(stack.asScala:_*)
        path.append(node.data)
        paths.append(path.toList)
      }
    } else {
      stack.push(node.data)
      generate(node.leftChild, target - node.data, stack, paths)
      generate(node.rightChild, target - node.data, stack, paths)
      stack.pop()
    }
  }
}
