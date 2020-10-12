package com.scala.offer.Problem23

import com.scala.offer.Node.BTreeNode
import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/31.
 * Github: Lemonjing
 */
class Problem23Test extends FunSuite with BeforeAndAfterEach {

  override def beforeEach() {

  }

  override def afterEach() {

  }

  test("testLevelTraverse") {
    val root1 = new BTreeNode(8)
    val node1 = new BTreeNode(7)
    val node2 = new BTreeNode(2)
    val node3 = new BTreeNode(9)
    val node4 = new BTreeNode(2)
    val node5 = new BTreeNode(4)
    val node6 = new BTreeNode(7)
    root1.leftChild = node1
    root1.rightChild = node2
    node1.leftChild = node3
    node1.rightChild = node4
    node4.leftChild = node5
    node4.rightChild = node6
    assert(Problem23.levelTraverse(root1) === List(root1, node1, node2, node3, node4, node5, node6))
  }

}
