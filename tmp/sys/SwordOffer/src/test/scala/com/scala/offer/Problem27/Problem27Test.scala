package com.scala.offer.Problem27

import com.scala.offer.Node.BTreeNode
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/6/2.
 * Github: Lemonjing
 */
class Problem27Test extends FunSuite with BeforeAndAfterEach {

  var root: BTreeNode = _

  override def beforeEach() {
    root = new BTreeNode(10)
    val node1 = new BTreeNode(6)
    val node2 = new BTreeNode(14)
    val node3 = new BTreeNode(4)
    val node4 = new BTreeNode(8)
    val node5 = new BTreeNode(12)
    val node6 = new BTreeNode(16)
    root.leftChild = node1
    root.rightChild = node2
    node1.leftChild = node3
    node1.rightChild = node4
    node2.leftChild = node5
    node2.rightChild = node6
  }

  override def afterEach() {
    root = null
  }

  test("testConvert") {
    var head = Problem27.convert(root)
    val datas = new ListBuffer[Int]
    while (head != null) {
      datas.append(head.data)
      head = head.rightChild
    }
    assert(datas.toList === List(4, 6, 8, 10, 12, 14, 16))
  }

}
