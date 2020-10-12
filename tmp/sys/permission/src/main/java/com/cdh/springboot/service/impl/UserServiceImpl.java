package com.cdh.springboot.service.impl;

import com.cdh.springboot.entity.User;
import com.cdh.springboot.mapper.UserMapper;
import com.cdh.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2018-11-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
