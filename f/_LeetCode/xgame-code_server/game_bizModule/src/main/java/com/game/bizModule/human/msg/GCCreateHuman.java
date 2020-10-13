package com.game.bizModule.human.msg;

import com.game.bizModule.global.AllMsgSerialUId;
import com.game.gameServer.msg.AbstractGCMsgObj;
import com.game.part.msg.type.MsgBool;
import com.game.part.msg.type.MsgStr;

/**
 * 创建角色 GC 消息
 *
 * @author hjj2017
 * @since 2015/7/12
 *
 */
public class GCCreateHuman extends AbstractGCMsgObj {
    /** 建角成功? */
    public MsgBool _success;
    /** 失败消息 */
    public MsgStr _errorMsg;

    /**
     * 类默认构造器
     *
     */
    public GCCreateHuman() {
    }

    /**
     * 类参数构造器
     *
     * @param success
     *
     */
    public GCCreateHuman(boolean success) {
        this._success = new MsgBool(success);
    }

    @Override
    public short getSerialUId() {
        return AllMsgSerialUId.GC_CREATE_HUMAN;
    }
}
