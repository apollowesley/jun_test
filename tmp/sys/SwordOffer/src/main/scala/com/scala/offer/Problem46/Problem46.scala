package com.scala.offer.Problem46

/**
 * Created by Lemonjing on 2018/6/4.
 * Github: Lemonjing
 * 求1+2+3+...+n，不可使用算术运算
 */
class Problem46 {

  private def terminal(n: Int):Int = 0

  def sum(n: Int):Int = {
    var _n = n
    val list = List(false, true)
    // getDeclaredMethods能拿到所有方法（不包括继承），getMethods只能拿到public方法（包括继承的类或接口的方法）
    val methods = this.getClass.getDeclaredMethods
    val index = list.indexOf(_n == 0)
    val curN = _n
    _n -= 1
    curN + methods(index).invoke(this, new Integer(_n)).asInstanceOf[Integer]
  }
}