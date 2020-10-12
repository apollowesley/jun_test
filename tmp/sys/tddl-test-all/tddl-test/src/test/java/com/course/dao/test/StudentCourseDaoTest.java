package com.course.dao.test;

import com.course.base.BaseJunitCase;
import com.course.dao.StudentCourseDao;
import com.course.model.JoinResult;
import com.course.model.StudentCourse;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class StudentCourseDaoTest extends BaseJunitCase {

    private Long id = 1L;

    @Autowired
    private StudentCourseDao studentCourseDao;

    @Test
    public void insertTest(){
        StudentCourse sc = new StudentCourse();
        sc.setScId(Consts.scId);
        sc.setcId(Consts.cId);
        sc.setsId(Consts.sId);
        sc.setScore(56.85);
        sc.setExamDate(new Date());
        studentCourseDao.insertStudentCourse(sc);
    }

    @Test
    public void getTest(){
        StudentCourse sc = studentCourseDao.getStudentCourseBySId(id);
        System.out.println(sc.getExamDate());
        Assert.assertEquals(sc.getScore(), 56.85);
    }
    @Test
    public void updateTest(){
        StudentCourse sc = studentCourseDao.getStudentCourseBySId(id);
        sc.setScore(85.85);
        studentCourseDao.updateStudentCourseById(sc);
        StudentCourse sc2 = studentCourseDao.getStudentCourseBySId(id);
        Assert.assertEquals(sc2.getScore(),85.85);
    }

    @Test
    public void deleteTest(){
        Long[] ids = new Long[1];
        ids[0] = Consts.scId;
        studentCourseDao.deleteStudentCourse(ids);
        StudentCourse sc = studentCourseDao.getStudentCourseBySId(Consts.scId);
        Assert.assertNull(sc);
    }

    @Test
    public void joinTest(){
        List<JoinResult> list = studentCourseDao.getStudentAndStudentCourseInfo(Consts.sId);
        System.out.println(list.get(0));
    }

    @Test
    public void joinTes2t(){
        List<JoinResult> list = studentCourseDao.joinTest2(Consts.sId);
        System.out.println(list.get(0));
    }

}
