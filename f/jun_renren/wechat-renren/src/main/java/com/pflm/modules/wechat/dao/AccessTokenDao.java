package com.pflm.modules.wechat.dao;
import com.pflm.base.entity.AccessTokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;



@Mapper
public interface AccessTokenDao {

    @Select("SELECT * FROM  `w_token` where type=#{type} order by update_time limit 1 ")
    AccessTokenEntity getToken(@Param("type") int type);
}
