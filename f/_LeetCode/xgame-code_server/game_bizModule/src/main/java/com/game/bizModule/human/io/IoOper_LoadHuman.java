package com.game.bizModule.human.io;

import java.text.MessageFormat;

import com.game.bizModule.human.Human;
import com.game.bizModule.human.HumanLog;
import com.game.bizModule.human.entity.HumanEntity;
import com.game.bizModule.human.event.HumanEvent;
import com.game.bizModule.human.msg.GGLoadHumanFinish;
import com.game.gameServer.framework.Player;
import com.game.gameServer.io.AbstractPlayerOrSceneIoOper;
import com.game.part.dao.CommDao;

/**
 * 异步方式进入角色
 *
 * @author hjj2017
 * @since 2015/7/22
 *
 */
public class IoOper_LoadHuman extends AbstractPlayerOrSceneIoOper {
    /** 玩家 */
    public Player _p = null;
    /** 角色 UId */
    public long _humanUId;

    @Override
    public long getBindUId() {
        return this._humanUId;
    }

    @Override
    public boolean doIo() {
        if (this._p == null ||
            this._humanUId <= 0) {
            // 如果参数对象为空,
            // 则直接退出!
            return false;
        }

        HumanLog.LOG.info(MessageFormat.format(
            "加载角色数据 - 开始, hUId = {0}",
            String.valueOf(this._humanUId)
        ));

        // 获取角色实体
        final HumanEntity he = CommDao.OBJ.find(HumanEntity.class, this._humanUId);

        if (he == null) {
            // 如果角色实体为空,
            // 则直接退出!
            HumanLog.LOG.error(MessageFormat.format(
                "角色实体为空, platformUIdStr = {0}, humanUId = {1}",
                this._p._platformUIdStr,
                String.valueOf(this._humanUId)
            ));
            return false;
        }

        // 创建角色
        Human h = Human.create(he);
        // 触发加载数据事件
        HumanEvent.OBJ.fireLoadDbEvent(this._p, h);

        HumanLog.LOG.info(MessageFormat.format(
            "加载角色数据 - 结束, hUId = {0}",
            String.valueOf(this._humanUId)
        ));

        // 创建加载完成消息
        GGLoadHumanFinish ggMSG = new GGLoadHumanFinish();
        ggMSG._p = this._p;
        ggMSG._h = h;
        ggMSG._finish = true;
        // 分派消息
        this.msgDispatch(ggMSG);

        return true;
    }
}
