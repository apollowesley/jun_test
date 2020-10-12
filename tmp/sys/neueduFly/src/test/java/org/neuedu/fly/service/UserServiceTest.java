package org.neuedu.fly.service;


import org.junit.Test;
import org.neuedu.fly.entity.vo.ResponseEntity;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/10/28-14:18
 **/
public class UserServiceTest {

    private UserService userService = new UserService();

    @Test
    public void checkedTel() {

        ResponseEntity entity = userService.checkedTel("13628357344");
        System.out.println(entity);

    }
}