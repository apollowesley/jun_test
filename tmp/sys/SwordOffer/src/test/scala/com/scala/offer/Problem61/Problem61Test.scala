package com.scala.offer.Problem61

import com.scala.offer.Node.BTreeNode
import com.scala.offer.Problem62.Problem62
import org.scalatest.FunSuite

/**
 * Created by Lemonjing on 2018/6/17.
 * Github: Lemonjing
 */
class Problem61Test extends FunSuite {

  var serializeStr: String = _
  var root: BTreeNode = _

  test("testSerialize") {
    root = new BTreeNode(5)
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
    serializeStr = Problem62.serialize(root)
    assert(serializeStr === "5,3,2,$,$,4,$,$,7,6,$,$,8,$,$,")
  }

  test("testDeserialize") {
    val rootNode = Problem62.deserialize(serializeStr)
    assert(rootNode.data === root.data)
  }

}
