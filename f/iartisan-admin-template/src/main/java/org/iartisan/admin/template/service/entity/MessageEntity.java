package org.iartisan.admin.template.service.entity;

import lombok.Data;

import java.util.Date;

/**
 * 消息实体
 *
 * @author King  2019-9-25
 */
@Data
public class MessageEntity {

    private String receiverId;

    private String receiverName;

    private String msgTitle;

    private Date createTime;

    
}
