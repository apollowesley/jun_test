package com.bodsite.demo.dao;

import com.bodsite.demo.entity.Demo;

public interface DemoMapper {
    int deleteByPrimaryKey(Integer demoId);

    int insert(Demo record);

    int insertSelective(Demo record);

    Demo selectByPrimaryKey(Integer demoId);

    int updateByPrimaryKeySelective(Demo record);

    int updateByPrimaryKey(Demo record);
}