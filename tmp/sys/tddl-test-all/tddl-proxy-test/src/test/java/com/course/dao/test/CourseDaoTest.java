package com.course.dao.test;

import com.course.base.BaseJunitCase;
import com.course.dao.CourseDao;
import com.course.dao.SClassDao;
import com.course.dao.StudentCourseDao;
import com.course.dao.StudentDao;
import com.course.model.Course;
import com.course.model.Student;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class CourseDaoTest extends BaseJunitCase {


    @Autowired
    private CourseDao courseDao;

    @Autowired
    private com.course.dao.SClassDao SClassDao;

    @Autowired
    private StudentCourseDao studentCourseDao;

    @Autowired
    private StudentDao studentDao;



    @Test
    public void insertTest(){
        Course c = new Course();
        c.setcId(Consts.cId);
        c.setcName("语文");
        c.setTeacher("张老师");
        courseDao.insertCourse(c);
    }

    @Test
    public void getTest(){
        Course c = courseDao.getCourseBySId(Consts.cId);
        System.out.println(c.getcName());
        System.out.println(c.getTeacher());
        Assert.assertEquals(c.getcName(), "语文");
    }
    @Test
    public void updateTest(){
        Course c = courseDao.getCourseBySId(Consts.cId);
        c.setTeacher("李老师");
        courseDao.updateCourseById(c);
        Course c2 = courseDao.getCourseBySId(Consts.cId);
        Assert.assertEquals(c.getTeacher(),"李老师");
    }

    @Test
    public void deleteTest(){
        Long[] ids = new Long[1];
        ids[0] = Consts.cId;
        courseDao.deleteCourse(ids);
        Course c = courseDao.getCourseBySId(50L);
        Assert.assertNull(c);
    }

}
