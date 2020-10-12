package com.scala.offer.Problem65

import scala.collection.mutable.ListBuffer
import java.{util => ju}

/**
 * Created by Lemonjing on 2018/6/17.
 * Github: Lemonjing
 *
 * 滑动窗口的最大值
 *
 * 如{2,3,4,2,6,2,5,1}和3 => {4,4,6,6,6,5}
 *
 */
object Problem65 {

  def maxInWindows(nums: Array[Int], size: Int): List[Int] = {
    if (nums == null || nums.length <= 0 || size <= 0) return null

    val list = new ListBuffer[Int]
    // deque中保存索引 以支持滑动
    val deque = new ju.LinkedList[Int]()
    // 第一个窗口前
    for (i <- 0 until size - 1) {
      while (!deque.isEmpty && nums(i) > nums(deque.getLast)) {
        deque.removeLast()
      }
      deque.addLast(i)
    }
    // 窗口计算和滑动
    for (i <- size - 1 until nums.length) {
      while (!deque.isEmpty && nums(i) > nums(deque.getLast)) {
        deque.removeLast()
      }
      deque.addLast(i)
      // 滑动
      if (i - deque.getFirst + 1 > size) {
        deque.removeFirst()
      }
      list.append(nums(deque.getFirst))
    }

    list.toList
  }
}
