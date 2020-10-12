package com.dx.messageboard.service;

import com.dx.messageboard.biz.MessageImpl;
import com.dx.messageboard.common.ServerResponse;
import com.dx.messageboard.dto.MessageAddReqDto;
import com.dx.messageboard.dto.PageSearchReqDto;
import com.dx.messageboard.vo.Message;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 留言接口实现
 * Create by zhoushiyu
 */
@RestController
public class MessageService implements IMessage{

    @Autowired
    MessageImpl messageImpl;

    @Override
    public ServerResponse<String> addMessage(MessageAddReqDto messageAddReqDto) {
        ServerResponse<String> response = messageImpl.addMessage(messageAddReqDto);
        return response;
    }

    @Override
    public ServerResponse<PageInfo<Message>> searchMessage(PageSearchReqDto pageSearchReqDto) {
        ServerResponse<PageInfo<Message>> response = messageImpl.searchMessageAll(pageSearchReqDto);
        return response;
    }
}
