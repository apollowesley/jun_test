package com.scala.offer.Problem59

import com.scala.offer.Node.BTreeNode
import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/17.
 * Github: Lemonjing
 */
class Problem59Test extends FunSuite {

  test("testIsSymmetric") {
    val root1 = new BTreeNode(5)
    val node1 = new BTreeNode(3)
    val node2 = new BTreeNode(7)
    val node3 = new BTreeNode(2)
    val node4 = new BTreeNode(4)
    val node5 = new BTreeNode(6)
    val node6 = new BTreeNode(8)
    root1.leftChild = node1
    root1.rightChild = node2
    node1.leftChild = node3
    node1.rightChild = node4
    node2.leftChild = node5
    node2.rightChild = node6
    assert(Problem59.isSymmetric(root1))
  }

  test("testIsSymmetric2") {
    val root2 = new BTreeNode(8)
    val node1 = new BTreeNode(6)
    val node2 = new BTreeNode(6)
    val node3 = new BTreeNode(5)
    val node4 = new BTreeNode(7)
    val node5 = new BTreeNode(7)
    val node6 = new BTreeNode(5)
    root2.leftChild = node1
    root2.rightChild = node2
    node1.leftChild = node3
    node1.rightChild = node4
    node2.leftChild = node5
    node2.rightChild = node6
    assert(Problem59.isSymmetric(root2))
  }

}
