package com.website.service.user.impl;

import com.website.common.utils.DateUtils;
import com.website.common.utils.Result;
import com.website.common.utils.StatusCode;
import com.website.model.user.User;
import com.website.model.user.UserDao;
import com.website.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 *
 * @Author: xiaokai
 * @Description: web用户登录注册业务逻辑
 * @Date: 2019/5/18
 * @Version: 1.0
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;




    @Override
    public Result userRegister(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String phone = (String) redisTemplate.boundValueOps(user.getUsername()).get();
        String code = (String) redisTemplate.boundValueOps(user.getCode()).get();
        if (StringUtils.isEmpty(code)){
            return new Result(false, StatusCode.ERROR,"验证码过期，请重新获取验证码!");
        }else{
            if (code.equals(user.getCode())) {
                if (userDao.findUser(user.getUsername()) != null) {
                    return new Result(false, StatusCode.ERROR, "用户:" + user.getUsername() + "已存在!");
                }
                if (!user.getUsername().equals(phone)) {
                    return new Result(false, StatusCode.ERROR, "用户名不正确,请输入正确的用户名!");
                }
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                user.setRegisterTime(DateUtils.getFormatDate(new Date()));
                userDao.userRegist(user);

                redisTemplate.delete(phone);
                redisTemplate.delete(code);
                return new Result(true, StatusCode.SUCCESS, "恭喜您注册成功,即将跳转去登录!");
            } else {

                return new Result(false, StatusCode.ERROR,  "您输入的验证码不正确!");
            }
        }
    }

    @Override
    public Result userLogin(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        try {
            //根据用户输入信息去数据库搜索
            User loginUser = userDao.findUser(user.getUsername());
            if (loginUser == null) {
                //如果数据库没查询到用户,则返回用户输入的用户名
                System.out.println(user.getUsername() + "用户不存在");
                return new Result(false, StatusCode.ERROR, user.getUsername() + "不存在,请先注册再登录!");
            }
            //如果登录输入的密码跟数据库的密码不匹配,则返回密码错误
            if (!bCryptPasswordEncoder.matches(user.getPassword(),loginUser.getPassword())) {
                return new Result(false, StatusCode.ERROR, "密码不正确,请输入正确的密码!");
            }
            //校验成功
            return new Result(true, StatusCode.SUCCESS, "登录成功!");
        } catch (Exception e) {
            e.printStackTrace();
            //如果发生异常,则给用户友好提示
            return new Result(false, StatusCode.ERROR, "服务器繁忙,登录失败!");
        }
    }
}
