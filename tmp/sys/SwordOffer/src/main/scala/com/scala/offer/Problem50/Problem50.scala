package com.scala.offer.Problem50

import com.scala.offer.Node.BTreeNode

import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/6/10.
 * Github: Lemonjing
 * 二叉树中两个结点的最近公共祖先
 * 1. 二叉搜索树
 * 2. 二叉树带指向父结点的指针（转为链表的公共结点求解）
 * 3. 普通二叉树
 *        16
 *      /    \
 *     10    20
 *    /       \
 *   6        14
 *  / \       / \
 * 4  8     12 16
 *
 */
object Problem50 {

  //二叉搜索树
  def get1(root: BTreeNode, node1: BTreeNode, node2: BTreeNode): BTreeNode = {
    if (root == null || node1 == null || node2 == null) return null

    if (root.data > node1.data && root.data > node2.data) {
      get1(root.leftChild, node1, node2)
    } else if (root.data < node1.data && root.data < node2.data) {
      get1(root.rightChild, node1, node2)
    } else {
      return root
    }
  }

  //二叉树带指向父结点的指针
  def get2(root: BTreeNode, node1: BTreeNode, node2: BTreeNode): BTreeNode = {
    // 见两个链表的公共结点
    null
  }

  //普通二叉树
  def get3(root: BTreeNode, node1: BTreeNode, node2: BTreeNode): BTreeNode = {
    if (root == null || node1 == null || node2 == null) return null
    val path1 = new ListBuffer[BTreeNode]
    val path2 = new ListBuffer[BTreeNode]
    getNodePath(root, node1, path1)
    getNodePath(root, node2, path2)
    val len = if (path1.size > path2.size) path2.size else path1.size
    var pre = root
    for (i <- 0 until len) {
      if (path1(i) == path2(i)) {
        pre = path1(i)
      } else {
        return pre
      }
    }
    null
  }

  private def getNodePath(root: BTreeNode, target: BTreeNode, path: ListBuffer[BTreeNode]): Int = {
    if (root == null) return -1
    path.append(root)

    if (root.leftChild == target) {
      path.append(root.leftChild)
      return 1
    } else if (root.rightChild == target) {
      path.append(root.rightChild)
      return 1
    } else {
      // 剪枝，找到路径后直接返回
      if (getNodePath(root.leftChild, target, path) == 1) return 1
      else if (getNodePath(root.rightChild, target, path) == 1) return 1
    }
    path.remove(path.size - 1)
    -1
  }
}
