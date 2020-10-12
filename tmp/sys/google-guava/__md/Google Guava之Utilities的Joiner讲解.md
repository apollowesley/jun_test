# Google Guava之Utilities的Joiner讲解

## 一、核心源码

```java
public <A extends Appendable> A appendTo(A appendable, Iterator<?> parts) throws IOException {
    Preconditions.checkNotNull(appendable);
    if(parts.hasNext()) {
        appendable.append(this.toString(parts.next()));

        while(parts.hasNext()) {
            appendable.append(this.separator);
            appendable.append(this.toString(parts.next()));
        }
    }
    return appendable;
}
```

> PS：简单分析：
>
> 1、泛型A决定了，传过来什么类型的参数就返回去什么类型。
>
> 2、大概意思就是先判断null，然后遍历第二个参数（是个迭代器），并且将遍历的每一个结果都append到泛型里。append方法哪里来？可以发现返回值A继承了Appendable。每追加一个就追加一个分隔符进去，最后return。

**源码及其的简单易懂，其余部分还要靠大家自己摸索。**

## 二、Demo

``` java
package com.chentongwei.guava.utilities;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.google.common.collect.ImmutableMap.of;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author chentongwei@bshf360.com 2018-04-20 11:20
 */
public final class JoinerTest {

    private Logger logger = Logger.getLogger(getClass().getName());

    private final List<String> lists = Arrays.asList("A", "B", "C", "D");

    private final List<String> listsWithNull = Arrays.asList("A", "B", "C", null);

    private final String targetFileName = "D:\\BaiduNetdiskDownload\\guava\\guava.txt";

    private final Map<String, String> stringMap = of("key1", "value1", "key2", "value2", "key3", "value3");
}

```

### 1、将List中的元素按照自定义的分隔符分割成字符串

```java 
/**
 * 将List中的元素按照自定义的分隔符分割成字符串
 * A,B,C,D
 */
@Test
public void testJoinOn() {
    String result = Joiner.on(",").join(lists);
    logger.info(result);
    assertThat(result, equalTo("A,B,C,D"));
}
```

> PS：join底层实现自己去看，很简单，调用的就是appendTo

### 2、将List中的带有null值的元素按照自定义的分隔符分割成字符串

```java
/**
 * 将List中的带有null值的元素按照自定义的分隔符分割成字符串
 * 会出现空指针
 */
@Test(expected = NullPointerException.class)
public void testJoinOnWithNullValue() {
    String result = Joiner.on(",").join(listsWithNull);
    logger.info(result);
    assertThat(result, equalTo("A,B,C"));
}
```

> PS：为什么出现空指针，自己读源码。

### 3、将List中的带有null值的元素按照自定义的分隔符分割成字符串，但是排除null值

```java
/**
 * 将List中的带有null值的元素按照自定义的分隔符分割成字符串，但是排除null值
 * A,B,C
 */
@Test
public void testJoinOnWithNullValueButSkip() {
    String result = Joiner.on(",").skipNulls().join(listsWithNull);
    logger.info(result);
    assertThat(result, equalTo("A,B,C"));
}
```

### 4、将List中的带有null值的元素按照自定义的分隔符分割成字符串，但是将null值换成自定义的值

```java
/**
 * 将List中的带有null值的元素按照自定义的分隔符分割成字符串，但是将null值换成自定义的值
 * A,B,C,JAVA
 */
@Test
public void testJoinOnWithNullValueButUseDefaultValue() {
    String result = Joiner.on(",").useForNull("JAVA").join(listsWithNull);
    logger.info(result);
    assertThat(result, equalTo("A,B,C,JAVA"));
}
```

### 5、将List中的带有null值的元素按照自定义的分隔符分割成字符串，但是将null值换成自定义的值，并append到StringBuilder里

``` java
/**
 * 将List中的带有null值的元素按照自定义的分隔符分割成字符串，但是将null值换成自定义的值，并append到StringBuilder里
 * A,B,C,JAVA
 */
@Test
public void testJoinOnAppendToStringBuilder() {
    StringBuilder result = Joiner.on(",").useForNull("JAVA").appendTo(new StringBuilder(), listsWithNull);
    logger.info(result.toString());
    assertThat(result.toString(), equalTo("A,B,C,JAVA"));
}
```

### 6、将List中的带有null值的元素按照自定义的分隔符分割成字符串，但是将null值换成自定义的值，并输出到文件中

```java
/**
 * 将List中的带有null值的元素按照自定义的分隔符分割成字符串，但是将null值换成自定义的值，并输出到文件中
 */
@Test
public void testJoinOnAppendToWriter() {
    try(FileWriter writer = new FileWriter(targetFileName)) {
        Joiner.on(",").useForNull("JAVA").appendTo(writer, listsWithNull);
        assertThat(com.google.common.io.Files.isFile().test(new File(targetFileName)), equalTo(true));
    } catch (IOException e) {
        fail("error append to writer");
    }
}
```

### 7、将Map中的key-value按照不同的分隔符分割成字符串

```java
/**
 * 将Map中的key-value按照不同的分隔符分割成字符串
 * key1=value1#key2=value2#key3=value3
 */
@Test
public void testJoinOnWithMap() {
    String result = Joiner.on("#").withKeyValueSeparator("=").join(stringMap);
    logger.info(result);
    assertThat(result, equalTo("key1=value1#key2=value2#key3=value3"));
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