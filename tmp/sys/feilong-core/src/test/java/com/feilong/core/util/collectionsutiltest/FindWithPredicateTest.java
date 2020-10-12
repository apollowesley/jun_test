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
package com.feilong.core.util.collectionsutiltest;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.Predicate;
import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.core.util.predicate.BeanPredicateUtil;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toMap;

/**
 * The Class CollectionsUtilFindWithPredicateTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class FindWithPredicateTest{

    /**
     * Test find2.
     */
    @Test
    public void testFind2(){
        User guanyu30 = new User("关羽", 30);
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25),
                        guanyu30);

        Map<String, ?> map = toMap("name", "关羽", "age", 30);

        Predicate<User> equalPredicate = BeanPredicateUtil.equalPredicate(map);
        assertEquals(guanyu30, CollectionsUtil.find(list, equalPredicate));
    }

    /**
     * Test find null predicate.
     */
    @Test(expected = NullPointerException.class)
    public void testFindNullPredicate(){
        List<User> list = toList(new User("张飞", 23));
        CollectionsUtil.find(list, null);
    }

    /**
     * Test find null predicate 1.
     */
    @Test(expected = NullPointerException.class)
    public void testFindNullPredicate1(){
        CollectionsUtil.find(null, null);
    }

    /**
     * Test find null iterable.
     */
    @Test
    public void testFindNullIterable(){
        assertEquals(null, CollectionsUtil.find(null, BeanPredicateUtil.equalPredicate("name", "关羽")));
    }

    /**
     * Test find not find.
     */
    @Test
    public void testFindNotFind(){
        List<User> list = toList(new User("张飞", 23));
        Predicate<User> equalPredicate = BeanPredicateUtil.equalPredicate("name", "关羽");
        assertEquals(null, CollectionsUtil.find(list, equalPredicate));
    }
}
