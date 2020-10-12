package org.neuedu.fly.service;


import org.apache.ibatis.session.SqlSession;
import org.neuedu.fly.entity.User;
import org.neuedu.fly.entity.vo.ResponseEntity;
import org.neuedu.fly.mapper.UserMapper;
import org.neuedu.fly.util.SqlSessionUtil;
import org.neuedu.fly.util.SysUtil;

import java.util.Objects;

/**
 * @description 业务逻辑处理
 * @auther: CDHONG.IT
 * @date: 2019/10/26-11:03
 **/
public class UserService {

    private SqlSession session = null;
    private UserMapper userMapper = null;

    public ResponseEntity checkedTel(String tel){
        session = SqlSessionUtil.getSession();
        userMapper = session.getMapper(UserMapper.class);
        boolean flg = userMapper.isUniqueTel(tel);
        if(flg){
            return ResponseEntity.error("该手机号已经注册过了");
        }
        return ResponseEntity.ok();
    }



    public ResponseEntity register(User user){
        boolean flg = false;
        try {
            session = SqlSessionUtil.getSession();
            userMapper = session.getMapper(UserMapper.class);
            flg = userMapper.register(user);
            //一定注意要提交
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        if(flg){
            return ResponseEntity.ok("注册成功！");
        }
        return ResponseEntity.error("注册失败！");
    }


    public ResponseEntity login(String tel, String pwd) {
        session = SqlSessionUtil.getSession();
        userMapper = session.getMapper(UserMapper.class);
        User user = userMapper.login(tel, pwd);
        //用户名或密码是否输入正确
        if(Objects.isNull(user)){
            return ResponseEntity.error("用户名或密码输入有误！");
        }
        //是否被禁用
        if(user.getUserStatus() == SysUtil.UserStatus.DISABELD){
            return ResponseEntity.error("你已被禁用，请联系管理员!");
        }
        return ResponseEntity.data(user);
    }
}
