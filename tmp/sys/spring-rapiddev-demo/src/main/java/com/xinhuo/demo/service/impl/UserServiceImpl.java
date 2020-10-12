package com.xinhuo.demo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinhuo.demo.dao.UserMapper;
import com.xinhuo.demo.model.User;
import com.xinhuo.demo.service.UserService;
import org.springframework.stereotype.Service;


/**
 * author 张新林
 * 时间 2019/1/20 18:09
 * 描述
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
