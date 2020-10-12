package com.course.sequenceTest;


import com.course.base.BaseJunitCase;
import com.taobao.tddl.client.sequence.*;
import com.taobao.tddl.client.sequence.impl.*;
import com.taobao.tddl.client.sequence.exception.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.course.base.BaseJunitCase;

public class SequenceTest extends BaseJunitCase {

    private DefaultSequence sequence;

    private SequenceDao sequenceDao;

    private GroupSequence groupSequence;



    @Before
    public void setUp() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/*.xml" });
        sequence = (DefaultSequence) context.getBean("sequence");
        sequenceDao = (SequenceDao) context.getBean("sequenceDao");
        groupSequence = (GroupSequence) context.getBean("groupSequence");
    }

    @Test
    public void sequenceTest()  throws SequenceException {
        for(int i=0; i<10;i++){
            long v = sequence.nextValue();
            System.out.println(v);
        }

        for(int i=0; i<10;i++){

            long v = sequence.nextValue();
            System.out.println(v);
        }

    }

    @Test
    public void sequenceDaoTest()  throws SequenceException {
        SequenceRange sequenceRange = sequenceDao.nextRange("studentId");
        System.out.println(sequenceRange);
    }

    @Test
    public void groupSequenceTest() throws SequenceException{
        for(int i=0; i<10;i++){
            long v = groupSequence.nextValue();
            System.out.println(v);
        }
    }

}
