package com.pflm.modules.wechat.dao;
import com.pflm.modules.wechat.entity.WxMenuEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WxMenuDao {


    @Select("select * from w_menu limit 1")
    WxMenuEntity get();

    @Insert("insert into w_menu(name,create_time) values(#{menu.name},#{menu.createTime}) ")
    void save(@Param("menu") WxMenuEntity menu);


}
