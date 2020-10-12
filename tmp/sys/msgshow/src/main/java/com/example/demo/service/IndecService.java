package com.example.demo.service;

import com.example.demo.entity.Msg;
import com.example.demo.mapper.IndecMapper;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndecService {

    @Autowired
    private IndecMapper indecMapper;

    public void insertData(String uuid, String username, String userpicture, String msgdate, String content, String msgmainid) {
        indecMapper.insertData(uuid,username,userpicture,msgdate,content,msgmainid);
    }

    public List<Msg> findCount() {
        return indecMapper.findCount();
    }

    public void deleteMsg(String msguuid) {
        indecMapper.deleteMsg(msguuid);
    }

    public Page<Msg> getListMsg(){
        return indecMapper.getListMsg();
    }
}
