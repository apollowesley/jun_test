package com.course.base;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseJunitCase {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @BeforeClass
    public static void before() {
        System.out.println("----操作开始----");
    }

    @AfterClass
    public static void after() {
        System.out.println("----操作结束----");
    }
}
