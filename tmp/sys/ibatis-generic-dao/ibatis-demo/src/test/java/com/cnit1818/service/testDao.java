package com.cnit1818.service;

import com.cnit1818.demo.entity.UserInfo;
import com.cnit1818.demo.service.UserInfoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * Created by mayong on 2016/4/23.
 */
public class testDao {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        UserInfoService userInfoService = (UserInfoService) context.getBean("userInfoService");
//        ====================   insert   =========================
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("张三");
        userInfo.setPassword("123456");
        userInfo.setOptTime(new Date());
        Long pk = userInfoService.insert(userInfo);
        System.out.println("====== pk : " + pk);
//        ====================  findById    =========================
        UserInfo info = userInfoService.findById(pk);
        System.out.println("====== username :" + info.getUsername());
//        ====================  findByConf    =========================
        UserInfo qry = new UserInfo();
        qry.setUsername("张三");
        List<UserInfo> list = userInfoService.findByConf(qry);
        System.out.println("======= count :" + list.size());
//        ====================  findByConf    =========================
        UserInfo update = new UserInfo();
        userInfo.setId(pk);
        userInfo.setUsername("李四");
        Integer count = userInfoService.updateById(userInfo);
        System.out.println("========== edit count :" + count);


    }
}
