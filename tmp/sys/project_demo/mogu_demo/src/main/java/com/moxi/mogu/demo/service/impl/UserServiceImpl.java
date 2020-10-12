package com.moxi.mogu.demo.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moxi.mogu.demo.entity.User;
import com.moxi.mogu.demo.mapper.UserMapper;
import com.moxi.mogu.demo.service.UserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-09-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
