package com.scala.offer.Problem25

import com.scala.offer.Node.BTreeNode
import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
 * Created by Lemonjing on 2018/6/1.
 * Github: Lemonjing
 */
class Problem25Test extends FunSuite with BeforeAndAfterEach {

  var root:BTreeNode = _

  override def beforeEach() {
    root = new BTreeNode(10)
    val node1 = new BTreeNode(5)
    val node2 = new BTreeNode(12)
    val node3 = new BTreeNode(4)
    val node4 = new BTreeNode(7)
    root.leftChild = node1
    root.rightChild = node2
    node1.leftChild = node3
    node1.rightChild = node4
  }

  override def afterEach() {
    root = null
  }

  test("testFindPath") {
    assert(Problem25.findPath(root, 22) === List(List(10,5,7), List(10,12)))
  }

}
