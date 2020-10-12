# Google Guava之Utilities的Preconditions讲解

## 一、核心源码

```java
public static void checkArgument(boolean expression) {
    if(!expression) {
        throw new IllegalArgumentException();
    }
}
public static void checkState(boolean expression) {
    if(!expression) {
        throw new IllegalStateException();
    }
}
@CanIgnoreReturnValue
public static <T> T checkNotNull(T reference) {
    if(reference == null) {
        throw new NullPointerException();
    } else {
        return reference;
    }
}
```

> PS：简单分析：
>
> 真心很简单，没什么可说的。唯一值得注意的就是checkNotNull方法若检查通过后会将原值原路返回。
>

## 二、Demo

``` java
package com.chentongwei.guava.utilities;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author TongWei.Chen 2018-04-22 18:38:00
 */
public class PreconditionsTest {

    /**
     * 检查是否会抛出空指针异常。
     */
    @Test(expected = NullPointerException.class)
    public void testCheckNotNull() {
        Preconditions.checkNotNull(null);
    }

    /**
     * 检查是否会抛出空指针异常。若会，则自定义message
     */
    @Test
    public void testCheckNotNullWithMessage() {
        try {
            Preconditions.checkNotNull(null, "空指针了");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("空指针了"));
        }
    }

    /**
     * 检查是否会抛出空指针异常。若会，则自定义可格式化的message（String.format()）
     */
    @Test
    public void testCheckNotNullWithFormatMessage() {
        try {
            Preconditions.checkNotNull(null, "test");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("null test."));
        }
    }

    /**
     * 检查是否会出现参数异常
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckArgument() {
        Preconditions.checkArgument("A".equals("B"));
    }

    /**
     * ************************************************************
     * checkArgument带参数的，和带格式化参数的省略不写.
     * ************************************************************
     */

    /**
     * 检查是否会出现状态异常
     */
    @Test(expected = IllegalStateException.class)
    public void testCheckState() {
        Preconditions.checkState("a".equals("b"));
    }
    /**
     * ************************************************************
     * checkState带参数的，和带格式化参数的省略不写.
     * ************************************************************
     */

    /**
     * 检查是否会出现数组下标越界
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testCheckElementIndex() {
        List<String> lists = ImmutableList.of();
        Preconditions.checkElementIndex(1, lists.size());
    }
}
```

## 三、总结

**真心没什么可讲的，就是前置条件的判断，另外Java8出的util包下的Objects的工具类也有这些功能。assert断言依然有。因为真心简单，自己写一个也是分分钟。**

## 四、广告

- **Demo源码已上传到码云，文章会定期更新。如果觉得对您有帮助，希望给个star。**

  https://gitee.com/geekerdream/google-guava/

- **QQ群【Java初学者学习交流群】：458430385**

- **微信公众号【Java码农社区】**

  ![img](https://upload-images.jianshu.io/upload_images/4582242-ca4a357ae859b1aa.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/258)

- **今日头条号：编程界的小学生**