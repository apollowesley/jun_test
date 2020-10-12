package com.dx.messageboard.biz;

import com.dx.messageboard.common.ServerResponse;
import com.dx.messageboard.dto.MessageAddReqDto;
import com.dx.messageboard.dto.PageSearchReqDto;
import com.dx.messageboard.mapper.MessageMapper;
import com.dx.messageboard.vo.Message;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 留言实现类
 * Create by zhoushiyu
 */
@Component
public class MessageImpl {

    @Autowired
    MessageMapper messageMapper;

    /**
     * 添加留言的方法
     * @param messageAddReqDto
     * @return
     */
    public ServerResponse<String> addMessage(MessageAddReqDto messageAddReqDto) {
        String title = messageAddReqDto.getTitle();
        String context = messageAddReqDto.getContext();
        if(!StringUtils.isNotBlank(title) || !StringUtils.isNotBlank(context)) {
            return ServerResponse.createByErrorMessage("留言标题或内容不能为空");
        }
        Message message = new Message();
        message.setMessageTitle(title);
        message.setMessageContext(context);
        message.setUserId(messageAddReqDto.getUserId());
        message.setUserName(messageAddReqDto.getUserName());
        message.setParentId(messageAddReqDto.getParentId());
        int resultCount = messageMapper.insert(message);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("留言失败");
        }
        return ServerResponse.createBySuccess("留言成功");
    }

    /**
     * 分页查询
     * @param pageSearchReqDto
     * @return
     */
    public ServerResponse<PageInfo<Message>> searchMessageAll(PageSearchReqDto pageSearchReqDto) {
        int currentPage = pageSearchReqDto.getCurrentPage();
        int pageSize = pageSearchReqDto.getPageSize();
        //PageHelper 分页
        PageHelper.startPage(currentPage, pageSize);
        List<Message> messageRoots = messageMapper.searchAllRootMessage();
        PageInfo<Message> pageInfo = new PageInfo<>(messageRoots);

        //添加回复
        // 回复分组
        List<Message> messageLists = messageMapper.searchChildMessage();
        Map map = new HashMap<Integer, List<Message>>();
        for(Message message: messageLists) {
            int parentId = message.getParentId();
            if(map.containsKey(parentId)) {
                List<Message> msg = (ObjectList) map.get(parentId);
                msg.add(message);
            } else {
                List<Message> addLists = new ArrayList<>();
                addLists.add(message);
                map.put(parentId, addLists);
            }
        }

        //用一个map将message与id进行映射
        Map<Integer, Message> messageMap = new HashMap<>();
        for(Message message : messageRoots) {
            messageMap.put(message.getMessageId(), message);
        }

        //遍历map
        Iterator<Map.Entry<Integer, List<Message>>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<Integer, List<Message>> entry = iterator.next();
            int pId = entry.getKey();
            if(messageMap.containsKey(pId)) {
                Message msg= messageMap.get(pId);
                msg.setChildMessages(entry.getValue());
            }
        }
        return ServerResponse.createBySuccess("查询成功", pageInfo);
    }
}
