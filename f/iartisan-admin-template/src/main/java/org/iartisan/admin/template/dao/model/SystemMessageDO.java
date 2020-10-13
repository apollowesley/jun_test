package org.iartisan.admin.template.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * system_message 表模型
 *
 * @author King
 */
@TableName(value = "system_message")
public class SystemMessageDO {

    /**
     * 列名: uuid
     * 备注: 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 列名: receiver_id
     * 备注: 接收者id
     */
    @TableField("receiver_id")
    private String receiverId;

    /**
     * 列名: receiver_name
     * 备注: 接收者name
     */
    @TableField("receiver_name")
    private String receiverName;

    /**
     * 列名: sender_id
     * 备注: 发送者id
     */
    @TableField("sender_id")
    private String senderId;

    /**
     * 列名: sender_name
     * 备注: 发送者name
     */
    @TableField("sender_name")
    private String senderName;

    /**
     * 列名: msg_type_id
     * 备注: 消息类型
     */
    @TableField("msg_type_id")
    private String msgTypeId;

    /**
     * 列名: msg_type_desc
     * 备注: 消息类型说明
     */
    @TableField("msg_type_desc")
    private String msgTypeDesc;

    /**
     * 列名: msg_title
     * 备注: 消息标题
     */
    @TableField("msg_title")
    private String msgTitle;

    /**
     * 列名: msg_content
     * 备注: 消息内容
     */
    @TableField("msg_content")
    private String msgContent;

    /**
     * 列名: msg_param
     * 备注: 消息参数
     */
    @TableField("msg_param")
    private String msgParam;

    /**
     * 列名: is_read
     * 备注: 是否已读
     */
    @TableField("is_read")
    private String isRead;

    /**
     * 列名: status
     * 备注: 数据状态
     */
    @TableField("status")
    private String status;

    /**
     * 列名: create_time
     * 备注: 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 列名: update_time
     * 备注: 更新时间
     */
    @TableField("update_time")
    private Date updateTime;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMsgTypeId() {
        return msgTypeId;
    }

    public void setMsgTypeId(String msgTypeId) {
        this.msgTypeId = msgTypeId;
    }

    public String getMsgTypeDesc() {
        return msgTypeDesc;
    }

    public void setMsgTypeDesc(String msgTypeDesc) {
        this.msgTypeDesc = msgTypeDesc;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgParam() {
        return msgParam;
    }

    public void setMsgParam(String msgParam) {
        this.msgParam = msgParam;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}