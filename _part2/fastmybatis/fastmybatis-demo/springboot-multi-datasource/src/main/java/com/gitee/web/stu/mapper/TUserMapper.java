package com.gitee.web.stu.mapper;

import com.gitee.fastmybatis.core.mapper.CrudMapper;
import com.gitee.web.stu.entity.TUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface TUserMapper extends CrudMapper<TUser, Integer> {

    // 自定义sql
    @Update("update t_user set username = #{username} where id = #{id}")
    int updateById(@Param("id") int id, @Param("username") String username);

    TUser selectByName(@Param("username") String username);

}