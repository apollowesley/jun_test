package com.scala.offer.Problem50

import com.scala.offer.Node.BTreeNode
import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/10.
 * Github: Lemonjing
 *
 * *           16
 * *         /   \
 * *        10   20
 * *       /   \
 * *      6    14
 * *     / \  / \
 * *    4  8 12 16
 *      4,12 => 10
 */
class Problem50Test extends FunSuite{

  test("testGet1") {
    val root = new BTreeNode(16)
    val node1 = new BTreeNode(10)
    val node2 = new BTreeNode(20)
    val node3 = new BTreeNode(6)
    val node4 = new BTreeNode(14)
    val node5 = new BTreeNode(4)
    val node6 = new BTreeNode(8)
    val node7 = new BTreeNode(12)
    val node8 = new BTreeNode(16)
    root.leftChild = node1
    root.rightChild = node2
    node1.leftChild = node3
    node1.rightChild = node4
    node3.leftChild = node5
    node3.rightChild = node6
    node4.leftChild = node7
    node4.rightChild = node8
    assert(Problem50.get1(root, node5, node7) === node1)
  }
}
