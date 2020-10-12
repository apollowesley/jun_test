package com.dx.messageboard.service;

import com.dx.messageboard.common.ServerResponse;
import com.dx.messageboard.dto.MessageAddReqDto;

import com.dx.messageboard.dto.PageSearchReqDto;
import com.dx.messageboard.vo.Message;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 留言接口
 * Create by zhoushiyu
 */
@RequestMapping("/message")
public interface IMessage {

    @RequestMapping(value = "/addMessage",method = RequestMethod.POST)
    @ResponseBody
    ServerResponse<String> addMessage(@RequestBody MessageAddReqDto messageReqDto);


    @RequestMapping(value = "/searchMessage",method = RequestMethod.POST)
    @ResponseBody
    ServerResponse<PageInfo<Message>> searchMessage(@RequestBody PageSearchReqDto pageSearchReqDto);
}
