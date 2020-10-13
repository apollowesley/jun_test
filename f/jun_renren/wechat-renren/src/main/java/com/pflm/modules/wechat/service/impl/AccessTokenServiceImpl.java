package com.pflm.modules.wechat.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.pflm.api.WeChatApi;
import com.pflm.base.entity.AccessTokenEntity;
import com.pflm.modules.wechat.dao.AccessTokenDao;
import com.pflm.modules.wechat.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author qinxuewu
 * @version 1.00
 * @time 15/11/2018下午 6:20
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {
    @Autowired
    AccessTokenDao accessTokenDao;

    @Autowired
    WeChatApi weChatApi;
    @Override
    public AccessTokenEntity getToken(int type) {

        return accessTokenDao.getToken(type);
    }


    @Override
    public String getToken() {
        JSONObject info=weChatApi.getToken();
        return info.containsKey("access_token")?info.getString("access_token"):"";
    }
}
