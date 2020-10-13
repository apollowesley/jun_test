package com.pflm.modules.wechat.service;
import com.pflm.base.entity.AccessTokenEntity;
import org.apache.ibatis.annotations.Param;
public interface AccessTokenService{

    AccessTokenEntity getToken(@Param("type") int type);
    String getToken();

}
