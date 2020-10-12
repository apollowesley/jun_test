package com.scala.offer.Problem64

import java.util.Comparator
import java.{util => ju}

/**
 * Created by Lemonjing on 2018/6/17.
 * Github: Lemonjing
 *
 * 无序数组partition法 O(n)
 * 边插边保证有序 O(n)
 * 最大堆最小堆法 O(logn)
 */
object Problem64 {

  val minHeap = new ju.PriorityQueue[Int](15)
  val maxHeap = new ju.PriorityQueue[Int](15, new Comparator[Int] {
    override def compare(o1: Int, o2: Int): Int = o2- o1
  })
  var count = 0

  def insert(num: Int) = {
    if (count % 2 == 0) {
      maxHeap.offer(num)
      val filterNum = maxHeap.poll()
      minHeap.offer(filterNum)
    } else {
      minHeap.offer(num)
      val filterNum = minHeap.poll()
      maxHeap.offer(filterNum)
    }
    count += 1
  }

  // 最大堆最小堆法
  def getMedian(): Double = {
    if (count % 2 == 0) {
      (maxHeap.peek() + minHeap.peek()) / 2.0
    } else {
      minHeap.peek().toDouble
    }
  }

  def main(args: Array[String]): Unit = {
    insert(2)
    insert(3)
    insert(4)
    insert(2)
    insert(6)
    insert(2)
    insert(5)
    insert(1)
    println(getMedian())
  }
}
