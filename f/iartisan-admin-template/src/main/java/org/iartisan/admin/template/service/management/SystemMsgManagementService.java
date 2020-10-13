package org.iartisan.admin.template.service.management;

import org.iartisan.admin.template.dao.mapper.SystemMessageMapper;
import org.iartisan.admin.template.dao.model.SystemMessageDO;
import org.iartisan.admin.template.service.entity.MessageEntity;
import org.iartisan.runtime.support.BaseManagementServiceSupport;
import org.springframework.stereotype.Service;

/**
 * 消息服务
 *
 * @author King  2019-9-25
 */
@Service
public class SystemMsgManagementService extends BaseManagementServiceSupport<SystemMessageMapper, MessageEntity> {


    public void addMsgEntity(MessageEntity entity) {
        SystemMessageDO dbInsert = new SystemMessageDO();
        dbInsert.setReceiverId(entity.getReceiverId());
        dbInsert.setReceiverName(entity.getReceiverName());
        this.baseMapper.insert(dbInsert);
    }

}
