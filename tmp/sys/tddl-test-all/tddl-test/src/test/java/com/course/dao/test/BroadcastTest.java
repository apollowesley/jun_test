package com.course.dao.test;

import com.course.base.BaseJunitCase;
import com.course.dao.BroadcastDao;
import com.course.dao.StudentDao;
import com.course.model.Broadcast;
import com.course.model.BroadcastJoinResult;
import com.course.model.Course;
import com.course.model.Student;
import com.taobao.tddl.client.sequence.impl.GroupSequence;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Mycat on 2016/1/23.
 */
public class BroadcastTest extends BaseJunitCase {

    @Autowired
    private BroadcastDao broadcastDao;

    @Autowired
    private StudentDao studentDao;


    @Test
    public void initData(){

        Broadcast broadcast = new Broadcast();
        broadcast.setId(1L);
        broadcast.setBname("张三");
        broadcast.setSchool("某学校");
        broadcastDao.insert(broadcast);


        Broadcast broadcast2 = new Broadcast();
        broadcast2.setId(2L);
        broadcast2.setBname("李四");
        broadcast2.setSchool("某二学校");
        broadcastDao.insert(broadcast2);

        //插入到groupKey_0
        Student s = new Student();
        s.setsId(34L);
        s.setsName("张三");
        s.setGender(1);
        s.setClassId(Consts.classId);
        s.setEmail("zhangsan@126.com");
        s.setPhoneNum("18612482592");
        s.setbId(1L);
        studentDao.insertStudent(s);

        //插入到groupKey_1
        Student s2 = new Student();
        s2.setsId(30L);
        s2.setsName("李四");
        s2.setGender(1);
        s2.setClassId(Consts.classId);
        s2.setEmail("lisi@126.com");
        s2.setPhoneNum("18612482548");
        s2.setbId(2L);
        studentDao.insertStudent(s2);
    }

    @Test
    public void joinTest(){
        BroadcastJoinResult result = broadcastDao.broadcastJoin(34L);
        System.out.println(result.getsId()+":"+result.getsName()+":"+result.getSchool());

        BroadcastJoinResult result2 = broadcastDao.broadcastJoin(30L);
        System.out.println(result2.getsId()+":"+result2.getsName()+":"+result2.getSchool());
    }

    @Test
    public void deleteAllTest(){
        broadcastDao.deleteAllBroadcast();
        studentDao.deleteStudent(new Long[]{30L,34L});
    }

}
