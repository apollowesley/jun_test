package com.liuburu.demo.service;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.support.json.JSONUtils;
import com.liuburu.demo.domain.Employ;
import com.liuburu.demo.mapper.EmployMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * ${DESCRIPTION}
 *
 * @author liuburu
 * @create 2017/09/27
 **/
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring-mybatis.xml",
        "classpath:spring-logical.xml"
})*/
public class EmployServiceTest {

    @Autowired
    private EmployMapper employMapper;

    @Test
    public void testPwd() throws Exception {
        for (int i=0;i<5;i++){
            System.out.println("test testPwd method...................................................");
        }
//        System.out.println(ConfigTools.encrypt("123456"));
//        System.out.println(ConfigTools.decrypt(ConfigTools.encrypt("123456")));
    }


    @Test
    public void queryAllEmp() throws Exception {
        for (int i=0;i<5;i++){
            System.out.println("test queryAllEmp method...................................................");
        }
//        List<Employ> employs = employMapper.selectAll();
//        for (Employ emp :
//                employs) {
//            System.out.println(JSONUtils.toJSONString(emp));
//        }
    }

}