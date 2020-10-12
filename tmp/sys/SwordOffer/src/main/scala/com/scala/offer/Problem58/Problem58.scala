package com.scala.offer.Problem58

import com.scala.offer.Node.BTreePNode

/**
 * Created by Lemonjing on 2018/6/17.
 * Github: Lemonjing
 *
 * 二叉树中序遍历的下一个结点
 *
 * 三种情况：1.
 *        a
 *      /   \
 *     b     c
 *    / \   / \
 *   d  e  f  g
 *     / \
 *    h  i
 */
object Problem58 {

  def getNext(pNode: BTreePNode): BTreePNode = {
    if (pNode == null) return null

    var pNext:BTreePNode = null
    // 情形1：有右子树时下一个结点是右子树的最左结点
    if (pNode.rightChild != null) {
      var pRight = pNode.rightChild
      while (pRight.leftChild != null) {
        pRight = pRight.leftChild
      }
      pNext = pRight
    } else if (pNode.parent != null) {
      // 情形3.没有右子树且是父结点的右结点
      // 沿父结点向上找到一个是父结点的左结点的结点，若存在则该结点的父结点是next
      var pCur = pNode
      var pParent = pNode.parent
      while (pParent != null && pCur == pParent.rightChild) {
        pCur = pParent
        pParent = pParent.parent
      }
      // 情形2.没有右子树但是父结点的左结点
      pNext = pParent
    }
    pNext
  }
}
