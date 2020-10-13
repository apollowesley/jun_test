package com.game.bizModule.human.msg;

import com.game.bizModule.human.Human;
import com.game.bizModule.human.handler.Handler_GGLoadHumanFinish;
import com.game.gameServer.framework.Player;
import com.game.gameServer.msg.AbstractGGMsgHandler;
import com.game.gameServer.msg.AbstractGGMsgObj;
import com.game.gameServer.msg.MsgTypeEnum;

/**
 * 加载角色完成
 *
 * @author hjj2017
 * @since 2015/7/12
 *
 */
public class GGLoadHumanFinish extends AbstractGGMsgObj {
    /** 玩家 */
    public Player _p = null;
    /** 角色 */
    public Human _h = null;
    /** 加载完成? */
    public boolean _finish = false;

    @Override
    @SuppressWarnings("unchecked")
    protected AbstractGGMsgHandler<GGLoadHumanFinish> newHandlerObj() {
        return new Handler_GGLoadHumanFinish();
    }

    @Override
    public MsgTypeEnum getMsgType() {
        return MsgTypeEnum.login;
    }
}
