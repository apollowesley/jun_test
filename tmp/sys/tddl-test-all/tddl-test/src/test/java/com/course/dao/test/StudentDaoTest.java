package com.course.dao.test;

import com.course.base.BaseJunitCase;
import com.course.dao.StudentDao;
import com.course.model.JoinResult;
import com.course.model.Student;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class StudentDaoTest extends BaseJunitCase {



    @Autowired
    private StudentDao studentDao;
    @Test
    public void insertTest(){
        Student s = new Student();
        s.setsId(Consts.sId);
        s.setsName("张三");
        s.setGender(1);
        s.setClassId(Consts.classId);
        s.setEmail("zhangsan@126.com");
        s.setPhoneNum("18612482592");
        studentDao.insertStudent(s);
    }

    @Test
    public void insertTest2(){
        Student s = new Student();
        s.setsId(7L);
        s.setsName("张三3");
        s.setGender(4);
        s.setClassId(Consts.classId);
        s.setEmail("zhangsan@126.com");
        s.setPhoneNum("18612482592");
        studentDao.insertStudent(s);
    }

    @Test
    public void getTest(){
        Student s = studentDao.getStudentBySId(Consts.sId);
        System.out.println(s.getsName());
        Assert.assertEquals(s.getsName(), "张三");
    }

    @Test
    public void updateTest(){
        Student s = studentDao.getStudentBySId(Consts.sId);
        s.setEmail("zhangsan@qq.com");
        studentDao.updateStudentById(s);
        Student s2 = studentDao.getStudentBySId(Consts.sId);
        Assert.assertEquals(s.getEmail(), "zhangsan@qq.com");

    }

    @Test
    public void deleteTest(){
        Long[] ids = new Long[1];
        ids[0] = Consts.sId;
        studentDao.deleteStudent(ids);
        Student s = studentDao.getStudentBySId(Consts.sId);
        Assert.assertNull(s);
    }


    @Test
    public void orderTest(){
        List<Student> list = studentDao.orderTest();
        System.out.println(list.get(0));
    }

    @Test
    public void createTest(){
        studentDao.createTest();
    }

}
