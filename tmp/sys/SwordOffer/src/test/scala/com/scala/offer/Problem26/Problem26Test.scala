package com.scala.offer.Problem26

import com.scala.offer.Node.ComplexNode
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import scala.collection.mutable.ListBuffer

/**
 * Created by Lemonjing on 2018/6/1.
 * Github: Lemonjing
 */
class Problem26Test extends FunSuite with BeforeAndAfterEach {

  var A: ComplexNode = _

  override def beforeEach() {
    A = new ComplexNode(0)
    val B = new ComplexNode(1)
    val C = new ComplexNode(2)
    val D = new ComplexNode(3)
    val E = new ComplexNode(4)

    A.next = B
    B.next = C
    C.next = D
    D.next = E

    A.sibling = C // 2
    B.sibling = E // 4
    D.sibling = B // 1
  }

  override def afterEach() {
    A = null
  }

  test("testCopyList") {
    var newHead = Problem26.copyList(A)
    val datas = new ListBuffer[Int]
    val siblings = new ListBuffer[Int]
    while (newHead != null) {
      datas.append(newHead.data)
      if (newHead.sibling != null) {
        siblings.append(newHead.sibling.data)
      } else {
        siblings.append(-1)
      }
      newHead = newHead.next
    }
    assert(datas.toList === List(0, 1, 2, 3, 4))
    assert(siblings.toList === List(2, 4, -1, 1, -1))
  }

}
