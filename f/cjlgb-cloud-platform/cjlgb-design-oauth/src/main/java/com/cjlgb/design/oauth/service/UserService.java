package com.cjlgb.design.oauth.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cjlgb.design.common.mybatis.service.BaseService;
import com.cjlgb.design.common.oauth.entity.OauthUser;

/**
 * @author WFT
 * @date 2020/5/9
 * description:
 */
public interface UserService extends BaseService<OauthUser> {

    /**
     * 根据手机号码获取用户信息
     * @param mobile 手机号码
     * @return com.cjlgb.design.common.oauth.entity.OauthUser
     */
    default OauthUser selectByMobile(String mobile){
        return this.getOne(Wrappers.<OauthUser>lambdaQuery().eq(OauthUser::getUserMobile,mobile));
    }

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return com.cjlgb.design.common.oauth.entity.OauthUser
     */
    default OauthUser selectByUsername(String username){
        return this.getOne(Wrappers.<OauthUser>lambdaQuery().eq(OauthUser::getUsername,username));
    }

}
