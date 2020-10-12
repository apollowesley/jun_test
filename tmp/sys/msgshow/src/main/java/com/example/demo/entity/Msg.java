package com.example.demo.entity;

import java.util.List;

public class Msg {
    private String msguuid;//主贴id
    private String msgusername;//主贴所属人
    private String msguserpicture;//主贴人的头像
    private String msgdate;//主贴发表日期
    private String msgcontent;//主贴内容
    private String msgmainid;//跟帖的信息

    public String getMsguuid() {
        return msguuid;
    }

    public void setMsguuid(String msguuid) {
        this.msguuid = msguuid;
    }

    public String getMsgusername() {
        return msgusername;
    }

    public void setMsgusername(String msgusername) {
        this.msgusername = msgusername;
    }

    public String getMsguserpicture() {
        return msguserpicture;
    }

    public void setMsguserpicture(String msguserpicture) {
        this.msguserpicture = msguserpicture;
    }

    public String getMsgdate() {
        return msgdate;
    }

    public void setMsgdate(String msgdate) {
        this.msgdate = msgdate;
    }

    public String getMsgcontent() {
        return msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }

    public String getMsgmainid() {
        return msgmainid;
    }

    public void setMsgmainid(String msgmainid) {
        this.msgmainid = msgmainid;
    }
}
