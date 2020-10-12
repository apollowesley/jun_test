# Google Guava之Utilities的Splitter讲解

## 一、核心源码

```java
public List<String> splitToList(CharSequence sequence) {
	checkNotNull(sequence);

	Iterator<String> iterator = splittingIterator(sequence);
	List<String> result = new ArrayList<String>();

	while (iterator.hasNext()) {
	  result.add(iterator.next());
	}

	return Collections.unmodifiableList(result);
}
```

> PS：简单分析：
>
> 就是先按照字符进行分割成迭代器，然后遍历迭代器add到List里。
>
> 建议好好读读此类的代码，还不到600行，里面有很多设计模式，可以学学设计理念。

**源码及其的简单易懂，其余部分还要靠大家自己摸索。**

## 二、Demo

``` java
package com.chentongwei.guava.utilities;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @author chentongwei@bshf360.com 2018-04-20 14:38
 */
public class SplitterTest {
}

```

### 1、将字符串（没有空值的且分隔符之间无空白）按照自定义分隔符进行拆分成list数组

```JAVA
/**
 * 将字符串（没有空值的）按照自定义分隔符进行拆分成list数组
 * 存在问题就是：hello,world中间逗号前后不能加空格
 */
@Test
public void testSplitOn() {
    List<String> list = Splitter.on(",").splitToList("hello,world");
    assertThat(list, notNullValue());
    assertThat(list.size(), equalTo(2));
    assertThat(list.get(0), equalTo("hello"));
    assertThat(list.get(1), equalTo("world"));
}
```

### 2、将字符串（有空值且分隔符之间无空白）按照自定义分隔符进行拆分成list数组

```java
/**
 * 将字符串（有空值且分隔符之间无空白）按照自定义分隔符进行拆分成list数组
 * 存在问题就是：hello,world中间逗号前后不能加空格
 * 出现异常，因为长度不是2而是5
 */
@Test
public void testSplitOnEmpty() {
    List<String> list = Splitter.on(",").splitToList("hello,world,,'',null");
    assertThat(list, notNullValue());
    assertThat(list.size(), equalTo(2));
}
```

### 3、将字符串（有空值且分隔符之间无空白）按照自定义分隔符进行拆分成list数组（刨除空值）

```java
/**
 * 将字符串（有空值且分隔符之间无空白）按照自定义分隔符进行拆分成list数组（刨除空值）
 * 存在的问题就是：空白还是无法解决，但是能把空值（,,）去掉
 * 所以长度是4，程序正常
 */
@Test
public void testSplitOnOmitEmpty() {
    List<String> list = Splitter.on(",").omitEmptyStrings().splitToList("hello,world,,'',null");
    assertThat(list, notNullValue());
    assertThat(list.size(), equalTo(4));
}
```

### 4、将字符串（有空值且分隔符之间无空白）按照自定义分隔符进行拆分成list数组（刨除空值和空白）

```Java
/**
 * 将字符串（有空值且分隔符之间无空白）按照自定义分隔符进行拆分成list数组（刨除空值和空白）
 */
@Test
public void testSplitOnOmitEmptyAndTrim() {
    List<String> list = Splitter.on(",").trimResults().omitEmptyStrings().splitToList("hello, world, ,'', null");
    assertThat(list, notNullValue());
    assertThat(list.size(), equalTo(4));
    assertThat(list.get(0), equalTo("hello"));
    assertThat(list.get(1), equalTo("world"));
    assertThat(list.get(2), equalTo("''"));
    assertThat(list.get(3), equalTo("null"));
}	
```

### 5、将字符串按照字符长度进行拆分成list数组

```java
/**
 * 将字符串按照字符长度进行拆分成list数组
 *
 * 比如：aaaabbbbccccdddd
 * 结果：
 * aaaa
 * bbbb
 * cccc
 * dddd
 */
@Test
public void testSplitFixLength() {
    List<String> list = Splitter.fixedLength(4).splitToList("aaaabbbbccccdddd");
    assertThat(list, notNullValue());
    assertThat(list.size(), equalTo(4));
    assertThat(list.get(0), equalTo("aaaa"));
    assertThat(list.get(1), equalTo("bbbb"));
    assertThat(list.get(2), equalTo("cccc"));
    assertThat(list.get(3), equalTo("dddd"));
}
```

### 6、将字符串按照自定义字符进行拆分成list数组，并且限制截取list的个数

``` JAVA
/**
 * 将字符串按照自定义字符进行拆分成list数组，并且限制截取list的个数
 *
 * 比如：on("#").limit(3)。按照#分割，分割出三个长度。
 * A#B#C#D#E#F
 * 结果：
 * A
 * B
 * C#D#E#F
 */
@Test
public void testSplitOnSplitLimit() {
    List<String> list = Splitter.on("#").limit(3).splitToList("A#B#C#D#E#F");
    assertThat(list, notNullValue());
    assertThat(list.size(), equalTo(3));
    assertThat(list.get(0), equalTo("A"));
    assertThat(list.get(1), equalTo("B"));
    assertThat(list.get(2), equalTo("C#D#E#F"));
}
```

### 7、按照正则表达式分割

```java
/**
 * 按照正则表达式分割一
 */
@Test
public void testSplitOnPatternString() {
    List<String> list = Splitter.onPattern("\\/").trimResults().omitEmptyStrings().splitToList("A / B / C / / ///");
    assertThat(list, notNullValue());
    assertThat(list.size(), equalTo(3));
    assertThat(list.get(0), equalTo("A"));
    assertThat(list.get(1), equalTo("B"));
    assertThat(list.get(2), equalTo("C"));
}

/**
 * 按照正则表达式分割二
 */
@Test
public void testSplitOnPattern() {
    List<String> list = Splitter.on(Pattern.compile("\\/")).trimResults().omitEmptyStrings().splitToList("A / B / C / / ///");
    assertThat(list, notNullValue());
    assertThat(list.size(), equalTo(3));
    assertThat(list.get(0), equalTo("A"));
    assertThat(list.get(1), equalTo("B"));
    assertThat(list.get(2), equalTo("C"));
}
```

### 8、按照某字符进行左右切割成map

```java
/**
 * 按照某字符进行左右切割成map
 */
@Test
public void testSplitOnSplitToMap() {
    Map<String, String> map = Splitter.on(Pattern.compile("\\/")).trimResults().omitEmptyStrings()
            .withKeyValueSeparator("=").split("key=value / key1=value1 / key2=valu2 / / ///");
    assertThat(map, notNullValue());
    assertThat(map.size(), equalTo(3));
    assertThat(map.get("key"), equalTo("value"));
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