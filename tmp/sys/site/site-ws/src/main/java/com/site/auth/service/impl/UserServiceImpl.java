package com.site.auth.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.site.auth.mapper.UserMapper;
import com.site.auth.model.User;
import com.site.auth.service.UserService;
import com.site.auth.util.UserEncodes;
import com.site.common.constants.Constants;
import com.site.common.exception.BusinessException;
import com.site.util.DateUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(List<User> list) {
        if (CollectionUtils.isEmpty(list))
            throw new BusinessException("user.add.fail", "用户新增失败");

        for (User user : list) {

            try {

                if (user == null) {
                    throw new BusinessException("user.registr.error", "注册信息错误");
                }

                if (StringUtils.isBlank(user.getLoginName()) || StringUtils.isBlank(user.getPassword())) {
                    throw new BusinessException("user.registr.error", "注册信息错误");
                }

                UserEncodes.entryptPassword(user);
                user.setIsDel(Constants.IS_DEL_N);
                user.setCreateTime(DateUtil.getSystemDateTime());
                userMapper.insert(user);
            } catch (Exception e) {
                log.error("#新增用户失败,error message=[{}]", e.getMessage());
            }
        }
    }

}
