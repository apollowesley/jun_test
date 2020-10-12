package com.example.demo.mapper;

import com.example.demo.entity.Msg;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface  IndecMapper {
    @Insert("insert into msgnote values (#{msguuid},#{msgusername},#{msguserpicture},#{msgdate},#{msgcontent},#{msgmainid})")
    public void insertData(@Param("msguuid") String uuuid, @Param("msgusername")String username, @Param("msguserpicture")String userpicture, @Param("msgdate")String msgdate, @Param("msgcontent")String content, @Param("msgmainid")String replysid);

    @Select("select * from msgnote")
    List<Msg> findCount();

    @Delete("delete from msgnote where msguuid=#{msguuid}")
    void deleteMsg(@Param("msguuid") String msguuid);

    //分页实现查询
    @Select("select * from msgnote")
    Page<Msg> getListMsg();
}
