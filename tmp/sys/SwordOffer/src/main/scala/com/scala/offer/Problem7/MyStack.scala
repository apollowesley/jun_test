package com.scala.offer.Problem7

import java.util

/**
 * Created by Lemonjing on 2018/5/8.
 * Github: Lemonjing
 *
 * 两个队列实现栈
 */
object MyStack {
  private val queue1 = new util.LinkedList[Integer]()
  private val queue2 = new util.LinkedList[Integer]()

  def push(e: Integer): Unit = {
    if (!queue1.isEmpty) {
      queue1.offer(e)
    } else if (!queue2.isEmpty) {
      queue2.offer(e)
    } else {
      queue1.offer(e)
    }
  }

  def pop(): Integer = {
    if (queue1.isEmpty && queue2.isEmpty) {
      throw new Exception("error, stack is empty.")
    }
    if (!queue1.isEmpty) {
      var n = queue1.size()
      while (n > 1) {
        queue2.offer(queue1.poll())
        n -= 1
      }
      return queue1.poll()
    } else {
      var n = queue2.size()
      while (n > 1) {
        queue1.offer(queue2.poll())
        n -= 1
      }
      return queue2.poll()
    }
  }
}
