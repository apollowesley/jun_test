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
package com.feilong.core.date.dateextensionutiltest;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.TimeInterval.SECONDS_PER_DAY;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.date.DateExtensionUtil.getIntervalMinute;
import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.test.AbstractThreeParamsAndOneResultParameterizedTest;

/**
 * The Class DateExtensionUtilGetIntervalMinuteParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetIntervalMinuteParameterizedTest extends AbstractThreeParamsAndOneResultParameterizedTest<String, String, String, Integer>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:DateExtensionUtil.getIntervalMinute(toDate(\"{0}\",\"{2}\"), toDate(\"{1}\",\"{2}\"))={3}")
    public static Iterable<Object[]> data(){
        return toList(//
                        ConvertUtil.<Object> toArray("2008-08-24 00:00:00", "2008-08-24 01:00:00", COMMON_DATE_AND_TIME, 1 * 60),

                        toArray("2008-08-24 00:00:00", "2008-08-24 00:00:00", COMMON_DATE_AND_TIME, 0),
                        toArray("2008-08-24 00:00:00", "2008-08-24 00:00:50", COMMON_DATE_AND_TIME, 0),

                        toArray("2008-08-24 00:00:00", "2008-08-23 00:00:00", COMMON_DATE_AND_TIME, SECONDS_PER_DAY / 60)
        //  
        );
    }

    /**
     * Test get interval minute.
     */
    @Test
    public void testGetIntervalMinute(){
        assertEquals(expectedValue, (Integer) getIntervalMinute(toDate(input1, input3), toDate(input2, input3)));
    }

}