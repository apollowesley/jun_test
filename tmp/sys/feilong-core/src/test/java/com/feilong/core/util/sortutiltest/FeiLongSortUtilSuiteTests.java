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
package com.feilong.core.util.sortutiltest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The Class FeiLongSortUtilSuiteTests.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
@RunWith(Suite.class)
@SuiteClasses({ //

                SortArrayComparatorsTest.class,
                SortArrayTest.class,
                SortListByFixedOrderArrayPropertyValuesTest.class,
                SortListByFixedOrderListPropertyValuesTest.class,
                SortListByPropertyNamesValueTest.class,
                SortListComparatorsTest.class,
                SortListTest.class,

                SortMapByKeyAscTest.class,
                SortMapByKeyDescTest.class,
                SortMapByKeyFixOrderTest.class,
                SortMapByValueAscTest.class,
                SortMapByValueDescTest.class,
                SortMapComparatorTest.class,
        //
})
public class FeiLongSortUtilSuiteTests{

}
