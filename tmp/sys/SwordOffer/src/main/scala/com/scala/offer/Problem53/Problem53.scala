package com.scala.offer.Problem53

/**
 * Created by Lemonjing on 2018/6/11.
 * Github: Lemonjing
 *
 * 正则表达式匹配
 * 模式ab*a*a => aaa
 */
object Problem53 {

  def regexMatch(input: String, pattern: String): Boolean = {
    if (input == null || pattern == null) return false
    matchCore(input, 0, pattern, 0)
  }

  private def matchCore(input: String, i: Int, pattern: String, p: Int): Boolean = {
    if (i == input.length && p == pattern.length) return true

    if (i != input.length && p == pattern.length) return false

    if (i == input.length && p != pattern.length) return false

    //pattern第二个字符为*
    if (p + 1 < pattern.length && pattern.charAt(p + 1) == '*') {
      // 首字母相匹配
      if (input.charAt(i) == pattern.charAt(p) || pattern.charAt(p) == '.') {
        return matchCore(input, i + 1, pattern, p + 2) || // *出现1次
          matchCore(input, i + 1, pattern, p) || // *出现多次
          matchCore(input, i, pattern, p + 2) // *出现0次
      } else {
        //首字母不匹配
        return matchCore(input, i, pattern, p + 2)
      }
    }

    if (input.charAt(i) == pattern.charAt(p) || pattern.charAt(p) == '.') {
      //pattern第二个字母不是*，且首字母匹配
      return matchCore(input, i + 1, pattern, p + 1)
    }

    //其余情况全部不匹配
    false
  }

  def main(args: Array[String]): Unit = {
    println(regexMatch("aaa", "ab*a*a"))
  }
}
