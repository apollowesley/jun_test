package com.game.bizModule.human.handler;

import com.game.bizModule.human.bizServ.HumanServ;
import com.game.bizModule.human.msg.CGQueryHumanEntryList;
import com.game.gameServer.msg.AbstractCGMsgHandler;

/**
 * 获取角色入口列表
 *
 * @author hjj2019
 * @since 2015/7/11
 *
 */
public class Handler_CGQueryHumanEntryList extends AbstractCGMsgHandler<CGQueryHumanEntryList> {
    @Override
    public void handle(CGQueryHumanEntryList msgObj) {
        // 异步查询角色入口列表
        HumanServ.OBJ.asyncQueryHumanEntryList(
            this.getPlayer(),
            msgObj._serverName.getStrVal()
        );
    }
}
