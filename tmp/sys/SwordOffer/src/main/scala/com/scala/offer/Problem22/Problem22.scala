package com.scala.offer.Problem22

import java.{util => ju}

/**
 * Created by Lemonjing on 2018/5/31.
 * Github: Lemonjing
 * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。
 * 假设压入栈的所有数字均不相等。
 * 例如序列1,2,3,4,5是某栈的压入顺序，序列4,5,3,2,1是该压栈序列对应的一个弹出序列，
 * 但4,3,5,1,2就不可能是该压栈序列的弹出序列。
 */
object Problem22 {

  def isPopOrder(push: Array[Int], pop: Array[Int]): Boolean = {
    if (push == null || pop == null || push.isEmpty || pop.isEmpty || push.length != pop.length) {
      return false
    }
    val stack = new ju.Stack[Int]
    var popIndex = 0
    for (i <- 0 until push.length) {
      stack.push(push(i))
      while (!stack.isEmpty && stack.peek == pop(popIndex)) {
        stack.pop()
        popIndex += 1
      }
    }
    stack.isEmpty
  }
}
