package com.scala.offer.Problem21

import java.{util => ju}

/**
 * Created by Lemonjing on 2018/5/31.
 * Github: Lemonjing
 */
class Problem21 {

  private val dataStack = new ju.Stack[Int]
  private val minStack = new ju.Stack[Int]

  def push(e: Int): Unit = {
    dataStack.push(e)
    if (minStack.isEmpty || e < minStack.peek) {
      minStack.push(e)
    } else {
      minStack.push(minStack.peek)
    }
  }

  def pop(): Int = {
    minStack.pop
    dataStack.pop
  }

  def min(): Int = {
    minStack.peek
  }
}
