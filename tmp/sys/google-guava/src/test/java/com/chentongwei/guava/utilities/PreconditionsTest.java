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
