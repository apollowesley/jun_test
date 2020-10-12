package com.jfast.common.model.online;

import com.jfast.common.utils.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/7 14:52
 */
public final class OnlineUserManager {

    private static final Map<String, OnlineUser> onlineMap = new HashMap<>();

    private static final OnlineUserManager onlineUserManager = new OnlineUserManager();

    private OnlineUserManager() {

    }

    public static OnlineUserManager getOnlineUserManager() {
        return onlineUserManager;
    }

    public void addOnlineUser(OnlineUser onlineUser) {
        onlineMap.put(onlineUser.getSessionId(), onlineUser);
    }

    public void removeOnlineUser(String sessionId) {
        onlineMap.remove(sessionId);
    }

    public OnlineUser getBySessionId(String sessionId) {
        if(ObjectUtils.isEmpty(sessionId)) {
            return null;
        }
        return onlineMap.get(sessionId);
    }

    public OnlineUser getByUserId(Integer userId) {
        for (Map.Entry<String, OnlineUser> e : onlineMap.entrySet()) {
            OnlineUser onlineUser = e.getValue();
            if (onlineUser.getUserId() == userId) {
                return onlineUser;
            }
        }
        return null;
    }
}
