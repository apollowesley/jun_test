package com.game.bizModule.login.msg;

import com.game.bizModule.login.handler.Handler_GGAuthFinish;
import com.game.gameServer.framework.Player;
import com.game.gameServer.msg.AbstractGGMsgHandler;
import com.game.gameServer.msg.AbstractGGMsgObj;
import com.game.gameServer.msg.MsgTypeEnum;

/**
 * 登陆验证完成
 *
 * @author hjj2017
 * @since 2015/7/10
 *
 */
public class GGAuthFinish extends AbstractGGMsgObj {
    /** 玩家对象 */
    public Player _p = null;
    /** 登陆成功? */
    public boolean _success = false;

    @Override
    @SuppressWarnings("unchecked")
    protected AbstractGGMsgHandler<GGAuthFinish> newHandlerObj() {
        return new Handler_GGAuthFinish();
    }

    @Override
    public MsgTypeEnum getMsgType() {
        return MsgTypeEnum.login;
    }
}
