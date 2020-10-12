package com.scala.offer.Problem7

import java.util

/**
 * Problem7
 * 两个栈实现队列
 *
 * @author lemonjing
 */

object MyQueue {

  private val stack1 = new util.Stack[Integer]()
  private val stack2 = new util.Stack[Integer]()

  def push(e: Integer): Unit = {
    stack1.push(e)
  }

  def pop(): Integer = {
    if (stack2.isEmpty) {
      while (!stack1.isEmpty) {
        stack2.push(stack1.pop())
      }
    }
    if (stack2.isEmpty) {
      throw new Exception("delete error, queue is empty.")
    }
    stack2.pop()
  }
}
