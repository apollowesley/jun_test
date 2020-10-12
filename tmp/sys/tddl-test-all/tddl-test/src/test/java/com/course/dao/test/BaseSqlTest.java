package com.course.dao.test;

import com.course.dao.CourseDao;
import com.course.dao.SClassDao;
import com.course.dao.StudentCourseDao;
import com.course.dao.StudentDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mycat on 2016/1/24.
 */
public class BaseSqlTest {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private com.course.dao.SClassDao SClassDao;

    @Autowired
    private StudentCourseDao studentCourseDao;

    @Autowired
    private StudentDao studentDao;

    @Test
    public void test(){
        System.out.println("hello");
    }
}
