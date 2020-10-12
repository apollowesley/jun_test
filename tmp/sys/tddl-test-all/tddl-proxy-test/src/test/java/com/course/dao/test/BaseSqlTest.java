package com.course.dao.test;

import com.course.base.BaseJunitCase;
import com.course.dao.CourseDao;
import com.course.dao.SClassDao;
import com.course.dao.StudentCourseDao;
import com.course.dao.StudentDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mycat on 2016/1/24.
 */
public class BaseSqlTest extends BaseJunitCase {

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

    @Test
    public void deleteAllTest(){
        courseDao.deleteAll();
        SClassDao.deleteAll();
        studentCourseDao.deleteAll();
        studentDao.deleteAll();
    }
}
