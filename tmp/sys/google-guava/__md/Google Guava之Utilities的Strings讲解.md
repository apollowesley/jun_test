# Google Guava之Utilities的Strings讲解

## 一、简介

**就是对字符串的一些基本操作，不得不吐槽的是功能很少，和Apache Commons Lang的StringUtils没得比。**

**源码及其的简单易懂，其余部分还要靠大家自己摸索。**

## 二、Demo

``` java
package com.chentongwei.guava.utilities;

import com.google.common.base.Strings;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author chentongwei@bshf360.com 2018-04-23 13:09
 */
public class StringsTest {
}

```

### 1、将空的字符串转换成null,若不为空字符串则原样输出

```JAVA
/**
 * 将空的字符串转换成null,若不为空字符串则原样输出
 */
@Test
public void testEmptyToNull() {
    assertThat(Strings.emptyToNull(""), nullValue());
    assertThat(Strings.emptyToNull("hello"), equalTo("hello"));
}
```

### 2、将null转换成空字符串,若不为null，则原样输出

```java
@Test
public void testNullToEmpty() {
    assertThat(Strings.nullToEmpty(null), equalTo(""));
    assertThat(Strings.nullToEmpty("hello"), equalTo("hello"));
}
```

### 3、返回两个单词公共的前缀，若没相同的前缀，则返回空字符串

```java
@Test
public void testCommonPrefix() {
    assertThat(Strings.commonPrefix("Hello", "Hi"), equalTo("H"));
    assertThat(Strings.commonPrefix("Hello", "World"), equalTo(""));
}
```

### 4、返回两个单词公共的后缀，若没相同的后缀，则返回空字符串

```Java
@Test
public void testCommonSuffix() {
    assertThat(Strings.commonSuffix("Hello", "Hi"), equalTo(""));
    assertThat(Strings.commonSuffix("Hello", "Wo"), equalTo("o"));
}	
```

### 5、返回某单词的重复N次后的结果

```java
/**
 * 返回某单词的重复N次后的结果
 */
@Test
public void testRepeat() {
    assertThat(Strings.repeat("abc", 2), equalTo("abcabc"));
}
```

### 6、判断是否为null或者空字符串

``` JAVA
@Test
public void testIsNullOrEmpty() {
    assertThat(Strings.isNullOrEmpty(""), equalTo(true));
    assertThat(Strings.isNullOrEmpty(null), equalTo(true));
}
```

### 7、在首位补充字符。

```java
/**
 * 在首位补充字符。
 * 比如：
 * Hello五个长度，我们指定4，则会判断Hello.length >= 4?，若是true，则原样返回。否则在首位拼接上第三个参数字符。
 */
@Test
public void testPadStart() {
    assertThat(Strings.padStart("Hello", -1, 'p'), equalTo("Hello"));
    assertThat(Strings.padStart("Hello", 4, 'p'), equalTo("Hello"));
    assertThat(Strings.padStart("Hello", 8, 'p'), equalTo("pppHello"));
}
```

### 8、在末尾补充字符

```java
/**
 * 在末尾补充字符。
 * 比如：
 * Hello五个长度，我们指定4，则会判断Hello.length >= 4?，若是true，则原样返回。否则在末尾拼接上第三个参数字符。
 */
@Test
public void testPadEnd() {
    assertThat(Strings.padEnd("Hello", -1, 'p'), equalTo("Hello"));
    assertThat(Strings.padEnd("Hello", 4, 'p'), equalTo("Hello"));
    assertThat(Strings.padEnd("Hello", 8, 'p'), equalTo("Helloppp"));
}
```

## 三、总结

**具体的使用大部分都在这里了，个人觉得使用期间可以学习学习源码，源码真的很易读，可以看看大师是怎么写代码的。**

## 四、广告

- **Demo源码已上传到码云，文章会定期更新。如果觉得对您有帮助，希望给个star。**

  https://gitee.com/geekerdream/google-guava/

- **QQ群【Java初学者学习交流群】：458430385**

- **微信公众号【Java码农社区】**

  ![img](https://upload-images.jianshu.io/upload_images/4582242-ca4a357ae859b1aa.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/258)

- **今日头条号：编程界的小学生**