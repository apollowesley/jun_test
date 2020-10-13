package com.cn.zjut.b2c.dao;

import com.cn.zjut.b2c.pojo.ResponseMessage;

public interface ResponseMessageDao {

    int deleteByPrimaryKey(Integer id);

    int insert(ResponseMessage record);

    int insertSelective(ResponseMessage record);

    ResponseMessage selectByPrimaryKey(Integer id);
    
    ResponseMessage selectByOrderId(String orderId);

    int updateByPrimaryKeySelective(ResponseMessage record);

    int updateByPrimaryKey(ResponseMessage record);
    
}
