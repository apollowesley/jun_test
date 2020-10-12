package com.jfast.common.model.online;


import com.jfast.common.model.UserSession;
import com.jfast.common.utils.Md5Utils;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/7 14:51
 */
@Setter
public class OnlineUser {

    private String sessionId;
    private UserSession userSession;
    private final static String SESSION_ID_KEY = "session_id_key";
    private final Map<String, Object> session = new HashMap<String, Object>();

    public OnlineUser(UserSession userSession) {
        this.userSession = userSession;
        this.sessionId = Md5Utils.generatorKey();
        setSessionAttr(SESSION_ID_KEY, sessionId);
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void removeSessionAttr(String key) {
        session.remove(key);
    }

    public void setSessionAttr(String key, Object code) {
        session.put(key, code);
    }

    public Object getSessionAttr(String key) {
        return session.get(key);
    }

    public Integer getUserId() {
        return userSession.getUserId();
    }
}
