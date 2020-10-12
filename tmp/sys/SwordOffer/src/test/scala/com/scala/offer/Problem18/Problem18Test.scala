package com.scala.offer.Problem18

import com.scala.offer.Node.BTreeNode
import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/5/24.
 * Github: Lemonjing
 */
class Problem18Test extends FunSuite with BeforeAndAfterEach {

  var root1:BTreeNode = _
  var root2:BTreeNode = _

  override def beforeEach() {
    root1 = new BTreeNode(8)
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

    root2 = new BTreeNode(7)
    val a = new BTreeNode(9)
    val b = new BTreeNode(2)
    root2.leftChild = a
    root2.rightChild = b
  }

  override def afterEach() {

  }

  test("testHasSubTree") {
    assert(Problem18.hasSubTree(root1, root2) === true)
  }

}
