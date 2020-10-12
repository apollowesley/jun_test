/*
 * Copyright (C) 2008 feilong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.core.bean.convertutiltest;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ToStringConfig.DEFAULT_CONFIG;
import static com.feilong.core.bean.ToStringConfig.IGNORE_NULL_OR_EMPTY_CONFIG;
import static java.lang.System.lineSeparator;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.bean.ToStringConfig;
import com.feilong.test.AbstractTwoParamsAndOneResultParameterizedTest;

/**
 * The Class ConvertUtilToStringCollectionToStringConfigParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToStringCollectionToStringConfigParameterizedTest
                extends AbstractTwoParamsAndOneResultParameterizedTest<Collection<?>, ToStringConfig, String>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index} ConvertUtil.toString({0},{1})= {2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = build();
        return toList(objects);
    }

    /**
     * Builds the.
     *
     * @return the object[][]
     * @since 1.10.3
     */
    private static Object[][] build(){
        ToStringConfig toStringConfig = new ToStringConfig(",", false);
        List<String> list = toList("feilong", "", "xinge");

        return new Object[][] {
                                { null, toStringConfig, EMPTY },
                                { toList(), toStringConfig, EMPTY },

                                { list, DEFAULT_CONFIG, "feilong,,xinge" },

                                { toList("feilong", "xinge"), DEFAULT_CONFIG, "feilong,xinge" },
                                { toList("feilong", "", "xinge", null), DEFAULT_CONFIG, "feilong,,xinge," },

                                { toList("feilong", "xinge"), IGNORE_NULL_OR_EMPTY_CONFIG, "feilong,xinge" },
                                { toList("feilong", "", "xinge", null), IGNORE_NULL_OR_EMPTY_CONFIG, "feilong,xinge" },

                                { list, toStringConfig, "feilong,xinge" },
                                { list, new ToStringConfig("@"), "feilong@@xinge" },
                                { toList("feilong", "", "xinge", null), new ToStringConfig("@"), "feilong@@xinge@" },
                                {
                                  toList("飞龙", "小金", "四金", "金金金金"),
                                  new ToStringConfig(lineSeparator()),
                                  "飞龙" + lineSeparator() + "小金" + lineSeparator() + "四金" + lineSeparator() + "金金金金" }, };
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString(){
        assertEquals(expectedValue, ConvertUtil.toString(input1, input2));
    }

}
