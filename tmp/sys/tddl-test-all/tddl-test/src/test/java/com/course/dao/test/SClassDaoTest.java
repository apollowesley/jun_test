package com.course.dao.test;

import com.course.base.BaseJunitCase;
import com.course.dao.SClassDao;
import com.course.model.SClass;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class SClassDaoTest extends BaseJunitCase {


    @Autowired
    private SClassDao SClassDao;
    @Test
    public void insertTest(){
        SClass s = new SClass();
        s.setClassId(3L);
        s.setClassName("三年级一班");
        s.setHeader("刘班");
        SClassDao.insertSClass(s);
    }

    @Test
    public void getTest(){
        SClass s = SClassDao.getSClassBySId(Consts.classId);
        System.out.println(s.getClassName());
        Assert.assertEquals(s.getClassName(), "刘班");
    }

    @Test
    public void updateTest(){
        SClass s = SClassDao.getSClassBySId(Consts.classId);
        s.setHeader("李班");
        SClassDao.updateSClassById(s);
        SClass s2 = SClassDao.getSClassBySId(Consts.classId);
        Assert.assertEquals(s.getHeader(), "李班");

    }

    @Test
    public void deleteTest(){
        Long[] ids = new Long[1];
        ids[0] = Consts.classId;
        SClassDao.deleteSClass(ids);
        SClass s = SClassDao.getSClassBySId(Consts.classId);
        Assert.assertNull(s);
    }

    @Test
    public void unionTest(){
        List<SClass> sList = SClassDao.getUnionResult();
        System.out.println(sList);
    }

}
