package com.scala.offer.Problem19

import com.scala.offer.Node.BTreeNode
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import java.{util => ju}

import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/5/29.
 * Github: Lemonjing
 */
class Problem19Test extends FunSuite with BeforeAndAfterEach {

  var root1: BTreeNode = _

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
  }

  override def afterEach() {
    root1 = null
  }

  test("testMirror") {
    val head1 = Problem19.mirror(root1)
    val datas = Problem19Test.levelTraverse(head1)
    assert(datas === List(8, 2, 7, 2, 9, 7, 4))

    val head2 = Problem19.mirror(null)
    assert(head2 === null)
  }

  test("testMirrorRecursive") {
    val head1 = Problem19.mirrorRecursive(root1)
    val datas = Problem19Test.levelTraverse(head1)
    assert(datas === List(8, 2, 7, 2, 9, 7, 4))

    val head2 = Problem19.mirrorRecursive(null)
    assert(head2 === null)
  }
}

object Problem19Test {

  private def levelTraverse(root: BTreeNode): List[Int] = {
    if (root == null) return null
    val datas = new ListBuffer[Int]
    val queue = new ju.LinkedList[BTreeNode]

    queue.offer(root)
    while (!queue.isEmpty) {
      val node = queue.poll()
      datas.append(node.data)
      if (node.leftChild != null) {
        queue.offer(node.leftChild)
      }
      if (node.rightChild != null) {
        queue.offer(node.rightChild)
      }
    }
    datas.toList
  }
}
