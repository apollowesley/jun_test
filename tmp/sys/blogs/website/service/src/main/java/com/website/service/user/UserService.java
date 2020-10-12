package com.website.service.user;

import com.website.common.utils.Result;
import com.website.model.user.User;

/**
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/5/18
 * @Version: 1.0
 */
public interface UserService {
    Result userRegister(User user);

    Result userLogin(User user);
}
