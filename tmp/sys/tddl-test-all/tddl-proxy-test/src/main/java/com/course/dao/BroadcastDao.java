package com.course.dao;

import com.course.model.Broadcast;
import com.course.model.BroadcastJoinResult;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Mycat on 2016/1/23.
 */
public interface BroadcastDao {

    public void insert(Broadcast broadcast);

    public Broadcast getBroadcastById(Long id);

    public void updateBroadcastById(Broadcast broadcast);

    public void deleteBroadcast(Long[] ids);

    public void deleteAllBroadcast();

    public BroadcastJoinResult broadcastJoin(Long sId);
}
